package com.ddantime.ddantime.domain.user.repository;

import com.ddantime.ddantime.domain.user.entity.User;
import com.ddantime.ddantime.domain.user.entity.UserActivityMeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface UserActivityMetaRepository extends JpaRepository<UserActivityMeta, Long> {
    UserActivityMeta findByUser(User user);

    // XXX 리팩토링 필요. UserActivityMetaRepository 에서 join이 필요한지 고민.
    @Query("""
        SELECT m
        FROM UserActivityMeta m
        JOIN m.user u
        JOIN NotificationSetting ns ON ns.user = u
        WHERE ns.comebackNotificationEnabled = true
          AND m.lastRecordDate IS NOT NULL
          AND m.lastRecordDate <= :threshold
        """)
    List<UserActivityMeta> findComeBackTargets(@Param("threshold") LocalDateTime threshold);

}