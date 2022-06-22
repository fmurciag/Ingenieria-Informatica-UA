%% creamos el robot
robot = DynamicParams(loadPA10Params());

%% coordenadas articulares del robot
qs = [0, pi/4, pi/2, 0, -pi/4, 0];
q1 = [0, pi/4, pi/4, 0, pi/2, 0];
q2 = [deg2rad(20), pi/2, pi/4, deg2rad(-22.5), pi/3, 0];

qh = [0 0 0 0 0 0];
qe = [0, pi/6, pi/2, 0, pi/3, 0];
qs = [0, pi/4, pi/2, 0, -pi/4, 0];
q1 = [0, pi/4, pi/4, 0, pi/2, 0];
q2 = [deg2rad(20), pi/2, pi/4, deg2rad(-22.5), pi/3, 0];

qhQ = robot.fkine(qh);
qeQ = robot.fkine(qe);
qsQ = robot.fkine(qs);
q1Q = robot.fkine(q1);
q2Q = robot.fkine(q2);

%% trayectorias articulares
[q , qd, qdd] = jtraj(qh, q1, 50);
robot.plot(q);
plot(qd);
pause(2)

[q , qd, qdd] = jtraj(qh, q2, 50);
robot.plot(q);
plot(qd);
pause(2)

[q , qd, qdd] = jtraj(qh, qs, 50);
robot.plot(q);
plot(qd);
pause(2)

close all

%% trayectorias cartesianas

Ts = ctraj(qh, q1, 20)
plot(ts)




