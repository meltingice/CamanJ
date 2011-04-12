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
import java.util.LinkedList;

import javax.imageio.ImageIO;

import com.meltingice.caman.util.CamanUtil;

/**
 * The main CamanJ class that is interacted with.
 * 
 * @author Ryan LeFevre
 * @version 1.0
 */
public class CamanJ {
	/**
	 * The Image object that stores important information about the current
	 * image loaded with this CamanJ object
	 */
	private Image image;

	/**
	 * The render queue of filters
	 */
	private LinkedList<CamanFilter> filters;

	/**
	 * A flag that says if this image has been rendered or not yet.
	 */
	private boolean isRendered = false;

	/**
	 * Creates a new standard CamanJ object
	 * 
	 * @param file
	 *            The filename of the image to load
	 */
	public CamanJ(String file) {
		try {
			this.image = new Image(file);
		} catch (IOException e) {
			System.err.println("CamanJ: Unable to load image from file");
		}

		this.filters = new LinkedList<CamanFilter>();
	}

	/**
	 * Lazy-loads a filter using reflection and adds it to the filter list so
	 * that it can be executed later. All filters at this time must reside in
	 * the package com.meltingice.caman.filters. In the future, this will
	 * support loading from JAR files for easily extensibility.
	 * 
	 * @param name
	 *            The name of the filter to instantiate
	 * @return The CamanFilter object
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	private CamanFilter loadFilter(String name) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException {

		Class c1 = Class.forName("com.meltingice.caman.filters."
				+ CamanUtil.getClassName(name));
		CamanFilter plugin = (CamanFilter) c1.newInstance();

		System.out.println("CamanJ: loaded filter " + name);
		filters.add(plugin);
		return plugin;
	}

	/**
	 * Lazy loads a preset using reflection. All presets must reside in
	 * com.meltingice.caman.presets at this point in time.
	 * 
	 * @param name
	 *            The name of the preset to load
	 * @return The preset
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("unchecked")
	private CamanPreset loadPreset(String name) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException {
		Class c1 = Class.forName("com.meltingice.caman.presets."
				+ CamanUtil.getClassName(name));
		CamanPreset preset = (CamanPreset) c1.newInstance();

		System.out.println("CamanJ: loaded preset " + name);
		return preset;
	}

	/**
	 * Loads a filter and returns it so that its parameters (if any) can be set.
	 * 
	 * @param name
	 *            The name of the filter
	 * @return The filter object
	 */
	public CamanFilter filter(String name) {
		try {
			CamanFilter plugin = loadFilter(name);

			// Reset the isRendered flag because we've added more filters
			isRendered = false;

			return plugin;
		} catch (Exception e) {
			System.err.println("CamanJ: Invalid filter name");
			return null;
		}
	}

	/**
	 * Loads and applies a preset set of filters to the current image.
	 * 
	 * @param name
	 *            The name of the preset
	 */
	public void preset(String name) {
		try {
			CamanPreset preset = loadPreset(name);
			preset.apply(this);
		} catch (Exception e) {
			System.err.println("CamanJ: Unable to find or execute preset "
					+ name);
		}
	}

	/**
	 * Goes through all of the filters (in order) and applies them to the image.
	 */
	public void render() {
		CamanRenderer renderer = new CamanRenderer(this.image, this.filters);
		renderer.render();
		
		isRendered = true;
	}

	/**
	 * Saves the modified image to a new BufferedImage object. Will
	 * automatically call render() if it has not been called already.
	 * 
	 * @return The new BufferedImage object
	 */
	public BufferedImage save() {
		if (!isRendered) {
			// If the image hasn't been rendered yet, do so first.
			render();
		}

		return image.getDestImage();
	}

	/**
	 * Saves the modified image to a file.
	 * 
	 * @param outFile
	 *            The path to the output file.
	 */
	public void save(String outFile) {
		BufferedImage dest = this.save();

		// Determine image type
		// TODO verify type is valid
		String ext = outFile.substring(outFile.lastIndexOf('.') + 1,
				outFile.length());

		File file = new File(outFile);
		try {
			System.out.println("CamanJ: writing " + outFile + " to file");
			ImageIO.write(dest, ext, file);
		} catch (IOException e) {
			System.err.println("CamanJ: error writing to file "
					+ file.getName());
		}
	}
}
