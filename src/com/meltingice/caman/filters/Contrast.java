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
import com.meltingice.caman.exceptions.InvalidArgumentsException;
import com.meltingice.caman.util.CamanUtil;

/**
 * Adjusts the contrast of the image. Negative values reduce contrast, and
 * positive values increase contrast.
 * 
 * Params: (double)
 * 
 * @author Ryan LeFevre
 * @version 1.0
 */
public class Contrast extends CamanFilter {
	private double arg;
	
	/*
	 * (non-Javadoc)
	 * @see com.meltingice.caman.CamanFilter#precomputeParams()
	 */
	@Override
	public void precomputeParams() throws InvalidArgumentsException {
		arg = getParamDouble(0);
	}

	/*
	 * (non-Javadoc)
	 * @see com.meltingice.caman.CamanFilter#process(int[])
	 */
	public int[] process(int[] rgb) {
		arg = (arg + 100) / 100;
		arg = Math.pow(arg, 2);

		double[] drgb = CamanUtil.toDouble(rgb);

		for (int i = 0; i < 3; i++) {
			drgb[i] /= 255;
			drgb[i] -= 0.5;
			drgb[i] *= arg;
			drgb[i] += 0.5;
			drgb[i] *= 255;
		}

		return CamanUtil.clampRGB(drgb);
	}
}
