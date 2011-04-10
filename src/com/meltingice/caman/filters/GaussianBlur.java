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
 * Applies a gaussian blur to the image.
 * 
 * @author Ryan LeFevre
 * @version 1.0
 */
public class GaussianBlur extends CamanFilter {

	/*
	 * (non-Javadoc)
	 * @see com.meltingice.caman.CamanFilter#precomputeParams()
	 */
	@Override
	public void precomputeParams() {
		super.type = PluginType.KERNEL;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.meltingice.caman.CamanFilter#getKernel()
	 */
	@Override
	public double[] getKernel() {
		return new double[] {
				1, 4, 6, 4, 1,
				4, 16, 24, 16, 4,
				6, 24, 36, 24, 6,
				4, 16, 24, 16, 4,
				1, 4, 6, 4, 1
		};
	}
}
