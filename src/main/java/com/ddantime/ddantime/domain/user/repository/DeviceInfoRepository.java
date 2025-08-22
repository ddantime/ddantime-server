package com.ddantime.ddantime.domain.user.repository;

import com.ddantime.ddantime.domain.user.entity.DeviceInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface DeviceInfoRepository extends JpaRepository<DeviceInfo, Long> {
    @Query("""
        SELECT d.fcmToken
        FROM DeviceInfo d
        WHERE d.user.id IN :userIds
          AND d.fcmToken IS NOT NULL
    """)
    List<String> findFcmTokensByUserIds(@Param("userIds") List<UUID> userIds);
}