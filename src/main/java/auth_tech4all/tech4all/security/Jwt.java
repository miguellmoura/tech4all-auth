package auth_tech4all.tech4all.security;

import auth_tech4all.tech4all.model.UserTech4All;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Deserializer;
import io.jsonwebtoken.jackson.io.JacksonDeserializer;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import io.jsonwebtoken.jackson.io.JacksonSerializer;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class Jwt {
    private static final Logger log = LoggerFactory.getLogger(Jwt.class);
    private static final String SECRET = "0e5582adfb7fa6bb770815f3c6b3534d311bd5fe";
    private static final long EXPIRE_HOURS = 48L;
    private static final long ADMIN_EXPIRE_HOURS = 2L;
    private static final String ISSUER = "PUCPR AuthServer";
    private static final String USER_FIELD = "User";

    public String createToken(UserTech4All userTech4All) {
        UserToken userToken = new UserToken(userTech4All);
        Key key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

        ZonedDateTime now = utcNow();
        ZonedDateTime expiration = now.plusHours(userToken.isAdmin() ? ADMIN_EXPIRE_HOURS : EXPIRE_HOURS);
        return Jwts.builder()
                .serializeToJsonWith(new JacksonSerializer<>())
                .signWith(key)
                .setIssuedAt(toDate(now))
                .setExpiration(toDate(expiration))
                .setIssuer(ISSUER)
                .setSubject(String.valueOf(userToken.getId()))
                .claim(USER_FIELD, userToken)
                .compact();
    }

    public Authentication extract(HttpServletRequest req) {
        try {
            String header = req.getHeader(HttpHeaders.AUTHORIZATION);
            if (header == null || !header.startsWith("Bearer ")) return null;

            String token = header.replaceFirst("Bearer ", "");
            Deserializer<Map<String, ?>> deserializer = new JacksonDeserializer<>(
                    Collections.singletonMap(USER_FIELD, UserToken.class)
            );

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8)))
                    .deserializeJsonWith(deserializer)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            if (!ISSUER.equals(claims.getIssuer())) {
                log.debug("Token rejected: {} != {}", ISSUER, claims.getIssuer());
                return null;
            }

            UserToken userToken = claims.get(USER_FIELD, UserToken.class);
            return toAuthentication(userToken);
        } catch (Exception e) {
            log.debug("Token rejected", e);
            return null;
        }
    }

    public boolean isValid(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            if (!ISSUER.equals(claims.getIssuer())) {
                log.debug("Token inválido: issuer incorreto");
                return false;
            }

            return true;
        } catch (Exception e) {
            log.debug("Token inválido: {}", e.getMessage());
            return false;
        }
    }


    private static ZonedDateTime utcNow() {
        return ZonedDateTime.now(ZoneOffset.UTC);
    }

    private static Date toDate(ZonedDateTime zdt) {
        return Date.from(zdt.toInstant());
    }

    private static Authentication toAuthentication(UserToken userToken) {
        List<SimpleGrantedAuthority> authorities = userToken.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());

        return UsernamePasswordAuthenticationToken.authenticated(userToken, userToken.getId(), authorities);
    }
}
