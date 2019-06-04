package com.example.ExamSys.controller;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.ExamSys.dao.QuestionBankRepository;
import com.example.ExamSys.dao.QuestionListRepository;
import com.example.ExamSys.domain.QuestionAnswer;
import com.example.ExamSys.domain.QuestionBank;
import com.example.ExamSys.domain.QuestionChoice;
import com.example.ExamSys.domain.QuestionJudgment;
import com.example.ExamSys.domain.QuestionList;
import com.example.ExamSys.domain.QuestionShort;
import com.example.ExamSys.domain.QuestionShow;
import com.example.ExamSys.domain.enumeration.QuestionType;
import com.example.ExamSys.service.QuestionBankService;
import com.example.ExamSys.service.QuestionChoiceService;
import com.example.ExamSys.service.QuestionJudgmentService;
import com.example.ExamSys.service.QuestionListService;
import com.example.ExamSys.service.QuestionShortService;
import com.example.ExamSys.service.QuestionShowService;


@RestController
@RequestMapping("/paperListAdmin")
public class PaperListController {

    private final Logger log = LoggerFactory.getLogger(PaperListController.class);
    @Autowired
    QuestionBankService questionBankService;

    @Autowired
    QuestionListService questionListService;
    
    @Autowired
    QuestionListRepository questionListRepository;

    @Autowired
    QuestionChoiceService questionChoiceService;

    @Autowired
    QuestionJudgmentService questionJudgmentService;

    @Autowired
    QuestionShortService questionShortService;

    @Autowired
    QuestionShowService questionShowService;
    
    @Autowired
    QuestionBankRepository questionBankRepository;
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
        int index = 1;//题号从第一题开始取
        QuestionList questionList = null;
        try {
            questionList = questionListService.findByNameandNumber(bankName, index);
        }catch (Exception e){
            return ResponseEntity.badRequest().header("").body(null);
        }
        List<Map<String, Object>> questionLists = new LinkedList<>();

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
                    questionLists.add(question);
                } else if (type == QuestionType.Judgment) {
                    QuestionJudgment questionJudgment = questionJudgmentService.findByIndex(questionList.getQuestion_id());
                    question.put("type", "judgment");
                    question.put("question", questionJudgment);
                    questionLists.add(question);
                } else if (type == QuestionType.Short) {
                    QuestionShort questionShort = questionShortService.findByIndex(questionList.getQuestion_id());
                    question.put("type", "short");
                    question.put("question", questionShort);
                    questionLists.add(question);
                } else if (type == QuestionType.Show){
                    QuestionShow questionShow = questionShowService.findByIndex(questionList.getQuestion_id());
                    question.put("type","show");
                    question.put("question",questionShow);
                    questionLists.add(question);
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
    
    
    @RequestMapping(value = "/delete_bank", method = RequestMethod.POST)
    public ResponseEntity deleteQuestionBank(HttpServletRequest request) {
    	String bankName = request.getParameter("name");
    	
    	QuestionBank questionBank = questionBankRepository.findByName(bankName);
    	if(questionBank == null)
    		return ResponseEntity.ok().body(null);
    	Set<QuestionAnswer> questionAnswers = questionBank.getQuestionAnswers();
    	for(QuestionAnswer qa : questionAnswers) {
    		if(qa.getQuestiontype().equals("2")) {
    			String[] urls = qa.getAnswer().split("<==>");
    			for(int i=0;i<urls.length;i++) {
    				try {
    					File file = new File(urls[i]);
    					file.delete();
    				} catch(Exception e) {
    					continue;
    				}
    			}
    		}
    	}
		questionBankRepository.delete(questionBank);
		questionListRepository.deleteByName(questionBank.getName());
		return ResponseEntity.ok().body(null);
    }
}
