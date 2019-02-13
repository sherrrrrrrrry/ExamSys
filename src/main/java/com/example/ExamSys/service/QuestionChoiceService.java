package com.example.ExamSys.service;

import com.example.ExamSys.dao.QuestionChoiceRepository;
import com.example.ExamSys.domain.QuestionChoice;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

@Service
public class QuestionChoiceService {

    @Resource
    private QuestionChoiceRepository questionChoiceRepository;

    @Transactional
    public QuestionChoice save(QuestionChoice questionChoice){
        questionChoiceRepository.save(questionChoice);
        return questionChoice;
    }
}
