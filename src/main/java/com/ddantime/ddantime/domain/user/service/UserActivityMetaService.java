package com.ddantime.ddantime.domain.user.service;

import com.ddantime.ddantime.common.exception.CustomException;
import com.ddantime.ddantime.common.exception.ErrorCode;
import com.ddantime.ddantime.domain.user.entity.User;
import com.ddantime.ddantime.domain.user.entity.UserActivityMeta;
import com.ddantime.ddantime.domain.user.repository.UserActivityMetaRepository;
import com.ddantime.ddantime.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserActivityMetaService {

    private final UserActivityMetaRepository userActivityMetaRepository;

    @Transactional
    public void updateLastAccessDate(User user) {
        UserActivityMeta meta = user.getActivityMeta();
        meta.setLastAccessDate(LocalDateTime.now());
        userActivityMetaRepository.save(meta);
    }

    @Transactional
    public void updateRecordDates(User user) {
        UserActivityMeta meta = user.getActivityMeta();

        if (meta.getFirstRecordDate() == null) {
            meta.setFirstRecordDate(LocalDateTime.now());
        }
        meta.setLastRecordDate(LocalDateTime.now());
        userActivityMetaRepository.save(meta);
    }

    @Transactional
    public void clearRecordDates(User user) {
        UserActivityMeta meta = user.getActivityMeta();

        meta.setFirstRecordDate(null);
        meta.setLastRecordDate(null);

        userActivityMetaRepository.save(meta); // 확실한 반영을 위해 명시적 저장
    }
}