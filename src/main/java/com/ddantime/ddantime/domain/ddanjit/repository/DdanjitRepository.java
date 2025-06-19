package com.ddantime.ddantime.domain.ddanjit.repository;

import com.ddantime.ddantime.domain.ddanjit.entity.Ddanjit;
import com.ddantime.ddantime.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DdanjitRepository extends JpaRepository<Ddanjit, Long> {
    List<Ddanjit> findAllByUserAndDateOrderByStartTimeDescEndTimeDescCreatedAtDesc(User user, LocalDate date);
}