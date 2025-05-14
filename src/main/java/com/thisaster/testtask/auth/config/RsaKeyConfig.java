package com.thisaster.testtask.auth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Configuration
public class RsaKeyConfig {

    @Value("${jwt.privateKey}")
    private String privateKeyStr;

    @Value("${jwt.publicKey}")
    private String publicKeyStr;

    @Bean
    public RsaKeyPair rsaKeys() {
        try {
            byte[] privateBytes = Base64.getDecoder().decode(privateKeyStr.replaceAll("\\s+", ""));
            byte[] publicBytes = Base64.getDecoder().decode(publicKeyStr.replaceAll("\\s+", ""));

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateBytes));
            RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(new X509EncodedKeySpec(publicBytes));

            return new RsaKeyPair(publicKey, privateKey);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse RSA keys", e);
        }
    }

    public record RsaKeyPair(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
    }
}
