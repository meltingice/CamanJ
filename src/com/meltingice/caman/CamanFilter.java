package com.meltingice.caman;

import com.meltingice.caman.exceptions.InvalidArgument;

/**
 * Abstract class for all filters. Override only the methods you need, and the
 * others will automatically throw an InvalidArgument exception.
 * 
 * @author Ryan LeFevre
 * 
 */
public abstract class CamanFilter {
	public int[] process(int[] rgb) throws InvalidArgument {
		throw new InvalidArgument();
	}
	
	public int[] process(int[] rgb, double arg) throws InvalidArgument {
		throw new InvalidArgument();
	}
	
	public int[] process(int[] rgb, String args) throws InvalidArgument {
		throw new InvalidArgument();
	}

	public int[] process(int[] rgb, String[] args) throws InvalidArgument {
		throw new InvalidArgument();
	}
}
