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
    private String author;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void update(String todo, String author) {
        this.todo = todo;
        this.author = author;
        this.updatedAt = LocalDateTime.now();
    }
}
