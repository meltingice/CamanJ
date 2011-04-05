package com.meltingice.caman;

import com.meltingice.caman.exceptions.InvalidArgument;

/**
 * Interface for all filters
 * 
 * @author Ryan LeFevre
 * 
 */
public interface CamanFilter {
	public int[] process(int[] rgb, double arg) throws InvalidArgument;

	public int[] process(int[] rgb, String[] args) throws InvalidArgument;
}
