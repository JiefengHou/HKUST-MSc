(function(imageproc) {
    "use strict";

    /*
     * Apply Sobel edge detection to the input data, assuming the input is
     * a grayscale image
     *
     * Returning a buffer containing the strength and direction of the gradient
     */
    imageproc.sobelEdge = function(inputData) {
        /* Initialize the two edge filters Gx and Gy */
        var Gx = [
            [-1, 0, 1],
            [-2, 0, 2],
            [-1, 0, 1]
        ];
        var Gy = [
            [-1,-2,-1],
            [ 0, 0, 0],
            [ 1, 2, 1]
        ];

        /* Prepare the buffer */
        var buffer = [];

        /* Apply the two edge filters */
        for (var y = 0; y < inputData.height; y++) {
            buffer[y] = [];
            for (var x = 0; x < inputData.width; x++) {
                var gx = 0, gy = 0;

                /* Sum the product of the two edge kernels */
                for (var j = -1; j <= 1; j++) {
                    for (var i = -1; i <= 1; i++) {
                        var pixel =
                            imageproc.getPixel(inputData, x + i, y + j);
                        gx += pixel.r * Gx[j + 1][i + 1];
                        gy += pixel.r * Gy[j + 1][i + 1];
                    }
                }

                /* Find the strength and direction of the gradient */
                var strength = Math.hypot(gx, gy);
                var direction;
                if (gx == 0) 
                    direction = (gy == 0)? 0 : Math.PI / 2;
                else
                    direction = Math.atan2(gy, gx);

                /* Put the values in the buffer */
                buffer[y][x] = { strength: strength, direction: direction };
            }
        }

        /* Return the results */
        return buffer;
    }
 
}(window.imageproc = window.imageproc || {}));
