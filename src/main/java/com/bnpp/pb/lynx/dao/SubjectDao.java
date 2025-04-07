package com.bnpp.pb.lynx.dao;

import java.util.List;
import com.bnpp.pb.lynx.model.Subject;

public interface SubjectDao {
    List<Subject> getAllSubjects();
    Subject getSubjectById(int subjectId);
    void insertSubjectIfNotExists(Subject subject);
} 