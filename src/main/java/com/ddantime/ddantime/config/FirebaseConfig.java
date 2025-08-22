package com.ddantime.ddantime.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.InputStream;

@Slf4j
@Configuration
public class FirebaseConfig {

    /**
     * (선택) 로컬 개발에서만 사용할 수 있는 서비스 키 파일 경로.
     * Cloud Run 등 GCP 런타임에서는 비워두고 ADC를 사용하세요.
     */
    @Value("${FIREBASE_CREDENTIALS_PATH:}")
    private String credentialsPath;

    /**
     * 반드시 Firebase가 연결된 "다른" 프로젝트(B)의 ID를 넣으세요.
     * 예) my-firebase-prod
     */
    @Value("${FIREBASE_PROJECT_ID}")
    private String firebaseProjectId;

    @PostConstruct
    public void initFirebase() {
        try {
            if (!FirebaseApp.getApps().isEmpty()) {
                log.info("Firebase already initialized. Skipping.");
                return;
            }

            GoogleCredentials credentials;
            if (credentialsPath != null && !credentialsPath.isBlank()) {
                // (로컬 전용) 파일 경로가 주어지면 그걸로 초기화
                log.info("Initializing Firebase with service account file: {}", credentialsPath);
                try (InputStream in = new FileInputStream(credentialsPath)) {
                    credentials = GoogleCredentials.fromStream(in);
                }
            } else {
                // 기본: ADC (Cloud Run 서비스 계정 자격증명)
                log.info("Initializing Firebase with ADC (Application Default Credentials)");
                credentials = GoogleCredentials.getApplicationDefault();
            }

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(credentials)
                    .setProjectId(firebaseProjectId) // ← 다른 Firebase 프로젝트(B) 지정
                    .build();

            FirebaseApp.initializeApp(options);
            log.info("✅ Firebase initialized (projectId={})", firebaseProjectId);

        } catch (Exception e) {
            log.error("❌ Failed to initialize Firebase", e);
            throw new IllegalStateException("Firebase initialization failed: " + e.getMessage(), e);
        }
    }
}
