package com.example.calender.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ScheduleRequestDto {

    private String todo;
    private String author;
    private String password;
}
