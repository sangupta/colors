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
 * Yxy Color Model
 * 
 * Refer
 * https://en.wikipedia.org/wiki/CIE_1931_color_space
 * http://chemaguerra.com/cie-xyz-yxy-rgb-and-gamuts/
 * 
 * @author sangupta
 * @since 1.0.0
 */
public class Yxy {

	public final float Y;
	
	public final float x;
	
	public final float y;
	
	public Yxy(float[] yxy) {
		if(yxy == null) {
			throw new IllegalArgumentException("Yxy array cannot be null");
		}
		
		if(yxy.length != 3) {
			throw new IllegalArgumentException("Yxy array needs exactly 3 elements");
		}
		
		this.Y = yxy[0];
		this.x = yxy[1];
		this.y = yxy[2];
	}
	
	public Yxy(float Y, float x, float y) {
		this.Y = Y;
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Return this color as a <code>float[]</code> array with the Y, x and y
	 * values in order.
	 * 
	 * @return the <code>float[]</code> array
	 */
	public float[] asArray() {
		return new float[] { this.Y, this.x, this.y };
	}
	
	@Override
	public String toString() {
		return "Yxy(" + this.Y + ", " + this.x + ", " + this.y + ")";
	}
	
	@Override
	public int hashCode() {
		return new Float(this.Y * 3100 + this.x * 1700 + this.y * 100).intValue();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) {
			return false;
		}
		
		if(this == obj) {
			return true;
		}
		
		if(!(obj instanceof Yxy)) {
			return false;
		}
		
		Yxy color = (Yxy) obj;
		return this.Y == color.Y && this.x == color.x && this.y == color.y;
	}
}
