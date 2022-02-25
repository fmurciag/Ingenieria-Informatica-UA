% DEI Practical assignment 2021/22
% Objective: 3D HCI Gesture learning

load DSentrenamiento.mat;
load DStest.mat;
disp("Creando modelo...");
%% Training
xtr=DSentrenamiento(:,1:3);%secuencias entrenamiento
ytr=DSentrenamiento(:,4);%clases

xt=DStest(:,1:3);%secuencias test
yt=DStest(:,4);%clases
% % entrenando 
modelo = fitcknn(xtr,ytr,'NumNeighbors',3,'Distance','euclidean');
% % test
resultado = predict(modelo, xt);
acierto = sum(resultado == yt)/length(yt)*100;
sp =['Acierto del modelo = ', num2str(acierto),'%'];
disp(sp)
save modelo.mat;