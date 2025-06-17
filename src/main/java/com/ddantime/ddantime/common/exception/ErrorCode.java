package com.ddantime.ddantime.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    MISSING_HEADER("E001", "필수 헤더가 없습니다.", HttpStatus.UNAUTHORIZED),
    INVALID_UUID("E002", "잘못된 UUID 형식입니다.", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND("E003","존재하지 않는 사용자입니다.", HttpStatus.NOT_FOUND),
    VALIDATION_ERROR("E004","유효성 검사 실패", HttpStatus.BAD_REQUEST),
    NICKNAME_DUPLICATED("E005", "이미 사용 중인 닉네임입니다.", HttpStatus.CONFLICT),
    ONBOARDING_MESSAGE_NOT_FOUND("E010", "온보딩 메시지를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    ONBOARDING_ALREADY_EXISTS("E007", "해당 사용자에 대한 온보딩 정보가 이미 존재합니다.", HttpStatus.CONFLICT);

    private final String code;
    private final String message;
    private final HttpStatus status;

}
