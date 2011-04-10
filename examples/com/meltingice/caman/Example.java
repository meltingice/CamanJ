package com.meltingice.caman;

public class Example {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CamanJ caman = new CamanJ("images/example1.jpg");
		caman.filter("brightness").set(10);
		caman.filter("contrast").set(15);
		caman.filter("sepia").set(50);
		caman.filter("channels").set(8).set(5).set(-5);
		
		caman.filter("curves")
			.set("g")
			.set(new Integer[] {0, 0})
			.set(new Integer[] {100, 10})
			.set(new Integer[] {200, 250})
			.set(new Integer[] {255, 255});

		caman.save("output/example1.png");
	}

}
