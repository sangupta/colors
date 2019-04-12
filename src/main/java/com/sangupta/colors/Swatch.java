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

import java.util.ArrayList;
import java.util.List;

/**
 * A swatch is an active palette of colors.
 * 
 * @author sangupta
 * @since 1.0.0
 */
public class Swatch<T extends ColorModel> {

	/**
	 * Colors that are part of this {@link Swatch}
	 */
	public final List<T> palette = new ArrayList<>();
	
	/**
	 * Create a new {@link Swatch} which is empty
	 * 
	 */
	public Swatch() {
		
	}
	
	/**
	 * Create a new {@link Swatch} and populate it with given colors.
	 * 
	 * @param colors
	 */
	public Swatch(T[] colors) {
		if(colors == null || colors.length == 0) {
			return;
		}
		
		for(T color : colors) {
			this.palette.add(color);
		}
	}

	/**
	 * Add the given {@link ColorModel} to the {@link Swatch}
	 * 
	 * @param color
	 */
	public void add(T color) {
		if(color == null) {
			return;
		}
		
		this.palette.add(color);
	}
	
}
