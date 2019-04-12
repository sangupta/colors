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

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * A helper class to extract prominent colors from an image.
 * <p>
 * A number of colors with different profiles are extracted from the image:
 * <ul>
 * <li>Vibrant</li>
 * <li>Vibrant Dark</li>
 * <li>Vibrant Light</li>
 * <li>Muted</li>
 * <li>Muted Dark</li>
 * <li>Muted Light</li>
 * </ul>
 * These can be retrieved from the appropriate getter method.
 *
 * <p>
 * Instances are created with a {@link Builder} which supports several options
 * to tweak the generated Palette. See that class' documentation for more
 * information.
 * <p>
 * Generation should always be completed on a background thread, ideally the one
 * in which you load your image on. {@link Builder} supports both synchronous
 * and asynchronous generation:
 *
 * <pre>
 * // Synchronous
 * Palette p = Palette.from(bitmap).generate();
 *
 * // Asynchronous
 * Palette.from(bitmap).generate(new PaletteAsyncListener() {
 * 	public void onGenerated(Palette p) {
 * 		// Use generated instance
 * 	}
 * });
 * </pre>
 */
public final class Palette {

	static final float MIN_CONTRAST_TITLE_TEXT = 3.0f;
	
	static final float MIN_CONTRAST_BODY_TEXT = 4.5f;

	static final String LOG_TAG = "Palette";

	static final boolean LOG_TIMINGS = false;

	private final List<Swatch> mSwatches;
	
	private final List<Target> mTargets;

	private final Map<Target, Swatch> mSelectedSwatches;
	
	private final SparseBooleanArray mUsedColors;

	private final Swatch mDominantSwatch;

	Palette(List<Swatch> swatches, List<Target> targets) {
		mSwatches = swatches;
		mTargets = targets;

		mUsedColors = new SparseBooleanArray();
		mSelectedSwatches = new LinkedHashMap<Target, Swatch>();

		mDominantSwatch = findDominantSwatch();
	}

	/**
	 * Returns all of the swatches which make up the palette.
	 */
	public List<Swatch> getSwatches() {
		return Collections.unmodifiableList(mSwatches);
	}

	/**
	 * Returns the targets used to generate this palette.
	 */
	public List<Target> getTargets() {
		return Collections.unmodifiableList(mTargets);
	}

	/**
	 * Returns the most vibrant swatch in the palette. Might be null.
	 *
	 * @see Target#VIBRANT
	 */
	public Swatch getVibrantSwatch() {
		return getSwatchForTarget(Target.VIBRANT);
	}

	/**
	 * Returns a light and vibrant swatch from the palette. Might be null.
	 *
	 * @see Target#LIGHT_VIBRANT
	 */
	public Swatch getLightVibrantSwatch() {
		return getSwatchForTarget(Target.LIGHT_VIBRANT);
	}

	/**
	 * Returns a dark and vibrant swatch from the palette. Might be null.
	 *
	 * @see Target#DARK_VIBRANT
	 */
	public Swatch getDarkVibrantSwatch() {
		return getSwatchForTarget(Target.DARK_VIBRANT);
	}

	/**
	 * Returns a muted swatch from the palette. Might be null.
	 *
	 * @see Target#MUTED
	 */
	public Swatch getMutedSwatch() {
		return getSwatchForTarget(Target.MUTED);
	}

	/**
	 * Returns a muted and light swatch from the palette. Might be null.
	 *
	 * @see Target#LIGHT_MUTED
	 */
	public Swatch getLightMutedSwatch() {
		return getSwatchForTarget(Target.LIGHT_MUTED);
	}

	/**
	 * Returns a muted and dark swatch from the palette. Might be null.
	 *
	 * @see Target#DARK_MUTED
	 */
	public Swatch getDarkMutedSwatch() {
		return getSwatchForTarget(Target.DARK_MUTED);
	}

	/**
	 * Returns the most vibrant color in the palette as an RGB packed int.
	 *
	 * @param defaultColor value to return if the swatch isn't available
	 * @see #getVibrantSwatch()
	 */
	public int getVibrantColor(final int defaultColor) {
		return getColorForTarget(Target.VIBRANT, defaultColor);
	}

	/**
	 * Returns a light and vibrant color from the palette as an RGB packed int.
	 *
	 * @param defaultColor value to return if the swatch isn't available
	 * @see #getLightVibrantSwatch()
	 */
	public int getLightVibrantColor(final int defaultColor) {
		return getColorForTarget(Target.LIGHT_VIBRANT, defaultColor);
	}

	/**
	 * Returns a dark and vibrant color from the palette as an RGB packed int.
	 *
	 * @param defaultColor value to return if the swatch isn't available
	 * @see #getDarkVibrantSwatch()
	 */
	public int getDarkVibrantColor(final int defaultColor) {
		return getColorForTarget(Target.DARK_VIBRANT, defaultColor);
	}

	/**
	 * Returns a muted color from the palette as an RGB packed int.
	 *
	 * @param defaultColor value to return if the swatch isn't available
	 * @see #getMutedSwatch()
	 */
	public int getMutedColor(final int defaultColor) {
		return getColorForTarget(Target.MUTED, defaultColor);
	}

	/**
	 * Returns a muted and light color from the palette as an RGB packed int.
	 *
	 * @param defaultColor value to return if the swatch isn't available
	 * @see #getLightMutedSwatch()
	 */
	public int getLightMutedColor(final int defaultColor) {
		return getColorForTarget(Target.LIGHT_MUTED, defaultColor);
	}

	/**
	 * Returns a muted and dark color from the palette as an RGB packed int.
	 *
	 * @param defaultColor value to return if the swatch isn't available
	 * @see #getDarkMutedSwatch()
	 */
	public int getDarkMutedColor(final int defaultColor) {
		return getColorForTarget(Target.DARK_MUTED, defaultColor);
	}

	/**
	 * Returns the selected swatch for the given target from the palette, or
	 * {@code null} if one could not be found.
	 */
	public Swatch getSwatchForTarget(final Target target) {
		return mSelectedSwatches.get(target);
	}

	/**
	 * Returns the selected color for the given target from the palette as an RGB
	 * packed int.
	 *
	 * @param defaultColor value to return if the swatch isn't available
	 */
	public int getColorForTarget(final Target target, final int defaultColor) {
		Swatch swatch = getSwatchForTarget(target);
		return swatch != null ? swatch.getRgb() : defaultColor;
	}

	/**
	 * Returns the dominant swatch from the palette.
	 *
	 * <p>
	 * The dominant swatch is defined as the swatch with the greatest population
	 * (frequency) within the palette.
	 * </p>
	 */
	public Swatch getDominantSwatch() {
		return mDominantSwatch;
	}

	/**
	 * Returns the color of the dominant swatch from the palette, as an RGB packed
	 * int.
	 *
	 * @param defaultColor value to return if the swatch isn't available
	 * @see #getDominantSwatch()
	 */
	public int getDominantColor(int defaultColor) {
		return mDominantSwatch != null ? mDominantSwatch.getRgb() : defaultColor;
	}

	void generate() {
		// We need to make sure that the scored targets are generated first. This is so
		// that
		// inherited targets have something to inherit from
		for (int i = 0, count = mTargets.size(); i < count; i++) {
			final Target target = mTargets.get(i);
			target.normalizeWeights();
			mSelectedSwatches.put(target, generateScoredTarget(target));
		}
		// We now clear out the used colors
		mUsedColors.clear();
	}

	private Swatch generateScoredTarget(final Target target) {
		final Swatch maxScoreSwatch = getMaxScoredSwatchForTarget(target);
		if (maxScoreSwatch != null && target.isExclusive()) {
			// If we have a swatch, and the target is exclusive, add the color to the used
			// list
			mUsedColors.append(maxScoreSwatch.getRgb(), true);
		}
		return maxScoreSwatch;
	}

	private Swatch getMaxScoredSwatchForTarget(final Target target) {
		float maxScore = 0;
		Swatch maxScoreSwatch = null;
		for (int i = 0, count = mSwatches.size(); i < count; i++) {
			final Swatch swatch = mSwatches.get(i);
			if (shouldBeScoredForTarget(swatch, target)) {
				final float score = generateScore(swatch, target);
				if (maxScoreSwatch == null || score > maxScore) {
					maxScoreSwatch = swatch;
					maxScore = score;
				}
			}
		}
		return maxScoreSwatch;
	}

	private boolean shouldBeScoredForTarget(final Swatch swatch, final Target target) {
		// Check whether the HSL values are within the correct ranges, and this color
		// hasn't
		// been used yet.
		final float hsl[] = swatch.getHsl();
		return hsl[1] >= target.getMinimumSaturation() && hsl[1] <= target.getMaximumSaturation()
				&& hsl[2] >= target.getMinimumLightness() && hsl[2] <= target.getMaximumLightness()
				&& !mUsedColors.get(swatch.getRgb());
	}

	private float generateScore(Swatch swatch, Target target) {
		final float[] hsl = swatch.getHsl();

		float saturationScore = 0;
		float luminanceScore = 0;
		float populationScore = 0;

		final int maxPopulation = mDominantSwatch != null ? mDominantSwatch.getPopulation() : 1;

		if (target.getSaturationWeight() > 0) {
			saturationScore = target.getSaturationWeight() * (1f - Math.abs(hsl[1] - target.getTargetSaturation()));
		}
		if (target.getLightnessWeight() > 0) {
			luminanceScore = target.getLightnessWeight() * (1f - Math.abs(hsl[2] - target.getTargetLightness()));
		}
		if (target.getPopulationWeight() > 0) {
			populationScore = target.getPopulationWeight() * (swatch.getPopulation() / (float) maxPopulation);
		}

		return saturationScore + luminanceScore + populationScore;
	}

	private Swatch findDominantSwatch() {
		int maxPop = Integer.MIN_VALUE;
		Swatch maxSwatch = null;
		for (int i = 0, count = mSwatches.size(); i < count; i++) {
			Swatch swatch = mSwatches.get(i);
			if (swatch.getPopulation() > maxPop) {
				maxSwatch = swatch;
				maxPop = swatch.getPopulation();
			}
		}
		return maxSwatch;
	}

	/**
	 * Represents a color swatch generated from an image's palette. The RGB color
	 * can be retrieved by calling {@link #getRgb()}.
	 */
	public static final class Swatch {
		private final int mRed, mGreen, mBlue;
		private final int mRgb;
		private final int mPopulation;

		private boolean mGeneratedTextColors;
		private int mTitleTextColor;
		private int mBodyTextColor;

		private float[] mHsl;

		public Swatch(int color, int population) {
			mRed = Color.red(color);
			mGreen = Color.green(color);
			mBlue = Color.blue(color);
			mRgb = color;
			mPopulation = population;
		}

		Swatch(int red, int green, int blue, int population) {
			mRed = red;
			mGreen = green;
			mBlue = blue;
			mRgb = Color.rgb(red, green, blue);
			mPopulation = population;
		}

		Swatch(float[] hsl, int population) {
			this(AndroidColorUtils.HSLToColor(hsl), population);
			mHsl = hsl;
		}

		/**
		 * @return this swatch's RGB color value
		 */
		public int getRgb() {
			return mRgb;
		}

		/**
		 * Return this swatch's HSL values. hsv[0] is Hue [0 .. 360) hsv[1] is
		 * Saturation [0...1] hsv[2] is Lightness [0...1]
		 */
		public float[] getHsl() {
			if (mHsl == null) {
				mHsl = new float[3];
			}
			AndroidColorUtils.RGBToHSL(mRed, mGreen, mBlue, mHsl);
			return mHsl;
		}

		/**
		 * @return the number of pixels represented by this swatch
		 */
		public int getPopulation() {
			return mPopulation;
		}

		/**
		 * Returns an appropriate color to use for any 'title' text which is displayed
		 * over this {@link Swatch}'s color. This color is guaranteed to have sufficient
		 * contrast.
		 */
		public int getTitleTextColor() {
			ensureTextColorsGenerated();
			return mTitleTextColor;
		}

		/**
		 * Returns an appropriate color to use for any 'body' text which is displayed
		 * over this {@link Swatch}'s color. This color is guaranteed to have sufficient
		 * contrast.
		 */
		public int getBodyTextColor() {
			ensureTextColorsGenerated();
			return mBodyTextColor;
		}

		private void ensureTextColorsGenerated() {
			if (!mGeneratedTextColors) {
				// First check white, as most colors will be dark
				final int lightBodyAlpha = AndroidColorUtils.calculateMinimumAlpha(Color.WHITE, mRgb, MIN_CONTRAST_BODY_TEXT);
				final int lightTitleAlpha = AndroidColorUtils.calculateMinimumAlpha(Color.WHITE, mRgb,
						MIN_CONTRAST_TITLE_TEXT);

				if (lightBodyAlpha != -1 && lightTitleAlpha != -1) {
					// If we found valid light values, use them and return
					mBodyTextColor = AndroidColorUtils.setAlphaComponent(Color.WHITE, lightBodyAlpha);
					mTitleTextColor = AndroidColorUtils.setAlphaComponent(Color.WHITE, lightTitleAlpha);
					mGeneratedTextColors = true;
					return;
				}

				final int darkBodyAlpha = AndroidColorUtils.calculateMinimumAlpha(Color.BLACK, mRgb, MIN_CONTRAST_BODY_TEXT);
				final int darkTitleAlpha = AndroidColorUtils.calculateMinimumAlpha(Color.BLACK, mRgb, MIN_CONTRAST_TITLE_TEXT);

				if (darkBodyAlpha != -1 && darkBodyAlpha != -1) {
					// If we found valid dark values, use them and return
					mBodyTextColor = AndroidColorUtils.setAlphaComponent(Color.BLACK, darkBodyAlpha);
					mTitleTextColor = AndroidColorUtils.setAlphaComponent(Color.BLACK, darkTitleAlpha);
					mGeneratedTextColors = true;
					return;
				}

				// If we reach here then we can not find title and body values which use the
				// same
				// lightness, we need to use mismatched values
				mBodyTextColor = lightBodyAlpha != -1 ? AndroidColorUtils.setAlphaComponent(Color.WHITE, lightBodyAlpha)
						: AndroidColorUtils.setAlphaComponent(Color.BLACK, darkBodyAlpha);
				mTitleTextColor = lightTitleAlpha != -1 ? AndroidColorUtils.setAlphaComponent(Color.WHITE, lightTitleAlpha)
						: AndroidColorUtils.setAlphaComponent(Color.BLACK, darkTitleAlpha);
				mGeneratedTextColors = true;
			}
		}

		@Override
		public String toString() {
			return new StringBuilder(getClass().getSimpleName()).append(" [RGB: #")
					.append(Integer.toHexString(getRgb())).append(']').append(" [HSL: ")
					.append(Arrays.toString(getHsl())).append(']').append(" [Population: ").append(mPopulation)
					.append(']').append(" [Title Text: #").append(Integer.toHexString(getTitleTextColor())).append(']')
					.append(" [Body Text: #").append(Integer.toHexString(getBodyTextColor())).append(']').toString();
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}
			if (o == null || getClass() != o.getClass()) {
				return false;
			}

			Swatch swatch = (Swatch) o;
			return mPopulation == swatch.mPopulation && mRgb == swatch.mRgb;
		}

		@Override
		public int hashCode() {
			return 31 * mRgb + mPopulation;
		}
	}

}
