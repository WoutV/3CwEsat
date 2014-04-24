function [V,D,errormat] = jacobitol(A,tol)

% A =de matrix waarvan de eigenwaarden moeten bepaald worden
% tol = de toegelaten tolerantie op de maximale fout van de eigenwaarden.
[n,p] = size(A);
errormat = [];
if n~=p,
  disp('A is geen vierkante matrix')
  return
end
if n<2
  disp('A moet minstens dimensie 2 hebben')
  return
end
error = tol +1;
V = eye(n);
while(error > tol)
for index = 1:(n-1);  
    %find largest off-diagonal element in row with number index
    place = index+1;
    for elementnumber = index+2:n;
        if (elementnumber > n)
            elementnumber_to_check = n;
        else
            elementnumber_to_check = elementnumber;
        end
        
        if (abs(A(index, elementnumber_to_check)) > abs(A(index, place)))
            place = elementnumber_to_check;
        end
    end
    a = A(index,index);
    b = A(place, place);
    d = A(index, place);
    
    theta = 0.5* atan(2*d/(b-a));
    if ( b == a)
        theta = pi/4;
    end
    s = sin(theta);
    c = cos(theta);
    spiegelmatrix = eye(n);
    spiegelmatrix(index,index) = c;
    spiegelmatrix(index,place) = s;
    spiegelmatrix(place,index) = -s;
    spiegelmatrix(place,place) = c;
    A = transpose(spiegelmatrix)* A * spiegelmatrix;
    V = V* spiegelmatrix;
%find the maximum off-diagonal element
%start at element(1,2)
error = abs(A(1,2)); 
end
for rownumber = 1:n;
    for columnnumber = 1:n;
        if(rownumber ~= columnnumber)
            if abs(A(rownumber, columnnumber)) > error
                error = abs(A(rownumber,columnnumber));
            end
        end
    end
end                 
errormat=[errormat error];
end
disp('error');
disp(error);
D = A;





