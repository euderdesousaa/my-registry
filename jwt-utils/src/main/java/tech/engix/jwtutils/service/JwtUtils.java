package tech.engix.jwtutils.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class JwtUtils {

    @Value("${jwtSecret}")
    private String jwtSecret;
    private SecretKey key() {

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