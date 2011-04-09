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
public class Saturation extends CamanFilter {
	
	@Override
	public int[] process(int[] rgb) {
		Double arg = (Double) params.get(0);
		arg *= -0.01;
		
		int max = Math.max(Math.max(rgb[0], rgb[1]), rgb[2]);
		int diff;
		
		for (int i = 0; i < 3; i++) {
			if (rgb[i] != max) {
				diff = max - rgb[i];
				rgb[i] += diff * arg;
			}
		}
		
		return CamanUtil.clampRGB(rgb);
	}
}
