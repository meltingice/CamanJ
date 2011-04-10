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
import com.meltingice.caman.util.Bezier;
import com.meltingice.caman.util.CamanUtil;

/**
 * Curves implementation loosely based off of Photoshop's similarly named
 * feature. Curves in CamanJ use Bezier curves to convert an input color X into
 * an output color Y based on the Bezier curve.
 * 
 * First param is the channels you wish to apply the curve to. This is simply a
 * string with r, g, and or b in it. The rest of the params are the start,
 * control 1, control 2, and end points in x/y pairs.
 * 
 * Params: (String, int x 8)
 * 
 * @author Ryan LeFevre
 * @version 1.0
 * 
 */
public class Curves extends CamanFilter {
	private int[] channels;
	private Bezier bezier;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.meltingice.caman.CamanFilter#precomputeParams()
	 */
	@Override
	public void precomputeParams() throws InvalidArgumentsException {
		if (params.size() != 9) {
			throw new InvalidArgumentsException();
		}

		channels = CamanUtil.channelsToIntArray((String) params.get(0));

		int[] start = new int[] { getParamInt(1), getParamInt(2) };
		int[] ctrl1 = new int[] { getParamInt(3), getParamInt(4) };
		int[] ctrl2 = new int[] { getParamInt(5), getParamInt(6) };
		int[] end = new int[] { getParamInt(7), getParamInt(8) };

		bezier = new Bezier(start, ctrl1, ctrl2, end, 0, 255);
		bezier.fillEnds(0, 255);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.meltingice.caman.CamanFilter#process(int[])
	 */
	@Override
	public int[] process(int[] rgb) {
		for (int i = 0; i < channels.length; i++) {
			rgb[channels[i]] = bezier.query(rgb[channels[i]]);
		}

		return CamanUtil.clampRGB(rgb);
	}

}
