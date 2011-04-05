/**
 * 
 */
package com.meltingice.caman;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @author Ryan LeFevre
 *
 */
public class Image {
	private String filename;
	private BufferedImage image;
	
	public int[][][] pixels;
	
	public Image(String file) throws IOException {
		this.filename = file;
		
		this.prepare();
	}
	
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
	
	public int getWidth() {
		return image.getWidth();
	}
	
	public int getHeight() {
		return image.getHeight();
	}
	
	public int getPixelRGB(int x, int y) {
		return pixels[x][y][3] << 24 | pixels[x][y][0] << 16 | pixels[x][y][1] << 8 | pixels[x][y][2];
	}
	
	public BufferedImage getDestImage() {
		ColorModel dCM = image.getColorModel();
		return new BufferedImage(dCM, dCM.createCompatibleWritableRaster(getWidth(), getHeight()), dCM.isAlphaPremultiplied(), null);
	}
	
	private int[] getPixelData(int i, int j) {
		int rgb = image.getRGB(i, j);
		int[] result = new int[] {
				(rgb >> 16)	& 0xff,	// red
				(rgb >> 8)	& 0xff,	// green
				(rgb) 		& 0xff,	// blue
				(rgb >> 24) & 0xff	// alpha
		};
		
		return result;
	}
}
