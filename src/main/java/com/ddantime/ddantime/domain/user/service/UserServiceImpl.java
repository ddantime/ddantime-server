package com.ddantime.ddantime.domain.user.service;

import com.ddantime.ddantime.domain.user.dto.UserCreateRequestDto;
import com.ddantime.ddantime.domain.user.dto.UserDeviceUpdateRequestDto;
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

