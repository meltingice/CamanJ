# CamanJ

This is a super-early, proof-of-concept, port of <a href="http://github.com/meltingice/CamanJS">CamanJS</a> to Java. A lot of the code patterns are still being fleshed out, and will probably change in the future until the library enters a more stable state.

Suggestions and contributions are welcome, although suggestions would probably be better suited for the project at this point in time.

## Example Usage

  // Load CamanJ with our image
  CamanJ caman = new CamanJ("images/example1.jpg");
  
  // Apply the brightness filter
  caman.apply("Brightness", 50);
  
  // Output the modified image to a new file
  caman.save("output/example1.png");

## Goals

* Similar interface to the original CamanJS library
* Very simple to use for image manipulation
* Android compatibility
* Easily extensible with new funcitonality (e.g. with JARs and ServiceLoader)