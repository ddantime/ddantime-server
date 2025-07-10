package com.ddantime.ddantime.domain.user.repository;

import com.ddantime.ddantime.domain.user.entity.User;
import com.ddantime.ddantime.domain.user.entity.UserActivityMeta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserActivityMetaRepository extends JpaRepository<UserActivityMeta, Long> {
    UserActivityMeta findByUser(User user);
}

