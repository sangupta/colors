package com.sangupta.colors.model;

/**
 * HunterLAB color model. Refer
 * https://en.wikipedia.org/wiki/Lab_color_space#Hunter_Lab
 * for details.
 * 
 * @author sangupta
 *
 */
public class HunterLAB {
	
	public final float l;
	
	public final float a;
	
	public final float b;

	public HunterLAB(float[] hlab) {
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
	
	public HunterLAB(float l, float a, float b) {
		this.l = l;
		this.a = a;
		this.b = b;
	}
	
	public float[] asArray() {
		return new float[] { this.l, this.a, this.b };
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
		
		if(!(obj instanceof LAB)) {
			return false;
		}
		
		LAB color = (LAB) obj;
		return this.l == color.l && this.a == color.a && this.b == color.b;
	}
}
