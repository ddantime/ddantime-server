package com.ddantime.ddantime.domain.quote.repository;

import com.ddantime.ddantime.domain.quote.entity.QuoteLog;
import com.ddantime.ddantime.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

public interface QuoteLogRepository extends JpaRepository<QuoteLog, Long> {
    Optional<QuoteLog> findByUserAndDate(User user, LocalDate date);
    @Query("SELECT ql.quote.id FROM QuoteLog ql WHERE ql.user = :user")
    Set<Long> findQuoteIdsByUser(@Param("user") User user);
    void deleteByUser(User user);
}