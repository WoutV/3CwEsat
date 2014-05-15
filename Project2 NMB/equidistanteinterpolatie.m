function equidistanteinterpolatie
derp=[];
x=[];
toplot=[];
xmat=zeros(5,26);
err=[];
hurr=[];
hold on;
for i=3:25
    t=[-1:0.001:1]; 
    for j=1:length(t)
       derp(j)=cos(t(j));
    end
    alpha = zeros(i,1);
    lambda=2*ones(i,1);
    lambda(1)=1;
    lambda(2)=1;

    beta=ones(i,1);
        x=[-1:2/i:1]';
        for j=1:length(x)
            f(j)=cos(x(j));
        end 
    [y,conditie]=interpolate(x,f',alpha,beta,lambda,x);
    hurr=[hurr conditie];
    benaderd=interpolate(x,f',alpha,beta,lambda,t);
    fout=abs(derp'-benaderd);
   if(mod(i,5)==0)
    %if i==6
       for j=1:length(t)
            xmat(i/5,j)=t(j);
            toplot(i/5,j)=fout(j);
        end
   end
   xmat(xmat==0)=NaN;
   toplot(toplot==0)=NaN;
  
  
  mval=max(fout);
  err=[err mval];
    end
%plotWout(xmat',toplot');
%plotWout(t,toplot')
plot(hurr);
%xmat(xmat==0)=NaN;
%toplot(toplot==0)=NaN;
%error
%plot(xmat',error');
%plot(t,derp,'r');
%plotWout(t,toplot')

