package com.bnpp.pb.lynx.service;

import java.util.List;

import com.bnpp.pb.lynx.dao.MarksDao;
import com.bnpp.pb.lynx.model.Marks;
import com.bnpp.pb.lynx.model.StudentRank;

public class MarksServiceImpl implements MarksService {
    
    private MarksDao marksDao;
    
    public void setMarksDao(MarksDao marksDao) {
        this.marksDao = marksDao;
    }

    @Override
    public List<Marks> getMarksByRollNumAndExamType(String rollNum, String examType) {
        return marksDao.getMarksByRollNumAndExamType(rollNum, examType);
    }

    @Override
    public void saveOrUpdateMarks(Marks marks) {
        if (marksDao.marksExist(marks.getRollNum(), marks.getSubjectId(), marks.getExamType())) {
            marksDao.updateMarks(marks);
        } else {
            marksDao.insertMarks(marks);
        }
    }

    @Override
    public List<StudentRank> getTopRankers(String examType, int limit) {
        return marksDao.getTopRankers(examType, limit);
    }
} 