package com.ddantime.ddantime.common.crypto;

import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

@Component
public class CryptoService {

    private static final String AES_GCM_NOPADDING = "AES/GCM/NoPadding";
    private static final int IV_LEN = 12;    // GCM 권장 IV 길이(12 bytes)
    private static final int TAG_BITS = 128; // Auth tag 128-bit

    private final SecretKey key;

    private final SecureRandom random = new SecureRandom();
    public CryptoService(SecretKey aesSecretKey) {
        this.key = aesSecretKey;
    }


    public String encrypt(String plaintext) {
        try {

            if (plaintext == null) return null;
            try {
                // 매번 랜덤 IV 생성
                byte[] iv = new byte[IV_LEN];
                random.nextBytes(iv);

                Cipher cipher = Cipher.getInstance(AES_GCM_NOPADDING);
                cipher.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(TAG_BITS, iv));


                byte[] ct = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));

                // 저장 포맷: Base64( IV + CT )
                byte[] out = ByteBuffer.allocate(iv.length + ct.length)
                        .put(iv)
                        .put(ct)
                        .array();
                return Base64.getEncoder().encodeToString(out);
            } catch (Exception e) {
                throw new IllegalStateException("Encrypt failed", e);
            }

        } catch (Exception e) {
            throw new IllegalStateException("Encrypt failed", e);
        }
    }

    public String decrypt(String encryptedText) {

        if (encryptedText == null) return null;
        try {
            byte[] all = Base64.getDecoder().decode(encryptedText);

            if (all.length <= IV_LEN) {
                throw new IllegalArgumentException("Ciphertext too short");
            }

            byte[] iv = new byte[IV_LEN];
            byte[] ct = new byte[all.length - IV_LEN];

            System.arraycopy(all, 0, iv, 0, IV_LEN); // iv 구하기
            System.arraycopy(all, IV_LEN, ct, 0, ct.length); // 암호화 구하기

            Cipher cipher = Cipher.getInstance(AES_GCM_NOPADDING);
            cipher.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(TAG_BITS, iv));

            byte[] pt = cipher.doFinal(ct);
            return new String(pt, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new IllegalStateException("Decrypt failed", e);
        }
    }
}
