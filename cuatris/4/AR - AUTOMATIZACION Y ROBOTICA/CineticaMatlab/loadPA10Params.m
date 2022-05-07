function robot_obj = loadPA10Params()

% Standard DH notation
alpha = [ -pi/2 0  pi/2 -pi/2 pi/2 0 ]; % link twist angle
A = [ 0 .450 0 0 0 0 ];                 % link length
rx = [0,-0.321,0,0,0,0];
ry = [0.117,-0.030,0,0.1122,0,0];
rz = [0, 0, 0.0485, 0, -0.042, -0.048];
IData = LoadInertiaData;

% Other link parameters, invariant to notation
theta = zeros(1,6);                    % link rotation angle
D = [ 0.317 0 0 0.480 0 0.070 ];       % link offset distance
sigma = zeros(1,6);                    % joint type (0 for revolute)
offset = [ 0 -pi/2 pi/2 0 0 0 ];       % offset for theta (for zero-pose)

mass = [9.29, 12.43, 4.86, 3.08, 2.07, 1.05];   % mass of the link
Ixx = IData(:,1)';   % elements of link inertia tensor about the link COG
Iyy = IData(:,2)';
Izz = IData(:,3)';
Ixy = IData(:,4)';
Iyz = IData(:,5)';
Ixz = IData(:,6)';
Jm = zeros(1,6);        % armature inertia
G = 50 * ones(1,6);     % reduction gear ratio. joint speed/link speed
B = zeros(1,6);         % viscous friction, motor refered
Tc_plus = zeros(1,6);   % coulomb friction (positive rotation), motor refered
Tc_minus = zeros(1,6);  % coulomb friction (negative rotation), motor refered

min = [-177, -64, -107, -255, -165, -255] * (pi/180);
max = [177, 124, 158, 255, 165, 255] * (pi/180);

dyn_matrix = [theta; D; A; alpha; sigma; mass; rx; ry; rz; Ixx; Iyy; Izz; ...
              Ixy; Iyz; Ixz; Jm; G; B; Tc_plus; Tc_minus];
          
for i=1:length(A)
    L(i) = Link(dyn_matrix(:,i), 'standard');
    L(i).offset = offset(i);
    L(i).qlim = [min(i) max(i)];
end

robot_obj = SerialLink(L,'name','PA10');


function InertiaData = LoadInertiaData
data = GetInertiaData;
T(:,:,1) = rotx(-pi/2) * rotz(-pi/2);
T(:,:,2) = rotx(-pi/2);
T(:,:,3) = rotz(pi);
T(:,:,4) = rotx(-pi/2);
T(:,:,5) = eye(3);
T(:,:,6) = eye(3);

InertiaData = zeros(6,6);
for n = 1:6
    I = [data(n,1), data(n,2), data(n,3); data(n,2), data(n,4), data(n,5); ...
         data(n,3), data(n,5), data(n,6)];
    I = T(1:3,1:3,n) * I * T(1:3,1:3,n)';
    InertiaData(n,:) = [I(1,1),I(2,2),I(3,3),I(1,2),I(2,3),I(1,3)]; 
end



function data = GetInertiaData

data = [0.13075775	-0.00000001	-0.00000001	0.10682179	-0.00000002	0.05280026; ...
        0.3782804	-0.00029527	-0.0201253	0.37582008	-0.00087555	0.06409578; ...
        0.05985722	0.00000002	-0.00000002	0.05773397	0.00181737	0.00837426; ...
        0.02826874	0	1.0E-08	0.0199375	0	0.01090199; ...
        0.00741334	0	-0.00001649	0.00698523	0	0.00301892; ...
        0.00135043	0	0	0.00135043	0	0.00058334];