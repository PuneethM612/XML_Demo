package com.bnpp.pb.lynx.service;

import java.util.List;
import com.bnpp.pb.lynx.model.Marks;
import com.bnpp.pb.lynx.model.StudentRank;

public interface MarksService {
    List<Marks> getMarksByRollNumAndExamType(String rollNum, String examType);
    void saveOrUpdateMarks(Marks marks);
    List<StudentRank> getTopRankers(String examType, int limit);
} 