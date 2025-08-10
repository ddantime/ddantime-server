package com.ddantime.ddantime.domain.user.repository;

import com.ddantime.ddantime.domain.user.entity.DeviceInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceInfoRepository extends JpaRepository<DeviceInfo, Long> {
}