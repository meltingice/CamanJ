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
 * Clips a color to max values when it falls outside of the specified range.
 * 
 * @author Ryan LeFevre
 * @version 1.0
 */
public class Clip extends CamanFilter {
	private int param;
	
	/*
	 * (non-Javadoc)
	 * @see com.meltingice.caman.CamanFilter#precomputeParams()
	 */
	@Override
	public void precomputeParams() throws InvalidArgumentsException {
		param = (int) (Math.abs(getParamInt(0)) * 2.55);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.meltingice.caman.CamanFilter#process(int[])
	 */
	@Override
	public int[] process(int[] rgb) {
		for (int i = 0; i < 3; i++) {
			if (rgb[i] > 255 - param) {
				rgb[i] = 255;
			} else if (rgb[i] < param) {
				rgb[i] = 0;
			}
		}
		
		return CamanUtil.clampRGB(rgb);
	}

}
