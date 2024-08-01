package com.sparta.preonboardingbackendcoursejava.infra.security.jwt;

import com.auth0.jwt.interfaces.DecodedJWT;
import io.vavr.control.Try;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

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
            Try<DecodedJWT> tryResult = jwtPlugin.validateToken(jwt);

            tryResult.onSuccess(decodedJWT -> {
                String email = decodedJWT.getSubject();
                String nickname = decodedJWT.getClaim("nickname").asString();

                UserPrincipal principal = new UserPrincipal(
                        email,
                        nickname,
                        Collections.<GrantedAuthority>emptySet()
                );

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        principal,
                        null,
                        Collections.emptySet()
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }).onFailure(ex -> {
//                ex.printStackTrace();
            });
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
