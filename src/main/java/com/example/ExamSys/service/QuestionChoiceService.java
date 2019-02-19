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

    @Transactional
    public boolean delete(Long id){
        try {
            questionChoiceRepository.deleteById(id);
            return true;

        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Transactional
    public QuestionChoice findByIndex(Long id){
        try{
            return questionChoiceRepository.findByIndex(id);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
