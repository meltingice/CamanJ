/**
 * 
 */
package com.meltingice.caman;

/**
 * @author ryanlefevre
 *
 */
public class CamanUtil {
	public static int clampRGB(int val) {
		if (val < 0) {
			return 0;
		} else if (val > 255) {
			return 255;
		}
		
		return val;
	}
	
	public static int[] clampRGB(int[] rgb) {
		rgb[0] = clampRGB(rgb[0]);
		rgb[1] = clampRGB(rgb[1]);
		rgb[2] = clampRGB(rgb[2]);
		
		return rgb;
	}
}
