package com.ddantime.ddantime.domain.user.service;

import com.ddantime.ddantime.common.exception.CustomException;
import com.ddantime.ddantime.common.exception.ErrorCode;
import com.ddantime.ddantime.domain.user.dto.*;
import com.ddantime.ddantime.domain.user.entity.User;
import com.ddantime.ddantime.domain.user.entity.UserActivityMeta;
import com.ddantime.ddantime.domain.user.entity.UserWithdrawalReason;
import com.ddantime.ddantime.domain.user.repository.UserRepository;
import com.ddantime.ddantime.domain.user.repository.UserWithdrawalReasonRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserWithdrawalReasonRepository reasonRepository;

    @Override
    public UserResponseDto createUser(UserCreateRequestDto requestDto) {
        User user = User.builder()
                .os(requestDto.getOs())
                .osVersion(requestDto.getOsVersion())
                .appVersion(requestDto.getAppVersion())
                .buildNumber(requestDto.getBuildNumber())
                .build();

        UserActivityMeta activityMeta = UserActivityMeta.of(user);
        user.setActivityMeta(activityMeta);

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

    @Override
    @Transactional
    public void withdraw(String uuid, UserWithdrawalRequestDto requestDto) {
        User user = userRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 탈퇴 사유 저장
        UserWithdrawalReason reason = UserWithdrawalReason.builder()
                .reasonType(requestDto.getReasonType())
                .reasonDetail(requestDto.getReasonDetail())
                .build();
        reasonRepository.save(reason);

        // 사용자 삭제
        userRepository.delete(user);
    }

    private UserResponseDto toDto(User user) {
        UserActivityMeta meta = user.getActivityMeta();

        return UserResponseDto.builder()
                .uuid(user.getId().toString())
                .nickname(user.getNickname())
                .onboardingCompleted(user.isOnboardingCompleted())
                .os(user.getOs())
                .osVersion(user.getOsVersion())
                .appVersion(user.getAppVersion())
                .buildNumber(user.getBuildNumber())
                .lastAccessDate(meta.getLastAccessDate())
                .firstRecordDate(meta.getFirstRecordDate())
                .lastRecordDate(meta.getLastRecordDate())
                .build();
    }


}

