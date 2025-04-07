package com.bnpp.pb.lynx.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.bnpp.pb.lynx.model.Marks;
import com.bnpp.pb.lynx.model.StudentRank;

public class MarksDaoImpl implements MarksDao {
    
    private JdbcTemplate jdbcTemplate;
    
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    public List<Marks> getMarksByRollNumAndExamType(String rollNum, String examType) {
        String sql = "SELECT m.roll_num, m.subject_id, s.subject_name, m.exam_type, m.marks " +
                     "FROM marks m " +
                     "JOIN subject s ON m.subject_id = s.subject_id " +
                     "WHERE m.roll_num = ? AND m.exam_type = ? " +
                     "ORDER BY s.subject_id";
        
        return jdbcTemplate.query(sql, new Object[]{rollNum, examType}, new MarksRowMapper());
    }
    
    @Override
    public void updateMarks(Marks marks) {
        String sql = "UPDATE marks SET marks = ? WHERE roll_num = ? AND subject_id = ? AND exam_type = ?";
        jdbcTemplate.update(sql, marks.getMarks(), marks.getRollNum(), marks.getSubjectId(), marks.getExamType());
    }
    
    @Override
    public void insertMarks(Marks marks) {
        String sql = "INSERT INTO marks (roll_num, subject_id, exam_type, marks) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, marks.getRollNum(), marks.getSubjectId(), marks.getExamType(), marks.getMarks());
    }
    
    @Override
    public boolean marksExist(String rollNum, int subjectId, String examType) {
        String sql = "SELECT COUNT(*) FROM marks WHERE roll_num = ? AND subject_id = ? AND exam_type = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, rollNum, subjectId, examType);
        return count != null && count > 0;
    }
    
    @Override
    public List<StudentRank> getTopRankers(String examType, int limit) {
        // SQL to get top rankers without using a separate table
        String sql = "SELECT s.roll_num, s.name, " +
                     "SUM(m.marks) as total_marks, " +
                     "RANK() OVER (ORDER BY SUM(m.marks) DESC) as rank " +
                     "FROM student s " +
                     "JOIN marks m ON s.roll_num = m.roll_num " +
                     "WHERE m.exam_type = ? " +
                     "GROUP BY s.roll_num, s.name " +
                     "ORDER BY total_marks DESC " +
                     "LIMIT ?";
        
        return jdbcTemplate.query(sql, new Object[]{examType, limit}, new StudentRankRowMapper());
    }
    
    private static final class MarksRowMapper implements RowMapper<Marks> {
        @Override
        public Marks mapRow(ResultSet rs, int rowNum) throws SQLException {
            Marks marks = new Marks();
            marks.setRollNum(rs.getString("roll_num"));
            marks.setSubjectId(rs.getInt("subject_id"));
            marks.setSubjectName(rs.getString("subject_name"));
            marks.setExamType(rs.getString("exam_type"));
            marks.setMarks(rs.getInt("marks"));
            return marks;
        }
    }
    
    private static final class StudentRankRowMapper implements RowMapper<StudentRank> {
        @Override
        public StudentRank mapRow(ResultSet rs, int rowNum) throws SQLException {
            StudentRank rank = new StudentRank();
            rank.setRollNum(rs.getString("roll_num"));
            rank.setName(rs.getString("name"));
            rank.setTotalMarks(rs.getInt("total_marks"));
            rank.setRank(rs.getInt("rank"));
            return rank;
        }
    }
} 