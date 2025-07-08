package com.ddantime.ddantime.domain.setting.notification.repository;

import com.ddantime.ddantime.domain.setting.notification.entity.NotificationSetting;
import com.ddantime.ddantime.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotificationSettingRepository extends JpaRepository<NotificationSetting, Long> {
    Optional<NotificationSetting> findByUser(User user);
}