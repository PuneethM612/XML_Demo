package com.bnpp.pb.lynx.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.bnpp.pb.lynx.model.Subject;

public class SubjectDaoImpl implements SubjectDao {
    
    private JdbcTemplate jdbcTemplate;
    
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    public List<Subject> getAllSubjects() {
        String sql = "SELECT subject_id, subject_name FROM subject ORDER BY subject_id";
        return jdbcTemplate.query(sql, new SubjectRowMapper());
    }
    
    @Override
    public Subject getSubjectById(int subjectId) {
        try {
            String sql = "SELECT subject_id, subject_name FROM subject WHERE subject_id = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{subjectId}, new SubjectRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    
    @Override
    public void insertSubjectIfNotExists(Subject subject) {
        String sql = "SELECT COUNT(*) FROM subject WHERE subject_name = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, subject.getSubjectName());
        
        if (count != null && count == 0) {
            String insertSql = "INSERT INTO subject (subject_name) VALUES (?)";
            jdbcTemplate.update(insertSql, subject.getSubjectName());
        }
    }
    
    private static final class SubjectRowMapper implements RowMapper<Subject> {
        @Override
        public Subject mapRow(ResultSet rs, int rowNum) throws SQLException {
            Subject subject = new Subject();
            subject.setSubjectId(rs.getInt("subject_id"));
            subject.setSubjectName(rs.getString("subject_name"));
            return subject;
        }
    }
} 