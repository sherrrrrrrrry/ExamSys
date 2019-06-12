package com.example.ExamSys.service;

import com.example.ExamSys.dao.QuestionShowRepository;
import com.example.ExamSys.domain.QuestionShow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

@Service
public class QuestionShowService {
    @Autowired
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

    @Transactional
    public boolean delete(Long id){
        try {
            questionShowRepository.deleteById(id);
            return true;

        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}