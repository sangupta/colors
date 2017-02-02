package com.sangupta.colors;

/**
 * Illuminance values for XYZ color space.
 * 
 * Reference white in XYZ coordinates
 * 
 * https://imagej.nih.gov/ij/plugins/download/Color_Space_Converter.java
 * http://www.easyrgb.com/index.php?X=MATH&H=15
 * 
 * @author sangupta
 *
 */
public enum Illuminance {
	
	A(109.850f, 100.0f, 35.585f, 111.144f, 100.0f, 35.200f),
	
	C(98.074f, 100.0f, 118.232f, 97.285f, 100.0f, 116.145f),
	
	D50(96.4212f, 100.0f, 82.5188f, 96.720f, 100.0f, 81.427f),
	
	D55(95.6797f, 100.0f, 92.1481f, 95.799f, 100.0f, 90.926f),
	
	D65(95.0429f, 100.0f, 108.8900f, 94.811f, 100.0f, 107.304f),
	
	D75(94.9722f, 100.0f, 122.6394f, 94.416f, 100.0f, 120.641f),
	
	F2(99.187f, 100.0f, 67.395f, 103.280f, 100.0f, 69.026f),
	
	F7(95.044f, 100.0f, 108.755f, 95.792f, 100.0f, 107.687f),
	
	F11(100.966f, 100.0f, 64.370f, 103.866f, 100.0f, 65.627f);
	
	/**
	 * CIE 1931 2-degree values
	 */
	private final float x2, y2, z2;
	
	/**
	 * CIE 1964 10-degree values
	 */
	private final float x10, y10, z10;
	
	private Illuminance(float x2, float y2, float z2, float x10, float y10, float z10) {
		this.x2 = x2;
		this.y2 = y2;
		this.z2 = z2;
		
		this.x10 = x10;
		this.y10 = y10;
		this.z10 = z10;
	}

	public float x2() {
		return this.x2;
	}
	
	public float y2() {
		return this.y2;

	}
	
	public float z2() {
		return this.z2;
	}
	
	public float x10() {
		return this.x10;
	}
	
	public float y10() {
		return this.y10;
	}
	
	public float z10() {
		return this.z10;
	}
}
