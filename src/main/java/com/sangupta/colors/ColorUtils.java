package com.sangupta.colors;

import java.awt.Color;

/**
 * Utility functions to work with colors.
 * 
 * @author sangupta
 *
 */
public class ColorUtils {
	
	/**
	 * Convert and return CMYK float array from a given {@link RGBColor}
	 * 
	 * @param color
	 *            the {@link RGBColor}
	 * 
	 * @return <code>float</code>-array with 4 elements
	 */
	public static float[] RGBtoCMYK(RGBColor color) {
		float[] cmyk = new float[4];
		RGBtoCMYK(color, cmyk);
		return cmyk;
	}
	
	public static void RGBtoCMYK(RGBColor color, float[] cmyk) {
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
	
	public static float[] RGBtoHSB(RGBColor rgbColor) {
		if(rgbColor == null) {
			throw new IllegalArgumentException("Color cannot be null");
		}
		
		float[] hsb = new float[3];
		RGBtoHSB(rgbColor, hsb);
		return hsb;
	}
	
	public static void RGBtoHSB(RGBColor rgbColor, float[] hsb) {
		if(rgbColor == null) {
			throw new IllegalArgumentException("Color cannot be null");
		}
		
		Color.RGBtoHSB(rgbColor.red, rgbColor.green, rgbColor.blue, hsb);
	}
	
	public static float[] RGBtoHSL(RGBColor rgbColor) {
		if(rgbColor == null) {
			throw new IllegalArgumentException("Color cannot be null");
		}
		
		float[] hsl = new float[3];
		RGBtoHSL(rgbColor, hsl);
		return hsl;
	}
	
	public static void RGBtoHSL(RGBColor rgbColor, float[] hsl) {
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
	
	public static int[] HSLtoRGB (float[] hsl) {
		int[] rgb = new int[3];
		HSLtoRGB(hsl, rgb);
		return rgb;
	}

	public static void HSLtoRGB (float[] hsl, int[] rgb) {
        final float h = hsl[0];
        final float s = hsl[1];
        final float l = hsl[2];
        
        final float c = (1f - Math.abs(2 * l - 1f)) * s;
        final float m = l - 0.5f * c;
        final float x = c * (1f - Math.abs((h / 60f % 2f) - 1f));
        final int hueSegment = (int) h / 60;
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
    }
	
	public static float[] RGBtoXYZ(RGBColor color) {
		float[] xyz = new float[3];
		RGBtoXYZ(color, xyz);
		return xyz;
	}
	
	public static void RGBtoXYZ(RGBColor color, float[] xyz) {
		float normalizer = 1.0f / 0.17697f;
		
		xyz[0] = normalizer * (0.490f * color.red) + (0.310f * color.green) + (0.20f * color.blue);
		xyz[1] = normalizer * (0.17697f * color.red) + (0.8124f * color.green) + (0.01063f * color.blue);
		xyz[2] = normalizer * (0.0f * color.red) + (0.01f * color.green) + (0.99f * color.blue);
	}
	
	public static void XYZtoRGB(XYZColor color, int[] rgb) {
		Float red = (0.41847f * color.x) - (0.15866f * color.y) - (0.082835f * color.z);
		Float green = (-0.091169f * color.x) + (0.25243f * color.y) + (0.015708f * color.z);
		Float blue = (0.0009209f * color.x) - (0.0025498f * color.y) + (0.17860f * color.z);
		
		rgb[0] = red.intValue();
		rgb[1] = green.intValue();
		rgb[2] = blue.intValue();
	}
	
//	public static double[] LABtoXYZ(double L, double a, double b) {
//		double[] result = new double[3];
//
//		double y = (L + 16.0) / 116.0;
//		double y3 = Math.pow(y, 3.0);
//		double x = (a / 500.0) + y;
//		double x3 = Math.pow(x, 3.0);
//		double z = y - (b / 200.0);
//		double z3 = Math.pow(z, 3.0);
//
//		if (y3 > 0.008856) {
//			y = y3;
//		} else {
//			y = (y - (16.0 / 116.0)) / 7.787;
//		}
//		if (x3 > 0.008856) {
//			x = x3;
//		} else {
//			x = (x - (16.0 / 116.0)) / 7.787;
//		}
//		if (z3 > 0.008856) {
//			z = z3;
//		} else {
//			z = (z - (16.0 / 116.0)) / 7.787;
//		}
//
//		result[0] = x * whitePoint[0];
//		result[1] = y * whitePoint[1];
//		result[2] = z * whitePoint[2];
//
//		return result;
//	}

	public static float[] RGBtoCMY(int red, int green, int blue) {
		float[] cmy = new float[3];
		
		cmy[0] = 1 - (red / 255f);
		cmy[1] = 1 - (green / 255f);
		cmy[2] = 1 - (blue / 255f);
		
		return cmy;
	}

	public static int[] CMYtoRGB(float cyan, float magenta, float yellow) {
		int[] rgb = new int[3];
		
		rgb[0] = new Float((1 - cyan) * 255).intValue();
		rgb[1] = new Float((1 - magenta) * 255).intValue();
		rgb[2] = new Float((1 - yellow) * 255).intValue();
		
		return rgb;
	}
}
