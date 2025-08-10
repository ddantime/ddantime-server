package com.ddantime.ddantime.domain.user.entity;

import com.ddantime.ddantime.domain.user.dto.UserCreateRequestDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "device_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 사용자 1명당 1개의 DeviceInfo만 허용 (현재 기준)
     * 추후 다중 디바이스 허용 시 unique 제약을 (user_id + device_id)로 확장 가능
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    /**
     * FCM 토큰
     */
    @Column(name = "fcm_token", nullable = false, columnDefinition = "TEXT")
    private String fcmToken;

    /**
     * FCM 토큰이 마지막으로 갱신된 시각 (값이 변경된 경우에만 갱신)
     */
    @Column(name = "fcm_token_last_updated_at")
    private LocalDateTime fcmTokenLastUpdatedAt;

    /**
     * 디바이스 정보
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "os", nullable = false)
    private OsType os;

    @Column(name = "os_version", length = 20)
    private String osVersion;

    @Column(name = "app_version", length = 20)
    private String appVersion;

    @Column(name = "build_number", length = 20)
    private String buildNumber;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public void update(OsType os, String osVersion, String appVersion, String buildNumber) {
        this.os = os;
        this.osVersion = osVersion;
        this.appVersion = appVersion;
        this.buildNumber = buildNumber;
    }

    public void updateFcmToken(String newFcmToken) {
        if (this.fcmToken == null || !this.fcmToken.equals(newFcmToken)) {
            this.fcmToken = newFcmToken;
            this.fcmTokenLastUpdatedAt = LocalDateTime.now();
        }
    }

    public static DeviceInfo of(User user, UserCreateRequestDto dto) {
        return DeviceInfo.builder()
                .user(user)
                .fcmToken(dto.getFcmToken())
                .fcmTokenLastUpdatedAt(LocalDateTime.now())
                .os(dto.getOs())
                .osVersion(dto.getOsVersion())
                .appVersion(dto.getAppVersion())
                .buildNumber(dto.getBuildNumber())
                .build();
    }
}