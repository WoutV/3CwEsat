function x = poly_zeros(n, alpha, beta, lambda)

%maak diagonaalmatrix met de elementen van alpha
alphaMat = diag(alpha);

%maak matrices met mu en nu elementen op respectievelijk eerste boven- en
%onderdiagonaal
mu=zeros(n-1,1);
nu=zeros(n-1,1);
for i=1:n-1
    nu(i) = beta(i+1)/lambda(i+1);
    mu(i) = 1/lambda(i);
end
muMat = diag(mu,1);
nuMat = diag(nu,-1);

%maak matrix A
A= alphaMat+nuMat+muMat;

%bereken de eigenwaarden van A en dus de nulpunten van de veelterm
x=eig(A);

