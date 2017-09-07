% myprewittedge computes a binary edge image from the given image.
%
%   g = myprewittedge(Im,T,direction) computes the binary edge image from the
%   input image Im.
%   
% The function myprewittedge, with the format g=myprewittedge(Im,T,direction), 
% computes the binary edge image from the input image Im. This function takes 
% an intensity image Im as its input, and returns a binary image g of the 
% same size as Im (mxn), with 1's where the function finds edges in Im and 0's 
% elsewhere. This function finds edges using the Prewitt approximation to the 
% derivatives with the assumption that input image values outside the bounds 
% are zero and all calculations are done using double-precision floating 
% point. The function returns g with size mxn. The image g contains edges at 
% those points where the absolute filter response is above or equal to the 
% threshold T.
%   
%       Input parameters:
%       Im = An intensity gray scale image.
%       T = Threshold for generating the binary output image. If you do not
%       specify T, or if T is empty ([ ]), myprewittedge(Im,[],direction) 
%       chooses the value automatically according to the Algorithm 1 (refer
%       to the assignment descripton).
%       direction = A string for specifying whether to look for
%       'horizontal' edges, 'vertical' edges, positive 45 degree 'pos45'
%       edges, negative 45 degree 'neg45' edges or 'all' edges.
%
%   For ALL submitted files in this assignment, 
%   you CANNOT use the following MATLAB functions:
%   edge, fspecial, imfilter, conv, conv2.
%
function g = myprewittedge(Im,T,direction)

[m,n]=size(Im);
g = zeros([m,n]);

image = zeros([m+2, n+2]);
for x = 1:m
    for y = 1:n
        image(x+1, y+1) = Im(x,y);
    end
end

for x = 2:m+1
    if x == 2
        image(x-1, 1) = Im(x-1,1);
    end
    if x == m+1
        image(x+1, 1) = Im(x-1,1);
    end
    image(x, 1) = Im(x-1,1); 
    image(x, n+2) = Im(x-1,n);
end

for y = 2:n+1
    if y == n+1
        image(1, y+1) = Im(1,y-1);
        image(m+2, y+1) = Im(m,y-1);
    end
    image(1, y) = Im(1,y-1);
    image(m+2, y) = Im(m,y-1);
end

for d = 1:4
    if (strcmp(direction,'horizontal') && d == 1) || (strcmp(direction,'all') && d == 1)
        prewitt = [-1 -1 -1; 0 0 0; 1 1 1];
    elseif (strcmp(direction,'vertical') && d == 1) || (strcmp(direction,'all') && d == 2)
        prewitt = [-1 0 1; -1 0 1; -1 0 1];
    elseif (strcmp(direction,'pos45') && d == 1) || (strcmp(direction,'all') && d == 3)
        prewitt = [0 1 1; -1 0 1; -1 -1 0];
    elseif (strcmp(direction,'neg45') && d == 1) || (strcmp(direction,'all') && d == 4)
        prewitt = [-1 -1 0; -1 0 1; 0 1 1];
    else 
        continue;
    end
    
    map = zeros([m, n]);
    for x = 2:m+1
        for y = 2:n+1
            temp = image(x-1:x+1, y-1:y+1) .* prewitt;
            map(x-1,y-1) = sum(temp(:));
        end
    end

    temp_T = T;
    if size(temp_T) == 0
        temp_T = (max(max(map)) + min(min(map))) / 2.0;
        for i = 1:10
            G1 = Im >= temp_T;
            G2 = Im < temp_T;
            m1 = sum(Im(G1)) / sum(sum(G1));
            m2 = sum(Im(G2)) / sum(sum(G2));
            temp_T = 0.5 * (m1 + m2);
        end
    end

    map = (abs(map) >= temp_T);
    
    g = max(g, map);
end