package com.ddantime.ddantime.domain.setting.notification.repository;

import com.ddantime.ddantime.domain.setting.notification.entity.NotificationSetting;
import com.ddantime.ddantime.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NotificationSettingRepository extends JpaRepository<NotificationSetting, Long> {
    Optional<NotificationSetting> findByUser(User user);

    @Query("""
        SELECT s.user.id
        FROM NotificationSetting s
        WHERE s.promiseNotificationEnabled = true
          AND s.promiseTimes IS EMPTY
    """)
    List<UUID> findUserIdsWithoutPromiseTimes();
}