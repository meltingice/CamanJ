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
import com.meltingice.caman.util.Bezier;
import com.meltingice.caman.util.CamanUtil;

/**
 * @author ryanlefevre
 * 
 */
public class Exposure extends CamanFilter {
	private Bezier bezier;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.meltingice.caman.CamanFilter#precomputeParams()
	 */
	@Override
	public void precomputeParams() {
		Integer[] ctrl1, ctrl2;
		double p;

		int adjust = getParamInt(0);

		p = (double) Math.abs(adjust) / 100.0;
		ctrl1 = new Integer[] { 0, (int) (255 * p) };
		ctrl2 = new Integer[] { (int) (255 - (255 * p)), 255 };

		if (adjust < 0) {
			ctrl1 = CamanUtil.reverseArray(ctrl1);
			ctrl2 = CamanUtil.reverseArray(ctrl2);
		}

		bezier = new Bezier(new int[] { 0, 0 }, new int[] {
				ctrl1[0].intValue(), ctrl1[1].intValue() }, new int[] {
				ctrl2[0].intValue(), ctrl2[1].intValue() }, new int[] { 255,
				255 });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.meltingice.caman.CamanFilter#process(int[])
	 */
	@Override
	public int[] process(int[] rgb) {
		for (int i = 0; i < 3; i++) {
			rgb[i] = bezier.query(rgb[i]);
		}

		return CamanUtil.clampRGB(rgb);
	}

}
