package com.ddantime.ddantime.domain.quote.service;

import com.ddantime.ddantime.domain.quote.dto.QuoteResponseDto;
import com.ddantime.ddantime.domain.quote.entity.Quote;
import com.ddantime.ddantime.domain.quote.entity.QuoteLog;
import com.ddantime.ddantime.domain.quote.repository.QuoteLogRepository;
import com.ddantime.ddantime.domain.quote.repository.QuoteRepository;
import com.ddantime.ddantime.domain.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class QuoteService {

    private final QuoteRepository quoteRepository;
    private final QuoteLogRepository quoteLogRepository;

    @Transactional
    public QuoteResponseDto getTodayQuote(User user) {
        LocalDate today = LocalDate.now();

        // 오늘 이미 조회한 명언이 있다면 그대로 반환
        Optional<QuoteLog> todayQuoteLog = quoteLogRepository.findByUserAndDate(user, today);
        if (todayQuoteLog.isPresent()) {
            return toDto(todayQuoteLog.get().getQuote());
        }

        // 아직 오늘 명언을 보지 않았다면 새로운 명언 추출
        Set<Long> shownQuoteIds = quoteLogRepository.findQuoteIdsByUser(user);

        List<Quote> availableQuotes = quoteRepository.findRandomQuoteExcludingIds(
                shownQuoteIds, PageRequest.of(0, 1)
        );

        if (availableQuotes.isEmpty()) {
            quoteLogRepository.deleteByUser(user);
            availableQuotes = quoteRepository.findRandomQuoteExcludingIds(
                    Collections.emptySet(), PageRequest.of(0, 1)
            );
        }

        Quote selected = availableQuotes.get(0);

        // 명언 조회 기록 저장
        QuoteLog log = QuoteLog.builder()
                .user(user)
                .date(today)
                .quote(selected)
                .build();
        quoteLogRepository.save(log);

        return toDto(selected);
    }

    private QuoteResponseDto toDto(Quote quote) {
        return QuoteResponseDto.builder()
                .content(quote.getContent())
                .build();
    }
}
