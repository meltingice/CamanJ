/**
 * 
 */
package com.meltingice.caman.filters;

import com.meltingice.caman.CamanFilter;
import com.meltingice.caman.CamanUtil;
import com.meltingice.caman.exceptions.InvalidArgument;

/**
 * @author Ryan LeFevre
 *
 */
public class Brightness implements CamanFilter {

	/* (non-Javadoc)
	 * @see com.meltingice.caman.CamanPlugin#process(java.lang.Object)
	 */
	@Override
	public int[] process(int[] rgb, double arg) {
		rgb[0] += arg;
		rgb[1] += arg;
		rgb[2] += arg;
		
		return CamanUtil.clampRGB(rgb);
	}

	@Override
	public int[] process(int[] rgb, String[] args) throws InvalidArgument {
		throw new InvalidArgument();
	}

}
