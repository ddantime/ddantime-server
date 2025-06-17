package com.ddantime.ddantime.domain.onboarding.controller;

import com.ddantime.ddantime.common.annotation.RequestUser;
import com.ddantime.ddantime.domain.onboarding.dto.OnboardingMessageDto;
import com.ddantime.ddantime.domain.onboarding.dto.OnboardingRequestDto;
import com.ddantime.ddantime.domain.onboarding.dto.OnboardingResponseDto;
import com.ddantime.ddantime.domain.onboarding.service.OnboardingService;
import com.ddantime.ddantime.domain.user.entity.User;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Onboarding", description = "온보딩 API")
@RestController
@RequestMapping("/api/v1/onboarding")
@RequiredArgsConstructor
public class OnboardingController {
    private final OnboardingService onboardingService;

    @PostMapping
    public ResponseEntity<OnboardingResponseDto> getMessage(
            @RequestHeader("Ddantime-User-Id") String uuid,
            @Parameter(hidden = true) @RequestUser User user,
            @RequestBody OnboardingRequestDto request
    ) {
        OnboardingResponseDto response = onboardingService.processOnboarding(
                user, request.getQ1(), request.getQ2(), request.getQ3()
        );

        return ResponseEntity.ok(response);
    }
}
