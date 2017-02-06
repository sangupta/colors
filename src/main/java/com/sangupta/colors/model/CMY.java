package com.sangupta.colors.model;

import com.sangupta.colors.utils.ColorConversionUtils;

/**
 * Cyan-Magenta-Yellow color model.
 * 
 * Refer <a href=
 * "http://www.mat.univie.ac.at/~kriegl/Skripten/CG/node13.html">http://www.mat.univie.ac.at/~kriegl/Skripten/CG/node13.html</a>
 * for more details.
 * 
 * @author sangupta
 *
 */
public class CMY {
	
	public final float cyan;
	
	public final float magenta;
	
	public final float yellow;

	public CMY(float[] cmy) {
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
	
	public CMY(float cyan, float magenta, float yellow) {
		this.cyan = cyan;
		this.magenta = magenta;
		this.yellow = yellow;
	}
	
	public CMY(RGB rgbColor) {
		if(rgbColor == null) {
			throw new IllegalArgumentException("RGB color cannot be null");
		}
		
		float[] cmy = ColorConversionUtils.RGBtoCMY(rgbColor.red, rgbColor.green, rgbColor.blue);
		
		this.cyan = cmy[0];
		this.magenta = cmy[1];
		this.yellow = cmy[2];
	}
	
	public float[] asArray() {
		return new float[] { this.cyan, this.magenta, this.yellow };
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
		
		if(!(obj instanceof CMY)) {
			return false;
		}
		
		CMY color = (CMY) obj;
		return this.cyan == color.cyan && this.magenta == color.magenta && this.yellow == color.yellow;
	}
	
}
