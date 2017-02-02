package com.sangupta.colors;

public class LABColor {
	
	public final float l;
	
	public final float a;
	
	public final float b;

	public LABColor(float[] lab) {
		if(lab == null) {
			throw new IllegalArgumentException("LAB color cannot be null");
		}
		
		if(lab.length != 3) {
			throw new IllegalArgumentException("LAB color array needs exactly 3 elements");
		}
		
		this.l = lab[0];
		this.a = lab[1];
		this.b = lab[2];
	}
	
	public LABColor(float l, float a, float b) {
		this.l = l;
		this.a = a;
		this.b = b;
	}
	
	public boolean isInRGBGamut() {
		// first, map CIE L*a*b* to CIE XYZ
		double y = (this.l + 16) / 116;
		double x = y + this.a / 500;
		double z = y - this.b / 200;

		// D65 standard referent
		double X = 0.950470, Y = 1.0, Z = 1.088830;

		x = X * (x > 0.206893034 ? x * x * x : (x - 4.0 / 29) / 7.787037);
		y = Y * (y > 0.206893034 ? y * y * y : (y - 4.0 / 29) / 7.787037);
		z = Z * (z > 0.206893034 ? z * z * z : (z - 4.0 / 29) / 7.787037);

		// second, map CIE XYZ to sRGB
		double r = 3.2404542 * x - 1.5371385 * y - 0.4985314 * z;
		double g = -0.9692660 * x + 1.8760108 * y + 0.0415560 * z;
		double b = 0.0556434 * x - 0.2040259 * y + 1.0572252 * z;
		r = r <= 0.00304 ? 12.92 * r : 1.055 * Math.pow(r, 1 / 2.4) - 0.055;
		g = g <= 0.00304 ? 12.92 * g : 1.055 * Math.pow(g, 1 / 2.4) - 0.055;
		b = b <= 0.00304 ? 12.92 * b : 1.055 * Math.pow(b, 1 / 2.4) - 0.055;

		// third, check sRGB values
		return !(r < 0 || r > 1 || g < 0 || g > 1 || b < 0 || b > 1);
	}
	
	@Override
	public String toString() {
		return "LAB(" + this.l + ", " + this.a + ", " + this.b + ")";
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
