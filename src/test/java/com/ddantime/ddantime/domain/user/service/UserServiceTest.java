package com.ddantime.ddantime.domain.user.service;

import com.ddantime.ddantime.common.exception.CustomException;
import com.ddantime.ddantime.common.exception.ErrorCode;
import com.ddantime.ddantime.domain.user.dto.UserCreateRequestDto;
import com.ddantime.ddantime.domain.user.dto.UserDeviceUpdateRequestDto;
import com.ddantime.ddantime.domain.user.dto.UserResponseDto;
import com.ddantime.ddantime.domain.user.entity.OsType;
import com.ddantime.ddantime.domain.user.entity.User;
import com.ddantime.ddantime.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.UUID;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private static final Logger log = LoggerFactory.getLogger(UserServiceTest.class);
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private UUID uuid;
    private User user;

    // 각 테스트 메서드 실행 전에 공통적으로 사용할 데이터 준비
    @BeforeEach
    void setUp() {
        uuid = UUID.randomUUID();
        user = User.builder()
                .id(uuid)
                .os(OsType.ANDROID)
                .osVersion("17.1")
                .appVersion("1.0.0")
                .buildNumber("100")
                .build();
    }

    @Test
    void createUser_정상등록() {
        // Given
        UserCreateRequestDto request = new UserCreateRequestDto(OsType.ANDROID, "17.1", "1.0.0", "100");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User u = invocation.getArgument(0);
            u.setId(UUID.randomUUID()); // 수동으로 id 세팅
            return u;
        });

        // When: createUser 메서드를 호출
        UserResponseDto result = userService.createUser(request);

        // Then: 반환값 확인 및 save() 호출 검증
        assertEquals(OsType.ANDROID, result.getOs());
        assertEquals("17.1", result.getOsVersion());
        verify(userRepository, times(1)).save(any(User.class)); // 1번 호출됐는지 확인

    }

    @Test
    void getUserByUuid_정상조회() {
        // Given
        when(userRepository.findById(uuid)).thenReturn(Optional.of(user));

        // When
        UserResponseDto result = userService.getUserByUuid(uuid.toString());

        // Then
        assertEquals(uuid.toString(), result.getUuid());
        verify(userRepository, times(1)).findById(uuid);
    }

    @Test
    void getUserByUuid_존재하지않으면_예외() {
        // Given
        when(userRepository.findById(uuid)).thenReturn(Optional.empty());

        // When
        CustomException exception = assertThrows(CustomException.class, () -> userService.getUserByUuid(uuid.toString()));

        // Then
        assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void updateDeviceInfo_정상수정() {
        // Given
        UserDeviceUpdateRequestDto updateRequest = new UserDeviceUpdateRequestDto("17.2", "1.0.1", "101");
        when(userRepository.findById(uuid)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        // When
        UserResponseDto result = userService.updateDeviceInfo(uuid.toString(), updateRequest);

        // Then
        assertEquals("17.2", result.getOsVersion());
        assertEquals("1.0.1", result.getAppVersion());
        assertEquals("101", result.getBuildNumber());
    }

    @Test
    void updateDeviceInfo_존재하지않으면_예외() {
        // Given
        when(userRepository.findById(uuid)).thenReturn(Optional.empty());
        UserDeviceUpdateRequestDto updateRequest = new UserDeviceUpdateRequestDto("17.2", "1.0.1", "101");

        // When
        CustomException exception = assertThrows(CustomException.class, () -> userService.updateDeviceInfo(uuid.toString(), updateRequest));

        // Then
        assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());
    }

}