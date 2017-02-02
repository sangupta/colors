package com.sangupta.colors;

/**
 * As per https://en.wikipedia.org/wiki/CIE_1931_color_space
 * 
 * @author sangupta
 *
 */
public class XYZColor {
	
	public final float x;
	
	public final float y;

	public final float z;
	
	public XYZColor(float[] xyz) {
		if(xyz == null) {
			throw new IllegalArgumentException("XYZ array cannot be null");
		}
		
		if(xyz.length != 3) {
			throw new IllegalArgumentException("XYZ array must have exactly 3 elements");
		}
		
		this.x = xyz[0];
		this.y = xyz[1];
		this.z = xyz[2];
	}
	
	public XYZColor(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
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
		return "XYZ(" + this.x + ", " + this.y + ", " + this.z + ")";
	}
	
	@Override
	public int hashCode() {
		return new Float(this.x * 31 + this.y * 17 + this.z).intValue();
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
		return this.x == color.x && this.y == color.y && this.z == color.z;
	}

}
