/**
 * 
 */
package com.meltingice.caman;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.meltingice.caman.exceptions.InvalidArgument;

/**
 * @author rlefevre
 * 
 */
public class CamanJ {
	private Image image;
	private HashMap<String, CamanPlugin> plugins;

	public CamanJ(String file) {
		try {
			this.image = new Image(file);
		} catch (IOException e) {
			System.err.println("CamanJ: Unable to load image from file");
		}
		
		this.plugins = new HashMap<String, CamanPlugin>();
	}

	private CamanPlugin loadPlugin(String name) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		if (plugins.containsKey(name)) {
			return plugins.get(name);
		}

		Class c1 = Class.forName("com.meltingice.caman.plugins." + name);
		CamanPlugin plugin = (CamanPlugin) c1.newInstance();

		System.out.println("CamanJ: loaded plugin " + name);
		plugins.put(name, plugin);
		return plugin;
	}

	public CamanJ apply(String name, double value) {
		try {
			CamanPlugin plugin = loadPlugin(name);

			for (int i = 0; i < image.getWidth(); i++) {
				for (int j = 0; j < image.getHeight(); j++) {
					try {
						image.pixels[i][j] = plugin.process(image.pixels[i][j],
								value);
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

	public BufferedImage save() {
		BufferedImage dest = image.getDestImage();

		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				dest.setRGB(i, j, image.getPixelRGB(i, j));
			}
		}

		return dest;
	}

	public void save(String outFile) {
		BufferedImage dest = this.save();
		File file = new File(outFile);
		try {
			ImageIO.write(
					dest,
					"png",
					file);
		} catch (IOException e) {
			System.err.println("CamanJ: error writing to file "
					+ file.getName());
		}
	}
}
