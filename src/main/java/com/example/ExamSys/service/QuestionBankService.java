package com.example.ExamSys.service;

import com.example.ExamSys.dao.QuestionAnswerRepository;
import com.example.ExamSys.dao.QuestionBankRepository;
import com.example.ExamSys.domain.Question;
import com.example.ExamSys.domain.QuestionBank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class QuestionBankService {

    @Autowired
    private QuestionBankRepository questionBankRepository;

    @Autowired
    private QuestionAnswerRepository questionAnswerRepository;

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
    public int getLevelByName(String name){
        try {
            return questionBankRepository.getLevelByname(name);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }

    }

    @Transactional
    public QuestionBank findByName(String name){
        try {
            return questionBankRepository.findByName(name);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    @Transactional
    public List<String> getBankNames(){return questionBankRepository.getBankNames();}

    @Transactional
    public List<QuestionBank> getQuestionBanklist(){return questionBankRepository.getQuestionBanklist();}
    @Transactional
    public String getBankNamesbyLevel_Random(int level,Long stuID){
      //  List<String> banknames = questionBankRepository.getBankNamesbyLevel(level);
        List<Long> bankIDs = questionBankRepository.getBankIdsbyLevel(level);
        for (int i=0; i<bankIDs.size(); i++){
            if (questionAnswerRepository.findByBankandStu(bankIDs.get(i),stuID).size()!=0){
                bankIDs.remove(i);
                i--;
            }
        }
        if (bankIDs.size()<=0){// 处理所有试卷都做过的情况！
            return null;
        }
        int random_index = (int)(1+Math.random()*(bankIDs.size()-1+1))-1;
        return questionBankRepository.getNameByID(bankIDs.get(random_index));
    }
}

