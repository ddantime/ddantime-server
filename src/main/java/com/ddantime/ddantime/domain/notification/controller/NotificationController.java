package com.ddantime.ddantime.domain.notification.controller;

import com.ddantime.ddantime.domain.notification.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Cloud Scheduler에서 호출하는 발송 엔드포인트
 */
@RestController
@RequestMapping("/internal/notifications")
@RequiredArgsConstructor
@Tag(name = "Push Message Test API", description = "마이페이지 내 알림 설정 관련 API")
public class NotificationController {

    private final NotificationService notificationService;


    /**
     * 마지막 기록한 시점부터 5일 이상 기록 없는 경우 알림 발송
     * 3일간 앱에 접속 유무에 따라 분기
     */
    @PostMapping("/comeback")
    @Operation(summary = "🛠️FCM 테스트 - 돌아와요")
    public void comeback() {
        notificationService.sendComeBackNotifications();
    }

    /**
     * 마지막 기록한 시점부터 5일 이상 기록 없는 경우 알림 발송
     * 3일간 앱에 접속 유무에 따라 분기
     */
    @PostMapping("/promise")
    @Operation(summary = "🛠️FCM 테스트 - 약속해요")
    public void promise() {
        notificationService.sendPromiseNotifications();
    }
}

