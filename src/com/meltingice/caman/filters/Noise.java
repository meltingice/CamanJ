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
 * Applies a variable amount of pseudorandom noise to an image.
 * 
 * @author Ryan LeFevre
 * @version 1.0
 */
public class Noise extends CamanFilter {
	private double param;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.meltingice.caman.CamanFilter#precomputeParams()
	 */
	@Override
	public void precomputeParams() throws InvalidArgumentsException {
		param = Math.abs(getParamDouble(0)) * 2.55;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.meltingice.caman.CamanFilter#process(int[])
	 */
	@Override
	public int[] process(int[] rgb) {
		double amt = CamanUtil.randomRange(param * -1.0, param);
		double drgb[] = CamanUtil.toDouble(rgb);
		
		drgb[0] += amt;
		drgb[1] += amt;
		drgb[2] += amt;
		
		return CamanUtil.clampRGB(drgb);
	}
}
