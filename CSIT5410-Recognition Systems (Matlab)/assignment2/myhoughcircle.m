function [y0detect,x0detect,Accumulator] = houghcircle(Imbinary,r,thresh)
%HOUGHCIRCLE - detects circles with specific radius in a binary image.
%
%Comments:
%       Function uses Standard Hough Transform to detect circles in a binary image.
%       According to the Hough Transform for circles, each pixel in image space
%       corresponds to a circle in Hough space and vise versa. 
%       upper left corner of image is the origin of coordinate system.
%
%Usage: [y0detect,x0detect,Accumulator] = houghcircle(Imbinary,r,thresh)
%
%Arguments:
%       Imbinary - a binary image. image pixels that have value equal to 1 are
%                  interested pixels for HOUGHLINE function.
%       r        - radius of circles.
%       thresh   - a threshold value that determines the minimum number of
%                  pixels that belong to a circle in image space. threshold must be
%                  bigger than or equal to 4(default).
%
%Returns:
%       y0detect    - row coordinates of detected circles.
%       x0detect    - column coordinates of detected circles. 
%       Accumulator - the accumulator array in Hough space.

if nargin == 2
    thresh = 4;
elseif thresh < 4
    error('treshold value must be bigger or equal to 4');
    return
end

%Voting
imageSize = size(Imbinary);
Accumulator = zeros(imageSize);
[yIndex,xIndex] = find(Imbinary);
rowNum = size(Imbinary,1);
colNum = size(Imbinary,2);

for cnt = 1:numel(xIndex) 
	xCenter = xIndex(cnt); 
    yCenter = yIndex(cnt);
    xLow = xCenter - r;
    xHigh = xCenter + r;
    for xCenterPoint = max(xLow, 1) : min(xHigh,colNum)
        yCenterPoint1 = round(yCenter - sqrt(r^2 - (xCenter - xCenterPoint)^2));
        yCenterPoint2 = round(yCenter + sqrt(r^2 - (xCenter - xCenterPoint)^2));

        if yCenterPoint1 >= 1 && yCenterPoint1 < rowNum
            Accumulator(yCenterPoint1,xCenterPoint) = Accumulator(yCenterPoint1,xCenterPoint) + 1;
        end
        
        if yCenterPoint2 >= 1 && yCenterPoint2 < rowNum
            Accumulator(yCenterPoint2,xCenterPoint) = Accumulator(yCenterPoint2,xCenterPoint) + 1;
        end
    end
end

% Finding local maxima in Accumulator
x0detect = [];
y0detect = []; 
[potentialY, potentialX] = find(imregionalmax(Accumulator) == 1);
for cnt = 1:numel(potentialY)
    if Accumulator(potentialY(cnt), potentialX(cnt)) - thresh >= 0
        y0detect = [y0detect;potentialY(cnt)];
        x0detect = [x0detect;potentialX(cnt)];
    end
end