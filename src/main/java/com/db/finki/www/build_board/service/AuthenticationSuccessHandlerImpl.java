package com.db.finki.www.build_board.service;

import com.db.finki.www.build_board.entity.user_types.BBUser;
import com.db.finki.www.build_board.service.threads.impl.FileUploadService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

import java.io.IOException;
import java.util.Objects;

public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        BBUser user = (BBUser) authentication.getPrincipal();
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        session.setMaxInactiveInterval(1800);

        String redirectUrl = getSavedRequestUrl(request);

        System.out.println(redirectUrl + "REDIR");

        response.sendRedirect(Objects.requireNonNullElse(redirectUrl, "/"));

    }

    private String getSavedRequestUrl(HttpServletRequest request) {
        HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
        DefaultSavedRequest savedRequest = (DefaultSavedRequest) requestCache.getRequest(request, null);
        return (savedRequest != null) ? savedRequest.getRedirectUrl() : null;
    }
}
