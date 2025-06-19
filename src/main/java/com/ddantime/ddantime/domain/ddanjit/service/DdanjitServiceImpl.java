package com.ddantime.ddantime.domain.ddanjit.service;

import com.ddantime.ddantime.domain.ddanjit.dto.DdanjitCreateRequestDto;
import com.ddantime.ddantime.domain.ddanjit.dto.DdanjitResponseDto;
import com.ddantime.ddantime.domain.ddanjit.entity.Ddanjit;
import com.ddantime.ddantime.domain.ddanjit.entity.LocationType;
import com.ddantime.ddantime.domain.ddanjit.repository.DdanjitRepository;
import com.ddantime.ddantime.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class DdanjitServiceImpl implements DdanjitService {

    private final DdanjitRepository ddanjitRepository;

    @Override
    public DdanjitResponseDto create(User user, DdanjitCreateRequestDto dto) {

        Ddanjit record = Ddanjit.builder()
                .user(user)
                .date(dto.getDate())
                .activity(dto.getActivity())
                .startTime(dto.getStartTime())
                .durationMin(dto.getDurationMin())
                .location(dto.getLocation())
                .locationEtc(dto.getLocation() == LocationType.OTHER ? dto.getLocationEtc() : null)
                .mood(dto.getMood())
                .memo(dto.getMemo())
                .build();


        Ddanjit saved = ddanjitRepository.save(record);
        return toDto(saved);
    }

    public DdanjitResponseDto toDto(Ddanjit entity) {
        return DdanjitResponseDto.builder()
                .id(entity.getId())
                .date(entity.getDate())
                .activity(entity.getActivity())
                .startTime(entity.getStartTime())
                .durationMin(entity.getDurationMin())
                .endTime(entity.getEndTime())
                .location(entity.getLocation())
                .locationEtc(entity.getLocationEtc())
                .mood(entity.getMood())
                .memo(entity.getMemo())
                .build();
    }
}
