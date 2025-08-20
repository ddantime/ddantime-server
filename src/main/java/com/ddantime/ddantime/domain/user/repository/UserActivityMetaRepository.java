package com.ddantime.ddantime.domain.user.repository;

import com.ddantime.ddantime.domain.user.entity.User;
import com.ddantime.ddantime.domain.user.entity.UserActivityMeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface UserActivityMetaRepository extends JpaRepository<UserActivityMeta, Long> {
    UserActivityMeta findByUser(User user);

    // XXX 리팩토링 필요. UserActivityMetaRepository 에서 join이 필요한지 고민.
    @Query(value = """
        SELECT m.*
        FROM user_activity_meta m
        JOIN notification_settings ns ON ns.user_id = m.user_id
        WHERE ns.all_notifications_enabled
          AND ns.comeback_notification_enabled
          AND m.last_record_date IS NOT NULL;
        """, nativeQuery = true)
    List<UserActivityMeta> findComeBackTargets();
}
//AND m.last_record_date <= now() - INTERVAL '0 days';
