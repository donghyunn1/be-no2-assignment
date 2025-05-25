package com.example.calender.service;

import com.example.calender.dto.AuthorRequestDto;
import com.example.calender.dto.AuthorResponseDto;
import com.example.calender.repository.AuthorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Transactional
    public AuthorResponseDto createAuthor(AuthorRequestDto requestDto) {
        return authorRepository.save(requestDto);
    }

    public List<AuthorResponseDto> getAllAuthors() {
        return authorRepository.findAll();
    }

    public AuthorResponseDto getAuthor(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("작성자를 찾을 수 없습니다. ID: " + id));
    }

    @Transactional
    public AuthorResponseDto updateAuthor(Long id, AuthorRequestDto requestDto) {
        AuthorResponseDto existingAuthor = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("작성자를 찾을 수 없습니다. ID: " + id));

        return authorRepository.update(id, requestDto);
    }

    @Transactional
    public void deleteAuthor(Long id) {
        authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("작성자를 찾을 수 없습니다. ID: " + id));

        authorRepository.deleteById(id);
    }
}
