function n=findBestMatching(I, I1, I2, I3)
num=zeros(1,3);
% mysiftalignment is used to align two images by using SIFT features 
num(1)=mysiftalignment(I,I1);
num(2)=mysiftalignment(I,I2);
num(3)=mysiftalignment(I,I3);
% Find the image with the largest number of matched pairs with the QR code image. 
[tp n]=max(num);