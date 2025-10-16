package com.lcsz.abcde.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JwtUtils {
    private static final Logger log = LoggerFactory.getLogger(JwtUtils.class);
    public static final String JWT_BEARER = "Bearer ";
    public static final String JWT_AUTHORIZATION = "Authorization";
    public static final String SECRET_KEY = "0123456789-0123456789-0123456789"; // Min 32 caracters

    private JwtUtils() {}

    private static javax.crypto.SecretKey generateKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    private static Date toExpireDate(Duration duration) {
        Date issuedAt = new Date();
        return Date.from(issuedAt.toInstant().plus(duration));
    }

    public static JwtToken createToken(UUID id, String login, String role, Duration duration) {
        Date issuedAt = new Date();
        Date limit = toExpireDate(duration);
        String token = Jwts.builder()
                .header().add("typ", "JWT")
                .and()
                .subject(login)
                .issuedAt(issuedAt)
                .expiration(limit)
                .signWith(generateKey())
                .claim("id", id)
                .claim("role", role)
                .compact();
        return new JwtToken(token);
    }

    private static Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(generateKey())
                    .build()
                    .parseSignedClaims(refactorToken(token)).getPayload();
        } catch (JwtException ex) {
            log.error("Erro ao buscar claims do token: {}", ex.getMessage());
            return null;
        }
    }

    public static String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    public static boolean isTokenValid(String token) {
        try {
            Jwts.parser()
                    .verifyWith(generateKey())
                    .build()
                    .parseSignedClaims(refactorToken(token));
            return true;
        } catch (JwtException ex) {
            log.error("Token inválido {}", ex.getMessage());
            return false;
        }
    }

    private static String refactorToken(String token) {
        if(token.contains(JWT_BEARER)) {
            return token.substring(JWT_BEARER.length());
        }

        return token;
    }
}
