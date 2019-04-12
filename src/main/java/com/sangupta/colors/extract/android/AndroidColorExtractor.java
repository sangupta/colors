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

import com.sangupta.colors.Swatch;
import com.sangupta.colors.model.RGB;

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

	private final List<PaletteSwatch> swatches;

	private final Bitmap bitmapImage;

	private final List<Target> targets = new ArrayList<Target>();

	private int maxColors = DEFAULT_CALCULATE_NUMBER_COLORS;

	private int resizeArea = DEFAULT_RESIZE_BITMAP_AREA;

	private int resizeMaxDimension = -1;

	private final List<PaletteFilter> filters = new ArrayList<PaletteFilter>();

	/**
	 * 
	 * @param bitmap
	 */
	public AndroidColorExtractor(Bitmap bitmap) {
		if (bitmap == null) {
			throw new IllegalArgumentException("Bitmap is not valid");
		}

		this.filters.add(PaletteFilter.DEFAULT_FILTER);

		this.bitmapImage = bitmap;
		this.swatches = null;

		// Add the default targets
		this.targets.add(Target.LIGHT_VIBRANT);
		this.targets.add(Target.VIBRANT);
		this.targets.add(Target.DARK_VIBRANT);
		this.targets.add(Target.LIGHT_MUTED);
		this.targets.add(Target.MUTED);
		this.targets.add(Target.DARK_MUTED);
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
		this.maxColors = colors;
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
		this.resizeArea = area;
		this.resizeMaxDimension = -1;
		return this;
	}

	/**
	 * Clear all added filters. This includes any default filters added
	 * automatically by {@link Palette}.
	 */
	public AndroidColorExtractor clearFilters() {
		this.filters.clear();
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
		if (!this.targets.contains(target)) {
			this.targets.add(target);
		}
		return this;
	}

	/**
	 * Clear all added targets. This includes any default targets added
	 * automatically by {@link Palette}.
	 */
	public AndroidColorExtractor clearTargets() {
		if (this.targets != null) {
			this.targets.clear();
		}
		return this;
	}

	/**
	 * Generate and return the {@link Palette} synchronously.
	 */
	public Palette generate() {
		List<PaletteSwatch> swatches;

		if (this.bitmapImage != null) {
			// We have a Bitmap so we need to use quantization to reduce the number of
			// colors

			// First we'll scale down the bitmap if needed
			final Bitmap bitmap = scaleBitmapDown(this.bitmapImage);

			// Now generate a quantizer from the Bitmap
			final ColorCutQuantizer quantizer = new ColorCutQuantizer(getPixelsFromBitmap(bitmap), this.maxColors,
					this.filters.isEmpty() ? null : this.filters.toArray(new PaletteFilter[this.filters.size()]));

			swatches = quantizer.getQuantizedColors();

		} else {
			// Else we're using the provided swatches
			swatches = this.swatches;
		}

		// Now create a Palette instance
		final Palette palette = new Palette(swatches, this.targets);
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

		if (this.resizeArea > 0) {
			final int bitmapArea = bitmap.getWidth() * bitmap.getHeight();
			if (bitmapArea > this.resizeArea) {
				scaleRatio = Math.sqrt(this.resizeArea / (double) bitmapArea);
			}
		} else if (this.resizeMaxDimension > 0) {
			final int maxDimension = Math.max(bitmap.getWidth(), bitmap.getHeight());
			if (maxDimension > this.resizeMaxDimension) {
				scaleRatio = this.resizeMaxDimension / (double) maxDimension;
			}
		}

		if (scaleRatio <= 0) {
			// Scaling has been disabled or not needed so just return the Bitmap
			return bitmap;
		}

		return Bitmap.createScaledBitmap(bitmap, (int) Math.ceil(bitmap.getWidth() * scaleRatio),
				(int) Math.ceil(bitmap.getHeight() * scaleRatio));
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
		if (image == null) {
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

	/**
	 * Get a {@link Swatch} of all dominant colors.
	 * 
	 * @param image the {@link BufferedImage} to use
	 * 
	 * @return
	 */
	public static Swatch<RGB> getSwatch(BufferedImage image) {
		if (image == null) {
			throw new IllegalArgumentException("Image cannot be null");
		}

		AndroidColorExtractor extractor = new AndroidColorExtractor(new Bitmap(image));
		Palette palette = extractor.generate();

		Swatch<RGB> swatch = new Swatch<>();
		addToSwatch(swatch, palette.getVibrantColor());

		return swatch;
	}

	/**
	 * Convert android swatch color to {@link RGB} color and add it to the
	 * {@link Swatch}.
	 * 
	 * @param swatch
	 * @param color
	 */
	private static void addToSwatch(Swatch<RGB> swatch, int color) {
		if (color < 0) {
			return;
		}

		swatch.add(new RGB(color));
	}
}
