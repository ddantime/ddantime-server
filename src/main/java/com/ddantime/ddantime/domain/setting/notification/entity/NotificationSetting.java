package com.ddantime.ddantime.domain.setting.notification.entity;

import com.ddantime.ddantime.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "notification_settings")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class NotificationSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    private boolean allNotificationsEnabled;    // 전체 알림 ON/OFF
    private boolean comebackNotificationEnabled; // 돌아와요 딴타임 알림
    private boolean promiseNotificationEnabled;  // 약속해요 딴타임 알림

    @OneToMany(mappedBy = "setting", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<NotificationPromiseTime> promiseTimes = new ArrayList<>();
}
