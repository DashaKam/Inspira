package nsu.fit.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.experimental.UtilityClass;
import nsu.fit.config.security.SecurityConstants;
import org.springframework.stereotype.Component;

import java.util.Date;

@UtilityClass
public class JwtUtil {
    public static String generateToken(String id) {
        return Jwts.builder()
                .setSubject(id)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, Keys.hmacShaKeyFor(SecurityConstants.JWT_SECRET.getBytes()))
                .compact();
    }

    public static String extractId(String token) {
        return getClaims(token).getSubject();
    }

    public static boolean validateToken(String token, String username) {
        return username.equals(extractId(token)) && !isTokenExpired(token);
    }

    private static boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    private static Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor(SecurityConstants.JWT_SECRET.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
