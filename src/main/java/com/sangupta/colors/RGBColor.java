package com.sangupta.colors;

import java.awt.Color;

/**
 * @author sangupta
 *
 */
public class RGBColor {
	
	/**
	 * Red component between 0-255
	 */
	public final int red;
	
	/**
	 * Green component between 0-255
	 */
	public final int green;
	
	/**
	 * Blue component between 0-255
	 */
	public final int blue;

	/**
	 * Construct an {@link RGBColor} instance using compound <code>int</code>
	 * value.
	 * 
	 * @param color
	 *            the <code>int</code> value
	 */
	public RGBColor(int color) {
		this.red = (color >> 16) & 0xFF;
		this.green = (color >> 8) & 0xFF;
		this.blue = color & 0xFF;
	}
	
	/**
	 * Construct an {@link RGBColor} instance using an integer array with 3
	 * elements, specifying red, green and blue components in that order.
	 * 
	 * @param rgb
	 *            the <code>int[]</code> array
	 */
	public RGBColor(int[] rgb) {
		this.red = rgb[0];
		this.green = rgb[1];
		this.blue = rgb[2];
	}
	
	/**
	 * Construct an {@link RGBColor} instance using given RGB values
	 * 
	 * @param red
	 *            between 0-255
	 *            
	 * @param green
	 *            between 0-255
	 *            
	 * @param blue
	 *            between 0-255
	 */
	public RGBColor(int red, int green, int blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	
	/**
	 * Construct an {@link RGBColor} instance using another instance
	 * 
	 * @param other
	 *            the {@link RGBColor} instance to copy values from
	 */
	public RGBColor(RGBColor other) {
		this.red = other.red;
		this.green = other.green;
		this.blue = other.blue;
	}
	
	/**
	 * Construct an {@link RGBColor} instance using another {@link HSLColor}
	 * instance
	 * 
	 * @param hsl
	 *            {@link HSLColor} instance to use
	 */
	public RGBColor(HSLColor hsl) {
		int rgb[] = ColorUtils.HSLtoRGB(hsl.asArray());
		
		this.red = rgb[0];
		this.green = rgb[1];
		this.blue = rgb[2];
	}
	
	/**
	 * Construct an {@link RGBColor} instance using another {@link HSBColor}
	 * instance
	 * 
	 * @param hsl
	 *            {@link HSBColor} instance to use
	 */
	public RGBColor(HSBColor color) {
		this(Color.HSBtoRGB(color.hue, color.saturation, color.brightness));
	}
	
	public RGBColor(String color) {
		color = color.trim();
		
		if(color.startsWith("#")) {
			color = color.substring(1);
		}
		
		throw new RuntimeException("not yet implemented");
	}
	
	public RGBColor invert() {
		return new RGBColor(255 - this.red, 255 - this.green, 255 - this.blue);
	}
	
	public RGBColor mix(RGBColor color) {
		int red = (this.red + color.red) / 2;
		int green = (this.green + color.green) / 2;
		int blue = (this.blue + color.blue) / 2;
		
		return new RGBColor(red, green, blue);
	}
	
	public float asGrayScaleColor() {
		return (float) (this.red * 0.3f + this.green * 0.59f + this.blue * 0.11f);
	}
	
	public int[] asArray() {
		return new int[] { this.red, this.green, this.blue };
	}
	
	public int value() {
		return (0xFF << 24) | (this.red << 16) | (this.green << 8) | this.blue;
	}
	
	// Convenience duplicate getters
	
	@Override
	public String toString() {
		return "RGB(" + this.red + ", " + this.green + ", " + this.blue + ")";
	}
	
	@Override
	public int hashCode() {
		return this.value();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) {
			return false;
		}
		
		if(this == obj) {
			return true;
		}
		
		if(!(obj instanceof RGBColor)) {
			return false;
		}
		
		RGBColor color = (RGBColor) obj;
		return this.red == color.red && this.green == color.green && this.blue == color.blue;
	}
	
}
