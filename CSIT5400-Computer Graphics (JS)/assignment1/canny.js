(function(imageproc) {
    "use strict";

    /*
     * Apply Canny edge detection to the input data
     */
    imageproc.cannyEdge = function(inputData, outputData, size,
                                   threshold1, threshold2) {
        /* Internal function to perform non-maximum suppression */
        function thinEdges() {
            /* Put the new edge data in a temporary array */
            var edgeData = [];

            for (var y = 0; y < inputData.height; y++) {
                edgeData[y] = [];
                for (var x = 0; x < inputData.width; x++) {
                    /* Get the strength */
                    var strength = sobelData[y][x].strength;

                    /* Normalize the direction to 0 and 1 */
                    var direction = sobelData[y][x].direction;
                    direction = direction / Math.PI;
                    if (direction < 0) direction += 1;

                    /* Get the adjacent pixels along the gradient */
                    var left, right;
                    if (direction < 0.125 || direction >= 0.875) {
                        left = (x > 0)?
                            sobelData[y][x - 1].strength : strength;
                        right = (x < inputData.width - 1)?
                            sobelData[y][x + 1].strength : strength;
                    }
                    else if (direction < 0.375) {
                        left = (x > 0 && y > 0)?
                            sobelData[y - 1][x - 1].strength : strength;
                        right =
                            (x < inputData.width-1 && y < inputData.height-1)?
                            sobelData[y + 1][x + 1].strength : strength;
                    }
                    else if (direction < 0.625) {
                        left = (y > 0)?
                            sobelData[y - 1][x].strength : strength;
                        right = (y < inputData.height - 1)?
                            sobelData[y + 1][x].strength : strength;
                    }
                    else if (direction < 0.875) {
                        left = (x > 0 && y < inputData.height - 1)?
                            sobelData[y + 1][x - 1].strength : strength;
                        right =
                            (x < inputData.width - 1 && y > 0)?
                            sobelData[y - 1][x + 1].strength : strength;
                    }

                    /* Suppress the non-maximum */
                    if (left > strength || right > strength)
                        edgeData[y][x] = 0;
                    else
                        edgeData[y][x] = strength;
                }
            }

            /* Copy the new edge data to the sobel buffer */
            for (var y = 0; y < inputData.height; y++) {
                for (var x = 0; x < inputData.width; x++) {
                    sobelData[y][x].strength = edgeData[y][x];
                }
            }
        }

        /* Internal function to recursively trace the edge */
        function traceEdge(x, y) {
            /* Get around the edge pixel to recursively trace a weak edge */
            for (var j = -1; j <= 1; j++) {
                for (var i = -1; i <= 1; i++) {
                    if (i == 0 && j == 0) continue;
                    if (x + i < 0 || x + i >= inputData.width) continue;
                    if (y + j < 0 || y + j >= inputData.height) continue;

                    /* If the pixel is a weak edge */
                    if (sobelData[y + j][x + i].strength > threshold2) {
                        /* And it is not yet an edge, trace the edge pixel */
                        if (!confirmedEdges[y + j][x + i]) {
                            confirmedEdges[y + j][x + i] = true;
                            traceEdge(x + i, y + j, confirmedEdges);
                        }
                    }
                }
            }
        }

        /* Internal function to trace the edges from the strong edge pixels */
        function traceEdges() {
            /* Start to trace the edges */
            for (var y = 0; y < inputData.height; y++) {
                for (var x = 0; x < inputData.width; x++) {
                    /* Strong threshold starts a new edge */
                    if (sobelData[y][x].strength > threshold1) {
                        /* If not yet a confirmed edge */
                        if (!confirmedEdges[y][x]) {
                            confirmedEdges[y][x] = true;
                            traceEdge(x, y);
                        }
                    }
                }
            }
        }

        /* Create two buffers and ping-pong between the two buffers */
        var buffer1 = imageproc.createBuffer(inputData);
        var buffer2 = imageproc.createBuffer(inputData);

        /* Stage 0 - Change the image to grayscale */
        imageproc.grayscale(inputData, buffer1);

        /* Stage 1 - Smooth the image using a Gaussian filter */
        imageproc.gaussianBlur(buffer1, buffer2, size);

        /* Stage 2 - Detect edges */
        var sobelData = imageproc.sobelEdge(buffer2);

        /* Stage 3 - Thin edges */
        thinEdges();

        /* Make an array to store the confirmed edges */
        var confirmedEdges = [];
        for (var y = 0; y < inputData.height; y++) {
            confirmedEdges[y] = [];
            for (var x = 0; x < inputData.width; x++)
                confirmedEdges[y][x] = false;
        }
            
        /* Stage 4 - Trace and confirm edges */
        traceEdges();

        /* Put the confirmed edges to the output data */
        for (var y = 0; y < inputData.height; y++) {
            for (var x = 0; x < inputData.width; x++) {
                var i = (x + y * inputData.width) * 4;
                outputData.data[i]     =
                outputData.data[i + 1] =
                outputData.data[i + 2] = (confirmedEdges[y][x])? 255 : 0;
            }
        }
    }
 
}(window.imageproc = window.imageproc || {}));
