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

/**
 * Lets you modify the intensity of any combination of red, green or blue color
 * channels independently.
 * 
 * @author Ryan LeFevre
 * @version 1.0
 */
public class Channels extends CamanFilter {
	private double[] args;

	/*
	 * (non-Javadoc)
	 * @see com.meltingice.caman.CamanFilter#precomputeParams()
	 */
	@Override
	public void precomputeParams() {
		args = new double[3];

		for (int i = 0; i < 3; i++) {
			try {
				args[i] = getParamDouble(i) / 100;
			} catch (Exception e) {
				args[i] = 0.0;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.meltingice.caman.CamanFilter#process(int[])
	 */
	@Override
	public int[] process(int[] rgb) {
		double[] drgb = CamanUtil.toDouble(rgb);
		
		for (int i = 0; i < 3; i++) {
			if (args[i] > 0) {
				drgb[i] += ((255 - drgb[i]) * args[i]);
			} else {
				drgb[i] -= drgb[i] * Math.abs(args[i]);
			}
		}
		
		return CamanUtil.clampRGB(drgb);
	}

}
