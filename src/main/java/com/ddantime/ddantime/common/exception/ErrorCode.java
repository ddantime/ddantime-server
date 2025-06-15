package com.ddantime.ddantime.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    MISSING_HEADER("E001", "필수 헤더가 없습니다.", HttpStatus.UNAUTHORIZED),
    INVALID_UUID("E002", "잘못된 UUID 형식입니다.", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND("E003","존재하지 않는 사용자입니다.",HttpStatus.NOT_FOUND);

    private final String code;
    private final String message;
    private final HttpStatus status;

}
