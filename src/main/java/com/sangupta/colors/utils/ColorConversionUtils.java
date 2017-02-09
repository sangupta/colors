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

package com.sangupta.colors.utils;

import java.awt.Color;

import com.sangupta.colors.model.CMY;
import com.sangupta.colors.model.HSB;
import com.sangupta.colors.model.HSI;
import com.sangupta.colors.model.HSL;
import com.sangupta.colors.model.HunterLAB;
import com.sangupta.colors.model.RGB;
import com.sangupta.colors.model.XYZ;
import com.sangupta.colors.model.XYZ.XYZIlluminant;
import com.sangupta.colors.model.YIQ;
import com.sangupta.colors.model.YUV.YUVQuality;
import com.sangupta.colors.model.Yxy;

/**
 * Utility functions to work with colors.
 * 
 * Refer 
 * 
 * * http://www.easyrgb.com/index.php?X=MATH&H=06
 * * https://imagej.nih.gov/ij/plugins/download/Color_Space_Converter.java
 * * https://gist.github.com/rzhukov/9129585
 * 
 * for more details.
 * 
 * @author sangupta
 * @since 1.0.0
 */
public class ColorConversionUtils {
	
	/**
	 * Convert and return CMYK float array from a given {@link RGB}
	 * 
	 * @param color
	 *            the {@link RGB}
	 * 
	 * @return <code>float</code>-array with 4 elements
	 */
	public static float[] RGBtoCMYK(RGB color) {
		float[] cmyk = new float[4];
		RGBtoCMYK(color, cmyk);
		return cmyk;
	}
	
	public static void RGBtoCMYK(RGB color, float[] cmyk) {
		float cyan = 1 - color.red / 255f;
		float magenta = 1 - color.green / 255f;
		float yellow = 1 - color.blue / 255f;
		
		float black = Math.min(cyan, Math.min(magenta, yellow));
		
		if(black == 1f) {
			cmyk[0] = 0f;
			cmyk[1] = 0f;
			cmyk[2] = 0f;
			cmyk[3] = 1f;
			return;
		}
		
		float divider = 1 - black;
		cyan = (cyan - black) / divider;
		magenta = (magenta - black) / divider;
		yellow = (yellow - black) / divider;
		
		cmyk[0] = cyan;
		cmyk[1] = magenta;
		cmyk[2] = yellow;
		cmyk[3] = black;
	}
	
	public static float[] RGBtoHSB(RGB rgbColor) {
		if(rgbColor == null) {
			throw new IllegalArgumentException("Color cannot be null");
		}
		
		float[] hsb = new float[3];
		RGBtoHSB(rgbColor, hsb);
		return hsb;
	}
	
	public static void RGBtoHSB(RGB rgbColor, float[] hsb) {
		if(rgbColor == null) {
			throw new IllegalArgumentException("Color cannot be null");
		}
		
		Color.RGBtoHSB(rgbColor.red, rgbColor.green, rgbColor.blue, hsb);
	}
	
	public static float[] RGBtoHSL(RGB rgbColor) {
		if(rgbColor == null) {
			throw new IllegalArgumentException("Color cannot be null");
		}
		
		float[] hsl = new float[3];
		RGBtoHSL(rgbColor, hsl);
		return hsl;
	}
	
	public static void RGBtoHSL(RGB rgbColor, float[] hsl) {
		if(rgbColor == null) {
			throw new IllegalArgumentException("Color cannot be null");
		}
		
		final float redFloat = rgbColor.red / 255f;
        final float greenFloat = rgbColor.green / 255f;
        final float blueFloat = rgbColor.blue / 255f;

        final float max = Math.max(redFloat, Math.max(greenFloat, blueFloat));
        final float min = Math.min(redFloat, Math.min(greenFloat, blueFloat));
        final float deltaMaxMin = max - min;

        float hue, saturation;
        float lumin = (max + min) / 2f;

        if (max == min) {
            // Monochromatic
            hue = saturation = 0f;
        } else {
            if (max == redFloat) {
                hue = ((greenFloat - blueFloat) / deltaMaxMin) % 6f;
            } else if (max == greenFloat) {
                hue = ((blueFloat - redFloat) / deltaMaxMin) + 2f;
            } else {
                hue = ((redFloat - greenFloat) / deltaMaxMin) + 4f;
            }

            saturation =  deltaMaxMin / (1f - Math.abs(2f * lumin - 1f));
        }

        hue = (hue * 60f) % 360f;
        if(hue < 0) {
        	hue = 360f + hue;
        }
        
        hsl[0] = hue;
        hsl[1] = saturation * 100f;
        hsl[2] = lumin * 100f;
	}
	
	public static int[] HSLtoRGB(HSL hsl) {
		return HSLtoRGB(hsl.hue, hsl.saturation, hsl.luminosity);
	}
	
	public static int[] HSLtoRGB (float[] hsl) {
		return HSLtoRGB(hsl);
	}

	public static int[] HSLtoRGB(final float hue, final float saturation, final float luminosity) {
		int[] rgb = new int[3];
		
        final float c = (1f - Math.abs(2 * luminosity - 1f)) * saturation;
        final float m = luminosity - 0.5f * c;
        final float x = c * (1f - Math.abs((hue / 60f % 2f) - 1f));
        final int hueSegment = (int) hue / 60;
        int red = 0, green = 0, blue = 0;
        
        switch (hueSegment) {
            case 0:
                red = Math.round(255 * (c + m));
                green = Math.round(255 * (x + m));
                blue = Math.round(255 * m);
                break;
            case 1:
                red = Math.round(255 * (x + m));
                green = Math.round(255 * (c + m));
                blue = Math.round(255 * m);
                break;
            case 2:
                red = Math.round(255 * m);
                green = Math.round(255 * (c + m));
                blue = Math.round(255 * (x + m));
                break;
            case 3:
                red = Math.round(255 * m);
                green = Math.round(255 * (x + m));
                blue = Math.round(255 * (c + m));
                break;
            case 4:
                red = Math.round(255 * (x + m));
                green = Math.round(255 * m);
                blue = Math.round(255 * (c + m));
                break;
            case 5:
            case 6:
                red = Math.round(255 * (c + m));
                green = Math.round(255 * m);
                blue = Math.round(255 * (x + m));
                break;
        }
        
        red = Math.max(0, Math.min(255, red));
        green = Math.max(0, Math.min(255, green));
        blue = Math.max(0, Math.min(255, blue));
        
        rgb[0] = red;
        rgb[1] = green;
        rgb[2] = blue;
        
        return rgb;
    }
	
	public static float[] RGBtoXYZ(RGB color) {
		float[] xyz = new float[3];
		RGBtoXYZ(color, xyz);
		return xyz;
	}
	
	public static void RGBtoXYZ(RGB color, float[] xyz) {
		float normalizer = 1.0f / 0.17697f;
		
		xyz[0] = normalizer * (0.490f * color.red) + (0.310f * color.green) + (0.20f * color.blue);
		xyz[1] = normalizer * (0.17697f * color.red) + (0.8124f * color.green) + (0.01063f * color.blue);
		xyz[2] = normalizer * (0.0f * color.red) + (0.01f * color.green) + (0.99f * color.blue);
	}
	
	public static int[] XYZtoRGB(XYZ color) {
		int[] rgb = new int[3];
		
		Float red = (0.41847f * color.x) - (0.15866f * color.y) - (0.082835f * color.z);
		Float green = (-0.091169f * color.x) + (0.25243f * color.y) + (0.015708f * color.z);
		Float blue = (0.0009209f * color.x) - (0.0025498f * color.y) + (0.17860f * color.z);
		
		rgb[0] = red.intValue();
		rgb[1] = green.intValue();
		rgb[2] = blue.intValue();
		
		return rgb;
	}
	
	public static double[] LABtoXYZ(double L, double a, double b, XYZIlluminant whitePoint) {
		double[] result = new double[3];

		double y = (L + 16.0) / 116.0;
		double y3 = Math.pow(y, 3.0);
		double x = (a / 500.0) + y;
		double x3 = Math.pow(x, 3.0);
		double z = y - (b / 200.0);
		double z3 = Math.pow(z, 3.0);

		if (y3 > 0.008856) {
			y = y3;
		} else {
			y = (y - (16.0 / 116.0)) / 7.787;
		}
		if (x3 > 0.008856) {
			x = x3;
		} else {
			x = (x - (16.0 / 116.0)) / 7.787;
		}
		if (z3 > 0.008856) {
			z = z3;
		} else {
			z = (z - (16.0 / 116.0)) / 7.787;
		}

		result[0] = x * whitePoint.x2();
		result[1] = y * whitePoint.y2();
		result[2] = z * whitePoint.z2();

		return result;
	}
	
	public static double[] XYZtoLAB(double X, double Y, double Z, XYZIlluminant whitePoint) {

	      double x = X / whitePoint.x2();
	      double y = Y / whitePoint.y2();
	      double z = Z / whitePoint.z2();

	      if (x > 0.008856) {
	        x = Math.pow(x, 1.0 / 3.0);
	      }
	      else {
	        x = (7.787 * x) + (16.0 / 116.0);
	      }
	      if (y > 0.008856) {
	        y = Math.pow(y, 1.0 / 3.0);
	      }
	      else {
	        y = (7.787 * y) + (16.0 / 116.0);
	      }
	      if (z > 0.008856) {
	        z = Math.pow(z, 1.0 / 3.0);
	      }
	      else {
	        z = (7.787 * z) + (16.0 / 116.0);
	      }

	      double[] result = new double[3];

	      result[0] = (116.0 * y) - 16.0;
	      result[1] = 500.0 * (x - y);
	      result[2] = 200.0 * (y - z);

	      return result;
	    }

	/**
	 * Convert between {@link RGB} to {@link CMY}.
	 * 
	 * @param red
	 * @param green
	 * @param blue
	 * @return
	 */
	public static float[] RGBtoCMY(int red, int green, int blue) {
		float[] cmy = new float[3];
		
		cmy[0] = 1 - (red / 255f);
		cmy[1] = 1 - (green / 255f);
		cmy[2] = 1 - (blue / 255f);
		
		return cmy;
	}

	/**
	 * Convert between {@link CMY} to {@link RGB}.
	 * 
	 * @param cyan
	 * @param magenta
	 * @param yellow
	 * @return
	 */
	public static int[] CMYtoRGB(float cyan, float magenta, float yellow) {
		int[] rgb = new int[3];
		
		rgb[0] = new Float((1 - cyan) * 255).intValue();
		rgb[1] = new Float((1 - magenta) * 255).intValue();
		rgb[2] = new Float((1 - yellow) * 255).intValue();
		
		return rgb;
	}
	
	public static float[] XYZtoYxy(XYZ xyz) {
		return XYZtoYxy(xyz.x, xyz.y, xyz.z);
	}
	
	/**
	 * Convert between {@link XYZ} to {@link Yxy}.
	 * 
	 * @param X
	 * @param Y
	 * @param Z
	 * @return
	 */
	public static float[] XYZtoYxy(float X, float Y, float Z) {
		float[] Yxy = new float[3];
		
		float sum = X + Y + Z;
		
		Yxy[0] = Y;
		Yxy[1] = X / sum;
		Yxy[2] = Y / sum;
		
		return Yxy;
	}
	
	public static float[] YxytoXYZ(Yxy yxy) {
		return YxytoXYZ(yxy.Y, yxy.x, yxy.y);
	}
	
	/**
	 * Convert from {@link Yxy} to {@link XYZ}.
	 * 
	 * @param Y
	 * @param x
	 * @param y
	 * @return
	 */
	public static float[] YxytoXYZ(float Y, float x, float y) {
		float[] XYZ = new float[3];
		float div = Y / y;
		
		XYZ[0] = x * div;
		XYZ[1] = Y;
		XYZ[2] = (1 - x - y) * div;
		
		return XYZ; 
	}
	
	public static float[] XYZtoHLAB(XYZ xyz) {
		return XYZtoHLAB(xyz.x, xyz.y, xyz.z);
	}
	
	/**
	 * Convert from {@link XYZ} to {@link HunterLAB}.
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public static float[] XYZtoHLAB(float x, float y, float z) {
		float[] hlab = new float[3];
		
		float sy = new Double(Math.sqrt(y)).floatValue();
		
		hlab[0] = 10f * sy;
		hlab[1] = 17.5f * (((1.02f * x) - y) / sy);
		hlab[2] = 7.0f * ((y - (0.847f * z)) / sy);
		
		return hlab;
	}
	
	public static float[] HLABtoXYZ(HunterLAB hlab) {
		return HLABtoXYZ(hlab.l, hlab.a, hlab.b);
	}
	
	/**
	 * Convert from {@link HunterLAB} to {@link XYZ}.
	 * 
	 * @param hl
	 * @param ha
	 * @param hb
	 * @return
	 */
	public static float[] HLABtoXYZ(float hl, float ha, float hb) {
		float[] xyz = new float[3];
		
		float varY = hl / 10f;
		float varX = ha / 17.5f * hl / 10.0f;
		float varZ = hb / 7.0f * hl / 10.0f;
		
		xyz[1] = varY * varY;
		xyz[0] = (varX + xyz[1]) / 1.02f;
		xyz[2] = -(varZ - xyz[1]) / 0.847f;
		
		return xyz;
	}
	
	/**
	 * Convert from {@link RGB} to {@link YIQ}.
	 * 
	 * @param rgb
	 * @return
	 */
	public static float[] RGBtoYIQ(RGB rgb) {
		return RGBtoYIQ(rgb.red, rgb.green, rgb.blue);
	}
	
	/**
	 * Convert from {@link RGB} to {@link YIQ}.
	 * 
	 * @param red
	 * @param green
	 * @param blue
	 * @return
	 */
	public static float[] RGBtoYIQ(int red, int green, int blue) {
		float[] yiq = new float[3];
		
		yiq[0] = new Double(0.299d * red + 0.587d * green + 0.114d * blue).floatValue();
		yiq[1] = new Double(0.596d * red - 0.274d * green - 0.332d * blue).floatValue();
		yiq[2] = new Double(0.211d * red - 0.523d * green + 0.312d * blue).floatValue();
		
		return yiq;
	}
	
	/**
	 * Convert from {@link YIQ} to {@link RGB}.
	 * 
	 * @param y
	 * @param i
	 * @param q
	 * @return
	 */
	public static int[] YIQtoRGB(float y, float i, float q) {
		int[] rgb = new int[3];
		
		rgb[0] = new Double(1.0d * y + 0.956d * i + 0.621d * q).intValue();
		rgb[1] = new Double(1.0d * y - 0.272d * i - 0.647d * q).intValue();
		rgb[2] = new Double(1.0d * y - 1.106d * i + 1.703d * q).intValue();
	
		return rgb;
	}
	
	public static float[] RGBtoYUV(RGB rgb, YUVQuality quality) {
		return RGBtoYUV(rgb.red, rgb.green, rgb.blue, quality);
	}
	
	/**
	 * Convert from {@link RGB} to {@link YIQ}.
	 * 
	 * @param red
	 * @param green
	 * @param blue
	 * @return
	 */
	public static float[] RGBtoYUV(int red, int green, int blue, YUVQuality quality) {
		if(quality == null) {
			throw new IllegalArgumentException("YUVQuality cannot be null");
		}
		
		float[] yuv = new float[3];
		
		switch(quality) {
			case SDTV:
			case BT_601:
				yuv[0] = new Double(0.299d * red + 0.587d * green + 0.114d * blue).floatValue();
				yuv[1] = new Double(-0.14173d * red - 0.28886d * green + 0.436d * blue).floatValue();
				yuv[2] = new Double(0.615d * red - 0.51499d * green - 0.10001d * blue).floatValue();
				break;
				
			case HDTV:
			case BT_709:
				yuv[0] = new Double(0.2126d * red + 0.7152d * green + 0.0722d * blue).floatValue();
				yuv[1] = new Double(-0.09991 * red - 0.33609d * green + 0.436d * blue).floatValue();
				yuv[2] = new Double(0.615d * red - 0.55861d * green - 0.05639d * blue).floatValue();
				break;
		}
		
		return yuv;
	}
	
	/**
	 * Convert from {@link YIQ} to {@link RGB}.
	 * 
	 * @param y
	 * @param u
	 * @param v
	 * @return
	 */
	public static int[] YUVtoRGB(float y, float u, float v, YUVQuality quality) {
		if(quality == null) {
			throw new IllegalArgumentException("YUVQuality cannot be null");
		}
		
		int[] rgb = new int[3];
		
		switch(quality) {
			case SDTV:
			case BT_601:
				rgb[0] = new Double(1.0d * y + 1.13983d * v).intValue();
				rgb[1] = new Double(1.0d * y - 0.39465d * u - 0.58060d * v).intValue();
				rgb[2] = new Double(1.0d * y + 2.03211d * u).intValue();
				break;
				
			case HDTV:
			case BT_709:
				rgb[0] = new Double(1.0d * y + 1.28033 * v).intValue();
				rgb[1] = new Double(1.0d * y - 0.21482d * u - 0.38059d * v).intValue();
				rgb[2] = new Double(1.0d * y + 2.12798d * u).intValue();
				break;
		}
	
		return rgb;
	}
	
	/**
	 * Convert from {@link RGB} to {@link HSI}.
	 * 
	 * @param rgb
	 * @return
	 */
	public static float[] RGBtoHSI(RGB rgb) {
		return RGBtoHSI(rgb.red, rgb.green, rgb.blue);
	}
	
	/**
	 * Convert from {@link RGB} to {@link HSI}.
	 * 
	 * @param red
	 * @param green
	 * @param blue
	 * @return
	 */
	public static float[] RGBtoHSI(int red, int green, int blue) {
		double sum = red + green + blue;
		Double intensity = sum / 3.0d;

		double rn = red / sum;
		double gn = green / sum;
		double bn = blue / sum;

		Double hue = Math.acos((0.5 * ((rn - gn) + (rn - bn))) / (Math.sqrt((rn - gn) * (rn - gn) + (rn - bn) * (gn - bn))));
		if(blue > green) {
			hue = 2 * Math.PI - hue;	
		}

		Double saturation = 1 - 3 * Math.min(rn, Math.min(gn, bn));
		
		return new float[] { hue.floatValue(), saturation.floatValue(), intensity.floatValue() };
	}
	
	/**
	 * Convert from {@link HSI} to {@link RGB}.
	 * 
	 * @param hue
	 * @param saturation
	 * @param intensity
	 * @return
	 */
	public static int[] HSItoRGB(double hue, double saturation, double intensity) {
		Double x = intensity * (1 - saturation);
		final double piDivThree = Math.PI / 3.0d;
		
		if (hue < 2 * piDivThree) {
			Double y = intensity * (1 + (saturation * Math.cos(hue)) / (Math.cos(Math.PI / 3 - hue)));
			Double z = 3 * intensity - (x + y);
			
			// *b = x; *r = y; *g = z;
			return new int[] { y.intValue(), z.intValue(), x.intValue() };
		}

		if (hue < 4 * piDivThree) {
			Double y = intensity * (1 + (saturation * Math.cos(hue - 2 * piDivThree)) / (Math.cos(piDivThree - (hue - 2 * piDivThree))));
			Double z = 3 * intensity - (x + y);
			
			// *r = x; *g = y; *b = z;
			return new int[] { x.intValue(), y.intValue(), z.intValue() };
		}

		Double y = intensity * (1 + (saturation * Math.cos(hue - 4 * piDivThree)) / (Math.cos(piDivThree - (hue - 4 * piDivThree))));
		Double z = 3 * intensity - (x + y);
		
		// *r = z; *g = x; *b = y;
		return new int[] { z.intValue(), x.intValue(), y.intValue() };
	}

	public static int[] HSBtoRGB(HSB hsb) {
		return null;
	}
	
}
