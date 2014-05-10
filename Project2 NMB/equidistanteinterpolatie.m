function equidistanteinterpolatie()



toplot=[];
derp=[];
hold on
x=[]
for i=3:25
    x=[-1:2/i:1];
    for j=1:length(x)
        f(j)=1/(1+6*x(j)^2);
    end
    
alpha = zeros(i,1);
size(alpha);
lambda=2*ones(i);
lambda(1)=1;
lambda(2)=1;

beta=ones(i);

y=interpolate(x,f',alpha,beta,lambda,x);
size(y)
%plot(y)
%error = f'-y;
%toplot=[toplot max(error)];
if(mod(i,5)==0)
plot(x,y);
end
end
%semilogy(toplot);
x=[-1:0.001:1]; 
for j=1:length(x)
f(j)=1/(1+6*x(j)^2);
end
plot(x,f,'r')


