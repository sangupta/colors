package com.sangupta.colors.model;

import java.awt.Color;

import com.sangupta.colors.model.YUV.YUVQuality;
import com.sangupta.colors.utils.ColorConversionUtils;

/**
 * RGBA color model for red, green, and blue channels. All values should be
 * between 0-255.
 * 
 * @author sangupta
 * @since 1.0.0
 */
public class RGB {
	
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
	 * Construct an {@link RGB} instance using compound <code>int</code>
	 * value.
	 * 
	 * @param color
	 *            the <code>int</code> value
	 */
	public RGB(int color) {
		this.red = (color >> 16) & 0xFF;
		this.green = (color >> 8) & 0xFF;
		this.blue = color & 0xFF;
	}
	
	/**
	 * Construct an {@link RGB} instance using an integer array with 3
	 * elements, specifying red, green and blue components in that order.
	 * 
	 * @param rgb
	 *            the <code>int[]</code> array
	 */
	public RGB(int[] rgb) {
		if(rgb == null) {
			throw new IllegalArgumentException("RGB array cannot be null");
		}
		
		if(rgb.length != 3) {
			throw new IllegalArgumentException("RGB array must have exactly 3 elements");
		}
		
		checkLimit("Red", rgb[0]);
		checkLimit("Green", rgb[1]);
		checkLimit("Blue", rgb[2]);
		
		this.red = rgb[0];
		this.green = rgb[1];
		this.blue = rgb[2];
	}
	
	/**
	 * Construct an {@link RGB} instance using given RGB values
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
	public RGB(int red, int green, int blue) {
		checkLimit("Red", red);
		checkLimit("Green", green);
		checkLimit("Blue", blue);
		
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	
	/**
	 * Construct an {@link RGB} instance using another instance
	 * 
	 * @param other
	 *            the {@link RGB} instance to copy values from
	 */
	public RGB(RGB other) {
		this.red = other.red;
		this.green = other.green;
		this.blue = other.blue;
	}
	
	/**
	 * Construct an {@link RGB} instance using another {@link HSL}
	 * instance
	 * 
	 * @param hsl
	 *            {@link HSL} instance to use
	 */
	public RGB(HSL hsl) {
		int rgb[] = ColorConversionUtils.HSLtoRGB(hsl.asArray());
		
		this.red = rgb[0];
		this.green = rgb[1];
		this.blue = rgb[2];
	}
	
	/**
	 * Construct an {@link RGB} instance using another {@link HSB}
	 * instance
	 * 
	 * @param hsl
	 *            {@link HSB} instance to use
	 */
	public RGB(HSB color) {
		this(Color.HSBtoRGB(color.hue, color.saturation, color.brightness));
	}
	
	// Conversion functions
	
	/**
	 * Convert to {@link HSB} model.
	 * 
	 * @return
	 */
	public HSB hsb() {
		return new HSB(ColorConversionUtils.RGBtoHSB(this));
	}
	
	/**
	 * Convert to {@link HSI} model.
	 * 
	 * @return
	 */
	public HSI hsi() {
		return new HSI(ColorConversionUtils.RGBtoHSI(this));
	}
	
	/**
	 * Convert to {@link HSL} model.
	 * 
	 * @return
	 */
	public HSL hsl() {
		return new HSL(ColorConversionUtils.RGBtoHSL(this));
	}
	
	/**
	 * Convert the {@link XYZ} model.
	 * 
	 * @return
	 */
	public XYZ xyz() {
		return new XYZ(ColorConversionUtils.RGBtoXYZ(this));
	}
	
	/**
	 * Convert the {@link YIQ} model.
	 * 
	 * @return
	 */
	public YIQ yiq() {
		return new YIQ(ColorConversionUtils.RGBtoYIQ(this));
	}
	
	/**
	 * Convert the {@link YUV} model.
	 * 
	 * @param quality
	 * @return
	 */
	public YUV yuv(YUVQuality quality) {
		return new YUV(ColorConversionUtils.RGBtoYUV(this, quality));
	}
	
	// Manipulation functions

	/**
	 * Invert this {@link RGB} color.
	 * 
	 * @return the new inverted {@link RGB} color.
	 */
	public RGB invert() {
		return new RGB(255 - this.red, 255 - this.green, 255 - this.blue);
	}
	
	/**
	 * Mix this {@link RGB} color with given {@link RGB} color.
	 * 
	 * @param color
	 *            the {@link RGB} color to mix with
	 * 
	 * @return the new mixed {@link RGB} color
	 */
	public RGB mix(RGB color) {
		int red = (this.red + color.red) / 2;
		int green = (this.green + color.green) / 2;
		int blue = (this.blue + color.blue) / 2;
		
		return new RGB(red, green, blue);
	}
	
	public float asGrayScaleColor() {
		return (float) (this.red * 0.3f + this.green * 0.59f + this.blue * 0.11f);
	}
	
	// Other common functions
	
	public int[] asArray() {
		return new int[] { this.red, this.green, this.blue };
	}
	
	/**
	 * Return the {@link RGB} color as a 24-bit RRGGBB value.
	 * 
	 * @return the 24-bit color value
	 */
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
		
		if(!(obj instanceof RGB)) {
			return false;
		}
		
		RGB color = (RGB) obj;
		return this.red == color.red && this.green == color.green && this.blue == color.blue;
	}
	
	/**
	 * Check that component values are within range.
	 * 
	 * @param component
	 * @param value
	 */
	protected void checkLimit(String component, int value) {
		if(value < 0 || value > 255) {
			throw new IllegalArgumentException(component + " value must be between 0-255 inclusive.");
		}
	}
}
