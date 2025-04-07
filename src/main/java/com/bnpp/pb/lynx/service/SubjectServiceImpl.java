package com.bnpp.pb.lynx.service;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;

import com.bnpp.pb.lynx.dao.SubjectDao;
import com.bnpp.pb.lynx.model.Subject;

public class SubjectServiceImpl implements SubjectService, InitializingBean {
    
    private SubjectDao subjectDao;
    
    public void setSubjectDao(SubjectDao subjectDao) {
        this.subjectDao = subjectDao;
    }

    @Override
    public List<Subject> getAllSubjects() {
        return subjectDao.getAllSubjects();
    }

    @Override
    public Subject getSubjectById(int subjectId) {
        return subjectDao.getSubjectById(subjectId);
    }

    @Override
    public void insertSubjectIfNotExists(Subject subject) {
        subjectDao.insertSubjectIfNotExists(subject);
    }
    
    @Override
    public void initializeSubjects() {
        // Pre-populate subjects as required
        String[] subjectNames = {"Maths", "Science", "Marathi", "Social", "Kannada", "Hindi", "English"};
        
        for (String name : subjectNames) {
            Subject subject = new Subject();
            subject.setSubjectName(name);
            insertSubjectIfNotExists(subject);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // This method is called after the bean is initialized
        initializeSubjects();
    }
} 