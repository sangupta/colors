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
 * XYZ color model as per https://en.wikipedia.org/wiki/CIE_1931_color_space
 * 
 * @author sangupta
 * @since 1.0.0
 */
public class XYZ implements ColorModel {
	
	public final float x;
	
	public final float y;

	public final float z;
	
	public final XYZIlluminant illuminant;
	
	public XYZ(float[] xyz) {
		this(xyz, XYZIlluminant.D65);
	}
	
	public XYZ(float[] xyz, XYZIlluminant illuminant) {
		if(xyz == null) {
			throw new IllegalArgumentException("XYZ array cannot be null");
		}
		
		if(xyz.length != 3) {
			throw new IllegalArgumentException("XYZ array must have exactly 3 elements");
		}
		
		if(illuminant == null) {
			throw new IllegalArgumentException("Illuminant cannot be null");
		}
		
		this.x = xyz[0];
		this.y = xyz[1];
		this.z = xyz[2];
		
		this.illuminant = illuminant;
	}
	
	public XYZ(float x, float y, float z) {
		this(x, y, z, XYZIlluminant.D65);
	}
	
	public XYZ(float x, float y, float z, XYZIlluminant illuminant) {
		if(illuminant == null) {
			throw new IllegalArgumentException("Illuminant cannot be null");
		}
		
		this.x = x;
		this.y = y;
		this.z = z;
		
		this.illuminant = illuminant;
	}
	
	/**
	 * Convert this to {@link HunterLAB} color model
	 * 
	 * @return the {@link HunterLAB} color
	 */
	public HunterLAB hunterLAB() {
		return ColorConversionUtils.XYZtoHLAB(this);
	}
	
	/**
	 * Convert this to {@link Yxy} color model.
	 * 
	 * @return the {@link Yxy} color
	 */
	public Yxy yxy() {
		return ColorConversionUtils.XYZtoYxy(this);
	}
	
	public LUV luv() {
		return ColorConversionUtils.XYZtoLUV(this);
	}
	
	/**
	 * Convert this to {@link RGB} color model.
	 * 
	 * @return the {@link RGB} color
	 */
	public RGB rgb() {
		return ColorConversionUtils.XYZtoRGB(this);
	}
	
	public XYZ multiply(float factor) {
		return new XYZ(this.x * factor, this.y * factor, this.z * factor);
	}
	
	public void normalize() {
		float sum = this.x + this.y + this.z;
        if (sum < 1e-6f) {
            return;
        }
        
        float factor = 1 / sum;
        this.multiply(factor);	
	}
	
	/**
	 * Return this color as a <code>float[]</code> array with the X, Y and Z values in order.
	 * 
	 * @return the <code>float[]</code> array
	 */
	public float[] asArray() {
		return new float[] { this.x, this.y, this.z };
	}
	
	@Override
	public String toString() {
		return "XYZ(" + this.x + ", " + this.y + ", " + this.z + ") @ " + this.illuminant + " illuminant";
	}
	
	@Override
	public int hashCode() {
		return new Float(this.x * 3100 + this.y * 1700 + this.z * 100).intValue();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) {
			return false;
		}
		
		if(this == obj) {
			return true;
		}
		
		if(!(obj instanceof XYZ)) {
			return false;
		}
		
		XYZ color = (XYZ) obj;
		return this.x == color.x && this.y == color.y && this.z == color.z && this.illuminant == color.illuminant;
	}

	/**
	 * Illuminant values for XYZ color space.
	 * 
	 * Reference white in XYZ coordinates
	 * 
	 * https://imagej.nih.gov/ij/plugins/download/Color_Space_Converter.java
	 * http://www.easyrgb.com/index.php?X=MATH&H=15
	 * https://en.wikipedia.org/wiki/Color_temperature
	 * 
	 * @author sangupta
	 *
	 */
	public static enum XYZIlluminant {
		
		A(109.850f, 100.0f, 35.585f, 111.144f, 100.0f, 35.200f),
		
		C(98.074f, 100.0f, 118.232f, 97.285f, 100.0f, 116.145f),
		
		D50(96.4212f, 100.0f, 82.5188f, 96.720f, 100.0f, 81.427f),
		
		D55(95.6797f, 100.0f, 92.1481f, 95.799f, 100.0f, 90.926f),
		
		/**
		 * Refer https://en.wikipedia.org/wiki/Illuminant_D65 for more details.
		 */
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
		
		private XYZIlluminant(float x2, float y2, float z2, float x10, float y10, float z10) {
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
}
