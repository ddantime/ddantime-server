package com.ddantime.ddantime.domain.onboarding.entity;

import com.ddantime.ddantime.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_onboarding") // 실제 테이블 이름을 명확하게 지정 (권장)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA에서 기본 생성자 보호 레벨 설정 (관례)
@AllArgsConstructor
@Builder
public class Onboarding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false)
    private String reason;

    @Column(nullable = false)
    private String timing;

    @Column(name = "day_state", nullable = false)
    private String dayState;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
