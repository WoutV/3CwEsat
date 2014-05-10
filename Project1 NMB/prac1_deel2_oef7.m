%**************************************************************************
% Practicum - Deel 2: Convergentie-eigenschappen
%                  
%       Opgave 7: Arnoldi
%
% Authors:  Martijn Boussé, Dario Incalza
% Date:     01/04/2013
% Version:  1.0
%**************************************************************************

clear all; close all; clc;

% Genereer ijle 1000x1000 matrix.
A = sprand(1000,1000,0.25);

% Bereken 6 grootste eigenwaarden via eigs.
d = eigs(A);
fprintf('6 grootste eigenwaarden: \n');
fprintf('------------------------ \n ');
fprintf('%d \n',d);
fprintf('------------------------ \n');

% Start Arnoldi.
b = rand([1000,1]);
mmax = 100;
[H,Q,E] = arnoldi(A,b,mmax);

% Plot.
j = [1 10:5:100];
figure('Position', [100, 100, 1000, 900]), hold on,
for i = 1:mmax
    R = E(:,i);
    if any(i==j)
        plot(i,R(R==real(R)),'ro');
    end 
end
set(gca,'fontsize',20), grid on;
set(get(gca,'xlabel'),'string','Iteraties','fontsize',20) 
set(get(gca,'ylabel'),'string','Ritz waarden','fontsize',20) 
set(gca,'XTick',0:10:mmax); 
ybnds = [floor(min(min(E))/20)*20; ceil(max(max(E))/20)*20];
set(gca,'YTick',ybnds(1):20:ybnds(2));
