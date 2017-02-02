package com.sangupta.colors;

import java.awt.Color;

/**
 * HSV stands for hue, saturation, and value, and is also often called HSB (B for brightness)
 * 
 * Refer https://en.wikipedia.org/wiki/HSL_and_HSV
 * 
 * @author sangupta
 *
 */
public class HSBColor {

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
	 * Construct a {@link HSBColor} instance using a <code>float</code> array
	 * with 3 values of hue, saturation and brightness in that order
	 * 
	 * @param hsb
	 *            the float array
	 */
	public HSBColor(float[] hsb) {
		this.hue = hsb[0];
		this.saturation = hsb[1];
		this.brightness = hsb[2];
	}
	
	/**
	 * Construct a {@link HSBColor} instance using given values of hue,
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
	public HSBColor(float hue, float saturation, float brightness) {
		this.hue = hue;
		this.saturation = saturation;
		this.brightness = brightness;
	}
	
	/**
	 * Construct a {@link HSBColor} instance using another {@link HSBColor}
	 * instance
	 * 
	 * @param other
	 *            the {@link HSBColor} instance to copy values from
	 */
	public HSBColor(HSBColor other) {
		this.hue = other.hue;
		this.saturation = other.saturation;
		this.brightness = other.brightness;
	}
	
	/**
	 * Construct a {@link HSBColor} instance using a {@link RGBColor} instance
	 * 
	 * @param color
	 *            the {@link RGBColor} instance
	 */
	public HSBColor(RGBColor color) {
		float[] hsb = Color.RGBtoHSB(color.red, color.green, color.blue, null);
		
		this.hue = hsb[0];
		this.saturation = hsb[1];
		this.brightness = hsb[2];
	}
	
	/**
	 * Return HSB values as a <code>float</code> array.
	 * 
	 * @return the <code>float</code> array
	 */
	public float[] asArray() {
		return new float[] { this.hue, this.saturation, this.brightness };
	}
	
	@Override
	public String toString() {
		return "HSV(" + this.hue + ", " + this.saturation + ", " + this.brightness + ")";
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
		
		if(!(obj instanceof HSBColor)) {
			return false;
		}
		
		HSBColor color = (HSBColor) obj;
		return this.hue ==  color.hue && this.saturation == color.saturation && this.brightness == color.brightness;
	}
	
}
