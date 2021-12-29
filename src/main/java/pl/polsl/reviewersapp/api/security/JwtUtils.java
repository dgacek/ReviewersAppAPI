package pl.polsl.reviewersapp.api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;
import pl.polsl.reviewersapp.api.model.entity.UserEntity;

import java.util.Date;

@Service
public class JwtUtils {
    private static final String JWT_SECRET = "QLSC6nSI0R7vBmyBsDX0J4XENTlkW4mI96gY7Wj_mAmiJFQIr7klqVHhQJmiQTSb1tHEYz222MzvucimU9tpvA";

    public String generateAccessToken(UserEntity user) {
        return Jwts.builder()
                .setSubject(String.format("%s,%s", user.getId(), user.getUsername()))
                .setExpiration(new Date(System.currentTimeMillis() + 12 * 60 * 60 * 1000)) // 12 hours
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    public Boolean isTokenValid(String token, UserEntity user) {
        return getAllClaims(token).getExpiration().after(new Date()) && getUsername(token).equals(user.getUsername());
    }

    public String getUsername(String token) {
        return getAllClaims(token).getSubject().split(",")[1];
    }

    public Long getUserId(String token) {
        return Long.parseLong(getAllClaims(token).getSubject().split(",")[0]);
    }

    private Claims getAllClaims(String token) {
        return Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();
    }

}
