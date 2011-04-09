package com.meltingice.caman.filters;

import com.meltingice.caman.CamanFilter;
import com.meltingice.caman.CamanUtil;

public class Contrast extends CamanFilter {

	public int[] process(int[] rgb) {
		Double arg = (Double) params.get(0);
		
		arg = (arg + 100) / 100;
		arg = Math.pow(arg, 2);
		
		double[] drgb = CamanUtil.toDouble(rgb);
		
		for (int i = 0; i < 3; i++) {
			drgb[i] /= 255;
			drgb[i] -= 0.5;
			drgb[i] *= arg;
			drgb[i] += 0.5;
			drgb[i] *= 255;
		}
		
		return CamanUtil.clampRGB(drgb);
	}
}
