function [res]=opgave5_zonder(A)
A = hess(A);
% function [e]=qr_zonder(A)
%
% berekent met de QR-methode zonder shift een eigenwaarde van de matrix A
%
% invoer
% A - matrix
%
% uitvoer
% e - de berekende eigenwaarde
% res - de normen van de residu's voor iedere iteratiestap

[n,m] = size(A);
res=[]

% predict the convergence speed of the norms of the subblocks
sort_eigA = sort(eig(A),'descend');

i=1;
while i<300
    [q,r]=qr(A);
    A = r*q;
    d= sort(diag(A),'descend');
    diff=[];
    for j=1:n
        norm_subblock(i,j) = norm(A(j+1:n,1:j));
        
        ew_est(i,j) = d(j,1);
        rel_e(i,j) = (abs((ew_est(i,j)-sort_eigA(j,1)))./sort_eigA(j,1));
        
        if(mod(i,5)==0 & i<100 & j<6)
            table(i/5,j) = rel_e(i,j);
        end
        
    end
    norm_rel(i) = norm(rel_e(i,:));
    i = i +1;
end

res = table;

figure()

semilogy(real(ew_est),'-');
xlabel('iteration step')
ylabel('Eigenvalue')

figure()
semilogy(norm_rel,'-');
xlabel('iteration step')
ylabel('relative error')
figure()

semilogy(real(rel_e),'-');
xlabel('iteration step')
ylabel('relative error')