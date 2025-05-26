package nsu.fit.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.experimental.UtilityClass;
import nsu.fit.config.security.SecurityConstants;

@UtilityClass
public class JwtUtil {
    public static String extractId(String token) {
        return getClaims(token).getSubject();
    }

    private static Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor(SecurityConstants.JWT_SECRET.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
