package com.example.calender.controller;

import com.example.calender.dto.ScheduleDeleteRequestDto;
import com.example.calender.dto.ScheduleRequestDto;
import com.example.calender.dto.ScheduleResponseDto;
import com.example.calender.dto.ScheduleUpdateRequestDto;
import com.example.calender.service.ScheduleService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    // 일정 생성
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto requestDto) {
        ScheduleResponseDto responseDto = scheduleService.createSchedule(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 전체 일정 조회 (필터 없이)
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> getSchedules(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate updatedDate,
            @RequestParam(required = false) String author) {

        List<ScheduleResponseDto> schedules;
        if (updatedDate == null && author == null) {
            schedules = scheduleService.getAllSchedules();
        } else {
            schedules = scheduleService.getSchedulesByFilters(updatedDate, author);
        }

        return ResponseEntity.ok(schedules);
    }

    // 선택 일정 조회
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> getSchedule(@PathVariable Long id) {
        ScheduleResponseDto responseDto = scheduleService.getSchedule(id);
        return ResponseEntity.ok(responseDto);
    }

    // 선택 일정 수정
    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @PathVariable Long id,
            @RequestBody ScheduleUpdateRequestDto requestDto) {
        ScheduleResponseDto responseDto = scheduleService.updateSchedule(id, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    // 선택 일정 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long id,
            @RequestBody ScheduleDeleteRequestDto requestDto) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }
}