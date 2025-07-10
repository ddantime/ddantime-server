package com.ddantime.ddantime.domain.user.dto;

import com.ddantime.ddantime.domain.user.entity.OsType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "사용자 생성 요청 DTO")
public class UserCreateRequestDto {
    @Schema(description = "운영체제 종류 (ANDROID, IOS 등)", example = "IOS")
    private OsType os;

    @Schema(description = "운영체제 버전", example = "17.5.1")
    private String osVersion;

    @Schema(description = "앱 버전", example = "1.2.3")
    private String appVersion;

    @Schema(description = "앱 빌드 번호", example = "123")
    private String buildNumber;
}
