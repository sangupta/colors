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

import java.util.Collection;
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

	static final String LOG_TAG = "Palette";

	static final boolean LOG_TIMINGS = false;

	private final List<PaletteSwatch> swatches;
	
	private final List<Target> targets;

	private final Map<Target, PaletteSwatch> selectedSwatches;
	
	private final SparseBooleanArray usedColors;

	private final PaletteSwatch dominantSwatch;

	Palette(List<PaletteSwatch> swatches, List<Target> targets) {
		this.swatches = swatches;
		this.targets = targets;

		this.usedColors = new SparseBooleanArray();
		this.selectedSwatches = new LinkedHashMap<Target, PaletteSwatch>();

		this.dominantSwatch = findDominantSwatch();
	}

	/**
	 * Returns all of the swatches which make up the palette.
	 */
	public List<PaletteSwatch> getSwatches() {
		return Collections.unmodifiableList(this.swatches);
	}

	/**
	 * Returns all of the swatches which make up the palette.
	 */
	public Collection<PaletteSwatch> getSeleectedSwatches() {
		return this.selectedSwatches.values();
	}

	/**
	 * Returns the targets used to generate this palette.
	 */
	public List<Target> getTargets() {
		return Collections.unmodifiableList(this.targets);
	}

	/**
	 * Returns the most vibrant swatch in the palette. Might be null.
	 *
	 * @see Target#VIBRANT
	 */
	public PaletteSwatch getVibrantSwatch() {
		return getSwatchForTarget(Target.VIBRANT);
	}

	/**
	 * Returns a light and vibrant swatch from the palette. Might be null.
	 *
	 * @see Target#LIGHT_VIBRANT
	 */
	public PaletteSwatch getLightVibrantSwatch() {
		return getSwatchForTarget(Target.LIGHT_VIBRANT);
	}

	/**
	 * Returns a dark and vibrant swatch from the palette. Might be null.
	 *
	 * @see Target#DARK_VIBRANT
	 */
	public PaletteSwatch getDarkVibrantSwatch() {
		return getSwatchForTarget(Target.DARK_VIBRANT);
	}

	/**
	 * Returns a muted swatch from the palette. Might be null.
	 *
	 * @see Target#MUTED
	 */
	public PaletteSwatch getMutedSwatch() {
		return getSwatchForTarget(Target.MUTED);
	}

	/**
	 * Returns a muted and light swatch from the palette. Might be null.
	 *
	 * @see Target#LIGHT_MUTED
	 */
	public PaletteSwatch getLightMutedSwatch() {
		return getSwatchForTarget(Target.LIGHT_MUTED);
	}

	/**
	 * Returns a muted and dark swatch from the palette. Might be null.
	 *
	 * @see Target#DARK_MUTED
	 */
	public PaletteSwatch getDarkMutedSwatch() {
		return getSwatchForTarget(Target.DARK_MUTED);
	}

	/**
	 * Returns the most vibrant color in the palette as an RGB packed int.
	 *
	 * @param defaultColor value to return if the swatch isn't available
	 * @see #getVibrantSwatch()
	 */
	public int getVibrantColor() {
		return getColorForTarget(Target.VIBRANT);
	}

	/**
	 * Returns a light and vibrant color from the palette as an RGB packed int.
	 *
	 * @param defaultColor value to return if the swatch isn't available
	 * @see #getLightVibrantSwatch()
	 */
	public int getLightVibrantColor() {
		return getColorForTarget(Target.LIGHT_VIBRANT);
	}

	/**
	 * Returns a dark and vibrant color from the palette as an RGB packed int.
	 *
	 * @param defaultColor value to return if the swatch isn't available
	 * @see #getDarkVibrantSwatch()
	 */
	public int getDarkVibrantColor() {
		return getColorForTarget(Target.DARK_VIBRANT);
	}

	/**
	 * Returns a muted color from the palette as an RGB packed int.
	 *
	 * @param defaultColor value to return if the swatch isn't available
	 * @see #getMutedSwatch()
	 */
	public int getMutedColor() {
		return getColorForTarget(Target.MUTED);
	}

	/**
	 * Returns a muted and light color from the palette as an RGB packed int.
	 *
	 * @param defaultColor value to return if the swatch isn't available
	 * @see #getLightMutedSwatch()
	 */
	public int getLightMutedColor() {
		return getColorForTarget(Target.LIGHT_MUTED);
	}

	/**
	 * Returns a muted and dark color from the palette as an RGB packed int.
	 *
	 * @param defaultColor value to return if the swatch isn't available
	 * @see #getDarkMutedSwatch()
	 */
	public int getDarkMutedColor() {
		return getColorForTarget(Target.DARK_MUTED);
	}

	/**
	 * Returns the selected swatch for the given target from the palette, or
	 * {@code null} if one could not be found.
	 */
	public PaletteSwatch getSwatchForTarget(final Target target) {
		return this.selectedSwatches.get(target);
	}

	/**
	 * Returns the selected color for the given target from the palette as an RGB
	 * packed int.
	 *
	 * @param defaultColor value to return if the swatch isn't available
	 */
	public int getColorForTarget(final Target target) {
		PaletteSwatch swatch = getSwatchForTarget(target);
		return swatch != null ? swatch.getRgb() : -1;
	}

	/**
	 * Returns the dominant swatch from the palette.
	 *
	 * <p>
	 * The dominant swatch is defined as the swatch with the greatest population
	 * (frequency) within the palette.
	 * </p>
	 */
	public PaletteSwatch getDominantSwatch() {
		return this.dominantSwatch;
	}

	/**
	 * Returns the color of the dominant swatch from the palette, as an RGB packed
	 * int.
	 *
	 * @param defaultColor value to return if the swatch isn't available
	 * @see #getDominantSwatch()
	 */
	public int getDominantColor(int defaultColor) {
		return this.dominantSwatch != null ? this.dominantSwatch.getRgb() : defaultColor;
	}

	void generate() {
		// We need to make sure that the scored targets are generated first. This is so
		// that
		// inherited targets have something to inherit from
		for (int i = 0, count = this.targets.size(); i < count; i++) {
			final Target target = this.targets.get(i);
			target.normalizeWeights();
			this.selectedSwatches.put(target, generateScoredTarget(target));
		}
		
		// We now clear out the used colors
		this.usedColors.clear();
	}

	private PaletteSwatch generateScoredTarget(final Target target) {
		final PaletteSwatch maxScoreSwatch = getMaxScoredSwatchForTarget(target);
		if (maxScoreSwatch != null && target.isExclusive()) {
			// If we have a swatch, and the target is exclusive, add the color to the used
			// list
			this.usedColors.append(maxScoreSwatch.getRgb(), true);
		}
		return maxScoreSwatch;
	}

	private PaletteSwatch getMaxScoredSwatchForTarget(final Target target) {
		float maxScore = 0;
		PaletteSwatch maxScoreSwatch = null;
		for (int i = 0, count = this.swatches.size(); i < count; i++) {
			final PaletteSwatch swatch = this.swatches.get(i);
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

	private boolean shouldBeScoredForTarget(final PaletteSwatch swatch, final Target target) {
		// Check whether the HSL values are within the correct ranges, and this color
		// hasn't
		// been used yet.
		final float hsl[] = swatch.getHsl();
		return hsl[1] >= target.getMinimumSaturation() && hsl[1] <= target.getMaximumSaturation()
				&& hsl[2] >= target.getMinimumLightness() && hsl[2] <= target.getMaximumLightness()
				&& !this.usedColors.get(swatch.getRgb());
	}

	private float generateScore(PaletteSwatch swatch, Target target) {
		final float[] hsl = swatch.getHsl();

		float saturationScore = 0;
		float luminanceScore = 0;
		float populationScore = 0;

		final int maxPopulation = this.dominantSwatch != null ? this.dominantSwatch.getPopulation() : 1;

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

	private PaletteSwatch findDominantSwatch() {
		int maxPop = Integer.MIN_VALUE;
		PaletteSwatch maxSwatch = null;
		for (int i = 0, count = this.swatches.size(); i < count; i++) {
			PaletteSwatch swatch = this.swatches.get(i);
			if (swatch.getPopulation() > maxPop) {
				maxSwatch = swatch;
				maxPop = swatch.getPopulation();
			}
		}
		return maxSwatch;
	}

}
