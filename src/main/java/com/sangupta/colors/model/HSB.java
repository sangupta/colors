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
 * HSV stands for hue, saturation, and value, and is also often called HSB (B for brightness)
 * 
 * Refer https://en.wikipedia.org/wiki/HSL_and_HSV
 * 
 * @author sangupta
 * @since 1.0.0
 */
public class HSB implements ColorModel {

	/**
	 * Hue component
	 */
	public final float hue;
	
	/**
	 * Saturation component
	 */
	public final  float saturation;
	
	/**
	 * Brightness component
	 */
	public final  float brightness;
	
	/**
	 * Construct a {@link HSB} instance using a <code>float</code> array
	 * with 3 values of hue, saturation and brightness in that order
	 * 
	 * @param hsb
	 *            the float array
	 */
	public HSB(float[] hsb) {
		this.hue = hsb[0];
		this.saturation = hsb[1];
		this.brightness = hsb[2];
	}
	
	/**
	 * Construct a {@link HSB} instance using given values of hue,
	 * saturation and brightness
	 * 
	 * @param hue
	 *            hue value
	 * 
	 * @param saturation
	 *            saturation value
	 * 
	 * @param brightness
	 *            brightness value
	 */
	public HSB(float hue, float saturation, float brightness) {
		this.hue = hue;
		this.saturation = saturation;
		this.brightness = brightness;
	}
	
	public int hueDegrees() {
		return ColorUtils.asInt(this.hue * 360d);
	}
	
	public int saturationPercent() {
		return ColorUtils.asInt(this.saturation * 100d);
	}
	
	public int brightnessPercent() {
		return ColorUtils.asInt(this.brightness * 100d);
	}
	
	/**
	 * Convert this to {@link RGB} color model
	 * 
	 * @return the {@link RGB} color
	 */
	public RGB rgb() {
		return new RGB(ColorConversionUtils.HSBtoRGB(this));
	}
	
	/**
	 * Return this color as a <code>float[]</code> array with the hue, saturation and
	 * brightness channels in order.
	 * 
	 * @return the <code>float[]</code> array
	 */
	public float[] asArray() {
		return new float[] { this.hue, this.saturation, this.brightness };
	}
	
	@Override
	public String toString() {
		return "HSB(" + this.hue + ", " + this.saturation + ", " + this.brightness + ")";
	}
	
	@Override
	public int hashCode() {
		Float value = this.hue * 31f + this.saturation * 17f + this.brightness;
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
		
		if(!(obj instanceof HSB)) {
			return false;
		}
		
		HSB color = (HSB) obj;
		return this.hue ==  color.hue && this.saturation == color.saturation && this.brightness == color.brightness;
	}
	
}
