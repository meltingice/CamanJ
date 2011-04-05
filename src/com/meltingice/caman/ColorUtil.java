package com.meltingice.caman;

public class ColorUtil {
	public static double[] rgbToHsv(int[] rgb) {
		double[] drgb = CamanUtil.toDouble(rgb);
		
		drgb[0] /= 255;
		drgb[1] /= 255;
		drgb[2] /= 255;
		
		double max = Math.max(Math.max(drgb[0], drgb[1]), drgb[2]);
		double min = Math.min(Math.min(drgb[0], drgb[1]), drgb[2]);
		double h = max, s = max, v = max;
		double d = max - min;
		
		s = (max == 0 ? 0 : d / max);
		
		if (max == min) {
			// Monochrome
			h = 0;
		} else {
			if (max == rgb[0]) {
				h = (rgb[1] - rgb[2]) / d + (rgb[1] < rgb[2] ? 6 : 0); 
			} else if (max == rgb[1]) {
				h = (rgb[2] - rgb[0]) / d + 2;
			} else if (max == rgb[2]) {
				h = (rgb[0] - rgb[1]) / d + 4;
			}
			
			h /= 6;
		}
		
		return new double[] {h, s, v};
	}
	
	public static int[] hsvToRgb(double[] hsv) {
		double r = 0, g = 0, b = 0;
		double i = Math.floor(hsv[0] * 6);
		double f = hsv[0] * 6 - i;
		double p = hsv[2] * (1 - hsv[1]);
		double q = hsv[2] * (1 - f * hsv[1]);
		double t = hsv[2] * (1 - (1 - f) * hsv[1]);
		
		int mod = (int) i % 6;
		if (mod == 0) {
			r = hsv[2];
			g = t;
			b = p;
		} else if (mod == 1) {
			r = q;
			g = hsv[2];
			b = p;
		} else if (mod == 2) {
			r = p;
			g = hsv[2];
			b = t;
		} else if (mod == 3) {
			r = p;
			g = q;
			b = hsv[2];
		} else if (mod == 4) {
			r = t;
			g = p;
			b = hsv[2];
		} else if (mod == 5) {
			r = hsv[2];
			g = p;
			b = q;
		}
		
		r *= 255;
		g *= 255;
		b *= 255;
		
		return new int[] {(int) r, (int) g, (int) b, 255};
	}
}
