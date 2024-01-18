package com.ethereal.witch.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.ethereal.witch.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
@AllArgsConstructor
public class AuthUser extends OncePerRequestFilter {

    private final UserService userService;
    private byte[] getBase64Decoder(String auth){
        return Base64.getDecoder().decode(auth);
    }
    private String[] getCredential(byte[] decode){
        String authString = new String((decode));
        return authString.split(":");
    }
    private Boolean decodePassword(char[] password, String userPassword){
        return BCrypt.verifyer().verify(password,userPassword).verified;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {


        String servletPath = request.getServletPath();

       if (servletPath.endsWith("/auth")){
            String auth = request.getHeader("Authorization");
            String authEncode = auth.substring("basic".length()).trim();
            byte[] authDecode = getBase64Decoder(authEncode);

            String[] credential = getCredential(authDecode);

            String username = credential[0];
            String password = credential[1];
            var user = userService.findUsername(username);
            if (user == null){
               response.sendError(401);
            }else{

                if (decodePassword(password.toCharArray(),user.getPassword())){
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
