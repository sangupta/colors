/**
 * colors - Strongly typed immutable color models
 * Copyright (c) 2017, Sandeep Gupta
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

/**
 * A third model, common in computer vision applications, is HSI, for hue,
 * saturation, and intensity
 * 
 * Refer https://en.wikipedia.org/wiki/HSL_and_HSV
 * 
 * @author sangupta
 * @since 1.0.0
 */
public class HSI {

	public final float hue;
	
	public final float saturation;
	
	public final float intensity;
	
	public HSI(float[] hsi) {
		if(hsi == null) {
			throw new IllegalArgumentException("HSI array cannot be null");
		}
		
		if(hsi.length != 3) {
			throw new IllegalArgumentException("HSI array must have exactly 3 elements");
		}
		
		this.hue = hsi[0];
		this.saturation = hsi[1];
		this.intensity = hsi[2];
		
		this.checkLimit();
	}
	
	public HSI(float hue, float saturation, float intensity) {
		this.hue = hue;
		this.saturation = saturation;
		this.intensity = intensity;
		
		this.checkLimit();
	}
	
	public int hueDegrees() {
		return ColorConversionUtils.asInt(this.hue * 360d);
	}
	
	public int saturationPercent() {
		return ColorConversionUtils.asInt(this.saturation * 100d);
	}
	
	public int intensityPercent() {
		return ColorConversionUtils.asInt(this.intensity * 100d);
	}
	
	/**
	 * Convert this color to {@link RGB} color model
	 * 
	 * @return the RGB color
	 */
	public RGB rgb() {
		return ColorConversionUtils.HSItoRGB(this);
	}
	
	/**
	 * Return this color as a <code>float[]</code> array with the hue, saturation and
	 * intensity channels in order.
	 * 
	 * @return the <code>float[]</code> array
	 */
	public float[] asArray() {
		return new float[] { this.hue, this.saturation, this.intensity };
	}
	
	@Override
	public String toString() {
		return "HSI(" + this.hue + ", " + this.saturation + ", " + this.intensity + ")";
	}
	
	@Override
	public int hashCode() {
		Float value = this.hue * 31f + this.saturation * 17f + this.intensity;
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
		
		if(!(obj instanceof HSI)) {
			return false;
		}
		
		HSI color = (HSI) obj;
		return this.hue ==  color.hue && this.saturation == color.saturation && this.intensity == color.intensity;
	}
	
	private void checkLimit() {
		if(this.hue < 0f || this.hue > 1f) {
			throw new IllegalArgumentException("Hue component must be between (0, 1), got: " + this.hue);
		}
		
		if(this.saturation < 0f || this.saturation > 1f) {
			throw new IllegalArgumentException("Saturation component must be between (0, 1), got: " + this.saturation);
		}
		
		if(this.intensity < 0f || this.intensity > 1f) {
			throw new IllegalArgumentException("Intensity component must be between (0, 1), got: " + this.intensity);
		}
	}
}
