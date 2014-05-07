function M= chebychevtest1(n)
derp=[-1:.01:1];
x=length(derp);
alpha = zeros(x);
lambda=2*ones(x);
lambda(1)=1;
lambda(2)=1;

beta=ones(x);


M=eval_recursion(derp',n,alpha,beta,lambda);

hold on;
for i=1:n
    plot(derp,M(:,i));
end

