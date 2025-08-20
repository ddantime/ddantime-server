package com.ddantime.ddantime.domain.setting.notification.service;

import com.ddantime.ddantime.domain.setting.notification.dto.NotificationSettingRequestDto;
import com.ddantime.ddantime.domain.setting.notification.dto.NotificationSettingResponseDto;
import com.ddantime.ddantime.domain.setting.notification.entity.NotificationPromiseTime;
import com.ddantime.ddantime.domain.setting.notification.entity.NotificationSetting;
import com.ddantime.ddantime.domain.setting.notification.repository.NotificationPromiseTimeRepository;
import com.ddantime.ddantime.domain.setting.notification.repository.NotificationSettingRepository;
import com.ddantime.ddantime.domain.user.entity.User;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationSettingService {

    private final NotificationSettingRepository notificationSettingRepository;
    private final NotificationPromiseTimeRepository notificationPromiseTimeRepository;

    @Transactional(readOnly = true)
    public NotificationSettingResponseDto getSetting(User user) {
        return notificationSettingRepository.findByUser(user)
                .map(setting -> {
                    List<String> promiseTimes = setting.getPromiseTimes().stream()
                            .map(t -> toHHMM(t.getMinuteOfDay()))
                            .toList();
                    return toDto(setting, promiseTimes);
                })
                .orElseGet(() -> NotificationSettingResponseDto.builder()
                        .allNotificationsEnabled(true)
                        .comebackNotificationEnabled(true)
                        .promiseNotificationEnabled(true)
                        .promiseTimes(Collections.emptyList())
                        .build());
    }

    @Transactional
    public NotificationSettingResponseDto saveOrUpdate(User user, NotificationSettingRequestDto dto) {
        NotificationSetting setting = notificationSettingRepository.findByUser(user)
                .orElseGet(() -> NotificationSetting.builder().user(user).build());

        // 비즈니스 규칙 적용
        boolean all = dto.isAllNotificationsEnabled();
        boolean comeback = dto.isComebackNotificationEnabled();
        boolean promise = dto.isPromiseNotificationEnabled();
        if (all) {
            comeback = true;
            promise = true;
        }

        setting.setAllNotificationsEnabled(all);
        setting.setComebackNotificationEnabled(comeback);
        setting.setPromiseNotificationEnabled(promise);

        // 기존 시간 삭제
        setting.getPromiseTimes().clear();

        if (dto.getPromiseTimes() != null) {
            dto.getPromiseTimes().stream()
                    .distinct()   // 중복 제거
                    .mapToInt(this::toMinutes)
                    .forEach(minuteOfDay -> {
                        NotificationPromiseTime e = new NotificationPromiseTime();
                        e.setSetting(setting);
                        e.setMinuteOfDay((short) minuteOfDay);
                        setting.getPromiseTimes().add(e);
                    });
        }
        NotificationSetting saved = notificationSettingRepository.save(setting);

        List<String> promiseTimes = saved.getPromiseTimes().stream()
                .map(t -> toHHMM(t.getMinuteOfDay()))
                .toList();

        return toDto(saved, promiseTimes);
    }

    private String toHHMM(int minutes) {
        int h = minutes / 60;
        int m = minutes % 60;
        return String.format("%02d:%02d", h, m);
    }

    private int toMinutes(String hhmm) {
        String[] p = hhmm.split(":");
        return Integer.parseInt(p[0]) * 60 + Integer.parseInt(p[1]);
    }

    private NotificationSettingResponseDto toDto(NotificationSetting setting, List<String> promiseTimes) {
        return NotificationSettingResponseDto.builder()
                .allNotificationsEnabled(setting.isAllNotificationsEnabled())
                .comebackNotificationEnabled(setting.isComebackNotificationEnabled())
                .promiseNotificationEnabled(setting.isPromiseNotificationEnabled())
                .promiseTimes(promiseTimes)
                .build();
    }
}