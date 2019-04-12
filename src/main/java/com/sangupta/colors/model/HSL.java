/**
 * colors - Strongly typed immutable color models
 * Copyright (c) 2017-present, Sandeep Gupta
 * 
 * https://sangupta.com/projects/colors
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sangupta.colors.model;

import com.sangupta.colors.ColorConversionUtils;
import com.sangupta.colors.ColorModel;
import com.sangupta.colors.ColorUtils;

/**
 * HSL stands for hue, saturation, and lightness (or luminosity), and is also often called HLS
 * 
 * Refer https://en.wikipedia.org/wiki/HSL_and_HSV
 * 
 * @author sangupta
 * @since 1.0.0
 */
public class HSL implements ColorModel {
	
	public final float hue;
	
	public final float saturation;
	
	public final float luminosity;

	/**
	 * Construct a {@link HSL} color from given <code>float[]</code> array.
	 * 
	 * @param hsl the <code>float[]</code> array
	 */
	public HSL(float[] hsl) {
		if(hsl == null) {
			throw new IllegalArgumentException("HSL color cannot be null");
		}
		
		if(hsl.length != 3) {
			throw new IllegalArgumentException("HSL color array needs exactly 3 elements");
		}
		
		this.hue = hsl[0];
		this.saturation = hsl[1];
		this.luminosity = hsl[2];
		
		this.checkLimit();
	}
	
	/**
	 * Construct a {@link HSL} color instance from given hue, saturation and
	 * luminosity values.
	 * 
	 * @param hue
	 *            the hue value
	 * 
	 * @param saturation
	 *            the saturation value
	 * 
	 * @param luminosity
	 *            the luminosity value
	 */
	public HSL(float hue, float saturation, float luminosity) {
		this.hue = hue;
		this.saturation = saturation;
		this.luminosity = luminosity;
		
		this.checkLimit();
	}
	
	public int hueDegrees() {
		return ColorUtils.asInt(this.hue * 360d);
	}
	
	public int saturationPercent() {
		return ColorUtils.asInt(this.saturation * 100d);
	}
	
	public int luminosityPercent() {
		return ColorUtils.asInt(this.luminosity * 100d);
	}
	
	/**
	 * Convert this color to {@link RGB} color model
	 * 
	 * @return the RGB color
	 */
	public RGB rgb() {
		return ColorConversionUtils.HSLtoRGB(this);
	}
	
	/**
	 * Return this color as a <code>float[]</code> array with the hue, saturation and
	 * intensity channels in order.
	 * 
	 * @return the <code>float[]</code> array
	 */
	public float[] asArray() {
		return new float[] { this.hue, this.saturation, this.luminosity };
	}
	
	@Override
	public String toString() {
		return "HSL(" + this.hue + ", " + this.saturation + ", " + this.luminosity + ")";
	}
	
	@Override
	public int hashCode() {
		Float value = this.hue * 31f + this.saturation * 17f + this.luminosity;
		return value.intValue();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) {
			return false;
		}
		
		if(this == obj) {
			return true;
		}
		
		if(!(obj instanceof HSL)) {
			return false;
		}
		
		HSL color = (HSL) obj;
		return this.hue ==  color.hue && this.saturation == color.saturation && this.luminosity == color.luminosity;
	}
	
	private void checkLimit() {
		if(this.hue < 0f || this.hue > 1f) {
			throw new IllegalArgumentException("Hue component must be between (0, 1), got: " + this.hue);
		}
		
		if(this.saturation < 0f || this.saturation > 1f) {
			throw new IllegalArgumentException("Saturation component must be between (0, 1), got: " + this.saturation);
		}
		
		if(this.luminosity < 0f || this.luminosity > 1f) {
			throw new IllegalArgumentException("Luminosity component must be between (0, 1), got: " + this.luminosity);
		}
	}
}
