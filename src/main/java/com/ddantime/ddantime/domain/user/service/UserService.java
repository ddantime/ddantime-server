package com.ddantime.ddantime.domain.user.service;

import com.ddantime.ddantime.domain.user.dto.*;
import com.ddantime.ddantime.domain.user.entity.User;

public interface UserService {
    UserResponseDto createUser(UserCreateRequestDto requestDto);
    UserResponseDto updateDeviceInfo(User user, UserDeviceUpdateRequestDto requestDto);
    void updateNickname(User user, UserNicknameUpdateRequestDto requestDto);
    void withdraw(String uuid, UserWithdrawalRequestDto requestDto);
}