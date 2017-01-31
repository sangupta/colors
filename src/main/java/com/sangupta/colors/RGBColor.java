package com.sangupta.colors;

/**
 * @author sangupta
 *
 */
public class RGBColor {
	
	protected int red;
	
	protected int blue;
	
	protected int green;
	
	public RGBColor(int color) {
		this.red = (color >> 16) & 0xFF;
		this.green = (color >> 8) & 0xFF;
		this.blue = color & 0xFF;
	}
	
	public RGBColor(int red, int green, int blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	
	public RGBColor(RGBColor other) {
		this.red = other.red;
		this.green = other.green;
		this.blue = other.blue;
	}
	
	public void invert() {
		this.red = 255 - this.red;
		this.green = 255 - this.green;
		this.blue = 255 - this.blue;
	}
	
	public void mix(RGBColor color) {
		this.red = (this.red + color.red) / 2;
		this.green = (this.green + color.green) / 2;
		this.blue = (this.blue + color.blue) / 2;
	}
	
	public float asGrayScaleColor() {
		return (float) (this.red * 0.3f + this.green * 0.59f + this.blue * 0.11f);
	}
	
	@Override
	public String toString() {
		return "RGB(" + this.red + ", " + this.green + ", " + this.blue + ")";
	}
	
	// Usual accessors follow

	/**
	 * @return the red
	 */
	public int getRed() {
		return red;
	}

	/**
	 * @param red the red to set
	 */
	public void setRed(int red) {
		this.red = red;
	}

	/**
	 * @return the blue
	 */
	public int getBlue() {
		return blue;
	}

	/**
	 * @param blue the blue to set
	 */
	public void setBlue(int blue) {
		this.blue = blue;
	}

	/**
	 * @return the green
	 */
	public int getGreen() {
		return green;
	}

	/**
	 * @param green the green to set
	 */
	public void setGreen(int green) {
		this.green = green;
	}

}
