package com.example.ExamSys.service;

import com.example.ExamSys.dao.QuestionListRepository;
import com.example.ExamSys.domain.QuestionList;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class QuestionListService {

    @Resource
    private QuestionListRepository questionListRepository;

    @Transactional
    public QuestionList save(QuestionList questionList){
        questionListRepository.save(questionList);
        return questionList;
    }

    @Transactional
    public List<QuestionList> findByName(String name){
        return questionListRepository.findByName(name);
    }

    @Transactional
    public QuestionList findByNameandNumber(String name, int number){
        return questionListRepository.findByNameandNumber(name, number);
    }

    @Transactional
    public boolean delete(Long id){
        try {
            questionListRepository.deleteById(id);
            return true;

        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
