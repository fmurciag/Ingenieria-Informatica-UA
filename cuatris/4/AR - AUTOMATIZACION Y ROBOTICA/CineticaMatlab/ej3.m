%ejercicio 3

%% Datos %
L1 = Link([0 0.317 0 -pi/2  0]);
L2 = Link([0 0 0.45 0 0 -pi/2]);
L3 = Link([0 0 0 pi/2 0 pi/2]);
L4 = Link([0 0.48 0 -pi/2 0]);
L5 = Link([0 0 0 pi/2 0]);
L6 = Link([0 0.07 0 0 0]);
L1.qlim=[deg2rad(-177) deg2rad(177)];
L2.qlim=[deg2rad(-64) deg2rad(124)];
L3.qlim=[deg2rad(-107) deg2rad(158)];
L4.qlim=[deg2rad(-255) deg2rad(255)];
L5.qlim=[deg2rad(-165) deg2rad(165)];
L6.qlim=[deg2rad(-255) deg2rad(255)];

L=[L1,L2,L3,L4,L5,L6];

robot = SerialLink(L,'name', 'PA10-6GDL');
robot;

%% Posición de home
figure(1);
qh = [0 0 0 0 0 0];
qhT = robot.fkine(qh);
robot.plot(qh);
disp(qhT)
fprintf('\n')
pause(2)

%% Posición de escape
figure(1);
qe = [0, pi/6, pi/2, 0, pi/3, 0];
qeT = robot.fkine(qe);
robot.plot(qe);
disp(qeT)
fprintf('\n')
pause(2)

%% Posición de seguridad
figure(1);
qs = [0, pi/4, pi/2, 0, -pi/4, 0];
qsT = robot.fkine(qs);
robot.plot(qs);
disp(qsT)
fprintf('\n')
pause(2)

%% Posición q1
figure(1);
q1 = [0, pi/4, pi/4, 0, pi/2, 0];
q1T = robot.fkine(q1);
robot.plot(q1);
disp(q1)
fprintf('\n')
pause(2)

%% Posición q2
figure(1);
q2 = [deg2rad(20), pi/2, pi/4, deg2rad(-22.5), pi/3, 0];
q2T = robot.fkine(q2);
robot.plot(q2);
disp(q2)
fprintf('\n')






