package com.ddantime.ddantime.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDeviceUpdateRequestDto {
    private String osVersion;
    private String appVersion;
    private String buildNumber;
}
