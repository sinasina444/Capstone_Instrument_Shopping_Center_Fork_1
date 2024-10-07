package com.fdu.capstone_instrument_shopping_center.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JwtUtil {
    private final String SECRET_KEY = base64KeyGenerator();

    private SecretKey getSignSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes); // HMAC-SHA sign
    }

    public String generateToken(String username, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000*60*60*24)) // set 24 hours expiration time
                .signWith(getSignSecretKey())
                .compact();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody(); // retrieve JWT Body 'claims'
    }


    public boolean validateToken(String token) {
        try {
            Claims claims = extractAllClaims(token);
            if(claims.isEmpty()) {
                return false;
            }
            String role = extractRole(token);
            if(role.isEmpty()) {
                return false;
            }
            if(!(role.equals("admin") || role.equals("customer") || role.equals("seller"))) {
                return false;
            }
            // check if token has expired
            if(claims.getExpiration().before(new Date())) {
                return false;
            }
            return true;
        } catch (ExpiredJwtException e) {
            log.error("Token validation failed, Expired JWT Token : {}.", e.getMessage());
            return false;
        } catch (Exception e) {
            log.error("Token validation fail: {}.", e.getMessage());
            return false;
        }
    }

    String base64KeyGenerator() {
        SecretKey key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS512);
        String base64Key = Base64.getEncoder().encodeToString(key.getEncoded());
        System.out.println("Generated base64 Key: " + base64Key);
        return base64Key;
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String extractRole(String token) {
        return (String) extractAllClaims(token).get("role");
    }

    public Boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }
}
