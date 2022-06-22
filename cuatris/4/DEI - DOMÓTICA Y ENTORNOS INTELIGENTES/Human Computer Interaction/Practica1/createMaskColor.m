function [maskColor] = createMaskColor(imColor,thrRMin,thrRMax,thrGMin,thrGMax,thrBMin,thrBMax)

  imauxR = zeros(size(imColor(:,:,1)));
  imauxG = zeros(size(imColor(:,:,2)));
  imauxB = zeros(size(imColor(:,:,3)));
  
  imauxR(imColor(:,:,1) >= thrRMin & imColor(:,:,1) <= thrRMax) = 1;
  imauxG(imColor(:,:,2) >= thrGMin & imColor(:,:,2) <= thrGMax) = 1;
  imauxB(imColor(:,:,3) >= thrBMin & imColor(:,:,3) <= thrBMax) = 1;
  
  %imaux(imColor == 0) = 0;
  
  maskColor = imauxR & imauxG & imauxB;

end
