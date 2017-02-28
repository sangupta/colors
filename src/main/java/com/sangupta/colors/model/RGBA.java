/**
 * colors - Strongly typed immutable color models
 * Copyright (c) 2017, Sandeep Gupta
 * 
 * https://sangupta.com/projects/colors
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

/**
 * RGBA color model for red, green, blue and alpha channels.
 * All values should be between 0-255 including alpha. The default
 * alpha value is 255 (100% opaque).
 * 
 * @author sangupta
 * @since 1.0.0
 */
public class RGBA extends RGB {
	
	/**
	 * The default alpha value
	 */
	public static final int DEFAULT_RGBA_ALPHA = 255;
	
	/**
	 * The alpha value for this color
	 */
	public final int alpha;

	/**
	 * Construct  a {@link RGBA} from 32-bit color value specified
	 * as AARRGGBB value.
	 * 
	 * @param color
	 */
	public RGBA(int color) {
		super(color);
		this.alpha = color >>> 24;
	}
	
	/**
	 * Construct a {@link RGBA} from a 24-bit color value specified as
	 * RRGGBB value, with the given alpha value.
	 * 
	 * @param color
	 * @param alpha
	 */
	public RGBA(int color, int alpha) {
		super(color);
		
		checkLimit("Alpha", alpha);
		this.alpha = alpha;
	}
	
	/**
	 * Construct a {@link RGBA} from individual values of red, green and blue
	 * channels. The alpha channel is assumed to be at default value.
	 * 
	 * @param red
	 * @param green
	 * @param blue
	 */
	public RGBA(int red, int green, int blue) {
		super(red, green, blue);
		this.alpha = DEFAULT_RGBA_ALPHA;
	}
	
	/**
	 * Construct a {@link RGBA} from individual values of red, green, blue and
	 * alpha channels.
	 * 
	 * @param red
	 * @param green
	 * @param blue
	 * @param alpha
	 */
	public RGBA(int red, int green, int blue, int alpha) {
		super(red, green, blue);
		
		checkLimit("Alpha", alpha);
		this.alpha = alpha;
	}
	
	/**
	 * Construct a {@link RGBA} from another {@link RGB} instance. The alpha channel
	 * is assigned the default value.
	 * 
	 * @param other
	 */
	public RGBA(RGB other) {
		super(other);
		this.alpha = DEFAULT_RGBA_ALPHA;
	}
	
	/**
	 * Construct a {@link RGBA} instance from another {@link RGBA} instance.
	 * 
	 * @param other
	 */
	public RGBA(RGBA other) {
		super(other);
		this.alpha = other.alpha;
	}
	
	public int[] asArray() {
		return new int[] { this.red, this.green, this.blue, this.alpha };
	}
	
	@Override
	public String toString() {
		return "RGBA(" + this.red + ", " + this.green + ", " + this.blue + ", " + this.alpha + ")";
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) {
			return false;
		}
		
		if(this == obj) {
			return true;
		}
		
		if(!(obj instanceof RGBA)) {
			return false;
		}
		
		RGBA color = (RGBA) obj;
		return this.red == color.red && this.green == color.green && this.blue == color.blue && this.alpha == color.alpha;
	}
	
}
