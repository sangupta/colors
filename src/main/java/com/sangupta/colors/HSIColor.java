package com.sangupta.colors;

/**
 * A third model, common in computer vision applications, is HSI, for hue, saturation, and intensity
 * 
 * Refer https://en.wikipedia.org/wiki/HSL_and_HSV
 * 
 * @author sangupta
 *
 */
public class HSIColor {

	protected float hue;
	
	protected float saturation;
	
	protected float intensity;
	
	public HSIColor(float hue, float saturation, float intensity) {
		this.hue = hue;
		this.saturation = saturation;
		this.intensity = intensity;
	}
	
	public HSIColor(HSIColor other) {
		this.hue = other.hue;
		this.saturation = other.saturation;
		this.intensity = other.intensity;
	}
	
	@Override
	public String toString() {
		return "HSI(" + this.hue + ", " + this.saturation + ", " + this.intensity + ")";
	}
	
}
