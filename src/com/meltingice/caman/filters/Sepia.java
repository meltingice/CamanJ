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
 * Applies a variable strength sepia filter to the image.
 * 
 * @author Ryan LeFevre
 * @version 1.0
 */
public class Sepia extends CamanFilter {
	private double param;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.meltingice.caman.CamanFilter#precomputeParams()
	 */
	@Override
	public void precomputeParams() throws InvalidArgumentsException {
		if (params.size() == 0) {
			param = 1;
		} else {
			param = getParamDouble(0) / 100.0;
			if (param < 0) {
				param = 0;
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

		drgb[0] = (drgb[0] * (1 - (0.607 * param)))
				+ (drgb[1] * (0.769 * param)) + (drgb[2] * (0.189 * param));
		drgb[1] = (drgb[0] * (0.349 * param))
				+ (drgb[1] * (1 - (0.314 * param)))
				+ (drgb[2] * (0.168 * param));
		drgb[2] = (drgb[0] * (0.272 * param)) + (drgb[1] * (0.534 * param))
				+ (drgb[2] * (1 - (0.869 * param)));

		return CamanUtil.clampRGB(drgb);
	}

}
