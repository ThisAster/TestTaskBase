package com.thisaster.testtask.auth.utils;

import com.thisaster.testtask.auth.config.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JWTUtils {

    private final JwtEncoder jwtEncoder;

    public String generateToken(UserDetails userDetails) {
        Instant now = Instant.now();
        Instant expiration = now.plusSeconds(1000 * 60 * 60 * 24 * 5);

        JwtClaimsSet.Builder claimsBuilder = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(expiration)
                .subject(userDetails.getUsername());

        if (userDetails instanceof UserPrincipal userPrincipal) {
            claimsBuilder
                    .claim("id", userPrincipal.getUser().getId())
                    .claim("username", userPrincipal.getUsername())
                    .claim("email", userPrincipal.getUser().getEmail())
                    .claim("roles", userPrincipal.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .collect(Collectors.toList()));
        }

        JwtClaimsSet claims = claimsBuilder.build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
