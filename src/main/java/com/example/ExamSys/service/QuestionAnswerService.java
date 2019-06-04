package com.example.ExamSys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.ExamSys.dao.QuestionAnswerRepository;
import com.example.ExamSys.domain.QuestionAnswer;
import com.example.ExamSys.domain.QuestionChoice;
import com.example.ExamSys.domain.QuestionJudgment;
import com.example.ExamSys.domain.QuestionList;
import com.example.ExamSys.domain.QuestionShort;
import com.example.ExamSys.domain.QuestionShow;
import com.example.ExamSys.domain.enumeration.QuestionType;

@Service
public class QuestionAnswerService {
    @Autowired
    private QuestionAnswerRepository questionAnswerRepository;

    @Autowired
    private QuestionBankService questionBankService;
    
    @Autowired
    private QuestionListService questionListService;
    
    @Autowired
    private QuestionChoiceService questionChoiceService;
    
    @Autowired
    private QuestionJudgmentService questionJudgmentService;
    
    @Autowired
    private QuestionShortService questionShortService;
    
    @Autowired
    private QuestionShowService questionShowService;
    @Transactional
    public QuestionAnswer findByIDandNumber(Long id, int number, Long studentId){
        try {
            return questionAnswerRepository.findByIDandNumber(id, number, studentId);
        }catch (Exception e){
            return null;
        }
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
    public List<QuestionAnswer> getTobeMarkedByBankandStu(Long bankid, Long stuid){
        try{
            return questionAnswerRepository.getTobeMarkedByBankandStu(bankid, stuid);
        }catch(Exception e){
            return null;
        }
    }
    @Transactional
    public HashMap<Integer, HashMap<String,String>> getTobeMarked(){
        //获取所有需要阅卷的试卷：包括 学生的用户名和试卷名
        List<QuestionAnswer> questionAnswerList;
        try {
             questionAnswerList = questionAnswerRepository.getTobeMarked();
        }catch (Exception e){
            return null;
        }
        HashMap<Integer, HashMap<String,String>> tobeMarkedMap = new HashMap<>();  //存储待阅卷试卷信息
        if (questionAnswerList.size()<=0){
            return null;
        }
        else{
            int level = 0;
            int num = 0;
            String questionBankName = "";
            String username = "";
            for (int i=0; i<questionAnswerList.size();i++){
                HashMap<String, String> tobeMarkedMetaData = new HashMap<>();      //  存储当前待阅卷试卷信息

                questionBankName = questionAnswerList.get(i).getQuestionBank().getName();
                level = questionBankService.getLevelByName(questionBankName);//试卷等级
                username = questionAnswerList.get(i).getStudent().getUser().getLogin();
                tobeMarkedMetaData.put("username",username);
                tobeMarkedMetaData.put("questionbankname",questionBankName);
                tobeMarkedMetaData.put("level",level+"");
                if (!tobeMarkedMap.containsValue(tobeMarkedMetaData)){       //重复数据不存
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
    
    public Map<String, Object> getQuestions(String bankName, int index){
    	QuestionList questionList = questionListService.findByNameandNumber(bankName, index);
    	if(questionList != null) {
    		try {
    			Map<String, Object> question = new HashMap<>();
    			QuestionType type = questionList.getType();
    			if(type == QuestionType.Choice) {
    				QuestionChoice questionChoice = questionChoiceService.findByIndex(questionList.getQuestion_id());
    				String questiontype = questionChoice.getChoicetype();
    				//单选
    				if (questiontype.equals("0") ) {
                        question.put("type", "singlechoice");
                    }
                    if (questiontype.equals("1")) {
                        question.put("type", "multichoice");
                    }
                    int choicenumber = questionChoice.getChoices().size();
                    question.put("choicenumber",choicenumber);
                    question.put("question", questionChoice);
    			}else if (type == QuestionType.Judgment) {
                    QuestionJudgment questionJudgment = questionJudgmentService.findByIndex(questionList.getQuestion_id());
                    question.put("type", "judgment");
                    question.put("question", questionJudgment);
                } else if (type == QuestionType.Short) {
                    QuestionShort questionShort = questionShortService.findByIndex(questionList.getQuestion_id());
                    question.put("type", "short");
                    question.put("question", questionShort);
                } else if (type == QuestionType.Show){
                    QuestionShow questionShow = questionShowService.findByIndex(questionList.getQuestion_id());
                    question.put("type","show");
                    question.put("question",questionShow);
                }
    			return question;
    		}catch (Exception e) {
                e.printStackTrace();
                return null;
            }
    	} else {
    		return null;
    	}
    }
}
