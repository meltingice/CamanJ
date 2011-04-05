package com.meltingice.caman.filters;

import com.meltingice.caman.CamanFilter;
import com.meltingice.caman.CamanUtil;
import com.meltingice.caman.ColorUtil;
import com.meltingice.caman.exceptions.InvalidArgument;

public class Hue extends CamanFilter {
	
	@Override
	public int[] process(int[] rgb, double arg) throws InvalidArgument {
		double[] hsv = ColorUtil.rgbToHsv(rgb);
		hsv[0] *= 100;
		hsv[0] += Math.abs(arg);
		hsv[0] = hsv[0] % 100;
		hsv[0] /= 100;
		
		rgb = ColorUtil.hsvToRgb(hsv);
		return CamanUtil.clampRGB(rgb);
	}
}
