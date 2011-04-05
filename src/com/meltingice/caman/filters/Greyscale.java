/**
 * 
 */
package com.meltingice.caman.filters;

import com.meltingice.caman.CamanFilter;
import com.meltingice.caman.exceptions.InvalidArgument;

/**
 * @author Ryan LeFevre
 *
 */
public class Greyscale extends CamanFilter {
	
	@Override
	public int[] process(int[] rgb) throws InvalidArgument {
		int avg = (int) (0.3 * rgb[0] + 0.59 * rgb[1] + 0.11 * rgb[2]);
		
		rgb[0] = avg;
		rgb[1] = avg;
		rgb[2] = avg;
		
		return rgb;
	}
}
