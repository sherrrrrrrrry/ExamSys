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

    @Transactional
    public boolean delete(Long id){
        try {
            questionJudgmentRepository.deleteById(id);
            return true;

        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Transactional
    public QuestionJudgment findByIndex(Long id){
        try{
            return questionJudgmentRepository.findByIndex(id);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
