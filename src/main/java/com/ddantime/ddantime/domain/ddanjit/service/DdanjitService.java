package com.ddantime.ddantime.domain.ddanjit.service;

import com.ddantime.ddantime.domain.ddanjit.dto.DdanjitCreateRequestDto;
import com.ddantime.ddantime.domain.ddanjit.dto.DdanjitResponseDto;
import com.ddantime.ddantime.domain.user.entity.User;

import java.time.LocalDate;
import java.util.List;

public interface DdanjitService {
    DdanjitResponseDto create(User user, DdanjitCreateRequestDto requestDto);
    List<DdanjitResponseDto> getRecordsByDate(User user, LocalDate date);
}
