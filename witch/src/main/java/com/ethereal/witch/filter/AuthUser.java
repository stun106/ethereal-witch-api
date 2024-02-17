package com.ethereal.witch.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.ethereal.witch.models.user.User;
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

    private String[] getAuthCredential(String auth, HttpServletRequest request) {
            String authEncode = auth.substring("Basic".length()).trim();
            byte[] authDecode = this.getBase64Decoder(authEncode);
            return getCredential(authDecode);
    }

    private byte[] getBase64Decoder(String auth) {
        return Base64.getDecoder().decode(auth);
    }

    private String[] getCredential(byte[] decode) {
        String authString = new String((decode));
        return authString.split(":");
    }

    private Boolean decodePassword(char[] password, String userPassword) {
        return BCrypt.verifyer().verify(password, userPassword).verified;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String servletPath = request.getServletPath();

        if (servletPath.endsWith("/auth")) {
            String auth = request.getHeader("Authorization");

            if (auth != null && auth.startsWith("Basic")) {
               getAuthCredential(auth,request);
                String username = getAuthCredential(auth, request)[0];
                String password = getAuthCredential(auth, request)[1];
                User user = userService.findUsername(username);
                if (user == null) {
                    response.sendError(403,"Requires authorization! Please contact the developer. " +
                             "mail: antoniojr.strong@gmail.com");
                } else {
                    if (decodePassword(password.toCharArray(), user.getPassword())) {
                        request.setAttribute("idUser", user.getId());
                        filterChain.doFilter(request, response);
                    } else {
                        response.sendError(401,"Password invalid.");
                    }
                }
            } else {
                response.sendError(401, "You need to be logged in.");
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}

