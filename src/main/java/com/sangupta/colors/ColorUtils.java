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

package com.sangupta.colors;

/**
 * Utility functions.
 * 
 * @author sangupta
 * @since 1.0.0
 */
public class ColorUtils {

	/**
	 * Convert the given <code>double</code> value to an <code>int</code> value
	 * using rounding principles.
	 * 
	 * @param value
	 * @return
	 */
	public static int asInt(double value) {
		Long longValue = Math.round(value);
		return longValue.intValue();
	}

	public static double minimum(double value, double... values) {
		double min = value;
		for(double item : values) {
			if(min > item) {
				min = item;
			}
		}
		
		return min;
	}
	
	public static double maximum(double value, double... values) {
		double max = value;
		for(double item : values) {
			if(max < item) {
				max = item;
			}
		}
		
		return max;
	}
	
}
