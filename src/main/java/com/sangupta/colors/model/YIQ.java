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
 * YIQ Color model. Refer https://en.wikipedia.org/wiki/YIQ
 * for more details.
 * 
 * @author sangupta
 * @since 1.0.0
 */
public class YIQ {

	public final float y;
	
	public final float i;
	
	public final float q;
	
	public YIQ(float[] yiq) {
		this.y = yiq[0];
		this.i = yiq[1];
		this.q = yiq[2];
		
		this.checkLimit();
	}
	
	public YIQ(float y, float i, float q) {
		this.y = y;
		this.i = i;
		this.q = q;
		
		this.checkLimit();
	}
	
	/**
	 * Return this color as a <code>float[]</code> array with the Y, I and Q
	 * values in order.
	 * 
	 * @return the <code>float[]</code> array
	 */
	public float[] asArray() {
		return new float[] { this.y, this.i, this.q };
	}
	
	@Override
	public String toString() {
		return "YIQ(" + this.y + ", " + this.i + ", " + this.q + ")";
	}
	
	@Override
	public int hashCode() {
		return new Float(this.y * 3100 + this.i * 1700 + this.q * 100).intValue();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) {
			return false;
		}
		
		if(this == obj) {
			return true;
		}
		
		if(!(obj instanceof YIQ)) {
			return false;
		}
		
		YIQ color = (YIQ) obj;
		return this.y == color.y && this.i == color.i && this.q == color.q;
	}
	
	private void checkLimit() {
		if(this.y < 0f || this.y > 1f) {
			throw new IllegalArgumentException("Y component must be between (0, 1), got: " + this.y);
		}
		
		if(this.i < -0.596f || this.i > 0.596f) {
			throw new IllegalArgumentException("I component must be between (-0.596, 0.596), got: " + this.i);
		}
		
		if(this.q < -0.523f || this.q > 0.523f) {
			throw new IllegalArgumentException("I component must be between (-0.523, 0.523), got: " + this.q);
		}
	}
	
}
