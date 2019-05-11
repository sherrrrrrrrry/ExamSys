package com.example.ExamSys.service;

import com.example.ExamSys.dao.QuestionAnswerRepository;
import com.example.ExamSys.domain.QuestionAnswer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Service
public class QuestionAnswerService {
    @Resource
    private QuestionAnswerRepository questionAnswerRepository;

    @Transactional
    public QuestionAnswer findByIDandNumber(Long id, int number){
        return questionAnswerRepository.findByIDandNumber(id,number);
    }

    @Transactional
    public QuestionAnswer save(QuestionAnswer questionAnswer){
        return questionAnswerRepository.save(questionAnswer);
    }

    @Transactional
    public void updateisModified(boolean isMarked, Long id){
        questionAnswerRepository.updateisModified(isMarked,id);
    }

    @Transactional
    public HashMap<Integer, HashMap<String,String>> getTobeMarked(){
        //获取所有需要阅卷的试卷：包括 学生的用户名和试卷名
        List<QuestionAnswer> questionAnswerList = questionAnswerRepository.getTobeMarked();
        HashMap<Integer, HashMap<String,String>> tobeMarkedMap = new HashMap<>();  //存储待阅卷试卷信息
        if (questionAnswerList.size()<=0){
            return null;
        }
        else{

            int num = 0;
            for (int i=0; i<questionAnswerList.size();i++){
                HashMap<String, String> tobeMarkedMetaData = new HashMap<>();      //  存储当前待阅卷试卷信息
                     tobeMarkedMetaData.put(questionAnswerList.get(0).getStudent().getUser().getLogin(),questionAnswerList.get(0).getQuestionBank().getName());
                if (!tobeMarkedMap.containsKey(tobeMarkedMetaData)){       //重复数据不存
                    tobeMarkedMap.put(num,tobeMarkedMetaData);
                    num++;
                }
            }
        }
        return tobeMarkedMap;
    }

    @Transactional
    public void updateismarked(boolean isMarked, Long bankid, Long stuid){
        
    }
}
