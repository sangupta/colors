package com.sangupta.colors;

import org.junit.Assert;
import org.junit.Test;

import com.sangupta.colors.model.CMYK;
import com.sangupta.colors.model.HSB;
import com.sangupta.colors.model.HSL;
import com.sangupta.colors.model.RGB;

public class TestColorUtils {
	
	@Test
	public void testCMYKConversion() {
		// test values picked up from http://www.rapidtables.com/convert/color/rgb-to-cmyk.htm
		
		testCMYK(0, 0, 0, 0, 0, 0, 1); // black
		testCMYK(255, 255, 255, 0, 0, 0, 0); // white
		testCMYK(255, 0, 0, 0, 1, 1, 0); // red
		testCMYK(0, 255, 0, 1, 0, 1, 0); // green
		testCMYK(0, 0, 255, 1, 1, 0, 0); // blue
		testCMYK(255, 255, 0, 0, 0, 1, 0); // yellow
		testCMYK(0, 255, 255, 1, 0, 0, 0); // cyan
		testCMYK(255, 0, 255, 0, 1, 0, 0); // magenta
	}
	
	@Test
	public void testHSLConversion() {
		// test values picked up from http://www.rapidtables.com/convert/color/rgb-to-hsl.htm
		
		testHSL(0, 0, 0, 0f, 0f, 0f); // black
		testHSL(255, 255, 255, 0f, 0f, 100f); // white
		testHSL(255, 0, 0, 0f, 100f, 50f); // red
		testHSL(0, 255, 255, 180f, 100f, 50f); // green
		testHSL(0, 0, 255, 240f, 100f, 50f); // blue
		testHSL(255, 255, 0, 60f, 100f, 50f); // yellow
		testHSL(0, 255, 255, 180f, 100f, 50f); // cyan
		testHSL(255, 0, 255, 300f, 100f, 50f); // magenta
		testHSL(192, 192, 192, 0f, 0f, 75f); // silver
		testHSL(128, 128, 128, 0f, 0f, 50f); // gray
		testHSL(128, 0, 0, 0f, 100f, 25f); // maroon
		testHSL(128, 128, 0, 60f, 100f, 25f); // olive
		testHSL(0, 128, 0, 120f, 100f, 25f); // dark green
		testHSL(128, 0, 128, 300f, 100f, 25f); // purple
		testHSL(0, 128, 128, 180f, 100f, 25f); // teal
		testHSL(0, 0, 128, 240f, 100f, 25f); // navy
	}
	
	@Test
	public void testHSBConversion() {
		// test values picked up from http://www.rapidtables.com/convert/color/rgb-to-hsl.htm
		
		testHSB(0, 0, 0, 0f, 0f, 0f); // black
		testHSB(255, 255, 255, 0f, 0f, 1f); // white
		testHSB(255, 0, 0, 0f, 1f, 50f); // red
		testHSB(0, 255, 255, 180f, 100f, 50f); // green
		testHSB(0, 0, 255, 240f, 100f, 50f); // blue
		testHSB(255, 255, 0, 60f, 100f, 50f); // yellow
		testHSB(0, 255, 255, 180f, 100f, 50f); // cyan
		testHSB(255, 0, 255, 300f, 100f, 50f); // magenta
		testHSB(192, 192, 192, 0f, 0f, 75f); // silver
		testHSB(128, 128, 128, 0f, 0f, 50f); // gray
		testHSB(128, 0, 0, 0f, 100f, 25f); // maroon
		testHSB(128, 128, 0, 60f, 100f, 25f); // olive
		testHSB(0, 128, 0, 120f, 100f, 25f); // dark green
		testHSB(128, 0, 128, 300f, 100f, 25f); // purple
		testHSB(0, 128, 128, 180f, 100f, 25f); // teal
		testHSB(0, 0, 128, 240f, 100f, 25f); // navy
	}
	
	public void testCMYK(int red, int green, int blue, float cyan, float magenta, float yellow, float black) {
		CMYK cmyk = ColorConversionUtils.RGBtoCMYK(new RGB(red, green, blue));
		
		Assert.assertEquals(cyan, cmyk.cyan, 0f);
		Assert.assertEquals(magenta, cmyk.magenta, 0f);
		Assert.assertEquals(yellow, cmyk.yellow, 0f);
		Assert.assertEquals(black, cmyk.black, 0f);
	}
	
	public void testHSL(int red, int green, int blue, float hue, float saturation, float lumen) {
		HSL hsl = ColorConversionUtils.RGBtoHSL(new RGB(red, green, blue));
		
		Assert.assertEquals(hue, hsl.hue, 0f);
		Assert.assertEquals(saturation, hsl.saturation, 0f);
		Assert.assertEquals(lumen, hsl.luminosity, 0.3f);
	}
	
	public void testHSB(int red, int green, int blue, float hue, float saturation, float brightness) {
		HSB hsb = ColorConversionUtils.RGBtoHSB(new RGB(red, green, blue));
		
		Assert.assertEquals(hue, hsb.hue, 0f);
		Assert.assertEquals(saturation, hsb.saturation, 0f);
		Assert.assertEquals(brightness, hsb.brightness, 0.3f);
	}

}
