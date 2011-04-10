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

/**
 * Inverts all colors in the image.
 * 
 * Params: (void)
 * 
 * @author Ryan LeFevre
 * @version 1.0
 */
public class Invert extends CamanFilter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.meltingice.caman.CamanFilter#process(int[])
	 */
	@Override
	public int[] process(int[] rgb) {
		rgb[0] = 255 - rgb[0];
		rgb[1] = 255 - rgb[1];
		rgb[2] = 255 - rgb[2];

		return rgb;
	}

}
