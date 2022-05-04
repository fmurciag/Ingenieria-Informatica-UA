%ejercicio 3

%robot PA10%
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

%robot PLANAR%
L10 = Link([0 0 1 0]);
L20 = Link([0 0 1 0]);
L30 = Link([0 0 1 0]);

Lplanar=[L10,L20,L30];

robotPlanar = SerialLink(Lplanar,'name', 'PLANAR');
robotPlanar;




q = [0 -pi/2 0 0 0 0];
disp(jacob0(robotPA10, q));
disp(det(jacob0(robotPA10, q)));
figure(1)
robotPA10.plot(q);
pause(2)

q = [0, pi/6, pi/2, deg2rad(-255), pi/3, 0];
disp(jacob0(robotPA10, q));
disp(det(jacob0(robotPA10, q)));
figure(1)
robotPA10.plot(q);
pause(2)

q = [0, pi/6, deg2rad(158), pi/2, pi/3, 0];
disp(jacob0(robotPA10, q));
disp(det(jacob0(robotPA10, q)));
figure(1)
robotPA10.plot(q);
pause(2)


close all
%{
for i = 1:10
    disp(i)
    q = [deg2rad((108- -1).*rand(1,1) + -117) ...
        deg2rad((180- -180).*rand(1,1) + -180) ...
        deg2rad((180- -180).*rand(1,1) + -180)];
    disp(jacob0(robotPlanar, q));
    disp(det(jacob0(robotPlanar, q)));
    figure(i)
    robotPlanar.plot(q);
    pause(2)
end
%}
