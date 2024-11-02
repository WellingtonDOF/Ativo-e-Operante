
package br.unoeste.wellington.ativooperante2024.security;

import java.io.IOException;

import br.unoeste.wellington.ativooperante2024.security.JWTTokenProvider;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



public class AccessFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        String token = req.getHeader("Authorization");
        System.out.println("print do header "+req.getHeader("Authorization"));

        //SE DER ERRO COLOCAR O TOKEN VALIDO NA VARIAVEL TOKEN PARA TESTAR
        //token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjEuMTExLjU4OS02MyIsImlzcyI6ImxvY2FsaG9zdDo4MDgwIiwibml2ZWwiOiIxIiwiaWF0IjoxNzE4MjkzNDU5LCJleHAiOjE3MTgyOTM3NTl9.t9INHbKyhLym6-0U_6tbl848MurhRk0com6eBdUS_-w";
        //Tive que mudar isso tudo aí abaixo para funcionar..



        /*
        if(token!=null && token.startsWith("Bearer "))
        {
            token = token.substring(7);//Pega apenas a string do caracter 7 para frente ou seja elimina Bearer' '
            System.out.println("token de verificação "+token);

            if(JWTTokenProvider.verifyToken(token))//verifica se o token ainda está ativo e liberado o acesso
            {
                chain.doFilter(request,response);
            }
            else
            {
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                //httpResponse.getWriter().write("Acesso não autorizado");
            }
        }else
        {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }*/


        //para testar comentar tudo e deixar apenas isso aqui, que ele vai retornar o acesso mesmo que não tenha token.
        chain.doFilter(request,response);

    }
}

    
