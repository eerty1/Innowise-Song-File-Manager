package com.innowise.jwt;

import com.innowise.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.innowise.model.Role.MICROSERVICE;
import static java.time.temporal.ChronoUnit.MINUTES;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtEncoder jwtEncoder;

    public String generateToken(Authentication authentication, ChronoUnit chronoUnit) {
        return this.generateToken(
                (User) authentication.getPrincipal(), chronoUnit
        );
    }

    public String generateToken(User user, ChronoUnit chronoUnit) {
        return jwtEncoder.encode(JwtEncoderParameters
                .from(
                        buildClaims(user, chronoUnit)
                )
        ).getTokenValue();
    }

    public String generateTokenInternalRequest() {
        return jwtEncoder.encode(JwtEncoderParameters
                .from(
                        buildClaims(
                                new User(
                                        UUID.randomUUID(),
                                        "AuthAPI",
                                        MICROSERVICE
                                ),
                                MINUTES
                        )
                )
        ).getTokenValue();
    }

    private String buildScope(Collection<? extends GrantedAuthority> authorities) {
        return authorities
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
    }

    private JwtClaimsSet buildClaims(User user, ChronoUnit chronoUnit) {
        Instant now = Instant.now();
        String scope = buildScope(user.getAuthorities());
        return JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(now.plus(1, chronoUnit))
                .subject(user.getUsername())
                .id(user.getId().toString())
                .claim("scope", scope)
                .build();
    }
}
