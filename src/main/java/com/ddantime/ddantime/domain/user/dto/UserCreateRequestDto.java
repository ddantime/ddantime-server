package com.ddantime.ddantime.domain.user.dto;

import com.ddantime.ddantime.domain.user.entity.OsType;
import lombok.Getter;

@Getter
public class UserCreateRequestDto {
        private OsType os;
        private String osVersion;
        private String appVersion;
        private String buildNumber;
}
