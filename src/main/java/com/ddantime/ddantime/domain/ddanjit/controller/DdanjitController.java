package com.ddantime.ddantime.domain.ddanjit.controller;

import com.ddantime.ddantime.common.annotation.RequestUser;
import com.ddantime.ddantime.domain.ddanjit.dto.DdanjitCreateRequestDto;
import com.ddantime.ddantime.domain.ddanjit.service.DdanjitService;
import com.ddantime.ddantime.domain.onboarding.dto.OnboardingRequestDto;
import com.ddantime.ddantime.domain.onboarding.dto.OnboardingResponseDto;
import com.ddantime.ddantime.domain.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Ddanjit", description = "딴짓 기록 API")
@RestController
@RequestMapping("/api/v1/ddanjit")
@RequiredArgsConstructor
public class DdanjitController {

    private final DdanjitService ddanjitService;

    @PostMapping
    @Operation(summary = "딴짓 기록 생성", description = "사용자의 딴짓 기록을 저장합니다.")
    public ResponseEntity<Void> createDdanjit(
            @RequestHeader("Ddantime-User-Id") String uuid,
            @Parameter(hidden = true) @RequestUser User user,
            @RequestBody @Valid DdanjitCreateRequestDto requestDto
    ) {
        ddanjitService.create(user, requestDto);
        return ResponseEntity.noContent().build(); // 204 응답
    }
}
