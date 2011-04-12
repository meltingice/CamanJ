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

	public int[] pixels;

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
		pixels = new int[getWidth() * getHeight()];
		
		image.getRGB(0, 0, getWidth(), getHeight(), pixels, 0, getWidth());
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
		int index = xyToIndex(x, y);
		return pixels[index] << 24 | pixels[index] << 16
				| pixels[index] << 8 | pixels[index];
	}

	/**
	 * Returns a writeable BufferedImage that is used to write the rendered
	 * image into.
	 * 
	 * @return A writeable BufferedImage object
	 */
	public BufferedImage getDestImage() {
		image.setRGB(0, 0, getWidth(), getHeight(), pixels, 0, getWidth());
		return image;
	}

	public int[] getPixel(int x, int y) {
		int index = xyToIndex(x, y);
		return new int[] {
				pixels[index] >> 16 & 0xff,
				pixels[index] >> 8 & 0xff,
				pixels[index] & 0xff
		};
	}
	
	public void setPixel(int x, int y, int[] rgb) {
		int index = xyToIndex(x, y);
		pixels[index] = 0xff000000 | (rgb[0] << 16) | (rgb[1] << 8) | rgb[2];
	}
	
	private int xyToIndex(int x, int y) {
		return y * getWidth() + x;
	}
}
