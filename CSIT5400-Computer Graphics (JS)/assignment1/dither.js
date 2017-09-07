(function(imageproc) {
    "use strict";

    /*
     * Apply ordered dithering to the input data
     */
    imageproc.dither = function(inputData, outputData, matrix, levels) {
        for (var y = 0; y < inputData.height; y++) {
            for (var x = 0; x < inputData.width; x++) {
                var pixel = imageproc.getPixel(inputData, x, y);

                /* Change the colour to grayscale and normalize it */
                var value = pixel.r * 0.2126 +
                            pixel.g * 0.7152 +
                            pixel.b * 0.0722;
                value = value / 255 * levels;

                /* Get the corresponding threshold of the pixel */
                var threshold = matrix[y % matrix.length][x % matrix[0].length];

                /* Set the colour to black or white based on threshold */
                var i = (x + y * outputData.width) * 4;
                outputData.data[i]     =
                outputData.data[i + 1] =
                outputData.data[i + 2] = (value < threshold)? 0 : 255;
            }
        }
    }
 
}(window.imageproc = window.imageproc || {}));
