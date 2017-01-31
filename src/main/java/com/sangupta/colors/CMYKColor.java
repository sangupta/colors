package com.sangupta.colors;

/**
 * CMYK color based on https://en.wikipedia.org/wiki/CMYK_color_model
 * 
 * 
 * @author sangupta
 *
 */
public class CMYKColor {
	
	/**
	 * Cyan component
	 */
	protected float cyan;
	
	/**
	 * Magenta component
	 */
	protected float magenta;
	
	/**
	 * Yellow component
	 */
	protected float yellow;
	
	/**
	 * Black component
	 */
	protected float black;
	
	public CMYKColor(float[] cmyk) {
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
	
	public CMYKColor(float cyan, float magenta, float yellow, float black) {
		this.cyan = cyan;
		this.magenta = magenta;
		this.yellow = yellow;
		this.black = black;
	}
	
	public CMYKColor(RGBColor color) {
		if(color == null) {
			throw new IllegalArgumentException("RGBColor cannot be null");
		}
		
		float[] cmyk = ColorUtils.RGBtoCMYK(color);
		
		this.cyan = cmyk[0];
		this.magenta = cmyk[1];
		this.yellow = cmyk[2];
		this.black = cmyk[3];
	}
	
	@Override
	public String toString() {
		return "CMYK(" + this.cyan + ", " + this.magenta + ", " + this.yellow + ", " + this.black + ")";
	}
	
}
