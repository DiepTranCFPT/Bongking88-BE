package com.example.demo.config;

import org.springframework.security.web.util.matcher.RequestMatcher;


public class CustomRequestMatcher implements RequestMatcher {

    @Override
    public boolean matches(jakarta.servlet.http.HttpServletRequest request) {
        String method = request.getMethod();
        String uri = request.getRequestURI();
        return "GET".equals(method) && uri.startsWith("/api/location");
    }


    @Override
    public MatchResult matcher(jakarta.servlet.http.HttpServletRequest request) {
        return RequestMatcher.super.matcher(request);
    }
}
