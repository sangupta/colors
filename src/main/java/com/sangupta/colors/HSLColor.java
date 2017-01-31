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
	
	public HSLColor(float[] hsl) {
		this.hue = hsl[0];
		this.saturation = hsl[1];
		this.luminosity = hsl[2];
	}
	
	public HSLColor(HSLColor other) {
		this.hue = other.hue;
		this.saturation = other.saturation;
		this.luminosity = other.luminosity;
	}
	
	public HSLColor(RGBColor rgbColor) {
		float hsl[] = ColorUtils.RGBtoHSL(rgbColor);
		
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
		
		if(!(obj instanceof HSLColor)) {
			return false;
		}
		
		HSLColor color = (HSLColor) obj;
		return this.hue ==  color.hue && this.saturation == color.saturation && this.luminosity == color.luminosity;
	}
	
	// Usual accessors follow

	/**
	 * @return the hue
	 */
	public float getHue() {
		return hue;
	}

	/**
	 * @param hue the hue to set
	 */
	public void setHue(float hue) {
		this.hue = hue;
	}

	/**
	 * @return the saturation
	 */
	public float getSaturation() {
		return saturation;
	}

	/**
	 * @param saturation the saturation to set
	 */
	public void setSaturation(float saturation) {
		this.saturation = saturation;
	}

	/**
	 * @return the luminosity
	 */
	public float getLuminosity() {
		return luminosity;
	}

	/**
	 * @param luminosity the luminosity to set
	 */
	public void setLuminosity(float luminosity) {
		this.luminosity = luminosity;
	}
	
}
