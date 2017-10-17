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

import com.sangupta.colors.ColorConversionUtils;
import com.sangupta.colors.ColorModel;

/**
 * LCH color model definition.
 * <br><br>
 * References:
 * <ul>
 * <li>https://en.wikipedia.org/wiki/Lab_color_space#Cylindrical_representation:_CIELCh_or_CIEHLC</li>
 * </ul>
 * 
 * @author sangupta
 *
 */
public class LCH implements ColorModel {

	public final float lightness;
	
	public final float chroma;
	
	public final float hue;
	
	public LCH(float[] lch) {
		if(lch == null) {
			throw new IllegalArgumentException("LCH color cannot be null");
		}
		
		if(lch.length != 3) {
			throw new IllegalArgumentException("LCH color array needs exactly 3 elements");
		}
		
		this.lightness = lch[0];
		this.chroma = lch[1];
		this.hue = lch[2];
	}
	
	public LCH(float lightness, float chroma, float hue) {
		this.lightness = lightness;
		this.chroma = chroma;
		this.hue = hue;
	}
	
	public LAB lab() {
		return ColorConversionUtils.LCHtoLAB(this);
	}
	
	public float[] asArray() {
		return new float[] { this.lightness, this.chroma, this.hue };
	}
	
	@Override
	public String toString() {
		return "LCH(" + this.lightness + ", " + this.chroma + ", " + this.hue + ")";
	}
	
	@Override
	public int hashCode() {
		return new Float(this.lightness * 3100 + this.chroma * 1700 + this.hue * 100).intValue();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) {
			return false;
		}
		
		if(this == obj) {
			return true;
		}
		
		if(!(obj instanceof LCH)) {
			return false;
		}
		
		LCH color = (LCH) obj;
		return this.lightness == color.lightness && this.chroma == color.chroma && this.hue == color.hue;
	}
	
}
