package com.ddantime.ddantime.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserWithdrawalRequestDto {

    @NotBlank(message = "탈퇴 사유는 필수입니다.")
    @Schema(description = "탈퇴 사유", example = "오류가 많아요")
    private String reasonType;

    @Schema(description = "탈퇴 사유 선택입력", example = "그냥")
    @Size(max = 100, message = "최대 100자까지 가능합니다.")
    private String reasonDetail; // 선택 입력
}
