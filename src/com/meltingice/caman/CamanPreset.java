/**
 * CamanJ - Java Image Manipulation
 * Ported from the CamanJS Javascript library
 *
 * Copyright 2011, Ryan LeFevre
 * Licensed under the new BSD License
 * See LICENSE for more info.
 * 
 * Project Home: http://github.com/meltingice/CamanJ
 */
package com.meltingice.caman;

/**
 * @author Ryan LeFevre
 * @version 1.0
 */
public interface CamanPreset {
	/**
	 * Executes the preset so that it can apply whatever filters it needs to the
	 * current instance of CamanJ.
	 * 
	 * @param caman
	 *            The current instance of CamanJ.
	 */
	public void apply(CamanJ caman);
}
