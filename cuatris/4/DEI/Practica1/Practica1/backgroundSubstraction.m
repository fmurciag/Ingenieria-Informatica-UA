function [foreground] = backgroundSubstraction(MBG,Frame,threshold)

  imaR = Frame(:,:,1); fondR = MBG(:,:,1);
  imaG = Frame(:,:,2); fondG = MBG(:,:,2);
  imaB = Frame(:,:,3); fondB = MBG(:,:,3);

  imauxR = zeros(size(imaR));
  imauxG = zeros(size(imaG));
  imauxB = zeros(size(imaB));

%   tam = 80;
% 
%   fR = imopen(imaR,strel('disk',tam));
%   fG = imopen(imaG,strel('disk',tam));
%   fB = imopen(imaB,strel('disk',tam));
% 
%   imauxR(abs(imsubtract(fR,imaR)) > threshold) = 1;
%   imauxG(abs(imsubtract(fG,imaG)) > threshold) = 1;
%   imauxB(abs(imsubtract(fB,imaB)) > threshold) = 1;

%   figure;
%   imagesc(imaR);
%   figure;
%   imagesc(fR);
%   figure;
%   imagesc(imauxR);


  cR = abs(fondR - imaR);
  cG = abs(fondG - imaG);
  cB = abs(fondB - imaB);

  imauxR(cR > threshold) = 1;
  imauxG(cG > threshold) = 1;
  imauxB(cB > threshold) = 1;

%   figure;
%   imagesc(imaR);
%   figure;
%   imagesc(fondR);
%   figure;
%   imagesc(cR);

  
  %imaux(imColor == 0) = 0;
  
  foreground = imauxR & imauxG & imauxB;
end

