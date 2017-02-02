package com.sangupta.colors.model;

/**
 * A third model, common in computer vision applications, is HSI, for hue, saturation, and intensity
 * 
 * Refer https://en.wikipedia.org/wiki/HSL_and_HSV
 * 
 * @author sangupta
 *
 */
public class HSI {

	public final float hue;
	
	public final float saturation;
	
	public final float intensity;
	
	public HSI(float hue, float saturation, float intensity) {
		this.hue = hue;
		this.saturation = saturation;
		this.intensity = intensity;
	}
	
	public HSI(HSI other) {
		this.hue = other.hue;
		this.saturation = other.saturation;
		this.intensity = other.intensity;
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
}