package com.ddantime.ddantime.domain.quote.controller;

import com.ddantime.ddantime.common.annotation.RequestUser;
import com.ddantime.ddantime.domain.quote.dto.QuoteResponseDto;
import com.ddantime.ddantime.domain.quote.service.QuoteService;
import com.ddantime.ddantime.domain.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Quote", description = "오늘의 명언 API")
@RestController
@RequestMapping("/api/v1/quotes")
@RequiredArgsConstructor
public class QuoteController {

    private final QuoteService quoteService;

    /**
     * 오늘의 명언 조회
     * - 이미 조회한 경우 동일 명언 반환
     * - 처음 조회 시 랜덤 명언 반환 후 기록
     * @param user
     * @return
     */
    @GetMapping("/today")
    @Operation(
            summary = "오늘의 명언 조회",
            description = "오늘 하루를 시작할 수 있는 짧은 문장을 제공합니다. <br/> 아직 보지 않은 명언 중 하나가 랜덤으로 선택되며, 모든 명언을 다 보면 다시 처음부터 순환됩니다. <br/> 하루 한 번만 조회할 수 있습니다."
    )
    public ResponseEntity<QuoteResponseDto> getTodayQuote(
            @RequestHeader("Ddantime-User-Id") String uuid,
            @Parameter(hidden = true) @RequestUser User user) {
        QuoteResponseDto response = quoteService.getTodayQuote(user);
        return ResponseEntity.ok(response);
    }
}