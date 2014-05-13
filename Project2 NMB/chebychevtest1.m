function M= chebychevtest1
derp=[-1:.01:1];
x=length(derp);
alpha = zeros(x);
lambda=2*ones(x);
lambda(1)=1;
lambda(2)=1;

beta=ones(x);


M=eval_recursion(derp',10,alpha,beta,lambda);

plot(derp,M,'LineWidth',2);

