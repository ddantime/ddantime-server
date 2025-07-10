package com.ddantime.ddantime.domain.ddanjit.service;

import com.ddantime.ddantime.common.exception.CustomException;
import com.ddantime.ddantime.common.exception.ErrorCode;
import com.ddantime.ddantime.domain.ddanjit.dto.DdanjitRequestDto;
import com.ddantime.ddantime.domain.ddanjit.dto.DdanjitResponseDto;
import com.ddantime.ddantime.domain.ddanjit.entity.Ddanjit;
import com.ddantime.ddantime.domain.ddanjit.entity.LocationType;
import com.ddantime.ddantime.domain.ddanjit.repository.DdanjitRepository;
import com.ddantime.ddantime.domain.user.entity.User;
import com.ddantime.ddantime.domain.user.entity.UserActivityMeta;
import com.ddantime.ddantime.domain.user.service.UserActivityMetaService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DdanjitServiceImpl implements DdanjitService {

    private final DdanjitRepository ddanjitRepository;
    private final UserActivityMetaService userActivityMetaService;

    @Override
    @Transactional
    public DdanjitResponseDto create(User user, DdanjitRequestDto dto) {

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

        userActivityMetaService.updateRecordDates(saved.getUser());

        return toDto(saved);
    }

    @Override
    public List<DdanjitResponseDto> getRecordsByDate(User user, LocalDate date) {
        List<Ddanjit> records = ddanjitRepository.findAllByUserAndDateOrderByStartTimeDescEndTimeDescCreatedAtDesc(user, date);
        return records.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<LocalDate> getDatesByUser(User user) {
        return ddanjitRepository.findDistinctDatesByUser(user);
    }

    @Override
    @Transactional
    public void update(Long id, DdanjitRequestDto requestDto, User user) {
        Ddanjit ddanjit = ddanjitRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new CustomException(ErrorCode.DDANJIT_NOT_FOUND));

        ddanjit.update(requestDto);
    }

    @Override
    public void delete(Long id, User user) {
        Ddanjit ddanjit = ddanjitRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new CustomException(ErrorCode.DDANJIT_NOT_FOUND));

        ddanjitRepository.delete(ddanjit);
    }

    @Override
    @Transactional
    public void deleteAllByUser(User user) {
        ddanjitRepository.deleteByUser(user);
        // 활동 메타데이터 초기화
        userActivityMetaService.clearRecordDates(user);
    }

    public DdanjitResponseDto toDto(Ddanjit entity) {
        return DdanjitResponseDto.builder()
                .id(entity.getId())
                .date(entity.getDate())
                .activity(entity.getActivity())
                .startTime(entity.getStartTime())
                .durationMin(entity.getDurationMin())
                .location(entity.getLocation())
                .locationEtc(entity.getLocationEtc())
                .mood(entity.getMood())
                .memo(entity.getMemo())
                .build();
    }
}
