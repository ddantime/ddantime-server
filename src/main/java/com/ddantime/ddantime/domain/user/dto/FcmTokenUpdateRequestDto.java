package com.ddantime.ddantime.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "FCM 토큰 업데이트 요청 DTO")
public class FcmTokenUpdateRequestDto {

    @NotBlank
    @Schema(description = "FCM 토큰", example = "fcm_token_abc123")
    private String fcmToken;
}
