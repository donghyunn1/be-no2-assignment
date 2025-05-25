package com.example.calender.repository;

import com.example.calender.dto.AuthorRequestDto;
import com.example.calender.dto.AuthorResponseDto;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    AuthorResponseDto save(AuthorRequestDto authorRequestDto);
    List<AuthorResponseDto> findAll();
    Optional<AuthorResponseDto> findById(Long id);
    AuthorResponseDto update(Long id, AuthorRequestDto authorRequestDto);
    void deleteById(Long id);
}
