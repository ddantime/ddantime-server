package com.ddantime.ddantime.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    INTERNAL_SERVER_ERROR("E000", "INTERNAL_SERVER_ERROR.", HttpStatus.INTERNAL_SERVER_ERROR),

    MISSING_HEADER("E001", "필수 헤더가 없습니다.", HttpStatus.UNAUTHORIZED),
    VALIDATION_ERROR("E002","유효성 검사 실패", HttpStatus.BAD_REQUEST),
    INVALID_ENUM("E003","잘못된 enum 값이 입력되었습니다.", HttpStatus.BAD_REQUEST),

    INVALID_UUID("E101", "잘못된 UUID 형식입니다.", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND("E102","존재하지 않는 사용자입니다.", HttpStatus.NOT_FOUND),
    NICKNAME_DUPLICATED("E103", "이미 사용 중인 닉네임입니다.", HttpStatus.CONFLICT),
    ONBOARDING_MESSAGE_NOT_FOUND("E104", "온보딩 메시지를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    ONBOARDING_ALREADY_EXISTS("E105", "해당 사용자에 대한 온보딩 정보가 이미 존재합니다.", HttpStatus.CONFLICT),

    DDANJIT_NOT_FOUND("E201", "요청한 딴짓 기록을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);



    private final String code;
    private final String message;
    private final HttpStatus status;

}
