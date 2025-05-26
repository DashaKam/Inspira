package nsu.fit.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.experimental.UtilityClass;
import nsu.fit.config.security.SecurityConstants;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@UtilityClass
public class JwtUtil {
    private static final Key SECRET_KEY =
            Keys.hmacShaKeyFor(SecurityConstants.JWT_SECRET.getBytes(StandardCharsets.UTF_8));
    public static String generateToken(String id) {
        return Jwts.builder()
                .subject(id)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }
}
