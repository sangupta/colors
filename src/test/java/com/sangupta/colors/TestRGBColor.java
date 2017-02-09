package com.sangupta.colors;

import org.junit.Assert;
import org.junit.Test;

import com.sangupta.colors.model.RGB;

public class TestRGBColor {
	
	@Test
	public void testCreation() {
		RGB rgb = new RGB(new int[] { 29, 39, 49 });
		Assert.assertEquals(29, rgb.red);
		Assert.assertEquals(39, rgb.green);
		Assert.assertEquals(49, rgb.blue);
		
		rgb = new RGB(new int[] { 29, 39, 49})		// obtain the RGB color
			    .hsb()								// let's convert it to HSB
			    .rgb().hsi()						// now over to HSI
			    .rgb().hsl()						// over to HSL
			    .rgb().xyz()						// to XYZ
			    .hunterLAB()						// to HunterLAB
			    .xyz().yxy()						// to Yxy
			    .xyz().rgb();						// and back to RGB
	}

}
