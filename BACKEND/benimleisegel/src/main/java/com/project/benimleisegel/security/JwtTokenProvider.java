package com.project.benimleisegel.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration-miliseconds}")
    private long jwtExpiration;


    //generate token
    public String generateToken(Authentication authentication) {
        String email = authentication.getName();

        Date now = new Date(System.currentTimeMillis());
        Date expiration = new Date(now.getTime() + jwtExpiration);

        String token = Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(expiration)
                .signWith(getSecretKey())
                .compact();
        return token;
    }

    //extract username from token
    public String extractEmailFromToken(String token) {
        String email = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
        return email;
    }

    //validate token
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parse(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Expired or invalid JWT token");
        } catch (MalformedJwtException e) {
            throw new RuntimeException("Malformed JWT token");
        } catch (SecurityException e) {
            throw new RuntimeException("Security exception");
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Illegal argument exception thrown");
        }
    }

    //secret key
    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

}
