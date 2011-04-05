/**
 * CamanJ is a loose Java port of the CamanJS image manipulation library (originally written in Javascript).
 */
package com.meltingice.caman;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.meltingice.caman.exceptions.InvalidArgument;

/**
 * @author Ryan LeFevre
 * 
 */
public class CamanJ {
	private Image image;
	private HashMap<String, CamanFilter> plugins;

	public CamanJ(String file) {
		try {
			this.image = new Image(file);
		} catch (IOException e) {
			System.err.println("CamanJ: Unable to load image from file");
		}

		this.plugins = new HashMap<String, CamanFilter>();
	}

	/**
	 * Lazy-loads a filter using reflection and caches the object instance so
	 * reflection is only used once per filter.
	 * 
	 * @param name
	 *            The name of the filter to instantiate
	 * @return The CamanFilter object
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	private CamanFilter loadFilter(String name) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		if (plugins.containsKey(name)) {
			return plugins.get(name);
		}

		Class c1 = Class.forName("com.meltingice.caman.filters."
				+ CamanUtil.getFilterName(name));
		CamanFilter plugin = (CamanFilter) c1.newInstance();

		System.out.println("CamanJ: loaded filter " + name);
		plugins.put(name, plugin);
		return plugin;
	}

	/**
	 * Applies a filter with the given name to the image.
	 * 
	 * @param name
	 *            The name of the filter
	 * @param value
	 *            The adjustment amount
	 * @return This object (for chaining purposes)
	 */
	public CamanJ applyFilter(String name, double... value) {
		try {
			CamanFilter plugin = loadFilter(name);

			for (int i = 0; i < image.getWidth(); i++) {
				for (int j = 0; j < image.getHeight(); j++) {
					try {
						if (value.length == 0) {
							image.pixels[i][j] = plugin.process(image.pixels[i][j]);
						} else if (value.length == 1) {
							image.pixels[i][j] = plugin.process(image.pixels[i][j], value[0]);
						}
						
					} catch (InvalidArgument e) {
						System.err.println("CamanJ: invalid arguments to "
								+ name + " plugin");
						return this;
					}
				}
			}
		} catch (Exception e) {
			System.err.println("Error executing plugin " + name);
			e.printStackTrace();
		}

		return this;
	}

	/**
	 * Saves the modified image to a new BufferedImage object
	 * 
	 * @return The new BufferedImage object
	 */
	public BufferedImage save() {
		BufferedImage dest = image.getDestImage();

		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				dest.setRGB(i, j, image.getPixelRGB(i, j));
			}
		}

		return dest;
	}

	/**
	 * Saves the modified image to a file.
	 * 
	 * @param outFile
	 *            The path to the output file.
	 */
	public void save(String outFile) {
		BufferedImage dest = this.save();
		File file = new File(outFile);
		try {
			ImageIO.write(dest, "png", file);
		} catch (IOException e) {
			System.err.println("CamanJ: error writing to file "
					+ file.getName());
		}
	}
}
