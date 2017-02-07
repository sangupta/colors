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

/**
 * Refer
 * https://en.wikipedia.org/wiki/YUV#SDTV_with_BT.601
 * for more details.
 * 
 * @author sangupta
 * @since 1.0.0
 */
public class YUV {
	
	public final float y;
	
	public final float u;
	
	public final float v;
	
	public YUV(float[] yuv) {
		this.y = yuv[0];
		this.u = yuv[1];
		this.v = yuv[2];
	}
	
	public YUV(float y, float u, float v) {
		this.y = y;
		this.u = u;
		this.v = v;
	}
	
	/**
	 * Return this color as a <code>float[]</code> array with the Y, U and V
	 * values in order.
	 * 
	 * @return the <code>float[]</code> array
	 */
	public float[] asArray() {
		return new float[] { this.y, this.u, this.v };
	}
	
	@Override
	public String toString() {
		return "YUV(" + this.y + ", " + this.u + ", " + this.v + ")";
	}
	
	@Override
	public int hashCode() {
		return new Float(this.y * 3100 + this.u * 1700 + this.v * 100).intValue();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) {
			return false;
		}
		
		if(this == obj) {
			return true;
		}
		
		if(!(obj instanceof YUV)) {
			return false;
		}
		
		YUV color = (YUV) obj;
		return this.y == color.y && this.u == color.u && this.v == color.v;
	}

	public static enum YUVQuality {
		
		SDTV,
		
		BT_601,
		
		HDTV,
		
		BT_709;
		
	}
}
