package com.sangupta.colors.extract.android;

import java.util.Arrays;

public class PaletteSwatch {
	
	static final float MIN_CONTRAST_TITLE_TEXT = 3.0f;
	
	static final float MIN_CONTRAST_BODY_TEXT = 4.5f;

	private final int mRed, mGreen, mBlue;
	
	private final int mRgb;
	
	private final int mPopulation;

	private boolean mGeneratedTextColors;
	
	private int mTitleTextColor;
	
	private int mBodyTextColor;

	private float[] mHsl;

	public PaletteSwatch(int color, int population) {
		mRed = Color.red(color);
		mGreen = Color.green(color);
		mBlue = Color.blue(color);
		mRgb = color;
		mPopulation = population;
	}

	PaletteSwatch(int red, int green, int blue, int population) {
		mRed = red;
		mGreen = green;
		mBlue = blue;
		mRgb = Color.rgb(red, green, blue);
		mPopulation = population;
	}

	PaletteSwatch(float[] hsl, int population) {
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
	 * over this {@link PaletteSwatch}'s color. This color is guaranteed to have sufficient
	 * contrast.
	 */
	public int getTitleTextColor() {
		ensureTextColorsGenerated();
		return mTitleTextColor;
	}

	/**
	 * Returns an appropriate color to use for any 'body' text which is displayed
	 * over this {@link PaletteSwatch}'s color. This color is guaranteed to have sufficient
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

		PaletteSwatch swatch = (PaletteSwatch) o;
		return mPopulation == swatch.mPopulation && mRgb == swatch.mRgb;
	}

	@Override
	public int hashCode() {
		return 31 * mRgb + mPopulation;
	}

}
