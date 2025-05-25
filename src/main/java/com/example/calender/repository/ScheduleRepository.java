package com.example.calender.repository;

import com.example.calender.dto.ScheduleRequestDto;
import com.example.calender.dto.ScheduleResponseDto;
import com.example.calender.dto.ScheduleUpdateRequestDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {

    ScheduleResponseDto save(ScheduleRequestDto scheduleRequestDto);
    List<ScheduleResponseDto> findAll();
    List<ScheduleResponseDto> findByFilters(LocalDate updatedDate, Long authorId);
    Optional<ScheduleResponseDto> findById(Long id);
    ScheduleResponseDto update(Long id, ScheduleUpdateRequestDto updateRequestDto);
    void deleteById(Long id);
}
