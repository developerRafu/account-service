package com.rafu.accountservice.services;

import com.rafu.accountservice.errors.InvalidTokenException;
import com.rafu.accountservice.models.rest.TokenResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTService {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;

    public TokenResponse getToken(final String email) {
        final var tokenString = generateToken(email);
        final var token = new TokenResponse();
        token.setType("Bearer");
        token.setToken(tokenString);
        token.setDuration(expiration);
        return token;
    }

    public String generateToken(final String email) {
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret.getBytes()).compact();
    }

    public boolean isValidToken(final String token) {
        Claims claims = getClaims(token);
        String username = claims.getSubject();
        Date expirationDate = claims.getExpiration();
        return username != null && expirationDate != null;
    }

    public boolean isExpiredToken(final String token) {
        Claims claims = getClaims(token);
        String username = claims.getSubject();
        Date expirationDate = claims.getExpiration();
        Date now = new Date(System.currentTimeMillis());
        return username != null && expirationDate != null && now.after(expirationDate);
    }

    public boolean isExpiredToken(final Claims claims) {
        String username = claims.getSubject();
        Date expirationDate = claims.getExpiration();
        Date now = new Date(System.currentTimeMillis());
        return username != null && expirationDate != null && now.after(expirationDate);
    }

    public Claims getClaims(final String token) {
        final var tokenWithoutBearer = token.substring(7);
        try {
            return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(tokenWithoutBearer).getBody();
        } catch (MalformedJwtException e) {
            throw new InvalidTokenException();
        }
    }

    public String getUsername(String token) {
        Claims claims = getClaims(token);
        return claims.getSubject();
    }
}
