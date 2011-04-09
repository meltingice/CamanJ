package com.meltingice.caman;

public class Example {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CamanJ caman = new CamanJ("images/example1.jpg");
		caman.filter("brightness").set(30);
		caman.filter("contrast").set(15.0);
		caman.filter("hue").set(90.0);
		
		caman.save("output/example1.png");
		
		System.out.println("Done!");
	}

}
