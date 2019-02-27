package com.example.ExamSys.service;

import com.example.ExamSys.dao.QuestionAnswerRepository;
import com.example.ExamSys.domain.QuestionAnswer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

@Service
public class QuestionAnswerService {
    @Resource
    private QuestionAnswerRepository questionAnswerRepository;

    @Transactional
    public QuestionAnswer findByIDandNumber(Long id, int number){
        return questionAnswerRepository.findByIDandNumber(id,number);
    }

    @Transactional
    public QuestionAnswer save(QuestionAnswer questionAnswer){
        return questionAnswerRepository.save(questionAnswer);
    }
}
