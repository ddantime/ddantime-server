package com.ddantime.ddantime.domain.user.controller;


import com.ddantime.ddantime.domain.user.dto.UserCreateRequestDto;
import com.ddantime.ddantime.domain.user.dto.UserDeviceUpdateRequestDto;
import com.ddantime.ddantime.domain.user.dto.UserResponseDto;
import com.ddantime.ddantime.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "사용자 API")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

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





}