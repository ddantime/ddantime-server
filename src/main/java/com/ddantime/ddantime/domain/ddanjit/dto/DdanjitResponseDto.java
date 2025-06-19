package com.ddantime.ddantime.domain.ddanjit.dto;

import com.ddantime.ddantime.domain.ddanjit.entity.LocationType;
import com.ddantime.ddantime.domain.ddanjit.entity.MoodType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
public class DdanjitResponseDto {
    private Long id;
    private LocalDate date;
    private String activity;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;
    private int durationMin;
    private LocationType location;
    private String locationEtc;
    private MoodType mood;
    private String memo;
}
