package com.example.shop_java.configuration.filter;

import com.example.shop_java.exception.InvalidJwtToken;
import com.example.shop_java.service.user.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

@Component
@Log4j2
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final String BEARER_PREFIX = "Bearer ";
    public static final String HEADER_NAME = "Authorization";

    private final JwtService jwtService;


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HEADER_NAME);
        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER_PREFIX)) {
            String token = authorizationHeader.substring(7);
            try {
                if (jwtService.validateToken(token)) {
                    SecurityContextHolder.getContext().setAuthentication(jwtService.getAuthentication(token));
                }
            } catch (InvalidJwtToken | RuntimeException e) {
                try (PrintWriter out = response.getWriter()) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json");
                    out.write("{\"error\":\"Invalid or expired token\"}");
                    out.flush();
                }
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

}
