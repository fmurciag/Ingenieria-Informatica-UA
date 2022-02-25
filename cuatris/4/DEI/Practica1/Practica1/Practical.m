% DEI Practical assignment 2021/22
% Objective: 3D HCI Gesture learning

%% Acquisition
clasificador;
load modelo.mat;
load('secuencias/test/scan3d-fw-27Feb2014-094834.mat'); % Load dataset
disp("Secuencia cargada");
disp("Analizando secuencia...");
%load('scan3d-up-27Feb2014-094145.mat'); % Load dataset
centroidesCompletos = [];
profundidad=[];
for i = 1:60
    %% Direct Segmentation
    %%figure;
    %%imagesc(scan3d.img(:,:,:,i)); %Show images
    % Colour direct
    maskAuxColor = createMaskColor(scan3d.img(:,:,:,i),140,250,150,220,100,250);
    
    % Depth direct
    maskAux = createMaskDepth(scan3d.depth(:,:,i),900);
    %fprintf('%i\n', i);
    % Máscara combinada
    maskFinal(:,:,i) = maskAuxColor & maskAux;
    
    centroide = regionprops(maskFinal(:,:,i), "Centroid", "Area", 'PixelIdxList');
    [maxValue,index] = max([centroide.Area]);
    centroids = cat(1,centroide.Centroid);

    [rw ,col]=size(centroide);
for j=1:rw
    if (j==index)
        if centroids(index,1)<=480 && centroids(index,2)<=640 
            profundidad=cat(1,profundidad,scan3d.depth(fix(centroids(index,2)),fix(centroids(index,1)),i));
        end
        centroidesCompletos = cat(1,centroidesCompletos, [centroids(index,2), centroids(index,1),i ]);
    end
end
    if size(centroidesCompletos)~=0
        control=1;
%          hold on
%          plot(centroidesCompletos(:,1),centroidesCompletos(:,2),'b*')
%          hold off
    else
        control=0;
    end
end
if control==1
    despX=[];
    despY=[];
    %%calculo de distancias
    Zi=median([profundidad(1) profundidad(2) profundidad(3)]);% coordenada z inicial
    Zf=median([profundidad(end-1) profundidad(end-2) profundidad(end-3)]);% coordenada z final
    despZ=(double(Zi)-double(Zf));
    for i=2:size(centroidesCompletos)
        despY=[despY,abs(centroidesCompletos(i,1)-centroidesCompletos(i-1,1))];
        despX=[despX,abs(centroidesCompletos(i,2)-centroidesCompletos(i-1,2))];
    end
    despX=median(despX);%distancia aumentada en eje x
    despY=median(despY);%distancia aumentada en eje y   
    %despZ=median(despZ);
end
% implay(maskFinal);

%% determinar tipo

datos=[despX,despY,despZ];
resultado = predict(modelo, datos);
switch resultado
    case 1
        disp("Movimiento detectado:  Desplazamiento frontal")
    case 2
        disp("Movimiento detectado:  Desplazamiento circular")
    case 3
        disp("Movimiento detectado:  Desplazamiento lateral derecha")
    case 4
        disp("Movimiento detectado:  Desplazamiento hacia arriba")
end









