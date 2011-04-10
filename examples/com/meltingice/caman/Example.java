package com.meltingice.caman;

public class Example {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CamanJ caman = new CamanJ("images/example1.jpg");
		caman.filter("brightness").set(10);
		caman.filter("contrast").set(15);
		caman.filter("sepia").set(80);
		caman.filter("noise").set(10);
		
		caman.save("output/example1.png");
		
		System.out.println("Done!");
	}

}
