package com.thisaster.testtask.auth.service;

import com.thisaster.testtask.auth.config.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
public class JwtService {
    @Value("${keys.privateKey}")
    private String privateKey;

    @Value("${keys.publicKey}")
    private String publicKey;

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }


    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userDetails.getUsername());
        if (userDetails instanceof UserPrincipal customUserDetails) {
            claims.put("id", customUserDetails.getUser().getId());
            claims.put("email", customUserDetails.getUser().getEmail());
            claims.put("roles", customUserDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
            claims.put("enabled", customUserDetails.isEnabled());
        }
        log.debug("auth clainms {}", claims);
        return generateToken(claims, userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 5))
                .signWith(getSigningKey())
                .compact();
    }

    private PrivateKey getSigningKey() {
        try {

            String privateKeyBase64 = privateKey.replace("\n", "");
            byte[] keyBytes = Base64.getDecoder().decode(privateKeyBase64);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);

            return keyFactory.generatePrivate(keySpec);

        } catch (Exception e) {
            throw new RuntimeException("Error while loading private key", e);
        }
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }


    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


    public Claims extractAllClaims(String token) {
        return (Claims) Jwts.parser()
                .verifyWith(getPublicKey())
                .build()
                .parse(token)
                .getPayload();
    }

    private PublicKey getPublicKey() {
        try {

            String publicKeyBase64 = this.publicKey.replace("\n", "");
            byte[] keyBytes = Base64.getDecoder().decode(publicKeyBase64);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

            return keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            throw new RuntimeException("Error while loading public key", e);
        }
    }

}
