package com.ethereal.witch.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.ethereal.witch.interfaces.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
public class AuthUser extends OncePerRequestFilter {
    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var servletPath = request.getServletPath();

       if (servletPath.endsWith("/auth")){
            var auth = request.getHeader("Authorization");
            var authEncode = auth.substring("basic".length()).trim();
            byte[] authDecode = Base64.getDecoder().decode(authEncode);

            var authString = new String((authDecode));
            String[] credential = authString.split(":");

            String username = credential[0];
            String password = credential[1];
            System.out.println(username + "<---------");
            var user = this.userRepository.findByUsername(username);
            if (user == null){
                response.sendError(401);
            }else{
                var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                System.out.println(passwordVerify + "<------------");
                if (passwordVerify.verified){
                    request.setAttribute("idUser",user.getId());
                    filterChain.doFilter(request,response);
                }else{
                    response.sendError(401);
                }
            }
        }else {
           filterChain.doFilter(request,response);
       }
    }
}
