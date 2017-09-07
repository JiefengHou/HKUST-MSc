(function(imageproc) {
    "use strict";

    /*
     * Convert the input data to grayscale
     */
    imageproc.grayscale = function(inputData, outputData) {
        for (var i = 0; i < inputData.data.length; i += 4) {
            var value = inputData.data[i]     * 0.2126 +
                        inputData.data[i + 1] * 0.7152 +
                        inputData.data[i + 2] * 0.0722;

            outputData.data[i]     =
            outputData.data[i + 1] =
            outputData.data[i + 2] = value;
        }
    }
 
}(window.imageproc = window.imageproc || {}));
