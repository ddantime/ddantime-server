package com.ddantime.ddantime.domain.user.dto;

import com.ddantime.ddantime.domain.user.entity.OsType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "사용자 생성 요청 DTO")
public class UserCreateRequestDto {
    @NotNull(message = "운영체제 종류는 필수입니다.")
    @Schema(description = "운영체제 종류 (ANDROID, IOS 등)", example = "IOS")
    private OsType os;

    @NotBlank(message = "운영체제 버전은 필수입니다.")
    @Schema(description = "운영체제 버전", example = "17.5.1")
    private String osVersion;

    @NotBlank(message = "앱 버전은 필수입니다.")
    @Schema(description = "앱 버전", example = "1.2.3")
    private String appVersion;

    @NotBlank(message = "빌드 번호는 필수입니다.")
    @Schema(description = "앱 빌드 번호", example = "123")
    private String buildNumber;

    @NotBlank(message = "FCM 토큰은 필수입니다.")
    @Schema(description = "알림 발송을 위한 FCM 토큰", example = "fcm_token_example")
    private String fcmToken;
}
