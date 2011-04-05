/**
 * 
 */
package com.meltingice.caman.filters;

import com.meltingice.caman.CamanFilter;
import com.meltingice.caman.CamanUtil;

/**
 * @author Ryan LeFevre
 *
 */
public class Brightness extends CamanFilter {

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
}
