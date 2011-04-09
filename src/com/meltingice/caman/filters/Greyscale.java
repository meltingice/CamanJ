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
 * Converts the image to an accurate greyscale representation. This is the
 * preferred method of making an image greyscale over simply using
 * {@link Saturation#Saturation()} to desaturate the image because it will
 * produce better results.
 * 
 * Params: (void)
 * 
 * @author Ryan LeFevre
 * @version 1.0
 */
public class Greyscale extends CamanFilter {

	@Override
	public int[] process(int[] rgb) {
		int avg = (int) (0.3 * rgb[0] + 0.59 * rgb[1] + 0.11 * rgb[2]);

		rgb[0] = avg;
		rgb[1] = avg;
		rgb[2] = avg;

		return rgb;
	}
}
