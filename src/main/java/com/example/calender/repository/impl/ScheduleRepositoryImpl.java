package com.example.calender.repository.impl;

import com.example.calender.dto.ScheduleRequestDto;
import com.example.calender.dto.ScheduleResponseDto;
import com.example.calender.dto.ScheduleUpdateRequestDto;
import com.example.calender.repository.ScheduleRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ScheduleRepositoryImpl implements ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<ScheduleResponseDto> scheduleDtoRowMapper = (rs, rowNum) ->
            new ScheduleResponseDto(
                    rs.getLong("id"),
                    rs.getString("todo"),
                    rs.getString("author"),
                    rs.getTimestamp("created_at").toLocalDateTime(),
                    rs.getTimestamp("updated_at").toLocalDateTime()
            );

    @Override
    public ScheduleResponseDto save(ScheduleRequestDto scheduleRequestDto) {
        String sql = "INSERT INTO schedule (todo, author, password, created_at, updated_at) VALUES (?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        LocalDateTime now = LocalDateTime.now();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, scheduleRequestDto.getTodo());
            ps.setString(2, scheduleRequestDto.getAuthor());
            ps.setString(3, scheduleRequestDto.getPassword());
            ps.setTimestamp(4, Timestamp.valueOf(now));
            ps.setTimestamp(5, Timestamp.valueOf(now));
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        return findById(id).orElseThrow(() -> new RuntimeException("저장 실패"));
    }

    @Override
    public List<ScheduleResponseDto> findAll() {
        String sql = "SELECT id, todo, author, created_at, updated_at FROM schedule ORDER BY updated_at DESC";
        return jdbcTemplate.query(sql, scheduleDtoRowMapper);
    }

    @Override
    public List<ScheduleResponseDto> findByFilters(LocalDate updatedDate, String author) {
        StringBuilder sql = new StringBuilder("SELECT id, todo, author, created_at, updated_at FROM schedule WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (updatedDate != null) {
            sql.append(" AND DATE(updated_at) = ?");
            params.add(updatedDate);
        }

        if (author != null && !author.trim().isEmpty()) {
            sql.append(" AND author = ?");
            params.add(author);
        }

        sql.append(" ORDER BY updated_at DESC");

        return jdbcTemplate.query(sql.toString(), params.toArray(), scheduleDtoRowMapper);
    }

    @Override
    public Optional<ScheduleResponseDto> findById(Long id) {
        String sql = "SELECT id, todo, author, created_at, updated_at FROM schedule WHERE id = ?";

        List<ScheduleResponseDto> results = jdbcTemplate.query(sql, scheduleDtoRowMapper, id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public ScheduleResponseDto update(Long id, ScheduleUpdateRequestDto updateRequestDto) {
        String sql = "UPDATE schedule SET todo = ?, author = ?, updated_at = ? WHERE id = ?";

        jdbcTemplate.update(sql,
                updateRequestDto.getTodo(),
                updateRequestDto.getAuthor(),
                Timestamp.valueOf(LocalDateTime.now()),
                id
        );

        return findById(id).orElseThrow(() -> new RuntimeException("수정 실패"));
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM schedule WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
