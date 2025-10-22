package com.lcsz.abcde.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(JwtAuthorizationFilter.class);

    // Lista de URLs que devem ser ignoradas pelo filtro JWT
    private static final List<String> EXCLUDED_PATHS = Arrays.asList(
            "/actuator",
            "/docs-abcde",
            "/v3/api-docs",
            "/swagger-ui",
            "/webjars",
            "/configuration",
            "/swagger-resources",
            "/ping-spring",
            "/ping-python",
            "/gabaritos"
    );

    @Autowired
    private JwtUserDetailsService detailsService;

    /**
     * Este método decide se o filtro deve ser executado
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return EXCLUDED_PATHS.stream().anyMatch(path::startsWith);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String token = request.getHeader(JwtUtils.JWT_AUTHORIZATION);

        if(token == null || !token.startsWith(JwtUtils.JWT_BEARER)) {
            // log.debug("JWT Token está nulo, vazio, ou não iniciado com 'Bearer '.");
            filterChain.doFilter(request, response);
            return;
        }

        if(!JwtUtils.isTokenValid(token)) {
            // log.warn("JWT Token está inválido ou expirado.");
            filterChain.doFilter(request, response);
            return;
        }

        String login = JwtUtils.getUsernameFromToken(token);
        toAuthentication(request, login);

        filterChain.doFilter(request, response);
    }

    private void toAuthentication(HttpServletRequest request, String login) {
        UserDetails userDetails = detailsService.loadUserByUsername(login);

        UsernamePasswordAuthenticationToken authenticationToken = UsernamePasswordAuthenticationToken
                .authenticated(userDetails, null, userDetails.getAuthorities());

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}