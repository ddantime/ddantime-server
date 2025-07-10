package com.ddantime.ddantime.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
@Schema(description = "닉네임 변경 요청 DTO")
public class UserNicknameUpdateRequestDto {

    @Schema(description = "닉네임", example = "딴타임유저")
    @NotBlank(message = "닉네임은 비어 있을 수 없습니다.")
    @Size(min=1, max = 8, message = "닉네임은 최대 8자까지 가능합니다.")
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]+$", message = "닉네임은 한글, 영문, 숫자만 사용할 수 있습니다.")
    private String nickname;
}