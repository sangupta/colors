package com.sangupta.colors;

import org.junit.Assert;

import com.sangupta.colors.model.CMYK;
import com.sangupta.colors.model.HSB;
import com.sangupta.colors.model.RGB;

public class AbstractTestColorConversionUtils {

	protected void testRGBtoCMYK(RGB rgbColor, float cyan, float magenta, float yellow, float black) {
		CMYK cmyk = ColorConversionUtils.RGBtoCMYK(rgbColor);
		Assert.assertEquals(cyan, cmyk.cyan, 0.001f);
		Assert.assertEquals(magenta, cmyk.magenta, 0.001f);
		Assert.assertEquals(yellow, cmyk.yellow, 0.001f);
		Assert.assertEquals(black, cmyk.black, 0.001f);
	}
	
	protected void testRGBtoHSB(RGB rgbColor, float hue, float saturation, float brightness) {
		HSB hsb = ColorConversionUtils.RGBtoHSB(rgbColor);
		
		Assert.assertEquals(hue, hsb.hue, 0.001f);
		Assert.assertEquals(saturation, hsb.saturation, 0.001f);
		Assert.assertEquals(brightness, hsb.brightness, 0.001f);
	}
	
}
