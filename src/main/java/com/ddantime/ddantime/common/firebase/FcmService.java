package com.ddantime.ddantime.common.firebase;


import com.google.firebase.messaging.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class FcmService {

    /**
     * 단일 기기에 알림 전송
     */
    public void sendMessage(String targetToken, String title, String body, Map<String, String> data ) {
        try {
            Message.Builder builder = Message.builder()
                    .setToken(targetToken)
                    .setNotification(Notification.builder()
                            .setTitle(title)
                            .setBody(body)
                            .build())
                    .setAndroidConfig(AndroidConfig.builder()
                        .setNotification(AndroidNotification.builder()
                            .setChannelId("ddantime")
                            .setTitle(title)
                            .setBody(body)
                            .build())
                    .build());

            // data 가 null 이 아니고 비어있지 않은 경우만 추가
            if (data != null && !data.isEmpty()) {
                builder.putAllData(data);
            }

            Message message = builder.build();

            String response = FirebaseMessaging.getInstance().send(message);
            log.info("✅ Successfully sent message: {}", response);
        } catch (Exception e) {
            log.error("❌ Failed to send FCM message", e);
            throw new RuntimeException("FCM 전송 실패", e);
        }
    }
}