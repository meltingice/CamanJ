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

	@Override
	public int[] process(int[] rgb) {
		int param = (Integer) params.get(0);
		
		rgb[0] += param;
		rgb[1] += param;
		rgb[2] += param;
		
		return CamanUtil.clampRGB(rgb);
	}
}
