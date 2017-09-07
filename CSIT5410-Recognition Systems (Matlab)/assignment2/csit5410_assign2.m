% CSIT5410_ASSIGN2.m runs CSIT5410 assignment 2 functions.
% 
%   CSIT5410_ASSIGN2 performs Hough Circle Detection
%   REMINDER: You cannot modify this file.

close all
clear all
clc

% TASK 1
disp('*********************************************');
disp('Task 1: Hough Circle Detection');
disp('*********************************************');
I = double(imread('Circle100.tiff'));
figure; imshow(I);
E=edge(I,'canny');
figure;imshow(E,[]);
[y0detect,x0detect,Accumulator] = myhoughcircle(I,100,220);

 [V,co] = max(max(Accumulator(:,:)));
 [Xc,Yc]=find(Accumulator==V);

for x=1:1:512
    for y=1:1:512
        if abs((x-Xc)*(x-Xc)+(y-Yc)*(y-Yc)-10000)<100
            I(x,y)=1;
        else I(x,y)=0;
        end
    end
end
figure,imshow(I);

% TASK 2
disp('*********************************************');
disp('Task 2: Fisher Linear Discriminant');
disp('*********************************************');
class1_samples=[1 2;2 3;3 3;4 5;5 5]; % each row represents a pair of x and y coordinates
class2_samples=[1 0;2 1;3 1;3 2;5 3;6 5]; % each row represents a pair of x and y coordinates
input_sample=[2 5]; % [x y] coordinates
[output_class w] = myfld(input_sample, class1_samples, class2_samples)
