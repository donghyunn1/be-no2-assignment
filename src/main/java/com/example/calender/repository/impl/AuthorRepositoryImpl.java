package com.example.calender.repository.impl;

import com.example.calender.dto.AuthorRequestDto;
import com.example.calender.dto.AuthorResponseDto;
import com.example.calender.repository.AuthorRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorRepositoryImpl implements AuthorRepository {

    private final JdbcTemplate jdbcTemplate;

    public AuthorRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<AuthorResponseDto> authorRowMapper = (rs, rowNum) ->
            new AuthorResponseDto(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getTimestamp("created_at").toLocalDateTime(),
                    rs.getTimestamp("updated_at").toLocalDateTime()
            );

    @Override
    public AuthorResponseDto save(AuthorRequestDto authorRequestDto) {
        String sql = "INSERT INTO author (name, email, created_at, updated_at) VALUES (?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        LocalDateTime now = LocalDateTime.now();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, authorRequestDto.getName());
            ps.setString(2, authorRequestDto.getEmail());
            ps.setTimestamp(3, Timestamp.valueOf(now));
            ps.setTimestamp(4, Timestamp.valueOf(now));
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        return findById(id).orElseThrow(() -> new RuntimeException("작성자 저장 실패"));
    }

    @Override
    public List<AuthorResponseDto> findAll() {
        String sql = "SELECT id, name, email, created_at, updated_at FROM author ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, authorRowMapper);
    }

    @Override
    public Optional<AuthorResponseDto> findById(Long id) {
        String sql = "SELECT id, name, email, created_at, updated_at FROM author WHERE id = ?";

        List<AuthorResponseDto> results = jdbcTemplate.query(sql, authorRowMapper, id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public AuthorResponseDto update(Long id, AuthorRequestDto authorRequestDto) {
        String sql = "UPDATE author SET name = ?, email = ?, updated_at = ? WHERE id = ?";

        jdbcTemplate.update(sql,
                authorRequestDto.getName(),
                authorRequestDto.getEmail(),
                Timestamp.valueOf(LocalDateTime.now()),
                id
        );

        return findById(id).orElseThrow(() -> new RuntimeException("작성자 수정 실패"));
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM author WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
