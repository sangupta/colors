package com.sangupta.colors;

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
		float cyan = (1 - color.red) / 255f;
		float magenta = (1 - color.green) / 255f;
		float yellow = (1 - color.blue) / 255f;
		
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
        
        hsl[0] = hue;
        hsl[1] = saturation;
        hsl[2] = lumin;
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
}
