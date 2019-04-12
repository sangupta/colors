/**
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sangupta.colors.extract.android;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.sangupta.colors.extract.android.Bitmap;
import com.sangupta.colors.extract.android.ColorCutQuantizer;
import com.sangupta.colors.extract.android.Palette;
import com.sangupta.colors.extract.android.Target;

/**
 * This class is based on the Android source code to extract colors from a given
 * image. The android class documentation can be accessed at
 * https://developer.android.com/reference/android/support/v7/graphics/Palette.html
 * 
 * Also, the source code is available at
 * https://android.googlesource.com/platform/frameworks/support/+/b14fc7c/v7/palette/src/android/support/v7/graphics/Palette.java
 * 
 * @author sangupta
 * @since 1.0.0
 */
public class AndroidColorExtractor {

	static final int DEFAULT_RESIZE_BITMAP_AREA = 112 * 112;
	
	static final int DEFAULT_CALCULATE_NUMBER_COLORS = 16;

	private final List<PaletteSwatch> mSwatches;

	private final Bitmap mBitmap;

	private final List<Target> targets = new ArrayList<Target>();

	private int maxColors = DEFAULT_CALCULATE_NUMBER_COLORS;

	private int resizeArea = DEFAULT_RESIZE_BITMAP_AREA;

	private int resizeMaxDimension = -1;

	private final List<PaletteFilter> filters = new ArrayList<PaletteFilter>();

	/**
	 * Construct a new {@link Builder} using a source {@link Bitmap}
	 */
	public AndroidColorExtractor(Bitmap bitmap) {
		if (bitmap == null) {
			throw new IllegalArgumentException("Bitmap is not valid");
		}
		filters.add(PaletteFilter.DEFAULT_FILTER);
		mBitmap = bitmap;
		mSwatches = null;

		// Add the default targets
		targets.add(Target.LIGHT_VIBRANT);
		targets.add(Target.VIBRANT);
		targets.add(Target.DARK_VIBRANT);
		targets.add(Target.LIGHT_MUTED);
		targets.add(Target.MUTED);
		targets.add(Target.DARK_MUTED);
	}

	/**
	 * Set the maximum number of colors to use in the quantization step when using a
	 * {@link Bitmap} as the source.
	 * <p>
	 * Good values for depend on the source image type. For landscapes, good values
	 * are in the range 10-16. For images which are largely made up of people's
	 * faces then this value should be increased to ~24.
	 */
	public AndroidColorExtractor maximumColorCount(int colors) {
		maxColors = colors;
		return this;
	}

	/**
	 * Set the resize value when using a {@link Bitmap} as the source. If the
	 * bitmap's area is greater than the value specified, then the bitmap will be
	 * resized so that it's area matches {@code area}. If the bitmap is smaller or
	 * equal, the original is used as-is.
	 * <p>
	 * This value has a large effect on the processing time. The larger the resized
	 * image is, the greater time it will take to generate the palette. The smaller
	 * the image is, the more detail is lost in the resulting image and thus less
	 * precision for color selection.
	 *
	 * @param area the number of pixels that the intermediary scaled down Bitmap
	 *             should cover, or any value &lt;= 0 to disable resizing.
	 */
	public AndroidColorExtractor resizeBitmapArea(final int area) {
		resizeArea = area;
		resizeMaxDimension = -1;
		return this;
	}

	/**
	 * Clear all added filters. This includes any default filters added
	 * automatically by {@link Palette}.
	 */
	public AndroidColorExtractor clearFilters() {
		filters.clear();
		return this;
	}

	/**
	 * Add a filter to be able to have fine grained control over which colors are
	 * allowed in the resulting palette.
	 *
	 * @param filter filter to add.
	 */
	public AndroidColorExtractor addFilter(PaletteFilter filter) {
		if (filter != null) {
			filters.add(filter);
		}
		return this;
	}

	/**
	 * Add a target profile to be generated in the palette.
	 *
	 * <p>
	 * You can retrieve the result via {@link Palette#getSwatchForTarget(Target)}.
	 * </p>
	 */
	public AndroidColorExtractor addTarget(final Target target) {
		if (!targets.contains(target)) {
			targets.add(target);
		}
		return this;
	}

	/**
	 * Clear all added targets. This includes any default targets added
	 * automatically by {@link Palette}.
	 */
	public AndroidColorExtractor clearTargets() {
		if (targets != null) {
			targets.clear();
		}
		return this;
	}

	/**
	 * Generate and return the {@link Palette} synchronously.
	 */
	public Palette generate() {
		List<PaletteSwatch> swatches;

		if (mBitmap != null) {
			// We have a Bitmap so we need to use quantization to reduce the number of
			// colors

			// First we'll scale down the bitmap if needed
			final Bitmap bitmap = scaleBitmapDown(mBitmap);

			// Now generate a quantizer from the Bitmap
			final ColorCutQuantizer quantizer = new ColorCutQuantizer(getPixelsFromBitmap(bitmap), maxColors,
					filters.isEmpty() ? null : filters.toArray(new PaletteFilter[filters.size()]));

			swatches = quantizer.getQuantizedColors();

		} else {
			// Else we're using the provided swatches
			swatches = mSwatches;
		}

		// Now create a Palette instance
		final Palette palette = new Palette(swatches, targets);
		// And make it generate itself
		palette.generate();

		return palette;
	}

	private int[] getPixelsFromBitmap(Bitmap bitmap) {
		return bitmap.getPixels();
	}

	/**
	 * Scale the bitmap down as needed.
	 */
	private Bitmap scaleBitmapDown(final Bitmap bitmap) {
		double scaleRatio = -1;

		if (resizeArea > 0) {
			final int bitmapArea = bitmap.getWidth() * bitmap.getHeight();
			if (bitmapArea > resizeArea) {
				scaleRatio = Math.sqrt(resizeArea / (double) bitmapArea);
			}
		} else if (resizeMaxDimension > 0) {
			final int maxDimension = Math.max(bitmap.getWidth(), bitmap.getHeight());
			if (maxDimension > resizeMaxDimension) {
				scaleRatio = resizeMaxDimension / (double) maxDimension;
			}
		}

		if (scaleRatio <= 0) {
			// Scaling has been disabled or not needed so just return the Bitmap
			return bitmap;
		}

		return Bitmap.createScaledBitmap(bitmap, (int) Math.ceil(bitmap.getWidth() * scaleRatio), (int) Math.ceil(bitmap.getHeight() * scaleRatio));
	}

	/**
	 * Returns a Color from a BufferedImage
	 * 
	 * @param image        An image to try to get a vibrant color from
	 * 
	 * @param defaultColor A color to return if we can't find a Vibrant or Muted
	 *                     Color
	 * 
	 * @return A Color Hex String
	 */
	public static int getColor(BufferedImage image) {
		if(image == null) {
			throw new IllegalArgumentException("Image cannot be null");
		}
		
		AndroidColorExtractor extractor = new AndroidColorExtractor(new Bitmap(image));
		Palette palette = extractor.generate();
		PaletteSwatch swatch = palette.getVibrantSwatch();

		if (swatch == null) {
			swatch = palette.getMutedSwatch();
		}

		if (swatch == null) {
			return -1;
		}

		int color = swatch.getRgb();
		return color;
	}
	
}
