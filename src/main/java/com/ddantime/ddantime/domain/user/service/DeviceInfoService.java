package com.ddantime.ddantime.domain.user.service;

import com.ddantime.ddantime.domain.user.dto.DeviceInfoUpdateRequestDto;
import com.ddantime.ddantime.domain.user.dto.FcmTokenUpdateRequestDto;
import com.ddantime.ddantime.domain.user.dto.UserResponseDto;
import com.ddantime.ddantime.domain.user.entity.DeviceInfo;
import com.ddantime.ddantime.domain.user.entity.User;
import com.ddantime.ddantime.domain.user.repository.DeviceInfoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeviceInfoService {

    private final DeviceInfoRepository deviceInfoRepository;

    @Transactional
    public UserResponseDto updateDeviceInfo(User user, DeviceInfoUpdateRequestDto request) {
        DeviceInfo deviceInfo = user.getDeviceInfo();

        deviceInfo.update(
                request.getOs(),
                request.getOsVersion(),
                request.getAppVersion(),
                request.getBuildNumber()
        );

        deviceInfoRepository.save(deviceInfo);
        return UserResponseDto.of(user);
    }

    @Transactional
    public UserResponseDto updateFcmToken(User user, FcmTokenUpdateRequestDto request) {
        DeviceInfo deviceInfo = user.getDeviceInfo();
        deviceInfo.updateFcmToken(request.getFcmToken());

        deviceInfoRepository.save(deviceInfo); // 명시적으로 저장
        return UserResponseDto.of(user);
    }
}
