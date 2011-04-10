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
 * Applies a gamma adjustment to the image. Values from 0 - 1 will decrease
 * image contrast while values > 1 will increase image contrast.
 * 
 * @author Ryan LeFevre
 * @version 1.0
 */
public class Gamma extends CamanFilter {
	private double param;
	
	/*
	 * (non-Javadoc)
	 * @see com.meltingice.caman.CamanFilter#precomputeParams()
	 */
	@Override
	public void precomputeParams() throws InvalidArgumentsException {
		param = getParamDouble(0);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.meltingice.caman.CamanFilter#process(int[])
	 */
	@Override
	public int[] process(int[] rgb) {
		double[] drgb = CamanUtil.toDouble(rgb);
		
		drgb[0] = Math.pow(drgb[0] / 255.0, param) * 255;
		drgb[1] = Math.pow(drgb[1] / 255.0, param) * 255;
		drgb[2] = Math.pow(drgb[2] / 255.0, param) * 255;
		
		return CamanUtil.clampRGB(drgb);
	}

}
