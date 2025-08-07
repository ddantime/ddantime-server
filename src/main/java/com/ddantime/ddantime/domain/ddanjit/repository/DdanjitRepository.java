package com.ddantime.ddantime.domain.ddanjit.repository;

import com.ddantime.ddantime.domain.ddanjit.entity.Ddanjit;
import com.ddantime.ddantime.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DdanjitRepository extends JpaRepository<Ddanjit, Long> {
    List<Ddanjit> findAllByUserAndDateOrderByStartTimeDescEndTimeDescCreatedAtDesc(User user, LocalDate date);
    @Query("SELECT DISTINCT d.date FROM Ddanjit d WHERE d.user = :user")
    List<LocalDate> findDistinctDatesByUser(@Param("user") User user);
    Optional<Ddanjit> findByIdAndUser(Long id, User user);

    @Modifying
    @Query("DELETE FROM Ddanjit d WHERE d.user = :user")
    void deleteByUser(User user);
}