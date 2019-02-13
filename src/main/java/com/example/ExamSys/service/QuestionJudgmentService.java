package com.example.ExamSys.service;

import com.example.ExamSys.dao.QuestionJudgmentRepository;
import com.example.ExamSys.domain.QuestionJudgment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

@Service
public class QuestionJudgmentService {
    @Resource
    private QuestionJudgmentRepository questionJudgmentRepository;

    @Transactional
    public QuestionJudgment save(QuestionJudgment questionJudgment){
        questionJudgmentRepository.save(questionJudgment);
        return questionJudgment;
    }
}
