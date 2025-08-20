package com.ddantime.ddantime.domain.setting.notification.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class NotificationSettingRequestDto {

    @Schema(description = "전체 알림 허용 여부 (true 설정 시 돌아와요/약속해요 알림도 함께 활성화됨)", example = "true")
    private boolean allNotificationsEnabled;

    @Schema(description = "5일 이상 딴짓 기록이 없을 때 알림 허용 여부", example = "true")
    private boolean comebackNotificationEnabled;

    @Schema(description = "하루 최대 1회, 설정된 시간에 알림 허용 여부", example = "false")
    private boolean promiseNotificationEnabled;

    @Schema(
            description = "약속해요 딴타임 알림 시간 리스트 (최대 5개). " +
                    "빈 배열일 수 있으며, 각 값은 HH:mm 형식의 24시간제 문자열이어야 합니다.",
            example = "[\"08:00\", \"12:00\", \"20:00\"]"
    )
    @Size(max = 5, message = "알림 시간은 최대 5개까지 설정할 수 있습니다.")
    private List<@Pattern(regexp = "^([01]\\d|2[0-3]):[0-5]\\d$", message = "시간 형식은 HH:mm이어야 합니다.") String> promiseTimes;

}