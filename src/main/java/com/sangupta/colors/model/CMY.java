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

import com.sangupta.colors.utils.ColorConversionUtils;

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
	
	public final float cyan;
	
	public final float magenta;
	
	public final float yellow;

	public CMY(float[] cmy) {
		if(cmy == null) {
			throw new IllegalArgumentException("CMY color cannot be null");
		}
		
		if(cmy.length != 3) {
			throw new IllegalArgumentException("CMY color array needs exactly 3 elements");
		}
		
		this.cyan = cmy[0];
		this.magenta = cmy[1];
		this.yellow = cmy[2];
	}
	
	public CMY(float cyan, float magenta, float yellow) {
		this.cyan = cyan;
		this.magenta = magenta;
		this.yellow = yellow;
	}
	
	public CMY(RGB rgbColor) {
		if(rgbColor == null) {
			throw new IllegalArgumentException("RGB color cannot be null");
		}
		
		float[] cmy = ColorConversionUtils.RGBtoCMY(rgbColor.red, rgbColor.green, rgbColor.blue);
		
		this.cyan = cmy[0];
		this.magenta = cmy[1];
		this.yellow = cmy[2];
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
	
}
