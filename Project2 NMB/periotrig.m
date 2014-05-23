function y = periotrig(x, K, M)

    %Geeft een periodische benadering terug van de kolommen van x
    
    %Parameters:
    % x: Een N x d matrix waarbij N even moet zijn
    % K: De graad van de trigonioetrische veelterm.
    % M: Het nieuwe aantal interpolatiepunten.
    
    [N, O] = size(x);
    
    if(mod(N, 2) ~= 0)
        disp('N must be even');
        return;
    end;
   
    %Maak de nieuwe matrix aan waar alle nieuwe interpolatie punten
    %inpassen.
    y = zeros(M, O);
    
    for col = 1:O
        Xk = fft(x(:,col)); %Bereken de fourrier coëfficienten van x.
        i = linspace(1, 0, M+1)';
        i = i(1:M);
        
        %Berekent de sommatie van formule (3) van de opgave.
        for k = 1:K-1
            y(:,col) = y(:,col) + real(Xk(k+1)) * cos(2*pi*k*i) + imag(Xk(k+1)) * sin(2*pi*k*i);
        end;
        
        y(:,col) = y(:,col) * 2; %Factor 2 in rekening brengen
        y(:,col) = y(:,col) + Xk(1); %X0 term van formmule (3) in rekening brengen.
        
        if(K == N/2)
            y(:,col) = y(:,col) + Xk(N/2 + 1) * cos(pi*N*i);
        end;
        
        y(:,col) = y(:,col) / N; %Het geheel delen door N
        %Deze y term is nu gelijk aan de yterm van formule (3)
    end
end

