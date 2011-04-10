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

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Represents a single image. Abstracts over retrieving pixel data and loading
 * images from files.
 * 
 * @author Ryan LeFevre
 * @version 1.0
 */
public class Image {
	private String filename;
	private BufferedImage image;

	public int[][][] pixels;

	/**
	 * Creates a new Image based on the given filename.
	 * 
	 * @param file
	 *            The filepath to the image to load
	 * @throws IOException
	 *             If the image cannot be found or loaded.
	 */
	public Image(String file) throws IOException {
		this.filename = file;

		this.prepare();
	}

	/**
	 * Prepares the image by loading it and retrieving its pixel values.
	 * 
	 * @throws IOException
	 *             If the image cannot be read or found.
	 */
	private void prepare() throws IOException {
		image = ImageIO.read(new File(this.filename));
		pixels = new int[getWidth()][getHeight()][3];

		for (int i = 0; i < getWidth(); i++) {
			for (int j = 0; j < getHeight(); j++) {
				int[] rgb = getPixelData(i, j);
				pixels[i][j] = rgb;
			}
		}
	}

	/**
	 * Gets the width of the image
	 * 
	 * @return the width
	 */
	public int getWidth() {
		return image.getWidth();
	}

	/**
	 * Gets the height of the image
	 * 
	 * @return the height
	 */
	public int getHeight() {
		return image.getHeight();
	}

	/**
	 * Gets the RGB value of an arbitrary pixel in the image specified by pixel
	 * location.
	 * 
	 * @param x
	 *            The x location of the pixel
	 * @param y
	 *            The y location of the pixel
	 * @return The RGB values of the pixel
	 */
	public int getPixelRGB(int x, int y) {
		return pixels[x][y][3] << 24 | pixels[x][y][0] << 16
				| pixels[x][y][1] << 8 | pixels[x][y][2];
	}

	/**
	 * Returns a writeable BufferedImage that is used to write the rendered
	 * image into.
	 * 
	 * @return A writeable BufferedImage object
	 */
	public BufferedImage getDestImage() {
		ColorModel dCM = image.getColorModel();
		return new BufferedImage(dCM, dCM.createCompatibleWritableRaster(
				getWidth(), getHeight()), dCM.isAlphaPremultiplied(), null);
	}

	private int[] getPixelData(int i, int j) {
		int rgb = image.getRGB(i, j);
		int[] result = new int[] { (rgb >> 16) & 0xff, // red
				(rgb >> 8) & 0xff, // green
				(rgb) & 0xff, // blue
				(rgb >> 24) & 0xff // alpha
		};

		return result;
	}
}
