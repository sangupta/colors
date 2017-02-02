package com.sangupta.colors;

/**
 * YIQ Color model. Refer https://en.wikipedia.org/wiki/YIQ
 * for more details.
 * 
 * @author sangupta
 *
 */
public class YIQColor {

	public final float y;
	
	public final float i;
	
	public final float q;
	
	public YIQColor(float[] yiq) {
		this.y = yiq[0];
		this.i = yiq[1];
		this.q = yiq[2];
	}
	
	public YIQColor(float y, float i, float q) {
		this.y = y;
		this.i = i;
		this.q = q;
	}
	
	@Override
	public String toString() {
		return "YIQ(" + this.y + ", " + this.i + ", " + this.q + ")";
	}
	
	@Override
	public int hashCode() {
		return new Float(this.y * 3100 + this.i * 1700 + this.q * 100).intValue();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) {
			return false;
		}
		
		if(this == obj) {
			return true;
		}
		
		if(!(obj instanceof YIQColor)) {
			return false;
		}
		
		YIQColor color = (YIQColor) obj;
		return this.y == color.y && this.i == color.i && this.q == color.q;
	}
	
}
