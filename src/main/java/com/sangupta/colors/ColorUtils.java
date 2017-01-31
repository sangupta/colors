package com.sangupta.colors;

import com.sangupta.colors.RGBColor;

public class ColorUtils {
	
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

}
