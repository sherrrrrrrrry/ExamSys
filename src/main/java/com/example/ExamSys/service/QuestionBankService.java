package com.example.ExamSys.service;

import com.example.ExamSys.dao.QuestionBankRepository;
import com.example.ExamSys.domain.QuestionBank;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

@Service
public class QuestionBankService {

    @Resource
    private QuestionBankRepository questionBankRepository;

    @Transactional
    public QuestionBank save(QuestionBank questionBank){
        questionBankRepository.save(questionBank);
        return questionBank;
    }


}

