package cc.nishant.authenticator.security;

import cc.nishant.authenticator.enums.Status;
import cc.nishant.authenticator.security.model.Token;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Component
public class TokenManager {

    @Value("${token.manager.secret}")
    private String secret;

    @Value("${token.manager.expiration.time}")
    private Long expirationTime;

    public Token generateToken(final Long id) {
        final Instant now = Instant.now();
        final Instant expiry = now.plus(this.expirationTime, ChronoUnit.MILLIS);
        final String token = Jwts.builder().setSubject(id.toString()).setExpiration(Date.from(expiry)).setIssuedAt(Date.from(now)).signWith(SignatureAlgorithm.HS512, this.secret).compact();
        return Token.builder().userId(id).expiry(expiry).token(token).status(Status.ACTIVE).build();
    }

    public boolean validateAccessToken(final String token) {
        if (Strings.isNotBlank(token)) {
            try {
                Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
                return true;
            } catch (final Exception ex) {
                log.error("Invalid access token");
            }
        }
        return false;
    }

    public Long getUserIdFromToken(final String token) {
        final Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        return Long.parseLong(claims.getSubject());
    }
}
