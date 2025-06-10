package com.ddantime.ddantime.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // 전역 예외 처리기 (모든 컨트롤러에서 발생하는 예외 처리)
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponseDto> handleCustomException(CustomException e) {
        return ErrorResponseDto.toResponseEntity(e.getErrorCode());
    }
}
