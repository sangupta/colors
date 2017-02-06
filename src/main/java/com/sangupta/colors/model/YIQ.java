package com.sangupta.colors.model;

/**
 * YIQ Color model. Refer https://en.wikipedia.org/wiki/YIQ
 * for more details.
 * 
 * @author sangupta
 * @since 1.0.0
 */
public class YIQ {

	public final float y;
	
	public final float i;
	
	public final float q;
	
	public YIQ(float[] yiq) {
		this.y = yiq[0];
		this.i = yiq[1];
		this.q = yiq[2];
	}
	
	public YIQ(float y, float i, float q) {
		this.y = y;
		this.i = i;
		this.q = q;
	}
	
	/**
	 * Return this color as a <code>float[]</code> array with the Y, I and Q
	 * values in order.
	 * 
	 * @return the <code>float[]</code> array
	 */
	public float[] asArray() {
		return new float[] { this.y, this.i, this.q };
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
		
		if(!(obj instanceof YIQ)) {
			return false;
		}
		
		YIQ color = (YIQ) obj;
		return this.y == color.y && this.i == color.i && this.q == color.q;
	}
	
}
