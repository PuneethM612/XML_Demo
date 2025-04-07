package com.bnpp.pb.lynx.service;

import java.util.List;
import com.bnpp.pb.lynx.model.Subject;

public interface SubjectService {
    List<Subject> getAllSubjects();
    Subject getSubjectById(int subjectId);
    void insertSubjectIfNotExists(Subject subject);
    void initializeSubjects();
} 