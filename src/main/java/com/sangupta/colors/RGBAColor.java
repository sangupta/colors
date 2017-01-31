package com.sangupta.colors;

public class RGBAColor extends RGBColor {
	
	protected int alpha;

	public RGBAColor(int color) {
		super(color);
		this.alpha = color >>> 24;
	}
	
	public RGBAColor(int red, int green, int blue) {
		super(red, green, blue);
		this.alpha = 1;
	}
	
	public RGBAColor(int red, int green, int blue, int alpha) {
		super(red, green, blue);
		this.alpha = alpha;
	}
	
	public RGBAColor(RGBColor other) {
		super(other);
	}
	
	public RGBAColor(RGBAColor other) {
		super(other);
		this.alpha = other.alpha;
	}
	
	@Override
	public String toString() {
		return "RGBA(" + this.red + ", " + this.green + ", " + this.blue + ", " + this.alpha + ")";
	}
	
	// Usual accessors follow

	/**
	 * @return the alpha
	 */
	public int getAlpha() {
		return alpha;
	}

	/**
	 * @param alpha the alpha to set
	 */
	public void setAlpha(int alpha) {
		this.alpha = alpha;
	}
}
