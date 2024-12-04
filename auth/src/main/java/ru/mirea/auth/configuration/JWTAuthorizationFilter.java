package ru.mirea.auth.configuration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import ru.mirea.auth.entity.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final String secret = "JAHFIDS8724yuJDGBHFJKdsgyfgsdhjfg&^%FDTS&TFGYuk";

    public JWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(req, res);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (token != null) {
            try {
                DecodedJWT jwt = JWT.require(Algorithm.HMAC512(this.secret.getBytes()))
                        .withClaimPresence("id")
                        .withClaimPresence("role")
                        .build()
                        .verify(token.replace("Bearer ", ""));
                User user = new User();
                user.setId(jwt.getClaim("id").asLong());
                user.setLogin(jwt.getSubject());
                user.setRole(jwt.getClaim("role").asString());
                SimpleGrantedAuthority role = new SimpleGrantedAuthority(user.getRole());
                return new UsernamePasswordAuthenticationToken(user, null, Collections.singletonList(role));
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }
}