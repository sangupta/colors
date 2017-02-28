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
	
	/**
	 * Create a {@link CMYK} color from given values of cyan, magenta, yellow and
	 * black in a 4-member floating point array. All values must be within
	 * <code>0.0f</code> to <code>1.0f</code>
	 * 
	 * @param cmy
	 *            the <code>float[]</code> array
	 * 
	 * @throws IllegalArgumentException
	 *             if array is null, or is not exactly 4-member
	 * 
	 */
	public CMYK(float[] cmyk) {
		if(cmyk == null) {
			throw new IllegalArgumentException("Array cannot be null");
		}
		
		if(cmyk.length != 4) {
			throw new IllegalArgumentException("Array needs to have 4 elements only");
		}
		
		checkLimit("Cyan", cmyk[0]);
		checkLimit("Magneta", cmyk[1]);
		checkLimit("Yellow", cmyk[2]);
		checkLimit("Black", cmyk[3]);
		
		this.cyan = cmyk[0];
		this.magenta = cmyk[1];
		this.yellow = cmyk[2];
		this.black = cmyk[3];
	}
	
	/**
	 * Create {@link CMYK} color from given values of cyan, magenta, yellow and
	 * black components. All values must be within <code>0.0f</code> to
	 * <code>1.0f</code>
	 * 
	 * @param cyan
	 *            the cyan component
	 * 
	 * @param magenta
	 *            the magenta component
	 * 
	 * @param yellow
	 *            the yellow component
	 * 
	 * @param black
	 *            the black component
	 */
	public CMYK(float cyan, float magenta, float yellow, float black) {
		checkLimit("Cyan", cyan);
		checkLimit("Magneta", magenta);
		checkLimit("Yellow", yellow);
		checkLimit("Black", black);
		
		this.cyan = cyan;
		this.magenta = magenta;
		this.yellow = yellow;
		this.black = black;
	}
	
	/**
	 * Return this color as a <code>float[]</code> array with the cyan, magenta,
	 * yellow and black channels in order.
	 * 
	 * @return the <code>float[]</code> array
	 */
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
	
	/**
	 * Check that component values are within range.
	 * 
	 * @param component
	 * @param value
	 */
	protected void checkLimit(String component, float value) {
		if(value < 0.f || value > 1.0f) {
			throw new IllegalArgumentException(component + " value must be between 0.0f to 1.0f inclusive.");
		}
	}
}
