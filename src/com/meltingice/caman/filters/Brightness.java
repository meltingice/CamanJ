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
 * Adjusts the brightness of the image
 * 
 * Params: (int)
 * 
 * @author Ryan LeFevre
 * @version 1.0
 */
public class Brightness extends CamanFilter {
	private int param;

	/*
	 * (non-Javadoc)
	 * @see com.meltingice.caman.CamanFilter#precomputeParams()
	 */
	@Override
	public void precomputeParams() {
		param = (Integer) params.get(0);
	}

	/*
	 * (non-Javadoc)
	 * @see com.meltingice.caman.CamanFilter#process(int[])
	 */
	@Override
	public int[] process(int[] rgb) {
		rgb[0] += param;
		rgb[1] += param;
		rgb[2] += param;

		return CamanUtil.clampRGB(rgb);
	}
}
