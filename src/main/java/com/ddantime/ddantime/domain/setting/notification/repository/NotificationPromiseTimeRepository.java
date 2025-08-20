package com.ddantime.ddantime.domain.setting.notification.repository;

import com.ddantime.ddantime.domain.setting.notification.entity.NotificationPromiseTime;
import com.ddantime.ddantime.domain.setting.notification.entity.NotificationSetting;
import com.ddantime.ddantime.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotificationPromiseTimeRepository extends JpaRepository<NotificationPromiseTime, Long> {
    List<NotificationPromiseTime> findBySetting(NotificationSetting setting);
}