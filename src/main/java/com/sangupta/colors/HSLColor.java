package com.sangupta.colors;

/**
 * HSL stands for hue, saturation, and lightness (or luminosity), and is also often called HLS
 * 
 * Refer https://en.wikipedia.org/wiki/HSL_and_HSV
 * 
 * @author sangupta
 *
 */
public class HSLColor {
	
	protected float hue;
	
	protected float saturation;
	
	protected float luminosity;

	public HSLColor(float hue, float saturation, float luminosity) {
		this.hue = hue;
		this.saturation = saturation;
		this.luminosity = luminosity;
	}
	
	public HSLColor(HSLColor other) {
		this.hue = other.hue;
		this.saturation = other.saturation;
		this.luminosity = other.luminosity;
	}
	
	@Override
	public String toString() {
		return "HSL(" + this.hue + ", " + this.saturation + ", " + this.luminosity + ")";
	}
	
}
