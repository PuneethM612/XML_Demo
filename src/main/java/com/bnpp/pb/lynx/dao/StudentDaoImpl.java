package com.bnpp.pb.lynx.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.bnpp.pb.lynx.model.Student;

public class StudentDaoImpl implements StudentDao {
    
    private JdbcTemplate jdbcTemplate;
    
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    public List<Student> getAllStudents() {
        String sql = "SELECT name, roll_num FROM student";
        return jdbcTemplate.query(sql, new StudentRowMapper());
    }
    
    @Override
    public Student getStudentByRollNum(String rollNum) {
        try {
            String sql = "SELECT name, roll_num FROM student WHERE roll_num = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{rollNum}, new StudentRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    
    @Override
    public void addStudent(Student student) {
        String sql = "INSERT INTO student (name, roll_num) VALUES (?, ?)";
        jdbcTemplate.update(sql, student.getName(), student.getRollNum());
    }
    
    @Override
    public boolean studentExists(String rollNum) {
        String sql = "SELECT COUNT(*) FROM student WHERE roll_num = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, rollNum);
        return count != null && count > 0;
    }
    
    private static final class StudentRowMapper implements RowMapper<Student> {
        @Override
        public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
            Student student = new Student();
            student.setName(rs.getString("name"));
            student.setRollNum(rs.getString("roll_num"));
            return student;
        }
    }
} 