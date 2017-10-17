package com.sangupta.colors;

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
