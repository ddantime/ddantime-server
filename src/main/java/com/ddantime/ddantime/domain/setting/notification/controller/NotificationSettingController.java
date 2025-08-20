package com.ddantime.ddantime.domain.setting.notification.controller;

import com.ddantime.ddantime.common.annotation.RequestUser;
import com.ddantime.ddantime.domain.onboarding.dto.OnboardingRequestDto;
import com.ddantime.ddantime.domain.setting.notification.dto.NotificationSettingRequestDto;
import com.ddantime.ddantime.domain.setting.notification.dto.NotificationSettingResponseDto;
import com.ddantime.ddantime.domain.setting.notification.service.NotificationSettingService;
import com.ddantime.ddantime.domain.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/settings/notifications")
@RequiredArgsConstructor
@Tag(name = "알림 설정 API", description = "마이페이지 내 알림 설정 관련 API")
public class NotificationSettingController {

    private final NotificationSettingService notificationSettingService;

    @GetMapping
    @Operation(summary = "알림 설정 조회", description = "사용자의 알림 설정을 조회합니다.")
    public ResponseEntity<NotificationSettingResponseDto> getSettings(
            @RequestHeader("Ddantime-User-Id") String uuid,
            @Parameter(hidden = true) @RequestUser User user
    ) {
        NotificationSettingResponseDto responseDto = notificationSettingService.getSetting(user);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping
    @Operation(summary = "알림 설정 저장", description = "전체 알림 여부, 돌아와요/약속해요 딴타임 알림 여부 및 시간 목록을 설정합니다.")
    public ResponseEntity<NotificationSettingResponseDto> saveSettings(
            @RequestHeader("Ddantime-User-Id") String uuid,
            @Parameter(hidden = true) @RequestUser User user,
            @RequestBody @Valid NotificationSettingRequestDto requestDto
    ) {
        NotificationSettingResponseDto responseDto = notificationSettingService.saveOrUpdate(user, requestDto);
        return ResponseEntity.ok(responseDto);
    }
}