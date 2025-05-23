package com.example.calender.dto;

import com.example.calender.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleResponseDto {

    private Long id;
    private String todo;
    private String author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ScheduleResponseDto from(Schedule schedule) {
        return new ScheduleResponseDto(
                schedule.getId(),
                schedule.getTodo(),
                schedule.getAuthor(),
                schedule.getCreatedAt(),
                schedule.getUpdatedAt()
        );
    }
}
