package com.ddantime.ddantime.domain.user.controller;


import com.ddantime.ddantime.common.annotation.RequestUser;
import com.ddantime.ddantime.domain.user.dto.UserCreateRequestDto;
import com.ddantime.ddantime.domain.user.dto.UserDeviceUpdateRequestDto;
import com.ddantime.ddantime.domain.user.dto.UserNicknameUpdateRequestDto;
import com.ddantime.ddantime.domain.user.dto.UserResponseDto;
import com.ddantime.ddantime.domain.user.entity.User;
import com.ddantime.ddantime.domain.user.service.UserActivityMetaService;
import com.ddantime.ddantime.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "사용자 API")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserActivityMetaService userActivityMetaService;

    @PostMapping
    @Operation(summary = "사용자 등록", description = "앱 최초 실행 시 디바이스 정보로 사용자 생성")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserCreateRequestDto request) {
        UserResponseDto response = userService.createUser(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    @Operation(summary = "사용자 조회", description = "요청 헤더의 UUID로 사용자 정보 조회")
    public ResponseEntity<UserResponseDto> getUserByUuid(
            @RequestHeader("Ddantime-User-Id") String uuid) {
        // TODO: 리팩토링 고민, 이미 인터셉터에서 존재여부를 판단함.
        UserResponseDto response = userService.getUserByUuid(uuid);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/me/device-info")
    @Operation(summary = "기기 정보 업데이트", description = "OS/App/Build 버전이 변경된 경우 업데이트")
    public ResponseEntity<UserResponseDto> updateDeviceInfo(
            @RequestHeader("Ddantime-User-Id") String uuid,
            @RequestBody UserDeviceUpdateRequestDto request) {
        UserResponseDto response = userService.updateDeviceInfo(uuid, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/me/nickname")
    @Operation(summary = "닉네임 수정", description = "사용자의 닉네임을 수정합니다.")
    public ResponseEntity<Void> updateNickname(
            @RequestHeader("Ddantime-User-Id") String uuid,
            @Valid @RequestBody UserNicknameUpdateRequestDto request) {
        userService.updateNickname(uuid, request);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/visit")
    @Operation(summary = "마지막 접속일 업데이트", description = "앱 접속 시 마지막 접속일을 현재 날짜로 갱신합니다.")
    public ResponseEntity<Void> updateLastAccessDate(
            @RequestHeader("Ddantime-User-Id") String uuid,
            @Parameter(hidden = true) @RequestUser User user
    ) {
        userActivityMetaService.updateLastAccessDate(user);
        return ResponseEntity.noContent().build();
    }
}