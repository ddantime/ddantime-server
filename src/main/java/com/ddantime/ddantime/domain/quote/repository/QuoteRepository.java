package com.ddantime.ddantime.domain.quote.repository;

import com.ddantime.ddantime.domain.quote.entity.Quote;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface QuoteRepository extends JpaRepository<Quote, Long> {
    @Query("SELECT q FROM Quote q WHERE q.id NOT IN :excludeIds ORDER BY function('RANDOM')")
    List<Quote> findRandomQuoteExcludingIds(@Param("excludeIds") Set<Long> excludeIds, Pageable pageable);
}