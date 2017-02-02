package com.sangupta.colors;

/**
 * 
 * XYZ color model as per https://en.wikipedia.org/wiki/CIE_1931_color_space
 * 
 * @author sangupta
 *
 */
public class XYZColor {
	
	public final float x;
	
	public final float y;

	public final float z;
	
	public final Illuminant illuminant;
	
	public XYZColor(float[] xyz) {
		this(xyz, Illuminant.D65);
	}
	
	public XYZColor(float[] xyz, Illuminant illuminant) {
		if(xyz == null) {
			throw new IllegalArgumentException("XYZ array cannot be null");
		}
		
		if(xyz.length != 3) {
			throw new IllegalArgumentException("XYZ array must have exactly 3 elements");
		}
		
		if(illuminant == null) {
			throw new IllegalArgumentException("Illuminant cannot be null");
		}
		
		this.x = xyz[0];
		this.y = xyz[1];
		this.z = xyz[2];
		
		this.illuminant = illuminant;
	}
	
	public XYZColor(float x, float y, float z) {
		this(x, y, z, Illuminant.D65);
	}
	
	public XYZColor(float x, float y, float z, Illuminant illuminant) {
		if(illuminant == null) {
			throw new IllegalArgumentException("Illuminant cannot be null");
		}
		
		this.x = x;
		this.y = y;
		this.z = z;
		
		this.illuminant = illuminant;
	}
	
	public XYZColor multiply(float factor) {
		return new XYZColor(this.x * factor, this.y * factor, this.z * factor);
	}
	
	public void normalize() {
		float sum = this.x + this.y + this.z;
        if (sum < 1e-6f) {
            return;
        }
        
        float factor = 1 / sum;
        this.multiply(factor);	
	}
	
	@Override
	public String toString() {
		return "XYZ(" + this.x + ", " + this.y + ", " + this.z + ") @ " + this.illuminant + " illuminant";
	}
	
	@Override
	public int hashCode() {
		return new Float(this.x * 3100 + this.y * 1700 + this.z * 100).intValue();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) {
			return false;
		}
		
		if(this == obj) {
			return true;
		}
		
		if(!(obj instanceof XYZColor)) {
			return false;
		}
		
		XYZColor color = (XYZColor) obj;
		return this.x == color.x && this.y == color.y && this.z == color.z && this.illuminant == color.illuminant;
	}

}
