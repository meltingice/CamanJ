package com.meltingice.caman;

import com.meltingice.caman.exceptions.InvalidArgument;

public interface CamanPlugin {
	public int[] process(int[] rgb, double arg) throws InvalidArgument;
	public int[] process(int[] rgb, String[] args) throws InvalidArgument;
}
