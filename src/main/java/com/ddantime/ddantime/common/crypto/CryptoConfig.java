package com.ddantime.ddantime.common.crypto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Configuration
public class CryptoConfig {

    @Bean
    public SecretKey aesSecretKey(@Value("${CRYPTO_DEK}") String dek) {

        if (dek == null || dek.isBlank()) {
            throw new IllegalStateException("Missing env var CRYPTO_DEK");
        }

        byte[] key = Base64.getDecoder().decode(dek);

        if (key.length != 32) { // AES-256 32바이트
            throw new IllegalArgumentException("CRYPTO_DEK must decode to 32 bytes (AES-256)");
        }

        return new SecretKeySpec(key, "AES");
    }
}
