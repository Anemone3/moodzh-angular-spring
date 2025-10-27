package api.kokonut.moodzh.security;

import api.kokonut.moodzh.api.dto.response.TokenResponse;
import api.kokonut.moodzh.util.ApplicationProperties;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {
    private final ApplicationProperties properties;

    public TokenResponse generateToken(UserPrincipal userPrincipal, long expirationInSeconds) {
        Date issuedAt = new Date(System.currentTimeMillis());
        Date expirationDate = new Date(issuedAt.getTime() + expirationInSeconds  * 1000L);
        log.info("Properties {}", properties);
        String token = JWT.create()
                .withSubject(userPrincipal.getUsername())
                .withIssuedAt(issuedAt)
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(properties.getSecretToken()));

        return TokenResponse.builder()
                .access_token(token)
                .refresh_token(null)
                .token_type("bearer")
                .expires_in(expirationDate)
                .build();
    }

    public String getSubjectByToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(properties.getSecretToken());

        DecodedJWT jwt = JWT.require(algorithm)
                .build()
                .verify(token);

        return jwt.getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(properties.getSecretToken());

            JWT.require(algorithm)
                    .build()
                    .verify(authToken);

            return true;
        } catch (JWTVerificationException ex) {
            log.error("Token de JWT inválido o expirado. Mensaje: {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.error("Token JWT nulo o mal formado.");
        }
        return false;
    }
}
