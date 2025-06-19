package com.ddantime.ddantime.domain.ddanjit.dto;

import com.ddantime.ddantime.domain.ddanjit.entity.LocationType;
import com.ddantime.ddantime.domain.ddanjit.entity.MoodType;
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
    private LocalTime startTime;
    private int durationMin;
    private LocalTime endTime;
    private LocationType location;
    private String locationEtc;
    private MoodType mood;
    private String memo;
}
