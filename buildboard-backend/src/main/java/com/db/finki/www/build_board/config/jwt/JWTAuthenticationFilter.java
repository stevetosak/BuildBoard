package com.db.finki.www.build_board.config.jwt;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private static String BEARER_HEADER = "Bearer ";
    private final JWTUtils jwtUtils;

    public JWTAuthenticationFilter(JWTUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getHeader("Authorization").isEmpty() || request.getHeader("Authorization").startsWith(BEARER_HEADER)) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = request.getHeader("Authorization").substring(BEARER_HEADER.length());
        SecurityContextHolder.getContext().setAuthentication(jwtUtils.extractFromRequest(token));

        filterChain.doFilter(request, response);
    }
}
