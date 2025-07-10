package com.ddantime.ddantime.domain.ddanjit.service;

import com.ddantime.ddantime.domain.ddanjit.dto.DdanjitRequestDto;
import com.ddantime.ddantime.domain.ddanjit.dto.DdanjitResponseDto;
import com.ddantime.ddantime.domain.user.entity.User;

import java.time.LocalDate;
import java.util.List;

public interface DdanjitService {
    DdanjitResponseDto create(User user, DdanjitRequestDto requestDto);
    List<DdanjitResponseDto> getRecordsByDate(User user, LocalDate date);
    List<LocalDate> getDatesByUser(User user);
    void update(Long id, DdanjitRequestDto requestDto, User user);
    void delete(Long id, User user);
    void deleteAllByUser(User user);
}
