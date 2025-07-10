package com.ddantime.ddantime.domain.user.service;

import com.ddantime.ddantime.domain.user.dto.*;
import com.ddantime.ddantime.domain.user.entity.User;

public interface UserService {
    UserResponseDto createUser(UserCreateRequestDto requestDto);
    UserResponseDto getUserByUuid(String uuid);
    UserResponseDto updateDeviceInfo(String uuid, UserDeviceUpdateRequestDto requestDto);
    void updateNickname(String uuid, UserNicknameUpdateRequestDto requestDto);
    void withdraw(String uuid, UserWithdrawalRequestDto requestDto);
}