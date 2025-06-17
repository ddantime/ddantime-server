package com.ddantime.ddantime.domain.onboarding.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class OnboardingResponseDto {
    private String reason;
    private String timing;
    private String dayState;
    private List<String> messages;
}