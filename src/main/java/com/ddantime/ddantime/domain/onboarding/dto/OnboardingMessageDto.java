package com.ddantime.ddantime.domain.onboarding.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OnboardingMessageDto {
    private String reason;
    private String timing;

    @JsonProperty("day_state")
    private String dayState;
    private List<String> message;
}
