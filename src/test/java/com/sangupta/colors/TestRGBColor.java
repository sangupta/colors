package com.sangupta.colors;

import org.junit.Assert;
import org.junit.Test;

public class TestRGBColor {
	
	@Test
	public void testCreation() {
		RGBColor rgb = new RGBColor(new int[] { 29, 39, 49 });
		Assert.assertEquals(29, rgb.red);
		Assert.assertEquals(39, rgb.green);
		Assert.assertEquals(49, rgb.blue);
	}

}
