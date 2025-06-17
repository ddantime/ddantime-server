package com.ddantime.ddantime.domain.onboarding.repository;

import com.ddantime.ddantime.domain.onboarding.entity.Onboarding;
import com.ddantime.ddantime.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OnboardingRepository extends JpaRepository<Onboarding, Long> {
    boolean existsByUser(User user);
}