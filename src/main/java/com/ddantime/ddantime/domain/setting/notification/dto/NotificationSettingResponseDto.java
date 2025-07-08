package com.ddantime.ddantime.domain.setting.notification.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class NotificationSettingResponseDto {
    @Schema(description = "전체 알림 허용 여부", example = "true")
    private boolean allNotificationsEnabled;

    @Schema(description = "돌아와요 딴타임 알림 허용 여부", example = "true")
    private boolean comebackNotificationEnabled;

    @Schema(description = "약속해요 딴타임 알림 허용 여부", example = "true")
    private boolean promiseNotificationEnabled;

    @Schema(description = "약속해요 딴타임 알림 시간 리스트", example = "[\"08:00\", \"12:00\"]")
    private List<String> promiseTimes;
}
