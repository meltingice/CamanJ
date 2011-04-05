/**
 * 
 */
package com.meltingice.caman.plugins;

import com.meltingice.caman.CamanPlugin;
import com.meltingice.caman.CamanUtil;
import com.meltingice.caman.exceptions.InvalidArgument;

/**
 * @author ryanlefevre
 *
 */
public class Brightness implements CamanPlugin {

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
