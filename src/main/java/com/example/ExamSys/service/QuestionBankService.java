package com.example.ExamSys.service;

import com.example.ExamSys.dao.QuestionBankRepository;
import com.example.ExamSys.domain.Question;
import com.example.ExamSys.domain.QuestionBank;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class QuestionBankService {

    @Resource
    private QuestionBankRepository questionBankRepository;

    @Transactional
    public QuestionBank save(QuestionBank questionBank){
        questionBankRepository.save(questionBank);
        return questionBank;
    }

    @Transactional
    public List<QuestionBank> findAll(){
        return questionBankRepository.findAll();
    }

    @Transactional
    public QuestionBank findByName(String name){
        return questionBankRepository.findByName(name);
    }



}

