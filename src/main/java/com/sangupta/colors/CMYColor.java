package com.sangupta.colors;

public class CMYColor {
	
	public final float cyan;
	
	public final float magenta;
	
	public final float yellow;

	public CMYColor(float[] cmy) {
		if(cmy == null) {
			throw new IllegalArgumentException("CMY color cannot be null");
		}
		
		if(cmy.length != 3) {
			throw new IllegalArgumentException("CMY color array needs exactly 3 elements");
		}
		
		this.cyan = cmy[0];
		this.magenta = cmy[1];
		this.yellow = cmy[2];
	}
	
	public CMYColor(float cyan, float magenta, float yellow) {
		this.cyan = cyan;
		this.magenta = magenta;
		this.yellow = yellow;
	}
	
	public CMYColor(RGBColor rgbColor) {
		if(rgbColor == null) {
			throw new IllegalArgumentException("RGB color cannot be null");
		}
		
		float[] cmy = ColorUtils.RGBtoCMY(rgbColor.red, rgbColor.green, rgbColor.blue);
		
		this.cyan = cmy[0];
		this.magenta = cmy[1];
		this.yellow = cmy[2];
	}
	
	@Override
	public String toString() {
		return "CMY(" + this.cyan + ", " + this.magenta + ", " + this.yellow + ")";
	}
	
	@Override
	public int hashCode() {
		return new Float(this.cyan * 3100 + this.magenta * 1700 + this.yellow * 100).intValue();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) {
			return false;
		}
		
		if(this == obj) {
			return true;
		}
		
		if(!(obj instanceof CMYColor)) {
			return false;
		}
		
		CMYColor color = (CMYColor) obj;
		return this.cyan == color.cyan && this.magenta == color.magenta && this.yellow == color.yellow;
	}
	
}
