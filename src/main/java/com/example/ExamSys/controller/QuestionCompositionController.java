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
//    @RequestMapping("/questionbank_Name1")
//    public ResponseEntity<List<String>> getQuestionBankName(){
//        List<String> questionBankNames = questionBankService.getBankNames();
////        for (int i=0; i<questionBankNames.size();i++){
////            questionBankNames.get(i);
////        }
//        if (questionBankNames!=null){
//            return ResponseEntity.ok().body(questionBankNames);
//        }
//        else{
//            return ResponseEntity.badRequest().header("PaperName","No Paper!").body(null);
//        }
//    }
    @RequestMapping("/questionbank_Name")
    public Map<Integer, String> getQuestionBankName() {
        List<String> questionBankNames = questionBankService.getBankNames();
        if (questionBankNames != null) {
            Map<Integer, String> questionBankName = new HashMap<>();
            for (int i = 0; i < questionBankNames.size(); i++) {
                questionBankName.put(i, questionBankNames.get(i));
            }
            return questionBankName;
        } else {
            return null;
        }
    }

    /**
     * POST请求，试卷名 name 及题号 index
     */
    @RequestMapping(value = "/get_questions", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<Map<String, Object>> getQuestions(HttpServletRequest request) {
        String bankName = request.getParameter("name");
        int index = Integer.parseInt(request.getParameter("index"));
        QuestionList questionList = questionListService.findByNameandNumber(bankName, index);
        if (questionList != null) {
            try {
                Map<String, Object> question = new HashMap<>();
                QuestionType type = questionList.getType();
                if (type == QuestionType.Choice) {
                    QuestionChoice questionChoice = questionChoiceService.findByIndex(questionList.getQuestion_id());
                    String questiontype = questionChoice.getChoicetype();
                    //单选
                    if (questiontype.equals("1") ) {
                        question.put("type", "singlechoice");
                    }
                    if (questiontype.equals("0")) {
                        question.put("type", "multichoice");
                    }
                    question.put("question", questionChoice);
                } else if (type == QuestionType.Judgment) {
                    QuestionJudgment questionJudgment = questionJudgmentService.findByIndex(questionList.getQuestion_id());
                    question.put("type", "judgment");
                    question.put("question", questionJudgment);
                } else if (type == QuestionType.Short) {
                    QuestionShort questionShort = questionShortService.findByIndex(questionList.getQuestion_id());
                    question.put("type", "short");
                    question.put("question", questionShort);
//            else{
//                //展示题 还没做！
//                return null;
//            }
                }
                return ResponseEntity.ok().body(question);

            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.badRequest().header("question", "No such question!").body(null);
            }


        } else {
            return ResponseEntity.badRequest().header("question", "No such question!").body(null);
        }

    }
}
