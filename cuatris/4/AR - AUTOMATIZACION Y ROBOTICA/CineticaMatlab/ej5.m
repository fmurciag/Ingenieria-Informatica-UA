%ejercicio 5

%% robots

%robot PA10
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

Lpa10=[L1,L2,L3,L4,L5,L6];

robotPA10 = SerialLink(Lpa10,'name', 'PA10-6GDL');
robotPA10;

%robot PLANAR
L10 = Link([0 0 1 0]);
L20 = Link([0 0 1 0]);
L30 = Link([0 0 1 0]);

Lplanar=[L10,L20,L30];

robotPlanar = SerialLink(Lplanar,'name', 'PLANAR');
robotPlanar;


%% calculo de singularidades robot PA10

q = [0 -pi/2 0 0 0 0];
j1=jacob0(robotPA10, q)
disp(det(j1));
figure(1)
robotPA10.plot(q);
pause(2)

q = [0, pi/6, pi/2, deg2rad(-255), pi/3, 0];
j2=jacob0(robotPA10, q)
disp(det(j2));
figure(1)
robotPA10.plot(q);
pause(2)

q = [0 -pi/4 0 0 0 pi/3];
j3=jacob0(robotPA10, q)
disp(det(j3));
figure(1)
robotPA10.plot(q);
pause(2)

close all

%% calculo de singularidades robot Planar

q = [0 0 0];
j4=jacob0(robotPlanar, q)
disp(det(j4));
figure(1)
robotPlanar.plot(q);
pause(2)

q = [1 0 0];
j5=jacob0(robotPlanar, q)
disp(det(j5));
figure(1)
robotPlanar.plot(q);
pause(2)

close all

