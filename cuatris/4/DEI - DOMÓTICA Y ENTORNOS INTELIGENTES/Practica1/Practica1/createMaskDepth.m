function [maskDepth] = createMaskDepth(imDepth,thrDepth)

  imaux = zeros(size(imDepth));
  imaux(imDepth <= thrDepth) = 1;
  
  imaux(imDepth == 0) = 0;
  
  maskDepth = imaux;

end