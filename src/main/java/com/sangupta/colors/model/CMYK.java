package com.sangupta.colors.model;

import com.sangupta.colors.utils.ColorConversionUtils;

/**
 * CMYK color based on https://en.wikipedia.org/wiki/CMYK_color_model
 * 
 * 
 * @author sangupta
 * @since 1.0.0
 */
public class CMYK {
	
	/**
	 * Cyan component
	 */
	public final float cyan;
	
	/**
	 * Magenta component
	 */
	public final float magenta;
	
	/**
	 * Yellow component
	 */
	public final float yellow;
	
	/**
	 * Black component
	 */
	public final float black;
	
	public CMYK(float[] cmyk) {
		if(cmyk == null) {
			throw new IllegalArgumentException("Array cannot be null");
		}
		
		if(cmyk.length != 4) {
			throw new IllegalArgumentException("Array needs to have 4 elements only");
		}
		
		this.cyan = cmyk[0];
		this.magenta = cmyk[1];
		this.yellow = cmyk[2];
		this.black = cmyk[3];
	}
	
	public CMYK(float cyan, float magenta, float yellow, float black) {
		this.cyan = cyan;
		this.magenta = magenta;
		this.yellow = yellow;
		this.black = black;
	}
	
	public CMYK(RGB color) {
		if(color == null) {
			throw new IllegalArgumentException("RGBColor cannot be null");
		}
		
		float[] cmyk = ColorConversionUtils.RGBtoCMYK(color);
		
		this.cyan = cmyk[0];
		this.magenta = cmyk[1];
		this.yellow = cmyk[2];
		this.black = cmyk[3];
	}
	
	public float[] asArray() {
		return new float[] { this.cyan, this.magenta, this.yellow, this.black };
	}
	
	@Override
	public String toString() {
		return "CMYK(" + this.cyan + ", " + this.magenta + ", " + this.yellow + ", " + this.black + ")";
	}
	
	@Override
	public int hashCode() {
		return new Float(this.cyan * 6900 + this.magenta * 3100 + this.yellow * 1700 + this.black * 100).intValue();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) {
			return false;
		}
		
		if(this == obj) {
			return true;
		}
		
		if(!(obj instanceof CMYK)) {
			return false;
		}
		
		CMYK color = (CMYK) obj;
		return this.cyan == color.cyan && this.magenta == color.magenta && this.yellow == color.yellow && this.black == color.black;
	}
}
