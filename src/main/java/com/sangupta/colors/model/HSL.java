package com.sangupta.colors.model;

import com.sangupta.colors.utils.ColorConversionUtils;

/**
 * HSL stands for hue, saturation, and lightness (or luminosity), and is also often called HLS
 * 
 * Refer https://en.wikipedia.org/wiki/HSL_and_HSV
 * 
 * @author sangupta
 *
 */
public class HSL {
	
	public final float hue;
	
	public final float saturation;
	
	public final float luminosity;

	public HSL(float hue, float saturation, float luminosity) {
		this.hue = hue;
		this.saturation = saturation;
		this.luminosity = luminosity;
	}
	
	public HSL(float[] hsl) {
		this.hue = hsl[0];
		this.saturation = hsl[1];
		this.luminosity = hsl[2];
	}
	
	public HSL(HSL other) {
		this.hue = other.hue;
		this.saturation = other.saturation;
		this.luminosity = other.luminosity;
	}
	
	public HSL(RGB rgbColor) {
		float hsl[] = ColorConversionUtils.RGBtoHSL(rgbColor);
		
		this.hue = hsl[0];
		this.saturation = hsl[1];
		this.luminosity = hsl[2];
	}
	
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
	
}
