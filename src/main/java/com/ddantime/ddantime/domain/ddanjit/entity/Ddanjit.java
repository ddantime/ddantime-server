package com.ddantime.ddantime.domain.ddanjit.entity;

import com.ddantime.ddantime.common.crypto.EncryptStringConverter;
import com.ddantime.ddantime.domain.ddanjit.dto.DdanjitRequestDto;
import com.ddantime.ddantime.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "ddanjit_records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ddanjit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    @Convert(converter = EncryptStringConverter.class)
    private String activity;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "duration_min", nullable = false)
    private int durationMin;

    // 종료 시간 (등록 시 계산하여 저장)
    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(name = "location", length = 20)
    @Enumerated(EnumType.STRING)
    private LocationType location;

    // 기타 장소 (locationType = OTHER인 경우)
    @Column(name = "location_etc")
    @Convert(converter = EncryptStringConverter.class)
    private String locationEtc;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MoodType mood;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = EncryptStringConverter.class)
    private String memo;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    @PreUpdate
    public void calculateEndTime() {
        if (startTime != null && durationMin > 0) {
            this.endTime = startTime.plusMinutes(durationMin);
        }
    }

    public void update(DdanjitRequestDto dto) {
        this.date = dto.getDate();
        this.activity = dto.getActivity();
        this.startTime = dto.getStartTime();
        this.durationMin = dto.getDurationMin();
        this.location = dto.getLocation();
        this.locationEtc = dto.getLocation() == LocationType.OTHER ? dto.getLocationEtc() : null;
        this.mood = dto.getMood();
        this.memo = dto.getMemo();
    }
}
