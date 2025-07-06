package com.ddantime.ddantime.domain.ddanjit.dto;

import com.ddantime.ddantime.domain.ddanjit.entity.LocationType;
import com.ddantime.ddantime.domain.ddanjit.entity.MoodType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class DdanjitRequestDto {

    @Schema(description = "딴짓 기록 날짜", example = "2025-06-17")
    @NotNull(message = "날짜는 필수입니다.")
    private LocalDate date;

    @Schema(description = "무엇을 했는지", example = "유튜브 보기")
    @NotBlank(message = "값이 비어 있을 수 없습니다.")
    @Size(min = 1, max = 20, message = "최대 20자까지 가능합니다.")
    private String activity;

    @Schema(description = "시작 시간", example = "12:30")
    @NotNull(message = "시작 시간은 필수입니다.")
    private LocalTime startTime;

    @Schema(description = "지속 시간 (분 단위)", example = "30")
    @Min(value = 1, message = "최소 1분 이상이어야 합니다.")
    @Max(value = 240, message = "최대 240분(4시간)을 초과할 수 없습니다.")
    private int durationMin;

    @Schema(description = "장소 (HOME, WORK, SCHOOL, RESTAURANT, CAFE, STREET, OTHER)", example = "CAFE")
    private LocationType location;

    @AssertTrue(message = "장소가 기타(OTHER)인 경우에만 locationEtc를 입력해야 합니다.")
    @Schema(hidden = true)
    public boolean isValidLocationEtc() {
        if (location == null) return true; // Enum 역직렬화 실패 여부와 별개로 무시

        boolean isEtc = location == LocationType.OTHER;

        // 기타인 경우에는 값이 있어야 함
        if (isEtc) {
            return locationEtc != null && !locationEtc.isBlank();
        }

        // 기타가 아닌 경우에는 값이 없어야 함
        return locationEtc == null || locationEtc.isBlank();
    }
    @Schema(description = "장소가 'OTHER' 경우 직접 입력", example = "버스 안")
    private String locationEtc;

    @Schema(description = "기분 ( HAPPY, SAD, ANGRY, NEUTRAL, PANIC, CURIOUS )", example = "HAPPY")
    @NotNull(message = "기분은 필수입니다.")
    private MoodType mood;

    @Schema(description = "메모 (최대 200자)", example = "오늘은 카페에서 힐링했다.")
    @Size(max = 200, message = "최대 200자까지 가능합니다.")
    private String memo;
}
