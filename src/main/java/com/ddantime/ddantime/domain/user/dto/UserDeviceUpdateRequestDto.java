package com.ddantime.ddantime.domain.user.dto;

import com.ddantime.ddantime.domain.user.entity.OsType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "디바이스 정보 변경 요청 DTO")
public class UserDeviceUpdateRequestDto {
    @Schema(description = "운영체제 버전", example = "17.5.1")
    private String osVersion;

    @Schema(description = "앱 버전", example = "1.2.3")
    private String appVersion;

    @Schema(description = "앱 빌드 번호", example = "123")
    private String buildNumber;
}
