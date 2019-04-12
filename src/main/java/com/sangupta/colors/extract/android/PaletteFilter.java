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

/**
 * A Filter provides a mechanism for exercising fine-grained control over which
 * colors are valid within a resulting {@link Palette}.
 * 
 * @author sangupta
 * @since 1.0.0
 */
public interface PaletteFilter {

	/**
	 * Hook to allow clients to be able filter colors from resulting palette.
	 *
	 * @param rgb the color in RGB888.
	 * @param hsl HSL representation of the color.
	 *
	 * @return true if the color is allowed, false if not.
	 *
	 * @see Builder#addFilter(PaletteFilter)
	 */
	boolean isAllowed(int rgb, float[] hsl);
	
	/**
	 * The default filter.
	 */
	public static final PaletteFilter DEFAULT_FILTER = new PaletteFilter() {
		
		private static final float BLACK_MAX_LIGHTNESS = 0.05f;
		
		private static final float WHITE_MIN_LIGHTNESS = 0.95f;

		@Override
		public boolean isAllowed(int rgb, float[] hsl) {
			return !isWhite(hsl) && !isBlack(hsl) && !isNearRedILine(hsl);
		}

		/**
		 * @return true if the color represents a color which is close to black.
		 */
		private boolean isBlack(float[] hslColor) {
			return hslColor[2] <= BLACK_MAX_LIGHTNESS;
		}

		/**
		 * @return true if the color represents a color which is close to white.
		 */
		private boolean isWhite(float[] hslColor) {
			return hslColor[2] >= WHITE_MIN_LIGHTNESS;
		}

		/**
		 * @return true if the color lies close to the red side of the I line.
		 */
		private boolean isNearRedILine(float[] hslColor) {
			return hslColor[0] >= 10f && hslColor[0] <= 37f && hslColor[1] <= 0.82f;
		}
	};
}
