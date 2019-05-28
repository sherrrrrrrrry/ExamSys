package com.example.ExamSys.controller;

import com.example.ExamSys.dao.StudentRepository;
import com.example.ExamSys.domain.*;
import com.example.ExamSys.domain.enumeration.QuestionType;
import com.example.ExamSys.service.*;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

/**
 * 组卷功能，这里只提供从QuedtionList里读现有试卷的功能*/

@RestController
@RequestMapping("/questioncomposition")
public class QuestionCompositionController {

    private final Logger LOG = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private QuestionBankService questionBankService;

    @Autowired
    private QuestionShortService questionShortService;

    @Autowired
    private QuestionShowService questionShowService;

    @Autowired
    private QuestionChoiceService questionChoiceService;

    @Autowired
    private QuestionJudgmentService questionJudgmentService;

    @Autowired
    private QuestionListService questionListService;

    @Autowired
    private StudentRepository studentRepository;


//    /**
//     * 获取所有试卷名
//     */
//
//    @RequestMapping("/questionbank_Name")
//    public Map<Integer, String> getQuestionBankName() {
//        List<String> questionBankNames = questionBankService.getBankNames();
//        if (questionBankNames != null) {
//            Map<Integer, String> questionBankName = new HashMap<>();
//            for (int i = 0; i < questionBankNames.size(); i++) {
//                questionBankName.put(i, questionBankNames.get(i));
//            }
//            return questionBankName;
//        } else {
//            return null;
//        }
//    }
    /**
     * 根据学生的level，随机分配一张试卷
     * POST：username*/
    @RequestMapping(value = "/getBankName",method = RequestMethod.POST, headers="Accept=application/json")
    public ResponseEntity<String> getBankName_random(HttpServletRequest request){
        LOG.info("正在分配试卷");
        String username = request.getParameter("username");
        int level = studentRepository.getLevel(username);
        Long stuID = studentRepository.getIDbyUsername(username);
        String bankname = questionBankService.getBankNamesbyLevel_Random(level,stuID);//随机获得该等级下的一张试卷
        if (bankname == null){
            return ResponseEntity.ok().body(null);//如果所有试卷都做过了，就返回空
        }
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("bankname",bankname);
            jsonObject.put("level",level);
            return ResponseEntity.ok().body(jsonObject.toString());
        }catch (Exception e){
            LOG.error("创建JSONObject 失败！");
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * POST请求，试卷名 name 及题号 index
     */
    @RequestMapping(value = "/get_questions", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<Map<String, Object>> getQuestions(HttpServletRequest request) {
        String bankName = request.getParameter("name");
        System.out.print(bankName);
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
                    if (questiontype.equals("0") ) {
                        question.put("type", "singlechoice");
                    }
                    if (questiontype.equals("1")) {
                        question.put("type", "multichoice");
                    }
                    int choicenumber = questionChoice.getChoices().size();
                    question.put("choicenumber",choicenumber);
                    question.put("question", questionChoice);
                } else if (type == QuestionType.Judgment) {
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
                return ResponseEntity.ok().body(question);

            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.badRequest().header("question", "something wrong happened!").body(null);
            }


        } else {
            return ResponseEntity.ok().header("question", "No such question!").body(null);
        }

    }
}
