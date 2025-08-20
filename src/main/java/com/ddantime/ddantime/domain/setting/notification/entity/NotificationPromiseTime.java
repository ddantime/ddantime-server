package com.ddantime.ddantime.domain.setting.notification.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "notification_promise_times",
        uniqueConstraints = @UniqueConstraint(columnNames = {"setting_id", "minute_of_day"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationPromiseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "setting_id", nullable = false)
    private NotificationSetting setting;

    @Column(name = "minute_of_day", nullable = false)
    private Short minuteOfDay;
}
