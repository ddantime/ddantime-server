package com.ddantime.ddantime.common.interceptor;

import com.ddantime.ddantime.common.exception.CustomException;
import com.ddantime.ddantime.common.exception.ErrorCode;
import com.ddantime.ddantime.domain.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.util.UUID;
@Component
@RequiredArgsConstructor
public class UUIDAuthInterceptor implements HandlerInterceptor {

    private static final String USER_ID_HEADER = "Ddantime-User-Id";

    private final UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException, MissingRequestHeaderException {

        String headerValue = request.getHeader(USER_ID_HEADER);

        if (headerValue == null || headerValue.isBlank()) {
            throw new CustomException(ErrorCode.MISSING_HEADER);
        }

        UUID uuid;
        try {
            uuid = UUID.fromString(headerValue);
        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorCode.INVALID_UUID);
        }

        if (!userRepository.existsById(uuid)) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        return true;
    }
}

