package com.sangupta.colors.model;

/**
 * Refer
 * https://en.wikipedia.org/wiki/YUV#SDTV_with_BT.601
 * for more details.
 * 
 * @author sangupta
 * @since 1.0.0
 */
public class YUV {
	
	public final float y;
	
	public final float u;
	
	public final float v;
	
	public YUV(float[] yuv) {
		this.y = yuv[0];
		this.u = yuv[1];
		this.v = yuv[2];
	}
	
	public YUV(float y, float u, float v) {
		this.y = y;
		this.u = u;
		this.v = v;
	}
	
	public float[] asArray() {
		return new float[] { this.y, this.u, this.v };
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
		
		if(!(obj instanceof YUV)) {
			return false;
		}
		
		YUV color = (YUV) obj;
		return this.y == color.y && this.u == color.u && this.v == color.v;
	}

	public static enum YUVQuality {
		
		SDTV,
		
		BT_601,
		
		HDTV,
		
		BT_709;
		
	}
}
