package com.example.ExamSys.service;

import com.example.ExamSys.dao.QuestionShortRepository;
import com.example.ExamSys.domain.QuestionShort;
import org.aspectj.weaver.patterns.TypePatternQuestions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

@Service
public class QuestionShortService {


    @Resource
    private QuestionShortRepository questionShortRepository;

    public QuestionShort createShort(String content, String type){
        QuestionShort questionShort = new QuestionShort();
        questionShort.setContent(content);
        questionShort.setType(content);
        questionShortRepository.save(questionShort);
        return questionShort;
    }
    @Transactional
    public QuestionShort save(QuestionShort questionShort){
        questionShortRepository.save(questionShort);
        return questionShort;
    }


    @Transactional
    public boolean delete(Long id){
        try {
            questionShortRepository.deleteById(id);
            return true;

        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Transactional
    public QuestionShort findByIndex(Long id){
        try{
            return questionShortRepository.findByIndex(id);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
