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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.meltingice.caman.CamanFilter;
import com.meltingice.caman.Image;

/**
 * Utility class of static methods
 * 
 * @author Ryan LeFevre
 * @version 1.0
 */
public class CamanUtil {
	/**
	 * Clamps an integer value between 0 and 255
	 * 
	 * @param val
	 *            The value to clamp
	 * @return The clamped integer
	 */
	public static int clampRGB(int val) {
		if (val < 0) {
			return 0;
		} else if (val > 255) {
			return 255;
		}

		return val;
	}

	/**
	 * Clamps an array of integers between 0 and 255. Useful when returning an
	 * integer array from {@link CamanFilter#process(int[])}
	 * 
	 * @param rgb
	 *            The array of integers to clamp
	 * @return The array of clamped integers
	 */
	public static int[] clampRGB(int[] rgb) {
		rgb[0] = clampRGB(rgb[0]);
		rgb[1] = clampRGB(rgb[1]);
		rgb[2] = clampRGB(rgb[2]);
		//rgb[3] = clampRGB(rgb[3]);

		return rgb;
	}

	/**
	 * Clamps an array of doubles and returns an array of integers. The reason
	 * it doesn't return an array of doubles is because this is designed to be
	 * returned from {@link CamanFilter#process(int[])}, and the return must be
	 * an int array. Doubles are converted to ints by simple casting, so the
	 * decimal portion of the double is dropped without rounding.
	 * 
	 * @param drgb
	 *            The array of doubles to clamp
	 * @return The array of clamped integers
	 */
	public static int[] clampRGB(double[] drgb) {
		int[] rgb = new int[4];
		rgb[0] = clampRGB((int) drgb[0]);
		rgb[1] = clampRGB((int) drgb[1]);
		rgb[2] = clampRGB((int) drgb[2]);
		//rgb[3] = clampRGB((int) drgb[3]);

		return rgb;
	}

	/**
	 * Converts an integer array to a double array.
	 * 
	 * @param rgb
	 *            The integer array to convert
	 * @return The array of doubles
	 */
	public static double[] toDouble(int[] rgb) {
		return new double[] { (double) rgb[0], (double) rgb[1],
				(double) rgb[2] };
	}

	/**
	 * Given an input string, format it to produce the class name for a filter.
	 * All it does is capitalize the first letter of the name.
	 * 
	 * @param input
	 *            The filter name
	 * @return The formatted filter name
	 */
	public static String getClassName(String input) {
		if (input.length() == 0) {
			return input;
		}

		return Character.toUpperCase(input.charAt(0)) + input.substring(1);
	}

	/**
	 * Returns a pseudorandom double within the given range.
	 * 
	 * @param min
	 *            The minimum inclusive value
	 * @param max
	 *            The maximum inclusive value
	 * @return The pseudorandom value
	 */
	public static double randomRange(double min, double max) {
		return min + (Math.random() * ((max - min) + 1));
	}

	/**
	 * Returns a pseudorandom int within the given range.
	 * 
	 * @param min
	 *            The minimum inclusive value
	 * @param max
	 *            The maximum inclusive value
	 * @return The pseudorandom value
	 */
	public static int randomRange(int min, int max) {
		return min + (int) (Math.random() * ((max - min) + 1));
	}

	/**
	 * Converts a string of channel characters (r, g, or b) to their appropriate
	 * index in the standard rgb array. In other words, r => 0, g => 1, b => 2
	 * 
	 * @param chans
	 *            A string of color channels
	 * @return An array of ints representing the color channels given
	 */
	public static int[] channelsToIntArray(String chans) {
		int[] result = new int[chans.length()];
		for (int i = 0; i < chans.length(); i++) {
			char c = chans.charAt(i);

			if (c == 'r') {
				result[i] = 0;
			} else if (c == 'g') {
				result[i] = 1;
			} else if (c == 'b') {
				result[i] = 2;
			}
		}

		return result;
	}

	/**
	 * Utility function to reverse an array of any type.
	 * 
	 * @param <T>
	 *            Will be automatically determined from the type of object array
	 *            given
	 * @param arr
	 *            The object array to reverse
	 * @return The reversed object array
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] reverseArray(T[] arr) {
		List<T> list = Arrays.asList(arr);
		Collections.reverse(list);
		return (T[]) list.toArray();
	}

	/**
	 * Generates a convolution kernel from the given image and location.
	 * 
	 * @param image
	 *            The image to retrieve the pixel color values from.
	 * @param kernelSize
	 *            The size of the kernel to produce.
	 * @param x
	 *            The x coordinate of the kernel.
	 * @param y
	 *            The y coordinate of the kernel.
	 * @return The convolution kernel.
	 */
	public static int[][] buildKernel(Image image, int kernelSize, int x, int y) {
		int[][] kernel = new int[kernelSize][4];
		int builder = ((int) Math.sqrt(kernelSize) - 1) / 2;

		int builderIndex = 0;
		for (int j = -builder; j <= builder; j++) {
			for (int i = -builder; i <= builder; i++) {
				try {
					kernel[builderIndex] = image.getPixel(x + i, y + j);
				} catch (Exception e) {
					// Edge of the image, set all colors to 0
					kernel[builderIndex] = new int[] { 0, 0, 0, 255 };
				}

				builderIndex++;
			}
		}

		return kernel;
	}
}
