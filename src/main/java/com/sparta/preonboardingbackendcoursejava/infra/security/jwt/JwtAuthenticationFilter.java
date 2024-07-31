package com.sparta.preonboardingbackendcoursejava.infra.security.jwt;



import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtPlugin jwtPlugin;

    @Autowired
    public JwtAuthenticationFilter(JwtPlugin jwtPlugin) {
        this.jwtPlugin = jwtPlugin;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String jwt = getBearerToken(request);

        if (jwt != null) {
            CompletableFuture<Void> future = jwtPlugin.validateToken(jwt)
                    .thenAccept(payload -> {
                        String email = payload.getSubject();
                        String nickname = payload.get("nickname", String.class);
                        UserPrincipal principal = new UserPrincipal(email, nickname, Set.of());

                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                principal, null, Set.of());

                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    })
                    .exceptionally(ex -> {
                        ex.printStackTrace();
                        return null;
                    });

            try {
                future.get();
            } catch (Exception e) {
                throw new ServletException("Token validation failed", e);
            }
        }

        filterChain.doFilter(request, response);
    }

    private String getBearerToken(HttpServletRequest request) {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorization != null && authorization.startsWith("Bearer ")) {
            return authorization.substring(7);
        }
        return null;
    }
}
