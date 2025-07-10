package com.ddantime.ddantime.domain.user.dto;


import com.ddantime.ddantime.domain.user.entity.OsType;
import com.ddantime.ddantime.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class UserResponseDto {
    private String uuid;
    private String nickname;
    private Boolean onboardingCompleted;
    private OsType os;
    private String osVersion;
    private String appVersion;
    private String buildNumber;
    private LocalDateTime firstRecordDate;
    private LocalDateTime lastAccessDate;
    private LocalDateTime lastRecordDate;
}