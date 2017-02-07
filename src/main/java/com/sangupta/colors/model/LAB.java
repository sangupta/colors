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
 * LAB color model definition.
 * <br><br>
 * References:
 * <br>
 * * https://github.com/StanfordHCI/c3/blob/master/java/src/edu/stanford/vis/color/LAB.java
 * 
 * @author sangupta
 * @since 1.0.0
 */
public class LAB {
	
	public final float l;
	
	public final float a;
	
	public final float b;

	public LAB(float[] lab) {
		if(lab == null) {
			throw new IllegalArgumentException("LAB color cannot be null");
		}
		
		if(lab.length != 3) {
			throw new IllegalArgumentException("LAB color array needs exactly 3 elements");
		}
		
		this.l = lab[0];
		this.a = lab[1];
		this.b = lab[2];
	}
	
	public LAB(float l, float a, float b) {
		this.l = l;
		this.a = a;
		this.b = b;
	}
	
	/**
	 * Compute the ciede2000 distance between this and a given {@link LAB} color
	 * 
	 * @param other
	 *            the secondary given {@link LAB} color
	 * 
	 * @return the <b>ciede2000</b> distance between the two colors
	 */
	public double ciede2000(LAB other) {
		return ciede2000(this, other);
	}
	
	/**
	 * Compute the ciede2000 distance between 2 given {@link LAB} colors.
	 * 
	 * @param color1
	 *            the first {@link LAB} color
	 *            
	 * @param color2
	 *            the second {@link LAB} color
	 * 
	 * @return the <b>ciede2000</b> distance between the two colors
	 */
	public static double ciede2000(LAB color1, LAB color2) {
		// adapted from Sharma et al's MATLAB implementation at
		//  http://www.ece.rochester.edu/~gsharma/ciede2000/

		// parametric factors, use defaults
		double kl = 1, kc = 1, kh = 1;

		// compute terms
		double pi = Math.PI,
		    L1 = color1.l, a1 = color1.a, b1 = color1.b, Cab1 = Math.sqrt(a1*a1 + b1*b1),
		    L2 = color2.l, a2 = color2.a, b2 = color2.b, Cab2 = Math.sqrt(a2*a2 + b2*b2),
		    Cab = 0.5*(Cab1 + Cab2),
		    G = 0.5*(1 - Math.sqrt(Math.pow(Cab,7)/(Math.pow(Cab,7)+Math.pow(25,7)))),
		    ap1 = (1+G) * a1,
		    ap2 = (1+G) * a2,
		    Cp1 = Math.sqrt(ap1*ap1 + b1*b1),
		    Cp2 = Math.sqrt(ap2*ap2 + b2*b2),
		    Cpp = Cp1 * Cp2;

		// ensure hue is between 0 and 2pi
		double hp1 = Math.atan2(b1, ap1); if (hp1 < 0) hp1 += 2*pi;
		double hp2 = Math.atan2(b2, ap2); if (hp2 < 0) hp2 += 2*pi;

		double dL = L2 - L1,
		       dC = Cp2 - Cp1,
		      dhp = hp2 - hp1;

		if (dhp > +pi) dhp -= 2*pi;
		if (dhp < -pi) dhp += 2*pi;
		if (Cpp == 0) dhp = 0;

		// Note that the defining equations actually need
		// signed Hue and chroma differences which is different
		// from prior color difference formulae
		double dH = 2 * Math.sqrt(Cpp) * Math.sin(dhp/2);

		// Weighting functions
		double Lp = 0.5 * (L1 + L2),
		       Cp = 0.5 * (Cp1 + Cp2);

		// Average Hue Computation
		// This is equivalent to that in the paper but simpler programmatically.
		// Average hue is computed in radians and converted to degrees where needed
		double hp = 0.5 * (hp1 + hp2);
		// Identify positions for which abs hue diff exceeds 180 degrees 
		if (Math.abs(hp1-hp2) > pi) hp -= pi;
		if (hp < 0) hp += 2*pi;

		// Check if one of the chroma values is zero, in which case set 
		// mean hue to the sum which is equivalent to other value
		if (Cpp == 0) hp = hp1 + hp2;

		double Lpm502 = (Lp-50) * (Lp-50),
		    Sl = 1 + 0.015*Lpm502 / Math.sqrt(20+Lpm502),
		    Sc = 1 + 0.045*Cp,
		    T = 1 - 0.17*Math.cos(hp - pi/6)
		          + 0.24*Math.cos(2*hp)
		          + 0.32*Math.cos(3*hp+pi/30)
		          - 0.20*Math.cos(4*hp - 63*pi/180),
		    Sh = 1 + 0.015 * Cp * T,
		    ex = (180/pi*hp-275) / 25,
		    delthetarad = (30*pi/180) * Math.exp(-1 * (ex*ex)),
		    Rc =  2 * Math.sqrt(Math.pow(Cp,7) / (Math.pow(Cp,7) + Math.pow(25,7))),
		    RT = -1 * Math.sin(2*delthetarad) * Rc;

		dL = dL / (kl*Sl);
		dC = dC / (kc*Sc);
		dH = dH / (kh*Sh);

		// The CIE 00 color difference
		return Math.sqrt(dL*dL + dC*dC + dH*dH + RT*dC*dH);
	}
	
	/**
	 * Check if this {@link LAB} color is in RGB Gamut or not.
	 * 
	 * @return <code>true</code> if color is in RGB Gamut, <code>false</code>
	 *         otherwise
	 */
	public boolean isInRGBGamut() {
		// first, map CIE L*a*b* to CIE XYZ
		double y = (this.l + 16) / 116;
		double x = y + this.a / 500;
		double z = y - this.b / 200;

		// D65 standard referent
		double X = 0.950470, Y = 1.0, Z = 1.088830;

		x = X * (x > 0.206893034 ? x * x * x : (x - 4.0 / 29) / 7.787037);
		y = Y * (y > 0.206893034 ? y * y * y : (y - 4.0 / 29) / 7.787037);
		z = Z * (z > 0.206893034 ? z * z * z : (z - 4.0 / 29) / 7.787037);

		// second, map CIE XYZ to sRGB
		double r = 3.2404542 * x - 1.5371385 * y - 0.4985314 * z;
		double g = -0.9692660 * x + 1.8760108 * y + 0.0415560 * z;
		double b = 0.0556434 * x - 0.2040259 * y + 1.0572252 * z;
		r = r <= 0.00304 ? 12.92 * r : 1.055 * Math.pow(r, 1 / 2.4) - 0.055;
		g = g <= 0.00304 ? 12.92 * g : 1.055 * Math.pow(g, 1 / 2.4) - 0.055;
		b = b <= 0.00304 ? 12.92 * b : 1.055 * Math.pow(b, 1 / 2.4) - 0.055;

		// third, check sRGB values
		return !(r < 0 || r > 1 || g < 0 || g > 1 || b < 0 || b > 1);
	}
	
	/**
	 * Return this color as a <code>float[]</code> array with the L, A and B values in order.
	 * 
	 * @return the <code>float[]</code> array
	 */
	public float[] asArray() {
		return new float[] { this.l, this.a, this.b };
	}
	
	@Override
	public String toString() {
		return "LAB(" + this.l + ", " + this.a + ", " + this.b + ")";
	}
	
	@Override
	public int hashCode() {
		return new Float(this.l * 3100 + this.a * 1700 + this.b * 100).intValue();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) {
			return false;
		}
		
		if(this == obj) {
			return true;
		}
		
		if(!(obj instanceof LAB)) {
			return false;
		}
		
		LAB color = (LAB) obj;
		return this.l == color.l && this.a == color.a && this.b == color.b;
	}
}
