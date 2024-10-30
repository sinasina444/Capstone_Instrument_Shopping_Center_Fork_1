package com.fdu.capstone_instrument_shopping_center.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@Setter
@Getter
public class JwtUtil {

//    private final String SECRET_KEY = "qgaY2kBoCHKxxE4HuKCBkNL3SLQSfxqqxvJb/8w41IGi99/A0WXBxGIWGcOWQ6LXp10eZuQUf7WOYVBBL1+H+Q==";
    @Value("${jwt.secret.key}")
    private  String SECRET_KEY;

    private final String ROLE_ADMIN = "ROLE_ADMIN";
    private final String ROLE_CUSTOMER = "ROLE_CUSTOMER";
    private final String ROLE_SELLER = "ROLE_SELLER";

    private SecretKey getSignSecretKey() {
        if (SECRET_KEY == null) {
            throw new IllegalStateException("SECRET_KEY is not initialized. Check configuration.");
        }
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
                .signWith(getSignSecretKey(), io.jsonwebtoken.SignatureAlgorithm.HS512)
                .compact();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSignSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody(); // retrieve JWT Body 'claims'
    }


    public boolean validateToken(String token) {
        try {
            Claims claims = extractAllClaims(token);
            String role = extractRole(token);

            if (role == null || !(role.equals(ROLE_ADMIN) || role.equals(ROLE_CUSTOMER) || role.equals(ROLE_SELLER))) {
                return false;
            }
            return !isTokenExpired(token);
        } catch (ExpiredJwtException e) {
            log.error("Token validation failed, Expired JWT Token : {}.", e.getMessage());
            return false;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}.", e.getMessage());
        } catch (Exception e) {
            log.error("Token validation fail: {}.", e.getMessage());
            return false;
        }
        return false;
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
