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
import com.meltingice.caman.util.ColorUtil;

/**
 * @author ryanlefevre
 * 
 */
public class Colorize extends CamanFilter {
	private int[] adjustRGB;
	private double amt;

	/*
	 * (non-Javadoc)
	 * @see com.meltingice.caman.CamanFilter#precomputeParams()
	 */
	@Override
	public void precomputeParams() throws InvalidArgumentsException {
		if (params.size() == 2) {
			adjustRGB = ColorUtil.hexToRgb((String) params.get(0));
			amt = getParamDouble(1) / 100.0;
		} else if (params.size() == 4) {
			adjustRGB = new int[] { getParamInt(0), getParamInt(1), getParamInt(2) };
			amt = getParamDouble(3) / 100.0;
		} else {
			throw new InvalidArgumentsException();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.meltingice.caman.CamanFilter#process(int[])
	 */
	@Override
	public int[] process(int[] rgb) {
		for (int i = 0; i < 3; i++) {
			rgb[i] -= (rgb[i] - adjustRGB[i]) * amt;
		}
		
		return CamanUtil.clampRGB(rgb);
	}

}
