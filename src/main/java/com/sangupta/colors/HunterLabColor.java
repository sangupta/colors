package com.sangupta.colors;

/**
 * HunterLAB color model. Refer
 * https://en.wikipedia.org/wiki/Lab_color_space#Hunter_Lab
 * for details.
 * 
 * @author sangupta
 *
 */
public class HunterLabColor {
	
	public final float l;
	
	public final float a;
	
	public final float b;

	public HunterLabColor(float[] hlab) {
		if(hlab == null) {
			throw new IllegalArgumentException("HunterLAB color cannot be null");
		}
		
		if(hlab.length != 3) {
			throw new IllegalArgumentException("HunterLAB color array needs exactly 3 elements");
		}
		
		this.l = hlab[0];
		this.a = hlab[1];
		this.b = hlab[2];
	}
	
	public HunterLabColor(float l, float a, float b) {
		this.l = l;
		this.a = a;
		this.b = b;
	}

	@Override
	public String toString() {
		return "HunterLAB(" + this.l + ", " + this.a + ", " + this.b + ")";
	}
	
	@Override
	public int hashCode() {
		return new Float(this.l * 3100 + this.a * 1700 + this.b * 100).intValue();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) {
			return false;
		}
		
		if(this == obj) {
			return true;
		}
		
		if(!(obj instanceof LABColor)) {
			return false;
		}
		
		LABColor color = (LABColor) obj;
		return this.l == color.l && this.a == color.a && this.b == color.b;
	}
}
