package com.ddantime.ddantime.domain.setting.notification.service;

import com.ddantime.ddantime.domain.setting.notification.dto.NotificationSettingRequestDto;
import com.ddantime.ddantime.domain.setting.notification.dto.NotificationSettingResponseDto;
import com.ddantime.ddantime.domain.setting.notification.entity.NotificationSetting;
import com.ddantime.ddantime.domain.setting.notification.repository.NotificationSettingRepository;
import com.ddantime.ddantime.domain.user.entity.User;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class NotificationSettingService {

    private final NotificationSettingRepository notificationSettingRepository;

    @Transactional(readOnly = true)
    public NotificationSettingResponseDto getSetting(User user) {
        return notificationSettingRepository.findByUser(user)
                .map(this::toDto)
                .orElseGet(() -> NotificationSettingResponseDto.builder()
                        .allNotificationsEnabled(true)
                        .comebackNotificationEnabled(true)
                        .promiseNotificationEnabled(true)
                        .promiseTimes(Collections.emptyList())
                        .build());
    }

    @Transactional
    public NotificationSettingResponseDto saveOrUpdate(User user, NotificationSettingRequestDto dto) {
        dto.applySettingsHelper();

        NotificationSetting setting = notificationSettingRepository.findByUser(user)
                .orElseGet(() -> NotificationSetting.builder().user(user).build());

        setting.setAllNotificationsEnabled(dto.isAllNotificationsEnabled());
        setting.setComebackNotificationEnabled(dto.isComebackNotificationEnabled());
        setting.setPromiseNotificationEnabled(dto.isPromiseNotificationEnabled());
        setting.setPromiseTimes(dto.getPromiseTimes());

        NotificationSetting saved = notificationSettingRepository.save(setting);
        return toDto(saved);
    }

    private NotificationSettingResponseDto toDto(NotificationSetting setting) {
        return NotificationSettingResponseDto.builder()
                .allNotificationsEnabled(setting.isAllNotificationsEnabled())
                .comebackNotificationEnabled(setting.isComebackNotificationEnabled())
                .promiseNotificationEnabled(setting.isPromiseNotificationEnabled())
                .promiseTimes(setting.getPromiseTimes())
                .build();
    }
}