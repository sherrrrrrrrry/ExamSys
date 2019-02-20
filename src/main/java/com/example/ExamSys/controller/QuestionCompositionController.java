package com.example.ExamSys.controller;

import com.example.ExamSys.domain.*;
import com.example.ExamSys.domain.enumeration.QuestionType;
import com.example.ExamSys.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/questioncomposition")
public class QuestionCompositionController {

    @Resource
    private QuestionBankService questionBankService;

    @Resource
    private QuestionShortService questionShortService;

    @Resource
    private QuestionChoiceService questionChoiceService;

    @Resource
    private QuestionJudgmentService questionJudgmentService;

    @Resource
    private QuestionListService questionListService;

    /**
     * 获取所有试卷名
     */
    @RequestMapping("/questionbank_Name1")
    public ResponseEntity<List<String>> getQuestionBankName(){
        List<String> questionBankNames = questionBankService.getBankNames();
//        for (int i=0; i<questionBankNames.size();i++){
//            questionBankNames.get(i);
//        }
        if (questionBankNames!=null){
            return ResponseEntity.ok().body(questionBankNames);
        }
        else{
            return ResponseEntity.ok().header("PaperName","No Paper!").body(null);
        }
    }

    @RequestMapping("/questionbank_Name2")
    public Map<Integer, String> getQuestionBankName2(){
        List<String> questionBankNames = questionBankService.getBankNames();
        if (questionBankNames!=null){
            Map<Integer,String> questionBankName = new HashMap<>();
            for (int i=0; i<questionBankNames.size();i++){
                questionBankName.put(i,questionBankNames.get(i));
            }
            return questionBankName;
        }
        else{
            return null;
        }
    }

    /**
     * POST请求，试卷名 name 及题号 index
     */
    @RequestMapping(value = "/get_questions", method = RequestMethod.POST, headers = "Accept=application/json")
    public Object getQuestions(HttpServletRequest request){
        String bankName = request.getParameter("name");
        int index = Integer.parseInt(request.getParameter("index"));
        QuestionList questionList = questionListService.findByNameandNumber(bankName,index);
        if (questionList!=null){
            QuestionType type = questionList.getType();
            if (type == QuestionType.Choice){
                return questionChoiceService.findByIndex(questionList.getQuestion_id());
            }
            else if (type == QuestionType.Judgment){
                return questionJudgmentService.findByIndex(questionList.getQuestion_id());
            }
            else if (type == QuestionType.Short){
                return questionShortService.findByIndex(questionList.getQuestion_id());
            }
            else{
                //展示题 还没做！
                return null;
            }
        }
        else {
            return ResponseEntity.badRequest().header("index","Index is out of range!");
        }
    }
}
