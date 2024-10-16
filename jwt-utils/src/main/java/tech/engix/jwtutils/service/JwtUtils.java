package tech.engix.jwtutils.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class JwtUtils {

    private SecretKey key() {
        String jwtSecret = System.getenv("JWT_SECRET");
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().
                verifyWith(key())
                .build()
                .parseSignedClaims(token).
                getPayload().
                getSubject();
    }

}