package com.ddantime.ddantime.domain.quote.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QuoteResponseDto {
    private final String content;
}
