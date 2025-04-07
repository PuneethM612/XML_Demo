package com.bnpp.pb.lynx.dao;

import java.util.List;
import com.bnpp.pb.lynx.model.Marks;
import com.bnpp.pb.lynx.model.StudentRank;

public interface MarksDao {
    List<Marks> getMarksByRollNumAndExamType(String rollNum, String examType);
    void updateMarks(Marks marks);
    void insertMarks(Marks marks);
    boolean marksExist(String rollNum, int subjectId, String examType);
    List<StudentRank> getTopRankers(String examType, int limit);
} 