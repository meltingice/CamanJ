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
package com.meltingice.caman.presets;

import com.meltingice.caman.CamanJ;
import com.meltingice.caman.CamanPreset;

/**
 * @author Ryan LeFevre
 * @version 1.0
 */
public class Jarques implements CamanPreset {

	/* (non-Javadoc)
	 * @see com.meltingice.caman.CamanPreset#apply()
	 */
	@Override
	public void apply(CamanJ caman) {
		caman.filter("saturation").set(-35);
		caman.filter("curves")
			.set("b")
			.set(new Integer[] {20, 0})
			.set(new Integer[] {90, 120})
			.set(new Integer[] {186, 144})
			.set(new Integer[] {255, 230});
		
		caman.filter("curves")
			.set("r")
			.set(new Integer[] {0, 0})
			.set(new Integer[] {144, 90})
			.set(new Integer[] {138, 120})
			.set(new Integer[] {255, 255});
		
		caman.filter("curves")
			.set("g")
			.set(new Integer[] {10, 0})
			.set(new Integer[] {115, 105})
			.set(new Integer[] {148, 100})
			.set(new Integer[] {255, 248});
		
		caman.filter("curves")
			.set("rgb")
			.set(new Integer[] {0, 0})
			.set(new Integer[] {120, 100})
			.set(new Integer[] {128, 140})
			.set(new Integer[] {255, 255});
		
		caman.filter("sharpen").set(20);
	}

}
