% MYFLD classifies an input sample into either class 1 or class 2.
%
%   [output_class w] = myfld(input_sample, class1_samples, class2_samples) 
%   classifies an input sample into either class 1 or class 2,
%   from samples of class 1 (class1_samples) and samples of
%   class 2 (class2_samples).
% 
% The implementation of the Fisher linear discriminant must follow the
% descriptions given in CSIT600M lecture notes.
% In this assignment, you do not need to handle cases when 'inv' function
% input is a matrix which is badly scaled, singular or nearly singular.
% All calculations are done using double-precision floating point. 
%
% Input parameters:
% input_sample = an input sample
%   - The number of dimensions of the input sample is N.
%
% class1_samples = a NC1xN matrix
%   - class1_samples contains all samples taken from class 1.
%   - The number of samples is NC1.
%   - The number of dimensions of each sample is N.
%
% class2_samples = a NC2xN matrix
%   - class2_samples contains all samples taken from class 2.
%   - The number of samples is NC2.
%   - NC1 and NC2 do not need to be the same.
%   - The number of dimensions of each sample is N.
%
% Output parameters:
% output_class = the class to which input_sample belongs. 
%   - output_class should have the value either 1 or 2.
%
% w = weight vector.
%   - The vector length must be one.
%
% For ALL submitted files in this assignment, 
%   you CANNOT use the following MATLAB functions:
%   mean, diff, classify, classregtree, eval, mahal.
function [output_class w] = myfld(input_sample, class1_samples, class2_samples)


% mean of class1_samples and class2_samples
[class1_row, class1_col] = size(class1_samples);
[class2_row, class2_col] = size(class2_samples);

class1_sum  = zeros(1,1);
for each = 1:class1_row
    class1_sum = class1_samples(each,:)' + class1_sum;
end
class1_mean = class1_sum/class1_row;

class2_sum  = zeros(1,1);
for each = 1:class2_row
    class2_sum = class2_samples(each,:)' + class2_sum;
end
class2_mean = class2_sum/class2_row;

% within class variance
class1_sum2  = zeros(2,2);
for each = 1:class1_row
    class1_sum2 = class1_sum2 + (class1_samples(each,:)' - class1_mean) * ((class1_samples(each,:)' - class1_mean)');
end

class2_sum2  = zeros(2,2);
for each = 1:class2_row
    class2_sum2 = class2_sum2 + (class2_samples(each,:)' - class2_mean) * ((class2_samples(each,:)' - class2_mean)');
end

% total within class variance
sw = class1_sum2 + class2_sum2;

% between class variance
sb = (class1_mean - class2_mean) * ((class1_mean - class2_mean)');

% separation point for decision making
w = (inv(sw) * (class1_mean - class2_mean)) / norm(inv(sw)*(class1_mean - class2_mean));
separationPoint = w'/2 * (class1_mean + class2_mean);

if (w' * input_sample') > separationPoint
    output_class = 1
else 
    output_class = 2
end