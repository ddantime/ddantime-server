package com.ddantime.ddantime.domain.user.service;

import com.ddantime.ddantime.common.exception.CustomException;
import com.ddantime.ddantime.common.exception.ErrorCode;
import com.ddantime.ddantime.domain.user.dto.*;
import com.ddantime.ddantime.domain.user.entity.DeviceInfo;
import com.ddantime.ddantime.domain.user.entity.User;
import com.ddantime.ddantime.domain.user.entity.UserActivityMeta;
import com.ddantime.ddantime.domain.user.entity.UserWithdrawalReason;
import com.ddantime.ddantime.domain.user.repository.UserRepository;
import com.ddantime.ddantime.domain.user.repository.UserWithdrawalReasonRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserWithdrawalReasonRepository reasonRepository;

    @Override
    @Transactional
    public UserResponseDto createUser(UserCreateRequestDto requestDto) {

        User user = User.builder()
                .nickname(null)
                .onboardingCompleted(false)
                .build();

        user.setActivityMeta(UserActivityMeta.of(user));
        user.setDeviceInfo(DeviceInfo.of(user, requestDto));

        userRepository.save(user);

        return UserResponseDto.of(user);
    }

    @Override
    public void updateNickname(User user, UserNicknameUpdateRequestDto requestDto) {

        String nickname = requestDto.getNickname();
        if (userRepository.existsByNickname(nickname)) {
            throw new CustomException(ErrorCode.NICKNAME_DUPLICATED);
        }

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
}