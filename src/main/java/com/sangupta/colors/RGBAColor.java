package com.sangupta.colors;

public class RGBAColor extends RGBColor {
	
	public static final int DEFAULT_RGBA_ALPHA = 255;
	
	public final int alpha;

	public RGBAColor(int color) {
		super(color);
		this.alpha = color >>> 24;
	}
	
	public RGBAColor(int red, int green, int blue) {
		super(red, green, blue);
		this.alpha = DEFAULT_RGBA_ALPHA;
	}
	
	public RGBAColor(int red, int green, int blue, int alpha) {
		super(red, green, blue);
		this.alpha = alpha;
	}
	
	public RGBAColor(RGBColor other) {
		super(other);
		this.alpha = DEFAULT_RGBA_ALPHA;
	}
	
	public RGBAColor(RGBAColor other) {
		super(other);
		this.alpha = other.alpha;
	}
	
	@Override
	public String toString() {
		return "RGBA(" + this.red + ", " + this.green + ", " + this.blue + ", " + this.alpha + ")";
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) {
			return false;
		}
		
		if(this == obj) {
			return true;
		}
		
		if(!(obj instanceof RGBAColor)) {
			return false;
		}
		
		RGBAColor color = (RGBAColor) obj;
		return this.red == color.red && this.green == color.green && this.blue == color.blue && this.alpha == color.alpha;
	}
	
}
