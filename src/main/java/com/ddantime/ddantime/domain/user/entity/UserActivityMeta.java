package com.ddantime.ddantime.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserActivityMeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private LocalDateTime lastAccessDate;
    private LocalDateTime firstRecordDate;
    private LocalDateTime lastRecordDate;

    public static UserActivityMeta of(User user) {
        return UserActivityMeta.builder()
                .user(user)
                .lastAccessDate(LocalDateTime.now())
                .firstRecordDate(null)
                .lastRecordDate(null)
                .build();
    }
}
