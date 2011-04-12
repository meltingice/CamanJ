# CamanJ

This is an early port of the JS image manipulation library <a href="http://github.com/meltingice/CamanJS">CamanJS</a> to Java. There is still a lot of room for improvement and not all functionality has been ported over yet; however, CamanJ is quickly improving in speed and memory management.

Both suggestions and contributions are welcome!

## Latest Benchmarks

All benchmarks are run against an image of size 3872x2592 using the Example.java program.

### Mac OSX
* Test CPU: Intel Core i7 2.66GHz (dual-core with hyperthreading)
* Render time: ~1880ms

### Windows 7
* Test CPU: Intel Core i7 2.75GHz (quad-core with hyperthreading)
* Render time: ~1100ms

## Example Usage

    // Load CamanJ with our image
    CamanJ caman = new CamanJ("images/example1.jpg");
  
    // Apply the brightness and contrast filters
    caman.filter("brightness").set(30);
    caman.filter("contrast").set(10);
    
    // Apply a preset too if you want
    caman.preset("lomography");
  
    // Output the modified image to a new file
    caman.save("output/example1.jpg");

## Goals

* Similar interface to the original CamanJS library
* Very simple to use for image manipulation
* Extremely fast with the ability to handle very large images
* Android compatibility
* Easily extensible with new funcitonality (e.g. with JARs and ServiceLoader)