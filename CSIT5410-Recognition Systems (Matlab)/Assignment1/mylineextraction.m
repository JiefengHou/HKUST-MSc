function [bp, ep] = mylineextraction(BW)
%   The function extracts the longest line segment from the given binary image
%       Input parameter:
%       BW = A binary image.
%
%       Output parameters:
%       [bp, ep] = beginning and end points of the longest line found
%       in the image.
%
%   You may need the following predefined MATLAB functions: hough,
%   houghpeaks, houghlines.


[H,T,R] = hough(BW);
P = houghpeaks(H,10);
lines = houghlines(BW,T,R,P);

max_len = 0;
for k = 1:length(lines)  
    len = norm(lines(k).point1 - lines(k).point2);
    if (len > max_len)
    	max_len = len;
        bp = lines(k).point1;
        ep = lines(k).point2;
    end
end












