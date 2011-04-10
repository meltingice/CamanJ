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
package com.meltingice.caman.util;

import java.util.HashMap;

/**
 * Class for generating a Bezier curve that can be queried by X coordinate to
 * return a Y coordinate. For CamanJ, the X coordinate is the input color, and
 * the Y coordinate is the output color. It helps to think of Photoshop's curves
 * functionality when thinking about Bezier curves. While that uses a different
 * curves equation, the idea is very similar.
 * 
 * It generates the curve by running a certain number of iterations from the
 * start coordinate to the end coordinate. For edge cases where certain X
 * coordinates aren't defined, it uses linear interpolation to approximate what
 * the value should be. Usually this approximation is very accurate.
 * 
 * @author Ryan LeFevre
 * @version 1.0
 */
public class Bezier {
	private int[] start, ctrl1, ctrl2, end;
	private int lowBound, highBound;
	private int iterations = 1000;

	/**
	 * The hashmap that will store our bezier curve values
	 */
	private HashMap<Integer, Integer> bezier;

	/**
	 * Create a new Bezier curve with the given points and bounds.
	 * 
	 * @param start
	 *            The start x,y coords
	 * @param ctrl1
	 *            The control point 1 x,y coords
	 * @param ctrl2
	 *            The control point 2 x,y coords
	 * @param end
	 *            The end x,y coords
	 * @param lowBound
	 *            The low bound of the Y values in the Bezier curve
	 * @param highBound
	 *            The high bound of the Y values in the Bezier curve
	 */
	public Bezier(int[] start, int[] ctrl1, int[] ctrl2, int[] end,
			int lowBound, int highBound) {
		this.start = start;
		this.ctrl1 = ctrl1;
		this.ctrl2 = ctrl2;
		this.end = end;
		this.lowBound = lowBound;
		this.highBound = highBound;

		this.bezier = new HashMap<Integer, Integer>();

		this.solve();
	}

	/**
	 * Create a new Bezier curve with the given points. This sets the low and
	 * high bound to 0 and 255, respectively.
	 * 
	 * @param start
	 *            The start x,y coords
	 * @param ctrl1
	 *            The control point 1 x,y coords
	 * @param ctrl2
	 *            The control point 2 x,y coords
	 * @param end
	 *            The end x,y coords
	 */
	public Bezier(int[] start, int[] ctrl1, int[] ctrl2, int[] end) {
		this.start = start;
		this.ctrl1 = ctrl1;
		this.ctrl2 = ctrl2;
		this.end = end;

		// Easiest way to specify no bounds is to simply set low and high to the
		// min and max values for Integer, respectively.
		this.lowBound = Integer.MIN_VALUE;
		this.highBound = Integer.MAX_VALUE;

		this.bezier = new HashMap<Integer, Integer>();

		this.solve();
	}

	/**
	 * Sets the number of iterations to run between the start and end
	 * coordinates. The default is normally ok, but if you have a very extreme
	 * bezier curve, you may need to increase the number of iterations for more
	 * accurate results.
	 * 
	 * @param iter
	 *            The number of iterations
	 */
	public void setIterations(int iter) {
		this.iterations = iter;
	}

	/**
	 * Fills the edges of the bezier curve out to a certain start and end point.
	 * This is used to ensure that all values between the start and end values
	 * are accounted for.
	 * 
	 * @param s
	 * @param e
	 */
	public void fillEnds(int s, int e) {
		// If our curve starts after x = 0, initialize it with a flat line until
		// the curve begins.
		if (start[0] > s) {
			for (int i = 0; i < start[0]; i++) {
				bezier.put(i, start[1]);
			}
		}

		// ... and the same with the end point
		if (end[0] < e) {
			for (int i = end[0]; i <= 255; i++) {
				bezier.put(i, end[1]);
			}
		}
	}

	/**
	 * Lets you query the Bezier curve to convert an X input into a Y output
	 * value.
	 * 
	 * @param x
	 *            The input color
	 * @return The output color based on the curve.
	 */
	public int query(int x) {
		return bezier.get(x);
	}

	/**
	 * Solves the bezier curve and generates all Y values based on the X values
	 * from the start to the end coordinates. Every value is guaranteed because
	 * it uses linear interpolation to estimate points that are missing.
	 */
	private void solve() {
		double Ax, Bx, Cx, Ay, By, Cy;
		double x0, x1, x2, x3, y0, y1, y2, y3;
		double t;
		int curveX, curveY;

		x0 = start[0];
		x1 = ctrl1[0];
		x2 = ctrl2[0];
		x3 = end[0];
		y0 = start[1];
		y1 = ctrl1[1];
		y2 = ctrl2[1];
		y3 = end[1];

		Cx = 3 * (x1 - x0);
		Bx = 3 * (x2 - x1) - Cx;
		Ax = x3 - x0 - Cx - Bx;

		Cy = 3 * (y1 - y0);
		By = 3 * (y2 - y1) - Cy;
		Ay = y3 - y0 - Cy - By;

		for (int i = 0; i < iterations; i++) {
			t = i / (double) iterations;

			curveX = (int) Math.round((Ax * Math.pow(t, 3))
					+ (Bx * Math.pow(t, 2)) + (Cx * t) + x0);
			curveY = (int) Math.round((Ay * Math.pow(t, 3))
					+ (By * Math.pow(t, 2)) + (Cy * t) + y0);

			if (curveY < lowBound) {
				curveY = lowBound;
			} else if (curveY > highBound) {
				curveY = highBound;
			}

			bezier.put(curveX, curveY);
		}

		// Do a search for missing values in the bezier array and use linear
		// interpolation to approximate their values.
		double[] leftCoord = new double[2];
		double[] rightCoord = new double[2];
		if (bezier.size() < end[0] + 1) {
			for (int i = start[0]; i <= end[0]; i++) {
				if (!bezier.containsKey(i)) {
					// The value to the left will always be defined. We don't
					// have to worry about when i = 0 because the starting point
					// is guaranteed (I think...)
					leftCoord[0] = i - 1;
					leftCoord[1] = bezier.get(i - 1);

					// Find the first value to the right that was found. Ideally
					// this loop will break
					// very quickly.
					for (int j = i; j <= end[0]; j++) {
						if (!bezier.containsKey(j)) {
							rightCoord[0] = j;
							rightCoord[1] = bezier.get(j);
							break;
						}
					}

					bezier.put(
							i,
							(int) (leftCoord[1] + ((rightCoord[1] - leftCoord[1]) / (rightCoord[0] - leftCoord[0]))
									* (i - leftCoord[0])));
				}
			}
		}

		// Edge case
		if (!bezier.containsKey(end[0])) {
			bezier.put(end[0], bezier.get(end[0] - 1));
		}
	}
}
