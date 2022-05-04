%ejercicio 3

% Datos %
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

robot = SerialLink(L);
robot;

%Posición de home inversa
figure(1);
qh = [0 0 0 0 0 0];
Tqh = robot.fkine(qh);
qinversaQH = robot.ikine6s(Tqh);
robot.plot(qinversaQH);
TinversaQH = robot.fkine(qinversaQH);
Tqh;
qinversaQH;
TinversaQH;
pause(2)

%Posición de escape inversa
figure(1);
qe = [0, pi/6, pi/2, 0, pi/3, 0];
Tqe = robot.fkine(qe);
qinversaQE = robot.ikine6s(Tqe);
robot.plot(qinversaQE);
TinversaQE = robot.fkine(qinversaQE);
Tqe;
qinversaQE;
TinversaQE;
pause(2)

%Posición de seguridad inversa
figure(1);
qs = [0, pi/4, pi/2, 0, -pi/4, 0];
Tqs = robot.fkine(qs);
qinversaQS = robot.ikine6s(Tqs);
robot.plot(qinversaQS);
TinversaQS = robot.fkine(qinversaQS);
Tqs;
qinversaQS;
TinversaQS;
pause(2)

%Posición q1 inversa
q1 = [0, pi/4, pi/4, 0, pi/2, 0];
Tq1 = robot.fkine(q1);
qinversaQ1 = robot.ikine6s(Tq1);
robot.plot(qinversaQ1);
TinversaQ1 = robot.fkine(qinversaQ1);
Tq1;
qinversaQ1;
TinversaQ1;
pause(2)

%Posición q2 inversa
figure(1);
q2 = [deg2rad(20), pi/2, pi/4, deg2rad(-22.5), pi/3, 0];
Tq2 = robot.fkine(q2);
qinversaQ2 = robot.ikine6s(Tq2);
robot.plot(qinversaQ2);
TinversaQ2 = robot.fkine(qinversaQ2);
Tq2;
qinversaQ2;
TinversaQ2;