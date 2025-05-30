package com.example.calender.service;

import com.example.calender.dto.ScheduleRequestDto;
import com.example.calender.dto.ScheduleResponseDto;
import com.example.calender.dto.ScheduleUpdateRequestDto;
import com.example.calender.repository.AuthorRepository;
import com.example.calender.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final AuthorRepository authorRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, AuthorRepository authorRepository) {
        this.scheduleRepository = scheduleRepository;
        this.authorRepository = authorRepository;
    }

    @Transactional
    public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto) {
        return scheduleRepository.save(requestDto);
    }

    public List<ScheduleResponseDto> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<ScheduleResponseDto> getSchedulesByFilters(LocalDate updatedDate, Long authorId) {
        if (authorId != null) {
            authorRepository.findById(authorId)
                    .orElseThrow(() -> new RuntimeException("존재하지 않는 작성자입니다. ID: " + authorId));
        }

        return scheduleRepository.findByFilters(updatedDate, authorId);
    }

    public ScheduleResponseDto getSchedule(Long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("일정을 찾을 수 없습니다. ID: " + id));
    }

    @Transactional
    public ScheduleResponseDto updateSchedule(Long id, ScheduleUpdateRequestDto requestDto) {
        scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("일정을 찾을 수 없습니다. ID: " + id));

        return scheduleRepository.update(id, requestDto);
    }

    @Transactional
    public void deleteSchedule(Long id, String password) {
        scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("일정을 찾을 수 없습니다. ID: " + id));

        scheduleRepository.deleteById(id);
    }
}