/**
 * 
 */
package com.meltingice.caman;

/**
 * @author Ryan LeFevre
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
		rgb[3] = clampRGB(rgb[3]);
		
		return rgb;
	}
	
	public static int[] clampRGB(double[] drgb) {
		int[] rgb = new int[4];
		rgb[0] = clampRGB((int) drgb[0]);
		rgb[1] = clampRGB((int) drgb[1]);
		rgb[2] = clampRGB((int) drgb[2]);
		rgb[3] = clampRGB((int) drgb[3]);
		
		return rgb;
	}
	
	public static double[] toDouble(int[] rgb) {
		return new double[] {
			(double) rgb[0],
			(double) rgb[1],
			(double) rgb[2],
			(double) rgb[3]
		};
	}
	
	public static String getFilterName(String input) {
		if (input.length() == 0) {
			return input;
		}
		
		return Character.toUpperCase(input.charAt(0)) + input.substring(1);
	}
}
