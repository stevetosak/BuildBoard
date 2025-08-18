package com.db.finki.www.build_board.config.jwt;

import com.db.finki.www.build_board.service.user.BBUserDetailsService;
import com.nimbusds.jwt.PlainJWT;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.text.ParseException;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private static String BEARER_HEADER = "Bearer ";
    private final JWTUtils jwtUtils;
    private final BBUserDetailsService userDetailsService;

    public JWTAuthenticationFilter(JWTUtils jwtUtils, BBUserDetailsService userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getHeader("Authorization") == null || !request.getHeader("Authorization").startsWith(BEARER_HEADER)) {
            filterChain.doFilter(request, response);
            return;
        }

        String tokenString = request.getHeader("Authorization").substring(BEARER_HEADER.length());
        boolean valid = jwtUtils.isValid(tokenString);
        System.out.println("Valid: " + valid);

        if(!jwtUtils.isValid(tokenString)) {
            System.out.println("Invalid jwt: " +  tokenString);
            filterChain.doFilter(request, response);
            return;
        }

        PlainJWT jwt = jwtUtils.parseJWT(tokenString);
        SecurityContextHolder.getContext().setAuthentication(jwtUtils.getAuthentication(jwt));

        filterChain.doFilter(request, response);
    }
}
