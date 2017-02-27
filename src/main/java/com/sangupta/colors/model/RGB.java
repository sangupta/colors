/**
 * avu - Strongly typed immutable color models
 * Copyright (c) 2017, Sandeep Gupta
 * 
 * https://sangupta.com/projects/avu
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sangupta.colors.model;

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
	
	protected RGB(RGB other) {
		this.red = other.red;
		this.green = other.green;
		this.blue = other.blue;
	}
	
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
	 * Construct a {@link RGB} instance using HEX-values in the format
	 * <code>#rrggbb</code> or <code>#rgb</code> format.
	 * 
	 * @param color the hex-based color string
	 */
	public RGB(String color) {
		if(color == null || color.isEmpty()) {
			throw new IllegalArgumentException("Color cannot be empty/null");
		}
		
		if(color.length() < 3) {
			throw new IllegalArgumentException("Color value must be expressed as hex in either #rrggbb or #rgb format");
		}
		
		if(color.startsWith("#")) {
			color = color.substring(1);
		}
		
		if(color.length() == 3) {
			char[] chars = color.toCharArray();
			
			this.red = Integer.parseInt(new String(""+ chars[0] + chars[0]), 16);
			this.green = Integer.parseInt(new String(""+ chars[1] + chars[1]), 16);
			this.blue = Integer.parseInt(new String(""+ chars[2] + chars[2]), 16);
			
			return;
		}
		
		if(color.length() == 6) {
			this.red = Integer.parseInt(new String(color.substring(0, 2)), 16);
			this.green = Integer.parseInt(new String(color.substring(2, 4)), 16);
			this.blue = Integer.parseInt(new String(color.substring(4, 6)), 16);
		}
		
		throw new IllegalArgumentException("Color value must be expressed as hex in either #rrggbb or #rgb format");
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
	 * Convert this color to {@link HSI} color model
	 * 
	 * @return the {@link HSI} color
	 */
	public HSI hsi() {
		return new HSI(ColorConversionUtils.RGBtoHSI(this));
	}
	
	/**
	 * Convert this color to {@link HSL} color model
	 * 
	 * @return the {@link HSL} color
	 */
	public HSL hsl() {
		return new HSL(ColorConversionUtils.RGBtoHSL(this));
	}
	
	/**
	 * Convert this color to {@link XYZ} color model
	 * 
	 * @return the {@link XYZ} color
	 */
	public XYZ xyz() {
		return ColorConversionUtils.RGBtoXYZ(this);
	}
	
	/**
	 * Convert this color to {@link YIQ} color model
	 * 
	 * @return the {@link YIQ} color
	 */
	public YIQ yiq() {
		return ColorConversionUtils.RGBtoYIQ(this);
	}
	
	public CMY cmy() {
		return ColorConversionUtils.RGBtoCMY(this);
	}
	
	public CMYK cmyk() {
		return ColorConversionUtils.RGBtoCMYK(this);
	}
	
	/**
	 * Convert this color to {@link YUV} color model
	 * 
	 * @param quality
	 *            the {@link YUVQuality} quality to use for conversion
	 * 
	 * @return the {@link YUV} color
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
	
	// Other common functions

	/**
	 * Return the gray-scale color value for this {@link RGB} color.
	 * 
	 * @return the floating-point value
	 */
	public float asGrayScaleColor() {
		return (float) (this.red * 0.3f + this.green * 0.59f + this.blue * 0.11f);
	}
	
	/**
	 * Return this color as an <code>int[]</code> array with the red, green and
	 * blue channels in order.
	 * 
	 * @return the <code>int[]</code> array
	 */
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
