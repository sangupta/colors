package com.sangupta.colors.model;

/**
 * Yxy Color Model
 * 
 * Refer
 * https://en.wikipedia.org/wiki/CIE_1931_color_space
 * http://chemaguerra.com/cie-xyz-yxy-rgb-and-gamuts/
 * 
 * @author sangupta
 * @since 1.0.0
 */
public class Yxy {

	public final float Y;
	
	public final float x;
	
	public final float y;
	
	public Yxy(float[] yxy) {
		if(yxy == null) {
			throw new IllegalArgumentException("Yxy array cannot be null");
		}
		
		if(yxy.length != 3) {
			throw new IllegalArgumentException("Yxy array needs exactly 3 elements");
		}
		
		this.Y = yxy[0];
		this.x = yxy[1];
		this.y = yxy[2];
	}
	
	public Yxy(float Y, float x, float y) {
		this.Y = Y;
		this.x = x;
		this.y = y;
	}
	
	public float[] asArray() {
		return new float[] { this.Y, this.x, this.y };
	}
	
	@Override
	public String toString() {
		return "Yxy(" + this.Y + ", " + this.x + ", " + this.y + ")";
	}
	
	@Override
	public int hashCode() {
		return new Float(this.Y * 3100 + this.x * 1700 + this.y * 100).intValue();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) {
			return false;
		}
		
		if(this == obj) {
			return true;
		}
		
		if(!(obj instanceof Yxy)) {
			return false;
		}
		
		Yxy color = (Yxy) obj;
		return this.Y == color.Y && this.x == color.x && this.y == color.y;
	}
}
