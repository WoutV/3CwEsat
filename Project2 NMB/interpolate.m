function [y,conditie] = interpolate(x, f, alpha, beta, lambda, t)

b=[];
n=length(alpha);
%maak M matrix
M=eval_recursion(x,length(alpha),alpha,beta,lambda);
conditie=cond(M);

%bereken de coefficienten van de interpolerende veelterm
c=M\f;

%bereken de waarden van de interpolerende veelterm in t
for j=1:length(t)
    b(n+2,1)=0;
    b(n+1,1)=0;
    b(n) = c(n);
    if(n>1)
       b(n-1,1) = c(n-1) + lambda(n,1)*(t(j)-alpha(n,1))*b(n);
    end
    for i=n-2:-1:1 
        b(i,1) = c(i,1) + b(i+1,1)*lambda(i+1,1)*(t(j)-alpha(i+1,1)) - b(i+2,1)*beta(i+2,1);
    end
    y(j,1) = b(1,1)*lambda(1,1);
end

