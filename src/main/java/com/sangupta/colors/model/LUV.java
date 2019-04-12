/**
 * colors - Strongly typed immutable color models
 * Copyright (c) 2017-present, Sandeep Gupta
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

import com.sangupta.colors.ColorModel;

/**
 * CIE LUV color model definition. Refer https://en.wikipedia.org/wiki/CIELUV
 * for more details.
 * 
 * @author sangupta
 * @since 1.0.0
 */
public class LUV implements ColorModel {

	public final float l;
	
	public final float u;
	
	public final float v;
	
	public LUV(float[] luv) {
		if(luv == null) {
			throw new IllegalArgumentException("LUV color cannot be null");
		}
		
		if(luv.length != 3) {
			throw new IllegalArgumentException("LUV color array needs exactly 3 elements");
		}
		
		this.l = luv[0];
		this.u = luv[1];
		this.v = luv[2];
	}
	
	public LUV(float l, float u, float v) {
		this.l = l;
		this.u = u;
		this.v = v;
	}
	
	public float[] asArray() {
		return new float[] { this.l, this.u, this.v };
	}
	
	@Override
	public String toString() {
		return "LUV(" + this.l + ", " + this.u + ", " + this.v + ")";
	}
	
	@Override
	public int hashCode() {
		return new Float(this.l * 3100 + this.u * 1700 + this.v * 100).intValue();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) {
			return false;
		}
		
		if(this == obj) {
			return true;
		}
		
		if(!(obj instanceof LUV)) {
			return false;
		}
		
		LUV color = (LUV) obj;
		return this.l == color.l && this.u == color.u && this.v == color.v;
	}
	
}
