package com.meltingice.caman;

public class Example {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CamanJ caman = new CamanJ("images/example1.jpg");
		caman.apply("Brightness", 50);
		caman.save("output/example1.png");
		
		System.out.println("Done!");
	}

}
