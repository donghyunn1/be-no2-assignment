package com.example.calender.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Schedule {

    private Long id;
    private String todo;
    private Long authorId;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void update(String todo, Long authorId) {
        this.todo = todo;
        this.authorId = authorId;
        this.updatedAt = LocalDateTime.now();
    }
}
