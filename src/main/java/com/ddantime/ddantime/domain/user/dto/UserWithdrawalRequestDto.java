package com.ddantime.ddantime.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserWithdrawalRequestDto {

    @NotBlank(message = "탈퇴 사유는 필수입니다.")
    private String reasonType;

    private String reasonDetail; // 선택 입력
}
