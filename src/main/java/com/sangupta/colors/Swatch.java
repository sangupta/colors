package com.sangupta.colors;

import java.util.ArrayList;
import java.util.List;

import com.sangupta.jerry.util.AssertUtils;

/**
 * A swatch is an active palette of colors.
 * 
 * @author sangupta
 *
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
		if(AssertUtils.isEmpty(colors)) {
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
