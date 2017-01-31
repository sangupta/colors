package com.sangupta.colors;

/**
 * HSV stands for hue, saturation, and value, and is also often called HSB (B for brightness)
 * 
 * Refer https://en.wikipedia.org/wiki/HSL_and_HSV
 * 
 * @author sangupta
 *
 */
public class HSVColor {

	protected float hue;
	
	protected float saturation;
	
	protected float brightness;
	
	public HSVColor(float hue, float saturation, float brightness) {
		this.hue = hue;
		this.saturation = saturation;
		this.brightness = brightness;
	}
	
	public HSVColor(HSVColor other) {
		this.hue = other.hue;
		this.saturation = other.saturation;
		this.brightness = other.brightness;
	}
	
	@Override
	public String toString() {
		return "HSV(" + this.hue + ", " + this.saturation + ", " + this.brightness + ")";
	}
	
}
