package com.ddantime.ddantime.domain.onboarding.service;

import com.ddantime.ddantime.common.exception.CustomException;
import com.ddantime.ddantime.common.exception.ErrorCode;
import com.ddantime.ddantime.domain.onboarding.dto.OnboardingMessageDto;
import com.ddantime.ddantime.domain.onboarding.dto.OnboardingResponseDto;
import com.ddantime.ddantime.domain.onboarding.entity.Onboarding;
import com.ddantime.ddantime.domain.onboarding.repository.OnboardingRepository;
import com.ddantime.ddantime.domain.user.entity.User;
import com.ddantime.ddantime.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OnboardingService {

    private final OnboardingMessageService messageService;
    private final OnboardingRepository onboardingRepository;
    private final UserRepository userRepository;

    public OnboardingResponseDto processOnboarding(User user, String q1, String q2, String q3) {
        if (onboardingRepository.existsByUser(user)) {
            throw new CustomException(ErrorCode.ONBOARDING_ALREADY_EXISTS);
        }

        OnboardingMessageDto message = messageService.getMessage(q1, q2, q3);

        onboardingRepository.save(
                Onboarding.builder()
                        .user(user)
                        .reason(q1)
                        .timing(q2)
                        .dayState(q3)
                        .build()
        );

        user.setOnboardingCompleted(true);
        userRepository.save(user);

        return OnboardingResponseDto.builder()
                .reason(message.getReason())
                .timing(message.getTiming())
                .dayState(message.getDayState())
                .messages(message.getMessage())
                .build();
    }
}