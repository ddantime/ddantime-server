package com.ddantime.ddantime.domain.user.repository;

import com.ddantime.ddantime.domain.user.entity.UserWithdrawalReason;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserWithdrawalReasonRepository extends JpaRepository<UserWithdrawalReason, Long> {
}
