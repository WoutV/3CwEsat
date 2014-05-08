function res = interpolate(x, f, alpha, beta, lambda, t)

%maak M matrix
M=eval_recursion(x,length(alpha),alpha,beta,lambda);

%bereken de coefficienten van de interpolerende veelterm
c=M\f;

%bereken de waarden van de interpolerende veelterm in t
values=eval_recursion(t,length(alpha),alpha,beta,lambda);
res=values*c;

%hold on
%plot(x,res);
%plot(x,1/(1+6*x'.^2))

