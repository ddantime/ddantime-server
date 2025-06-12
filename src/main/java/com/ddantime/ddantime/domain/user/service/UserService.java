package com.ddantime.ddantime.domain.user.service;

import com.ddantime.ddantime.domain.user.dto.UserCreateRequestDto;
import com.ddantime.ddantime.domain.user.dto.UserResponseDto;

public interface UserService {
    UserResponseDto createUser(UserCreateRequestDto requestDto);
    UserResponseDto getUserByUuid(String uuid);
}