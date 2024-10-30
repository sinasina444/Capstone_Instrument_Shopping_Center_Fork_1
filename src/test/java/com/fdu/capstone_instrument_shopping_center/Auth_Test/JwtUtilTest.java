package com.fdu.capstone_instrument_shopping_center.Auth_Test;

import com.fdu.capstone_instrument_shopping_center.security.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.crypto.SecretKey;
import java.util.Base64;


@Slf4j
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class JwtUtilTest {

    @Autowired
    JwtUtil jwtUtil;

    @BeforeEach
    public void setUp() {
        this.jwtUtil = new JwtUtil();
    }

    @Test
    public void base64KeyGeneratorTest() {
        SecretKey key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS512);
        String base64Key = Base64.getEncoder().encodeToString(key.getEncoded());
        System.out.println("Generated base64 Key: " + base64Key);
    }

//    @Test
//    public void getSignKeyTest() {
//        String strToken = jwtUtil.generateToken("testUser", "testAdmin");
//        System.out.println("Generated Token: " + strToken);
//    }

//    @Test
//    public void validateTokenTest() {
//        String strToken = jwtUtil.generateToken("testUser", "testAdmin");
//        boolean isValid = jwtUtil.validateToken(strToken);
//        if(!isValid) {
//            log.warn("Token validation fails in validation test.");
//        }
//        // test other three functions
//        String username = jwtUtil.extractUsername(strToken);
//        String role = jwtUtil.extractRole(strToken);
//        Claims claims = jwtUtil.extractAllClaims(strToken);
//        log.info("Test: username with {}, role with {}.", username, role);
//        log.info("Test: Claims role {}, user {}, issueAt {}, issueExpires {}.",
//                claims.get("role"), claims.getSubject(), claims.getIssuedAt(), claims.getExpiration());
//    }

    @Test
    void base64KeyGenerator() {
        SecretKey key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS512);
        String base64Key = Base64.getEncoder().encodeToString(key.getEncoded());
        log.info("Generated base64 Key: " + base64Key);
    }
}
