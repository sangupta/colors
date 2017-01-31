package com.sangupta.colors;

public class CMYKColor {
	
	protected float cyan;
	
	protected float magenta;
	
	protected float yellow;
	
	protected float black;

	@Override
	public String toString() {
		return "CMYK(" + this.cyan + ", " + this.magenta + ", " + this.yellow + ", " + this.black + ")";
	}
	
}
