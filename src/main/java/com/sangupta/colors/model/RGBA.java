package com.sangupta.colors.model;

public class RGBA extends RGB {
	
	public static final int DEFAULT_RGBA_ALPHA = 255;
	
	public final int alpha;

	public RGBA(int color) {
		super(color);
		this.alpha = color >>> 24;
	}
	
	public RGBA(int red, int green, int blue) {
		super(red, green, blue);
		this.alpha = DEFAULT_RGBA_ALPHA;
	}
	
	public RGBA(int red, int green, int blue, int alpha) {
		super(red, green, blue);
		this.alpha = alpha;
	}
	
	public RGBA(RGB other) {
		super(other);
		this.alpha = DEFAULT_RGBA_ALPHA;
	}
	
	public RGBA(RGBA other) {
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
		
		if(!(obj instanceof RGBA)) {
			return false;
		}
		
		RGBA color = (RGBA) obj;
		return this.red == color.red && this.green == color.green && this.blue == color.blue && this.alpha == color.alpha;
	}
	
}
