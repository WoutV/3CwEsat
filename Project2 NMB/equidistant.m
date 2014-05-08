function test_interpolate()


toplot=[];
for i=3:25

alpha = zeros(i,1);
size(alpha)
lambda=2*ones(i);
lambda(1)=1;
lambda(2)=1;

beta=ones(i);
x=poly_zeros(i,alpha,beta,lambda);
size(x)
f=1/(1+6*x.^2);
y=interpolate(x,f',alpha,beta,lambda,x);
error = f'-y;
toplot=[toplot max(error)]
end
semilogy(toplot);
