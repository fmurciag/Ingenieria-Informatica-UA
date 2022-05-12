%ejercicio 8
%% creamos el robot
robot = DynamicParams(loadPA10Params());

%% coordenadas articulares del robot
qs = [0, pi/4, pi/2, 0, -pi/4, 0];
q1 = [0, pi/4, pi/4, 0, pi/2, 0];
q2 = [deg2rad(20), pi/2, pi/4, deg2rad(-22.5), pi/3, 0];


%% con peso de 3kg
robot.payload(3, [0, 0, 0]);
fprintf("\n===============\n===Carga de 3Kg====\n===============\n:")

%%posicion qs
fprintf("\n===posicion qs====\n:")
fprintf("Articular:")
disp(robot.rne(qs, [0 0 0 0 0 0], [0 0 0 0 0 0]))
fprintf("Gravedad:")
disp(robot.gravload(qs));
fprintf("Inercia:")
disp(robot.itorque(qs,[1 0 0 0 0 0]));
fprintf("Coriolis:\n")
disp(robot.coriolis(qs, [1 0 0 0 0 0]));

%%posicion q1
fprintf("\n===posicion q1====\n:")
fprintf("Articular:")
disp(robot.rne(q1, [0 0 0 0 0 0], [0 0 0 0 0 0]))
fprintf("Gravedad:")
disp(robot.gravload(q1));
fprintf("Inercia:")
disp(robot.itorque(q1,[1 0 0 0 0 0]));
fprintf("Coriolis:\n")
disp(robot.coriolis(q1, [1 0 0 0 0 0]));

%%posicion q2
fprintf("\n===posicion q2====\n:")
fprintf("Articular:")
disp(robot.rne(q2, [0 0 0 0 0 0], [0 0 0 0 0 0]))
fprintf("Gravedad:")
disp(robot.gravload(q2));
fprintf("Inercia:")
disp(robot.itorque(q2,[1 0 0 0 0 0]));
fprintf("Coriolis:\n")
disp(robot.coriolis(q2, [1 0 0 0 0 0]));
%% con peso de 3kg separado en el eje X 0.3m
robot.payload(3, [0.3, 0, 0.1]);
fprintf("\n===============\n===Carga de 3Kg separada en X====\n===============\n:")

%%posicion qs
fprintf("\n===posicion qs====\n:")
fprintf("Articular:")
disp(robot.rne(qs, [0 0 0 0 0 0], [0 0 0 0 0 0]))
fprintf("Gravedad:")
disp(robot.gravload(qs));
fprintf("Inercia:")
disp(robot.itorque(qs,[1 0 0 0 0 0]));
fprintf("Coriolis:\n")
disp(robot.coriolis(qs, [1 0 0 0 0 0]));

%%posicion q1
fprintf("\n===posicion q1====\n:")
fprintf("Articular:")
disp(robot.rne(q1, [0 0 0 0 0 0], [0 0 0 0 0 0]))
fprintf("Gravedad:")
disp(robot.gravload(q1));
fprintf("Inercia:")
disp(robot.itorque(q1,[1 0 0 0 0 0]));
fprintf("Coriolis:\n")
disp(robot.coriolis(q1, [1 0 0 0 0 0]));

%%posicion q2
fprintf("\n===posicion q2====\n:")
fprintf("Articular:")
disp(robot.rne(q2, [0 0 0 0 0 0], [0 0 0 0 0 0]))
fprintf("Gravedad:")
disp(robot.gravload(q2));
fprintf("Inercia:")
disp(robot.itorque(q2,[1 0 0 0 0 0]));
fprintf("Coriolis:\n")
disp(robot.coriolis(q2, [1 0 0 0 0 0]));

