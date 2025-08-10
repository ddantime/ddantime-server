package com.ddantime.ddantime.domain.user.dto;


import com.ddantime.ddantime.domain.user.entity.DeviceInfo;
import com.ddantime.ddantime.domain.user.entity.OsType;
import com.ddantime.ddantime.domain.user.entity.User;
import com.ddantime.ddantime.domain.user.entity.UserActivityMeta;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@Schema(description = "사용자 정보 응답 DTO")
public class UserResponseDto {

    @Schema(description = "사용자 고유 UUID")
    private String uuid;

    @Schema(description = "사용자 닉네임", example = "딴타임유저")
    private String nickname;

    @Schema(description = "온보딩 완료 여부", example = "true")
    private Boolean onboardingCompleted;

    @Schema(description = "운영체제 종류", example = "ANDROID")
    private OsType os;

    @Schema(description = "운영체제 버전", example = "14.2.1")
    private String osVersion;

    @Schema(description = "앱 버전", example = "1.0.0")
    private String appVersion;

    @Schema(description = "앱 빌드 번호", example = "100")
    private String buildNumber;

    @Schema(description = "FCM 토큰", example = "d32Fcm45Token8x...")
    private String fcmToken;

    @Schema(description = "최초 딴짓 기록일", example = "2024-06-01T14:22:00")
    private LocalDateTime firstRecordDate;

    @Schema(description = "마지막 접속일", example = "2024-06-20T10:45:00")
    private LocalDateTime lastAccessDate;

    @Schema(description = "마지막 딴짓 기록일", example = "2024-06-22T19:12:00")
    private LocalDateTime lastRecordDate;

    public static UserResponseDto of(User user) {
        UserActivityMeta meta = user.getActivityMeta();
        DeviceInfo deviceInfo = user.getDeviceInfo();

        return UserResponseDto.builder()
                .uuid(user.getId().toString())
                .nickname(user.getNickname())
                .onboardingCompleted(user.isOnboardingCompleted())
                .os(deviceInfo.getOs())
                .osVersion(deviceInfo.getOsVersion())
                .appVersion(deviceInfo.getAppVersion())
                .buildNumber(deviceInfo.getBuildNumber())
                .fcmToken(deviceInfo.getFcmToken())
                .lastAccessDate(meta.getLastAccessDate())
                .firstRecordDate(meta.getFirstRecordDate())
                .lastRecordDate(meta.getLastRecordDate())
                .build();
    }
}