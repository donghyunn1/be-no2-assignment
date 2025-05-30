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
    private Long authorId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
