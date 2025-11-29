package Siddharth.demo.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // Generate a secure random key (store in application.yml or environment variable)
    private final Key key = Keys.hmacShaKeyFor("supersecretkeysupersecretkey123456".getBytes());

    // 10 minutes validity
    private static final long EXPIRATION_TIME = 10 * 60 * 1000;

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuer("MyBankAPI")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String validateTokenAndGetUsername(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .requireIssuer("MyBankAPI")
                    .build()
                    .parseClaimsJws(token);
            return claims.getBody().getSubject();
        } catch (JwtException e) {
            return null; // invalid or expired
        }
    }
}