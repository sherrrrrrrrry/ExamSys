package com.example.ExamSys.controller;

import com.example.ExamSys.domain.*;
import com.example.ExamSys.domain.enumeration.QuestionType;
import com.example.ExamSys.service.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.management.Query;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/paperListAdmin")
public class PaperListController {

    private final Logger log = LoggerFactory.getLogger(PaperListController.class);
    @Autowired
    QuestionBankService questionBankService;

    @Autowired
    QuestionListService questionListService;

    @Autowired
    QuestionChoiceService questionChoiceService;

    @Autowired
    QuestionJudgmentService questionJudgmentService;

    @Autowired
    QuestionShortService questionShortService;

    @Autowired
    QuestionShowService questionShowService;
    /**
     * 获取所有试卷名
     */

    @RequestMapping("/questionbank_Name")
    public ResponseEntity getQuestionBankName() {
        log.info("正在查询试卷列表...");
        List<QuestionBank> questionBankList = questionBankService.getQuestionBanklist();
        log.info("试卷列表查询成功!");
        JSONArray jsonArray = new JSONArray();
        if (questionBankList != null) {
            for (int i = 0; i < questionBankList.size(); i++) {
                try{
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("questionName",questionBankList.get(i).getName());
                    jsonObject.put("level",questionBankList.get(i).getLevel());
                    jsonArray.put(jsonObject);
                }catch (Exception e){
                    e.printStackTrace();
                    log.info(e.toString());
                    return ResponseEntity.badRequest().body(null);
                }

            }
            return ResponseEntity.ok().body(jsonArray.toString());
        } else {
            return ResponseEntity.ok().body(null);
        }
    }

    /**
     * 获取指定试卷的所有题目
     * POST请求，试卷名 name
     */
    @RequestMapping(value = "/get_questions", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity getQuestions(HttpServletRequest request) {
        String bankName = request.getParameter("name");
        System.out.print(bankName);
        int index = 1;//题号从第一题开始取
        QuestionList questionList = null;
        try {
            questionList = questionListService.findByNameandNumber(bankName, index);
        }catch (Exception e){
            return ResponseEntity.badRequest().header("").body(null);
        }
        HashMap<Integer,Map<String, Object>> questionLists = new HashMap<>();

        if (questionList ==null){
            ResponseEntity.ok().header("question", "No question!").body(questionLists);
        }
        while (questionList != null) {
            try {
                Map<String, Object> question = new HashMap<>();
                QuestionType type = questionList.getType();
                if (type == QuestionType.Choice) {
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
                    questionLists.put(index,question);
                } else if (type == QuestionType.Judgment) {
                    QuestionJudgment questionJudgment = questionJudgmentService.findByIndex(questionList.getQuestion_id());
                    question.put("type", "judgment");
                    question.put("question", questionJudgment);
                    questionLists.put(index,question);
                } else if (type == QuestionType.Short) {
                    QuestionShort questionShort = questionShortService.findByIndex(questionList.getQuestion_id());
                    question.put("type", "short");
                    question.put("question", questionShort);
                    questionLists.put(index,question);
                } else if (type == QuestionType.Show){
                    QuestionShow questionShow = questionShowService.findByIndex(questionList.getQuestion_id());
                    question.put("type","show");
                    question.put("question",questionShow);
                    questionLists.put(index,question);
                }


            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.badRequest().header("question", "something wrong happened!").body(null);
            }


            index ++;
            try {
                questionList = questionListService.findByNameandNumber(bankName, index);
            }catch (Exception e){
                return ResponseEntity.badRequest().header("").body(null);
            }
        }
        return ResponseEntity.ok().header("question", "All the questions are shown!").body(questionLists);
    }
}
