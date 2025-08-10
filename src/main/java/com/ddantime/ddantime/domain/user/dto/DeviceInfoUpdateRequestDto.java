package com.ddantime.ddantime.domain.user.dto;

import com.ddantime.ddantime.domain.user.entity.OsType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "기기 정보 업데이트 요청 DTO")
public class DeviceInfoUpdateRequestDto {

    @NotNull
    @Schema(description = "운영체제 종류", example = "ANDROID")
    private OsType os;

    @Schema(description = "운영체제 버전", example = "14.0")
    private String osVersion;

    @Schema(description = "앱 버전", example = "1.0.0")
    private String appVersion;

    @Schema(description = "앱 빌드 번호", example = "100")
    private String buildNumber;
}