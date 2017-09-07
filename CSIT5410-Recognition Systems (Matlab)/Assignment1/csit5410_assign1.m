% CSIT5410_ASSIGN1.m contains the main routine for CSIT5410 assignment 1.
%
%   CSIT5410_ASSIGN1(FILENAME) takes a gray scale image with filename FILENAME.
%   This routine completes the following tasks:
%
%   TASK 1: An image specified by FILENAME is read and displayed.
%   TASK 2: The corresponding binary edge image with a given threshold T is 
%           computed and displayed.
%   TASK 3: The corresponding binary edge image with the automatic threshold 
%           is computed and displayed.
%   TASK 4:  Longest line segment extraction based on Hough transform.
%   TASK 5: Image Alignment using SIFT.
%
%   REMINDER: You cannot modify this file.
%
function csit5410_assign1(FILENAME)

%
% TASK 1
%
% Read the image.
Im = im2double(imread(FILENAME));
% Show the image in a new window.
figure;imshow(Im, [min(min(Im)) max(max(Im))]);title('Original Image');
disp('Original image is read and displayed successfully.');


%
% TASK 2
%
% Generate the corresponding binary edge image of the given image Im.
T = double(max(max(Im)))*0.2;
direction = 'all';
g = myprewittedge(Im,T,direction);
% Show the image in a new window.
figure;imshow(g, [0 1]);title('Binary Edge Image 1');
disp('The corresponding binary edge image is computed and displayed successfully.');


%
% TASK 3
%
% Generate the corresponding binary edge image of the given image Im
% without specifying the threshold
direction = 'all';
f = myprewittedge(Im,[],direction);
% Show the image in a new window.
figure;imshow(f, [0 1]);title('Binary Edge Image 2');
disp('The corresponding binary edge image is computed and displayed successfully.');

% TASK 4
% Find the longest line segment based on Hough transform.
[x, y] = mylineextraction(f);
% Plot the line in the image
figure; imshow(Im, [min(min(Im)) max(max(Im))]), hold on
plot([x(1) y(1)], [x(2) y(2)],'LineWidth',2,'Color','blue');
plot(x(1),x(2),'x','LineWidth',2,'Color','red');
plot(y(1),y(2),'x','LineWidth',2,'Color','red');
hold off

%
% TASK 5
%
% Find the best matching image using SIFT

% The QR code image to be matched.
I = double(imread('QR-Code.jpg'));
figure;imshow(I,[]);

% 3 candidate images for matching.
I1 = double(imread('image1.jpg'));
figure;imshow(I1,[]);

I2 = double(imread('image2.jpg'));
figure;imshow(I2,[]);

I3 = double(imread('image3.jpg'));
figure;imshow(I3,[]);

% Find the image which matches image I best.
n=findBestMatching(I,I1,I2,I3);
fprintf('The image matches QR-code.jpg best is image%d.jpg\n', n);

