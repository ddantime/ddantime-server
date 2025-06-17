package com.ddantime.ddantime.domain.onboarding.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class OnboardingRequestDto {

    @Schema(description = "앱을 사용하려는 이유", example = "유튜브, 넷플릭스 보기")
    private String q1;

    @Schema(description = "사용 시점", example = "일/공부하다 집중 안 될 때")
    private String q2;

    @Schema(description = "오늘 하루 상태", example = "바쁘고 정신없어요")
    private String q3;
}
