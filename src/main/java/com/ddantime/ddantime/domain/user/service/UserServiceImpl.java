package com.ddantime.ddantime.domain.user.service;

import com.ddantime.ddantime.common.exception.CustomException;
import com.ddantime.ddantime.common.exception.ErrorCode;
import com.ddantime.ddantime.domain.user.dto.UserCreateRequestDto;
import com.ddantime.ddantime.domain.user.dto.UserDeviceUpdateRequestDto;
import com.ddantime.ddantime.domain.user.dto.UserNicknameUpdateRequestDto;
import com.ddantime.ddantime.domain.user.dto.UserResponseDto;
import com.ddantime.ddantime.domain.user.entity.User;
import com.ddantime.ddantime.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponseDto createUser(UserCreateRequestDto requestDto) {
        User user = User.builder()
                .os(requestDto.getOs())
                .osVersion(requestDto.getOsVersion())
                .appVersion(requestDto.getAppVersion())
                .buildNumber(requestDto.getBuildNumber())
                .build();

        userRepository.save(user);
        return toDto(user);
    }

    @Override
    public UserResponseDto getUserByUuid(String uuid) {
        User user = userRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        return toDto(user);
    }

    @Override
    public UserResponseDto updateDeviceInfo(String uuid, UserDeviceUpdateRequestDto requestDto) {
        User user = userRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        user.setOsVersion(requestDto.getOsVersion());
        user.setAppVersion(requestDto.getAppVersion());
        user.setBuildNumber(requestDto.getBuildNumber());

        userRepository.save(user);
        return toDto(user);
    }

    @Override
    public void updateNickname(String uuid, UserNicknameUpdateRequestDto requestDto) {

        String nickname = requestDto.getNickname();
        if (userRepository.existsByNickname(nickname)) {
            throw new CustomException(ErrorCode.NICKNAME_DUPLICATED);
        }

        User user = userRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        user.setNickname(nickname);
        userRepository.save(user);

    }


    private UserResponseDto toDto(User user) {
        return UserResponseDto.builder()
                .uuid(user.getId().toString())
                .nickname(user.getNickname())
                .onboardingCompleted(false) // TODO
                .os(user.getOs())
                .osVersion(user.getOsVersion())
                .appVersion(user.getAppVersion())
                .buildNumber(user.getBuildNumber())
                .build();
    }
}

