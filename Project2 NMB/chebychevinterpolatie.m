function chebychevinterpolatie


derp=[];
x=[];
toplot=[];
xmat=[];
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
        x=poly_zeros(i,alpha,beta,lambda);
        x=sort(x)
        for j=1:length(x)
            f(j)=cos(x(j));
        end 
    [y,conditie]=interpolate(x,f',alpha,beta,lambda,x);
    hurr=[hurr conditie];
 %   if(i<10)
 %       for j=1:length(x)
  %          xmat(i,j)=x(j);
            %toplot(i/5,j)=y(j);
  %          error(i,j)=abs(y(j)-f(j));
  %      end
   % end
    benaderd=interpolate(x,f',alpha,beta,lambda,t);
    fout=abs(derp'-benaderd);
   % maxerr=max(fout)
   % toplot=[toplot maxerr];
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
plot(hurr);
%plotWout(t,toplot')
%xmat(xmat==0)=NaN;
%toplot(toplot==0)=NaN;
%error
%plot(xmat',error');
%plot(t,derp,'r');


