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
import com.meltingice.caman.PluginType;

/**
 * Sharpens an image by a variable amount using convolution
 * 
 * @author Ryan LeFevre
 * @version 1.0
 */
public class Sharpen extends CamanFilter {
	private double amt;
	
	/*
	 * (non-Javadoc)
	 * @see com.meltingice.caman.CamanFilter#precomputeParams()
	 */
	@Override
	public void precomputeParams() {
		super.setType(PluginType.KERNEL);
		amt = getParamDouble(0) / 100.0;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.meltingice.caman.CamanFilter#getKernel()
	 */
	@Override
	public double[] getKernel() {
		return new double[] {
				0, -amt, 0,
				-amt, 4 * amt + 1, -amt,
				0, -amt, 0
		};
	}

}
