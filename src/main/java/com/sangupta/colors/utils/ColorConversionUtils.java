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
import com.sangupta.colors.model.CMYK;
import com.sangupta.colors.model.HSB;
import com.sangupta.colors.model.HSI;
import com.sangupta.colors.model.HSL;
import com.sangupta.colors.model.HunterLAB;
import com.sangupta.colors.model.LAB;
import com.sangupta.colors.model.LCH;
import com.sangupta.colors.model.RGB;
import com.sangupta.colors.model.XYZ;
import com.sangupta.colors.model.XYZ.XYZIlluminant;
import com.sangupta.colors.model.YIQ;
import com.sangupta.colors.model.YUV.YUVQuality;
import com.sangupta.colors.model.Yxy;

/**
 * Utility functions to work with colors.
 * 
 * <br><br>
 * 
 * Refer the following for more details: 
 * 
 * <ul>
 * <li><a href="http://www.easyrgb.com/index.php?X=MATH&H=06">http://www.easyrgb.com/index.php?X=MATH&H=06</a></li>
 * <li><a href="https://imagej.nih.gov/ij/plugins/download/Color_Space_Converter.java">https://imagej.nih.gov/ij/plugins/download/Color_Space_Converter.java</a></li>
 * <li><a href="https://gist.github.com/rzhukov/9129585">https://gist.github.com/rzhukov/9129585</a></li>
 * <li><a href="https://github.com/Qix-/color-convert/blob/master/conversions.js">https://github.com/Qix-/color-convert/blob/master/conversions.js</a></li>
 * </ul>
 * 
 * @author sangupta
 * @since 1.0.0
 */
public class ColorConversionUtils {
	
	/**
	 * Convert a {@link RGB} color to {@link CMYK} color.
	 * 
	 * @param rgb
	 * @return
	 */
	public static CMYK RGBtoCMYK(RGB rgb) {
		float cyan = 1 - rgb.red / 255f;
		float magenta = 1 - rgb.green / 255f;
		float yellow = 1 - rgb.blue / 255f;
		
		float black = Math.min(cyan, Math.min(magenta, yellow));
		
		if(black == 1f) {
			return new CMYK(0f, 0f, 0f, 1f);
		}
		
		float divider = 1 - black;
		cyan = (cyan - black) / divider;
		magenta = (magenta - black) / divider;
		yellow = (yellow - black) / divider;
		
		return new CMYK(cyan, magenta, yellow, black);
	}
	
	/**
	 * Convert a {@link CMYK} color to {@link RGB} color.
	 * 
	 * @param cmyk
	 * @return
	 */
	public static RGB CMYKtoRGB(CMYK cmyk) {
		// TODO: fix this
		return null;
	}
	
	/**
	 * Convert a {@link RGB} color to {@link HSB} color.
	 * 
	 * @param rgbColor
	 * @return
	 */
	public static HSB RGBtoHSB(RGB rgbColor) {
		if(rgbColor == null) {
			throw new IllegalArgumentException("Color cannot be null");
		}
		
		float[] hsb = new float[3];
		
		Color.RGBtoHSB(rgbColor.red, rgbColor.green, rgbColor.blue, hsb);
		return new HSB(hsb);
	}
	
	/**
	 * Convert a {@link HSB} color to {@link RGB} color.
	 * 
	 * @param hsb
	 * @return
	 */
	public static int[] HSBtoRGB(HSB hsb) {
		// TODO: fix this
		return null;
	}
	
	/**
	 * Convert a {@link RGB} color to {@link HSL} color.
	 * 
	 * @param rgb
	 * @return
	 */
	public static HSL RGBtoHSL(RGB rgb) {
		if(rgb == null) {
			throw new IllegalArgumentException("Color cannot be null");
		}
		
		final float redFloat = rgb.red / 255f;
        final float greenFloat = rgb.green / 255f;
        final float blueFloat = rgb.blue / 255f;

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
        
        return new HSL(hue, saturation * 100f, lumin * 100f);
	}

	/**
	 * Convert a {@link HSL} color to {@link RGB} color.
	 * 
	 * @param hsl
	 * @return
	 */
	public static RGB HSLtoRGB(HSL hsl) {
        final float c = (1f - Math.abs(2 * hsl.luminosity - 1f)) * hsl.saturation;
        final float m = hsl.luminosity - 0.5f * c;
        final float x = c * (1f - Math.abs((hsl.hue / 60f % 2f) - 1f));
        final int hueSegment = (int) hsl.hue / 60;
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
        
        return new RGB(red, green, blue);
    }
	
	/**
	 * Convert from {@link RGB} color to {@link XYZ} color.
	 * 
	 * @param rgbColor
	 * @return
	 */
	public static XYZ RGBtoXYZ(RGB rgbColor) {
		float normalizer = 1.0f / 0.17697f;
		
		float x = normalizer * (0.490f * rgbColor.red) + (0.310f * rgbColor.green) + (0.20f * rgbColor.blue);
		float y = normalizer * (0.17697f * rgbColor.red) + (0.8124f * rgbColor.green) + (0.01063f * rgbColor.blue);
		float z = normalizer * (0.0f * rgbColor.red) + (0.01f * rgbColor.green) + (0.99f * rgbColor.blue);
		
		return new XYZ(x, y, z);
	}

	/**
	 * Convert from {@link XYZ} color to {@link RGB} color.
	 * 
	 * @param xyzColor
	 * @return
	 */
	public static RGB XYZtoRGB(XYZ xyzColor) {
		Float red = (0.41847f * xyzColor.x) - (0.15866f * xyzColor.y) - (0.082835f * xyzColor.z);
		Float green = (-0.091169f * xyzColor.x) + (0.25243f * xyzColor.y) + (0.015708f * xyzColor.z);
		Float blue = (0.0009209f * xyzColor.x) - (0.0025498f * xyzColor.y) + (0.17860f * xyzColor.z);
		
		return new RGB(red.intValue(), green.intValue(), blue.intValue());
	}
	
	/**
	 * Convert from {@link LAB} color to {@link XYZ} color.
	 * 
	 * @param lab
	 * @param whitePoint
	 * @return
	 */
	public static XYZ LABtoXYZ(LAB lab, XYZIlluminant whitePoint) {
		double y = (lab.l + 16.0) / 116.0;
		double y3 = Math.pow(y, 3.0);
		double x = (lab.a / 500.0) + y;
		double x3 = Math.pow(x, 3.0);
		double z = y - (lab.b / 200.0);
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

		Double xx = x * whitePoint.x2();
		Double yy = y * whitePoint.y2();
		Double zz = z * whitePoint.z2();

		return new XYZ(xx.floatValue(), yy.floatValue(), zz.floatValue());
	}
	
	/**
	 * Convert from {@link XYZ} color to {@link LAB} color.
	 * 
	 * @param xyz
	 * @param whitePoint
	 * @return
	 */
	public static LAB XYZtoLAB(XYZ xyz, XYZIlluminant whitePoint) {
	      double x = xyz.x / whitePoint.x2();
	      double y = xyz.y / whitePoint.y2();
	      double z = xyz.z / whitePoint.z2();

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

	      Double l = (116.0 * y) - 16.0;
	      Double a = 500.0 * (x - y);
	      Double b = 200.0 * (y - z);

	      return new LAB(l.floatValue(), a.floatValue(), b.floatValue());
	    }

	/**
	 * Convert from {@link RGB} color to {@link CMY} color.
	 * 
	 * @param red
	 * @param green
	 * @param blue
	 * @return
	 */
	public static CMY RGBtoCMY(RGB rgbColor) {
		float cyan = 1 - (rgbColor.red / 255f);
		float magenta = 1 - (rgbColor.green / 255f);
		float yellow = 1 - (rgbColor.blue / 255f);
		
		return new CMY(cyan, magenta, yellow);
	}

	/**
	 * Convert from {@link CMY} color to {@link RGB} color.
	 * 
	 * @param cyan
	 * @param magenta
	 * @param yellow
	 * @return
	 */
	public static RGB CMYtoRGB(CMY cmyColor) {
		int red = new Float((1 - cmyColor.cyan) * 255).intValue();
		int green = new Float((1 - cmyColor.magenta) * 255).intValue();
		int blue = new Float((1 - cmyColor.yellow) * 255).intValue();
		
		return new RGB(red, green, blue);
	}
	
	/**
	 * Convert from {@link XYZ} color to {@link Yxy} color.
	 * 
	 * @param xyz
	 * @return
	 */
	public static Yxy XYZtoYxy(XYZ xyz) {
		
		float sum = xyz.x + xyz.y + xyz.z;
		
		float YY = xyz.y;
		float xx = xyz.x / sum;
		float yy = xyz.y / sum;
		
		return new Yxy(YY, xx, yy);
	}
	
	/**
	 * Convert from {@link Yxy} color to {@link XYZ} color.
	 * 
	 * @param yxy
	 * @return
	 */
	public static XYZ YxytoXYZ(Yxy yxy) {
		float div = yxy.Y / yxy.y;
		
		float xx = yxy.x * div;
		float yy = yxy.Y;
		float zz = (1 - yxy.x - yxy.y) * div;
		
		return new XYZ(xx, yy, zz);
	}
	
	/**
	 * Convert from {@link XYZ} color to {@link HunterLAB} color.
	 * 
	 * @param xyz
	 * @return
	 */
	public static HunterLAB XYZtoHLAB(XYZ xyz) {
		float sy = new Double(Math.sqrt(xyz.y)).floatValue();
		
		float l = 10f * sy;
		float a = 17.5f * (((1.02f * xyz.x) - xyz.y) / sy);
		float b = 7.0f * ((xyz.y - (0.847f * xyz.z)) / sy);
		
		return new HunterLAB(l, a, b);
	}
	
	/**
	 * Convert from {@link HunterLAB} color to {@link XYZ} color.
	 * 
	 * @param hlab
	 * @return
	 */
	public static XYZ HLABtoXYZ(HunterLAB hlab) {
		
		float varY = hlab.l / 10f;
		float varX = hlab.a / 17.5f * hlab.l / 10.0f;
		float varZ = hlab.b / 7.0f * hlab.l / 10.0f;
		
		float y = varY * varY;
		float x = (varX + y) / 1.02f;
		float z = -(varZ - y) / 0.847f;
		
		return new XYZ(x, y, z);
	}
	
	/**
	 * Convert from {@link RGB} color to {@link YIQ} color.
	 * 
	 * @param rgb
	 * @return
	 */
	public static YIQ RGBtoYIQ(RGB rgb) {
		Double y = 0.299d * rgb.red + 0.587d * rgb.green + 0.114d * rgb.blue;
		Double i = 0.596d * rgb.red - 0.274d * rgb.green - 0.332d * rgb.blue;
		Double q = 0.211d * rgb.red - 0.523d * rgb.green + 0.312d * rgb.blue;
		
		return new YIQ(y.floatValue(), i.floatValue(), q.floatValue());
	}
	
	/**
	 * Convert from {@link YIQ} color to {@link RGB} color.
	 * 
	 * @param y
	 * @param i
	 * @param q
	 * @return
	 */
	public static RGB YIQtoRGB(YIQ yiq) {
		Double red   = 1.0d * yiq.y + 0.956d * yiq.i + 0.621d * yiq.q;
		Double green = 1.0d * yiq.y - 0.272d * yiq.i - 0.647d * yiq.q;
		Double blue  = 1.0d * yiq.y - 1.106d * yiq.i + 1.703d * yiq.q;
	
		return new RGB(red.intValue(), green.intValue(), blue.intValue());
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

	public static float[] LABtoLCH(LAB lab) {
		return LABtoLCH(lab.l, lab.a, lab.b);
	}
	
	public static float[] LABtoLCH(float l, float a, float b) {
		Double hr = Math.atan2(b, a);
		Double h = hr * 360 / 2 / Math.PI;
		if(h < 0) {
			h = h + 360;
		}
		Double c = Math.sqrt(a * a + b * b);
		
		return new float[] { l, c.floatValue(), h.floatValue() };
	}
	
	public static float[] LCHtoLAB(LCH lch) {
		return LCHtoLAB(lch.lightness, lch.chroma, lch.hue);
	}
	
	public static float[] LCHtoLAB(float lightness, float chroma, float hue) {
		Double hr = hue / 360 * 2 * Math.PI;
		Double a = chroma * Math.cos(hr);
		Double b = chroma * Math.sin(hr);
		
		return new float[] { lightness, a.floatValue(), b.floatValue() };
	}
	
}
