package com.ecom.ecommerce_app.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil 
{

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    // Generate JWT token
    public String generateToken(String username) 
    {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    // Extract username from token
    public String extractUsername(String token) 
    {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Check token expiry
    public boolean isTokenExpired(String token) 
    {
        Date expiry = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();

        return expiry.before(new Date());
    }

    // Validate token
    public boolean validateToken(String token, String username) 
    {
        return extractUsername(token).equals(username) && !isTokenExpired(token);
    }
}
