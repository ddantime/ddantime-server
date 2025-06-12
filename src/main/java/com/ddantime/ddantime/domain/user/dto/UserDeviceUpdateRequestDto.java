package com.ddantime.ddantime.domain.user.dto;

import lombok.Getter;

@Getter
public class UserDeviceUpdateRequestDto {
    private String osVersion;
    private String appVersion;
    private String buildNumber;
}
