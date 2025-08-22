package com.ddantime.ddantime.domain.setting.notification.repository;

import com.ddantime.ddantime.domain.setting.notification.entity.NotificationPromiseTime;
import com.ddantime.ddantime.domain.setting.notification.entity.NotificationSetting;
import com.ddantime.ddantime.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NotificationPromiseTimeRepository extends JpaRepository<NotificationPromiseTime, Long> {
    @Query("""
    SELECT s.user.id
    FROM NotificationSetting s
    JOIN s.promiseTimes t
    WHERE t.minuteOfDay = :minuteOfDay
      AND s.promiseNotificationEnabled = true
    """)
    List<UUID> findUserIdsForMinute(@Param("minuteOfDay") short minuteOfDay);
}