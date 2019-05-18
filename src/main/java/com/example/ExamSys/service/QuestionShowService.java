package com.example.ExamSys.service;

import com.example.ExamSys.dao.QuestionShowRepository;
import com.example.ExamSys.domain.QuestionShow;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

@Service
public class QuestionShowService {
    @Resource
    QuestionShowRepository questionShowRepository;

    public QuestionShow save(QuestionShow questionShow) {
        questionShowRepository.save(questionShow);
        return questionShow;
    }

    @Transactional
    public QuestionShow findByIndex(Long id) {
        try {
            return questionShowRepository.findByIndex(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }
}