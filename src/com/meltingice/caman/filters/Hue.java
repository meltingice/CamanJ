/**
 * CamanJ - Java Image Manipulation
 * Ported from the CamanJS Javascript library
 *
 * Copyright 2011, Ryan LeFevre
 * Licensed under the new BSD License
 * See LICENSE for more info.
 * 
 * Project Home: http://github.com/meltingice/CamanJ
 */
package com.meltingice.caman.filters;

import com.meltingice.caman.CamanFilter;
import com.meltingice.caman.CamanUtil;
import com.meltingice.caman.ColorUtil;

/**
 * Adjusts the hue of the image.
 * 
 * Params: (double)
 * 
 * @author Ryan LeFevre
 * @version 1.0
 */
public class Hue extends CamanFilter {
	
	@Override
	public int[] process(int[] rgb) {
		Double param = (Double) params.get(0);
		
		double[] hsv = ColorUtil.rgbToHsv(rgb);
		hsv[0] *= 100;
		hsv[0] += Math.abs(param);
		hsv[0] = hsv[0] % 100;
		hsv[0] /= 100;
		
		rgb = ColorUtil.hsvToRgb(hsv);
		return CamanUtil.clampRGB(rgb);
	}
}
