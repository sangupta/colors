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
 * Cyan-Magenta-Yellow color model.
 * 
 * Refer <a href=
 * "http://www.mat.univie.ac.at/~kriegl/Skripten/CG/node13.html">http://www.mat.univie.ac.at/~kriegl/Skripten/CG/node13.html</a>
 * for more details.
 * 
 * @author sangupta
 * @since 1.0.0
 */
public class CMY {
	
	/**
	 * the cyan component
	 */
	public final float cyan;
	
	/**
	 * the magenta component
	 */
	public final float magenta;
	
	/**
	 * the yellow component
	 */
	public final float yellow;

	/**
	 * Create a {@link CMY} color from given values of cyan, magenta and yellow
	 * in a 3-member floating point array. All values must be within
	 * <code>0.0f</code> to <code>1.0f</code>
	 * 
	 * @param cmy
	 *            the <code>float[]</code> array
	 * 
	 * @throws IllegalArgumentException
	 *             if array is null, or is not exactly 3-member
	 * 
	 */
	public CMY(float[] cmy) {
		if(cmy == null) {
			throw new IllegalArgumentException("CMY color cannot be null");
		}
		
		if(cmy.length != 3) {
			throw new IllegalArgumentException("CMY color array needs exactly 3 elements");
		}
		
		checkLimit("Cyan", cmy[0]);
		checkLimit("Magneta", cmy[1]);
		checkLimit("Yellow", cmy[2]);
		
		this.cyan = cmy[0];
		this.magenta = cmy[1];
		this.yellow = cmy[2];
	}
	
	/**
	 * Create {@link CMY} color from given values of cyan, magenta and yellow
	 * components. All values must be within <code>0.0f</code> to
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
	 */
	public CMY(float cyan, float magenta, float yellow) {
		checkLimit("Cyan", cyan);
		checkLimit("Magneta", magenta);
		checkLimit("Yellow", yellow);
		
		this.cyan = cyan;
		this.magenta = magenta;
		this.yellow = yellow;
	}
	
	/**
	 * Return this color as a <code>float[]</code> array with the cyan, magenta and
	 * yellow channels in order.
	 * 
	 * @return the <code>float[]</code> array
	 */
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
