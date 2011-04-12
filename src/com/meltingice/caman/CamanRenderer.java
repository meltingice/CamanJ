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

import java.util.LinkedList;

import com.meltingice.caman.exceptions.InvalidArgumentsException;
import com.meltingice.caman.exceptions.InvalidPluginException;
import com.meltingice.caman.util.CamanUtil;

/**
 * Multithreaded image renderer. Applies all of the chosen filters to the image.
 * 
 * @author Ryan LeFevre
 * @version 1.0
 */
public class CamanRenderer {

	private Image image;
	private LinkedList<CamanFilter> filters;
	private LinkedList<RenderThread> threads;

	// Automatically set the concurrency to the number of available processors
	// on this computer, which should be the ideal amount.
	private static int CONCURRENCY = Runtime.getRuntime().availableProcessors();

	public CamanRenderer(Image image, LinkedList<CamanFilter> filters) {
		this.image = image;
		this.filters = filters;
		this.threads = new LinkedList<RenderThread>();
	}

	public static void setConcurrency(int level) {
		CONCURRENCY = level;
	}

	/**
	 * Goes through all of the filters (in order) and applies them to the image.
	 */
	public void render() {
		int blockHeight = image.getHeight() / CONCURRENCY;
		int lastBlockHeight = blockHeight + (image.getHeight() % CONCURRENCY);

		long startTime = System.currentTimeMillis();

		// Spawn the threads
		for (int i = 0; i < CONCURRENCY; i++) {
			// Figure out bounds
			int start = i * blockHeight;
			int end = start
					+ ((i == CONCURRENCY - 1) ? lastBlockHeight : blockHeight);

			// Spawn thread and start the rendering process
			RenderThread thread = new RenderThread(start, end);
			thread.start();

			threads.add(thread);
		}

		// Wait for the threads to finish
		for (int i = 0; i < CONCURRENCY; i++) {
			try {
				threads.get(i).join();
				System.out.println("CamanJ: block #" + i + " finished!");
			} catch (InterruptedException e) {
				continue;
			}
		}

		// Clean up our lists
		filters.clear();
		threads.clear();

		long endTime = System.currentTimeMillis();
		System.out.println("CamanJ: render finished in "
				+ (endTime - startTime) + "ms");
	}

	/**
	 * A class representing a single thread for rendering the image. The image
	 * is split into {@link CamanRenderer#CONCURRENCY} number of horizontal
	 * blocks, and each block is assigned to a thread.
	 * 
	 * @author Ryan LeFevre
	 * @version 1.0
	 */
	private class RenderThread extends Thread {
		private int start, end;

		public RenderThread(int start, int end) {
			this.start = start;
			this.end = end;

			System.out.println("CamanJ: render thread spawned! start = "
					+ start + ", end = " + end);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			for (int i = 0; i < filters.size(); i++) {
				CamanFilter filter = filters.get(i);

				try {
					filter.precomputeParams();
				} catch (InvalidArgumentsException e) {
					System.err.println("CamanJ: invalid arguments given to "
							+ filter.getClass().getName());
					continue;
				}

				if (filter.type() == PluginType.PIXELWISE) {
					renderPixelwise(filter);
				} else if (filter.type() == PluginType.KERNEL) {
					renderKernel(filter);
				}
			}
		}

		private void renderPixelwise(CamanFilter filter) {
			try {
				for (int i = 0; i < image.getWidth(); i++) {
					for (int j = start; j < end; j++) {
						if (filter.type() == PluginType.PIXELWISE) {
							image.setPixel(i, j,
									filter.process(image.getPixel(i, j)));
						}
					}
				}
			} catch (InvalidPluginException e) {
				System.err.println("CamanJ: Error executing filter "
						+ filter.getClass().getName());
				return;
			}
		}

		private void renderKernel(CamanFilter filter) {
			try {
				double[] adjust = filter.getKernel();
				int[][] kernel = new int[adjust.length][4];
				double divisor = 0;
				int[][][] destPixels = new int[image.getWidth()][image
						.getHeight()][4];

				for (int i = 0; i < adjust.length; i++) {
					divisor += adjust[i];
				}

				for (int y = start; y < end; y++) {
					for (int x = 0, width = image.getWidth(); x < width; x++) {
						// Build the kernel from the image's pixels
						kernel = CamanUtil.buildKernel(image, adjust.length, x,
								y);

						// Apply the convolution to the RGB values and place the
						// result in a new pixel array (since we don't want to
						// modify the original pixel array just yet)
						for (int i = 0; i < kernel.length; i++) {
							destPixels[x][y][0] += (int) (adjust[i] * (double) kernel[i][0]);
							destPixels[x][y][1] += (int) (adjust[i] * (double) kernel[i][1]);
							destPixels[x][y][2] += (int) (adjust[i] * (double) kernel[i][2]);
						}

						// Lets not worry about the alpha channel right now
						destPixels[x][y][0] /= divisor;
						destPixels[x][y][1] /= divisor;
						destPixels[x][y][2] /= divisor;
						destPixels[x][y][3] = 255;
					}
				}

				// Now we copy the destPixel array values back into the original
				// pixel array
				for (int i = 0; i < image.getWidth(); i++) {
					for (int j = start; j < end; j++) {
						image.setPixel(i, j,
								CamanUtil.clampRGB(destPixels[i][j]));
					}
				}

			} catch (InvalidPluginException e) {

			}
		}
	}
}
