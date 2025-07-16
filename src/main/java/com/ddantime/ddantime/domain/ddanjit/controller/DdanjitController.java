package com.ddantime.ddantime.domain.ddanjit.controller;

import com.ddantime.ddantime.common.annotation.RequestUser;
import com.ddantime.ddantime.domain.ddanjit.dto.DdanjitRequestDto;
import com.ddantime.ddantime.domain.ddanjit.dto.DdanjitResponseDto;
import com.ddantime.ddantime.domain.ddanjit.service.DdanjitService;
import com.ddantime.ddantime.domain.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Ddanjit", description = "딴짓 기록 API")
@RestController
@RequestMapping("/api/v1/ddanjits")
@RequiredArgsConstructor
public class DdanjitController {

    private final DdanjitService ddanjitService;

    @PostMapping
    @Operation(summary = "딴짓 기록 생성", description = "사용자의 딴짓 기록을 저장합니다.")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<DdanjitResponseDto> createDdanjit(
            @RequestHeader("Ddantime-User-Id") String uuid,
            @Parameter(hidden = true) @RequestUser User user,
            @RequestBody @Valid DdanjitRequestDto requestDto
    ) {
        DdanjitResponseDto response = ddanjitService.create(user, requestDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "날짜별 딴짓 기록 조회")
    public ResponseEntity<List<DdanjitResponseDto>> getRecordsByDate(
            @RequestHeader("Ddantime-User-Id") String uuid,
            @Parameter(hidden = true) @RequestUser User user,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<DdanjitResponseDto> records = ddanjitService.getRecordsByDate(user, date);
        return ResponseEntity.ok(records);
    }

    @GetMapping("/dates")
    @Operation(summary = "딴짓 기록이 있는 날짜 목록 조회", description = "사용자의 딴짓 기록이 존재하는 날짜 리스트 반환")
    public  ResponseEntity<List<LocalDate>> getRecordDates(
            @RequestHeader("Ddantime-User-Id") String uuid,
            @Parameter(hidden = true) @RequestUser User user) {
        List<LocalDate> dates = ddanjitService.getDatesByUser(user);
        return ResponseEntity.ok(dates);
    }

    @PutMapping("/{id}")
    @Operation(summary = "딴짓 기록 수정", description = "기존의 딴짓 기록을 수정합니다.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateDdanjit(
            @PathVariable Long id,
            @RequestBody @Valid DdanjitRequestDto requestDto,
            @RequestHeader("Ddantime-User-Id") String uuid,
            @Parameter(hidden = true) @RequestUser User user
    ) {
        ddanjitService.update(id, requestDto, user);
    }

    @Operation(summary = "딴짓 기록 삭제", description = "딴짓 기록 하나를 삭제합니다.")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long id,
            @RequestHeader("Ddantime-User-Id") String uuid,
            @Parameter(hidden = true) @RequestUser User user
    ) {
        ddanjitService.delete(id, user);
    }

    @DeleteMapping
    @Operation(summary = "딴짓 기록 초기화", description = "계정은 유지하고 사용자의 딴타임 내용과 활동일자만 초기화합니다.")
    public ResponseEntity<Void> resetRecords(
            @RequestHeader("Ddantime-User-Id") String uuid,
            @Parameter(hidden = true) @RequestUser User user
    ) {
        ddanjitService.deleteAllByUser(user);
        return ResponseEntity.noContent().build();
    }
}