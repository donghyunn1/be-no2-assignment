package com.example.calender.controller;

import com.example.calender.dto.AuthorRequestDto;
import com.example.calender.dto.AuthorResponseDto;
import com.example.calender.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    // 작성자 생성
    @PostMapping
    public ResponseEntity<AuthorResponseDto> createAuthor(@RequestBody AuthorRequestDto requestDto) {
        AuthorResponseDto responseDto = authorService.createAuthor(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 전체 작성자 조회
    @GetMapping
    public ResponseEntity<List<AuthorResponseDto>> getAllAuthors() {
        List<AuthorResponseDto> authors = authorService.getAllAuthors();
        return ResponseEntity.ok(authors);
    }

    // 특정 작성자 조회
    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponseDto> getAuthor(@PathVariable Long id) {
        AuthorResponseDto responseDto = authorService.getAuthor(id);
        return ResponseEntity.ok(responseDto);
    }

    // 작성자 수정
    @PutMapping("/{id}")
    public ResponseEntity<AuthorResponseDto> updateAuthor(
            @PathVariable Long id,
            @RequestBody AuthorRequestDto requestDto) {
        AuthorResponseDto responseDto = authorService.updateAuthor(id, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    // 작성자 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }
}