package com.ddantime.ddantime.common.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice // 전역 예외 처리기 (모든 컨트롤러에서 발생하는 예외 처리)
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponseDto> handleCustomException(CustomException e) {
        return ErrorResponseDto.toResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationException(MethodArgumentNotValidException e) {
        // 유효성 검사, 필드별 에러 메시지들을 하나의 문자열로 합침
        String errorMessage = e.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));

        return ResponseEntity.badRequest().body(
                ErrorResponseDto.builder()
                        .status(ErrorCode.VALIDATION_ERROR.getStatus().value())
                        .code(ErrorCode.VALIDATION_ERROR.getCode())
                        .message(errorMessage)
                        .build()
        );
    }

}
