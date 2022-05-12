%ejercicio 6
%% creamos el robot
robot = DynamicParams(loadPA10Params());

%% coordenadas articulares del robot
qs = [0, pi/4, pi/2, 0, -pi/4, 0];%posicion de escape
q1 = [0, pi/4, pi/4, 0, pi/2, 0];%posicion q1
q2 = [deg2rad(20), pi/2, pi/4, deg2rad(-22.5), pi/3, 0];%posicion q2

%%  pares articulares suponiendo velocidades articulares 0
fprintf("ESCAPE:")
robot.rne(qs, [0 0 0 0 0 0], [0 0 0 0 0 0])
fprintf("Q1:")
robot.rne(q1, [0 0 0 0 0 0], [0 0 0 0 0 0])
fprintf("Q2:")
robot.rne(q2, [0 0 0 0 0 0], [0 0 0 0 0 0])

