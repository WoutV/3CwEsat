function test_interpolate()

x=[-1:.2:1];
f=cos(x);
toplot=[];
for i=3:50
    alpha = zeros(i);
    lambda=2*ones(i);
    lambda(1)=1;
    lambda(2)=1;

    beta=ones(i);
    y=interpolate(x,f',alpha,beta,lambda,x);
    error = f'-y;
    toplot=[toplot max(error)]
end
plot(toplot);
plot(y)
