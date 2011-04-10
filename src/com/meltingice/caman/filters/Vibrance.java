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
import com.meltingice.caman.util.CamanUtil;

/**
 * Adjusts the vibrance of the colors in the image. Vibrance is a more selective
 * saturation control that does a better job of keeping colors from becoming
 * oversaturated.
 * 
 * @author Ryan LeFevre
 * @version 1.0
 */
public class Vibrance extends CamanFilter {
	private double arg;

	/*
	 * (non-Javadoc)
	 * @see com.meltingice.caman.CamanFilter#precomputeParams()
	 */
	@Override
	public void precomputeParams() {
		arg = getParamDouble(0) * -1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.meltingice.caman.CamanFilter#process(int[])
	 */
	@Override
	public int[] process(int[] rgb) {
		int max = Math.max(rgb[0], Math.max(rgb[1], rgb[2]));
		double avg = (rgb[0] + rgb[1] + rgb[2]) / 3;
		double amt = ((Math.abs(max - avg) * 2.0 / 255.0) * arg) / 100.0;

		int diff;
		for (int i = 0; i < 3; i++) {
			if (rgb[i] != max) {
				diff = max - rgb[i];
				rgb[i] += diff * amt;
			}
		}

		return CamanUtil.clampRGB(rgb);
	}

}
