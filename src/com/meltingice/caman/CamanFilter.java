package com.meltingice.caman;

import java.util.LinkedList;


/**
 * Abstract class for all filters. Override only the methods you need, and the
 * others will automatically throw an InvalidArgument exception.
 * 
 * @author Ryan LeFevre
 * 
 */
public abstract class CamanFilter {
	protected LinkedList<Object> params;
	
	public CamanFilter() {
		params = new LinkedList<Object>();
	}
	
	public CamanFilter set(Object param) {
		params.add(param);
		
		// Chainability
		return this;
	}
	
	public CamanFilter set(Object[] paramArr) {
		for (Object param : paramArr) {
			params.add(param);
		}
		
		return this;
	}
	
	public abstract int[] process(int[] rgb);
}
