package com.sangupta.colors;

/**
 * Refer
 * https://en.wikipedia.org/wiki/YUV#SDTV_with_BT.601
 * for more details.
 * 
 * @author sangupta
 *
 */
public class YUVColor {
	
	public final float y;
	
	public final float u;
	
	public final float v;
	
	public YUVColor(float[] yuv) {
		this.y = yuv[0];
		this.u = yuv[1];
		this.v = yuv[2];
	}
	
	public YUVColor(float y, float u, float v) {
		this.y = y;
		this.u = u;
		this.v = v;
	}
	
	@Override
	public String toString() {
		return "YUV(" + this.y + ", " + this.u + ", " + this.v + ")";
	}
	
	@Override
	public int hashCode() {
		return new Float(this.y * 3100 + this.u * 1700 + this.v * 100).intValue();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) {
			return false;
		}
		
		if(this == obj) {
			return true;
		}
		
		if(!(obj instanceof YUVColor)) {
			return false;
		}
		
		YUVColor color = (YUVColor) obj;
		return this.y == color.y && this.u == color.u && this.v == color.v;
	}

}
