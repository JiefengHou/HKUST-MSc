(function(imageproc) {
    "use strict";

    var source, result;
    var imageSelector;

    imageproc.operation = null;
    imageproc.paramIds = null;

    /*
     * Init the module and update the source image
     */
    imageproc.init = function(sourceCanvasId,
                              resultCanvasId,
                              sourceImageId) {
        source = document.getElementById(sourceCanvasId).getContext("2d");
        result = document.getElementById(resultCanvasId).getContext("2d");

        imageSelector = document.getElementById(sourceImageId);
        imageproc.updateSourceImage();
    }

    /*
     * Update the source image canvas
     */
    imageproc.updateSourceImage = function() {
        var image = new Image();
        image.onload = function () {
            source.drawImage(image, 0, 0);
        }
        image.src = imageSelector.value;
    }

    /*
     * Update a text span value to the value of a form element
     * assuming that the id of the text span is the id of the
     * form element plus '_value'
     */
    imageproc.updateInputValue = function(input) {
        document.getElementById(input.id + "_value").innerHTML = input.value;
    }

    /*
     * Get the required parameters for the image processing operation
     * and return an object with the parameters
     */
    function getParams() {
        var params = {};

        /* Get the values for each element */
        for (var i = 0; i < imageproc.paramIds.length; i++) {
            var id = imageproc.paramIds[i];
            params[id] = document.getElementById(id).value;
        }

        return params;
    }
       
    /*
     * Apply an image processing operation to a source image and
     * then put the result image in the result canvas
     */
    imageproc.apply = function() {
        /* Get the source image and create the result image buffer */
        var sourceImage = source.getImageData(0, 0, 400, 300);
        var resultImage = result.createImageData(400, 300);

        /* Update the alpha values of the newly created image */
        for (var i = 0; i < resultImage.data.length; i+=4)
            resultImage.data[i + 3] = 255;

        if (imageproc.operation) {
            /* Get the parameters */
            var params;
            if (imageproc.paramIds) params = getParams();

            /* Apply the operation */
            imageproc.operation(sourceImage, resultImage, params);
        }

        /* Put the result image in the canvas */
        result.putImageData(resultImage, 0, 0);
    }

    /*
     * Convert RGB to HSL
     */
    imageproc.fromRGBToHSL = function(r, g, b) {
        r = r / 255.0; g = g / 255.0; b = b / 255.0;
        var m1 = Math.min(r, g, b);
        var m2 = Math.max(r, g, b);
        var h, s, l;
        l = m2 + m1;
        if (m1 == m2) h = s = 0;
        else {
            var d = m2 - m1;
            switch (m2) {
            case r: h = (g - b) / d; break;
            case g: h = (b - r) / d + 2; break;
            case b: h = (r - g) / d + 4;
            }
            if (h < 0) h = h + 6;
            if (h >= 6) h = h - 6;
            h = h * 60;
            if (l <= 1) s = d / l;
            else s = d / (2 - l);
        }
        l = l / 2;
        return {"h": h, "s": s, "l": l};
    }

    /*
     * Convert HSL to RGB
     */
    imageproc.fromHSLToRGB = function(h, s, l) {
        /*
         * Internal function to get RGB from hue
         */
        function fromHueToRGB(m1, m2, h) {
            if (h < 0) h = h + 1;
            if (h > 1) h = h - 1;
            if (h * 6 < 1) return m1 + (m2 - m1) * h * 6;
            if (h * 2 < 1) return m2;
            if (h * 3 < 2) return m1 + (m2 - m1) * (2/3 - h) * 6;
            return m1;
        }

        h = h / 360.0;
        var m1, m2;
        if (l <= 0.5) m2 = l * (s + 1);
        else m2 = l + s - l * s;
        m1 = l * 2 - m2;
        var r = fromHueToRGB(m1, m2, h + 1/3)
        var g = fromHueToRGB(m1, m2, h)
        var b = fromHueToRGB(m1, m2, h - 1/3)
        return {"r": Math.round(r * 255),
                "g": Math.round(g * 255),
                "b": Math.round(b * 255)};
    }

    /*
     * Convert HSL to RGB
     */
    imageproc.fromHSLToRGB = function(h, s, l) {
        /*
         * Internal function to get RGB from hue
         */
        function fromHueToRGB(m1, m2, h) {
            if (h < 0) h = h + 1;
            if (h > 1) h = h - 1;
            if (h * 6 < 1) return m1 + (m2 - m1) * h * 6;
            if (h * 2 < 1) return m2;
            if (h * 3 < 2) return m1 + (m2 - m1) * (2/3 - h) * 6;
            return m1;
        }

        h = h / 360.0;
        var m1, m2;
        if (l <= 0.5) m2 = l * (s + 1);
        else m2 = l + s - l * s;
        m1 = l * 2 - m2;
        var r = fromHueToRGB(m1, m2, h + 1/3)
        var g = fromHueToRGB(m1, m2, h)
        var b = fromHueToRGB(m1, m2, h - 1/3)
        return {"r": Math.round(r * 255),
                "g": Math.round(g * 255),
                "b": Math.round(b * 255)};
    }

    /*
     * Get a pixel colour from an ImageData object
     * 
     * The parameter border can be either "extend" (default) and "wrap"
     */
    imageproc.getPixel = function(imageData, x, y, border) {
        // Handle the boundary cases
        if (x < 0)
            x = (border=="wrap")? imageData.width + (x % imageData.width) : 0;
        if (x >= imageData.width)
            x = (border=="wrap")? x % imageData.width : imageData.width - 1;
        if (y < 0)
            y = (border=="wrap")? imageData.height + (y % imageData.height) : 0;
        if (y >= imageData.height)
            y = (border=="wrap")? y % imageData.height : imageData.height - 1;

        var i = (x + y * imageData.width) * 4;
        return {
            r: imageData.data[i],
            g: imageData.data[i + 1],
            b: imageData.data[i + 2],
            a: imageData.data[i + 3]
        };
    }

    /*
     * Get an empty buffer of the same size as the image dat
     */
    imageproc.createBuffer = function(imageData) {
        /* Create the buffer */
        var buffer = {
            width: imageData.width,
            height: imageData.height,
            data: []
        };

        /* Initialize the buffer */
        for (var i = 0; i < imageData.data.length; i+=4) {
            buffer.data[i]     = 0;
            buffer.data[i + 1] = 0;
            buffer.data[i + 2] = 0;
            buffer.data[i + 3] = 255;
        }

        return buffer;
    }
 
}(window.imageproc = window.imageproc || {}));
