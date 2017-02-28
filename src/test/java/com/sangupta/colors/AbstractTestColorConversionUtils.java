package com.sangupta.colors;

import org.junit.Assert;

import com.sangupta.colors.model.CMY;
import com.sangupta.colors.model.CMYK;
import com.sangupta.colors.model.HSB;
import com.sangupta.colors.model.HSL;
import com.sangupta.colors.model.RGB;
import com.sangupta.colors.model.YIQ;

public class AbstractTestColorConversionUtils {

	protected void testRGBtoCMYK(RGB rgbColor, float cyan, float magenta, float yellow, float black) {
		CMYK color = ColorConversionUtils.RGBtoCMYK(rgbColor);
		
		Assert.assertEquals(cyan, color.cyan, 0.001f);
		Assert.assertEquals(magenta, color.magenta, 0.001f);
		Assert.assertEquals(yellow, color.yellow, 0.001f);
		Assert.assertEquals(black, color.black, 0.001f);
	}
	
	protected void testRGBtoHSB(RGB rgbColor, float hue, float saturation, float brightness) {
		HSB color = ColorConversionUtils.RGBtoHSB(rgbColor);
		
		Assert.assertEquals(hue, color.hue, 0.001f);
		Assert.assertEquals(saturation, color.saturation, 0.001f);
		Assert.assertEquals(brightness, color.brightness, 0.001f);
	}

	protected void testRGBtoHSL(RGB rgbColor, float hue, float saturation, float lightness) {
		HSL color = ColorConversionUtils.RGBtoHSL(rgbColor);
		
		Assert.assertEquals(hue, color.hue, 0.001f);
		Assert.assertEquals(saturation, color.saturation, 0.001f);
		Assert.assertEquals(lightness, color.luminosity, 0.001f);
	}

	protected void testRGBtoCMY(RGB rgbColor, float cyan, float magenta, float yellow) {
		CMY color = ColorConversionUtils.RGBtoCMY(rgbColor);
		
		Assert.assertEquals(cyan, color.cyan, 0.001f);
		Assert.assertEquals(magenta, color.magenta, 0.001f);
		Assert.assertEquals(yellow, color.yellow, 0.001f);
	}
	
	protected void testRGBtoYIQ(RGB rgbColor, float y, float i, float q) {
		YIQ color = ColorConversionUtils.RGBtoYIQ(rgbColor);
		
		Assert.assertEquals(y, color.y, 0.001f);
		Assert.assertEquals(i, color.i, 0.001f);
		Assert.assertEquals(q, color.q, 0.001f);
	}
}
