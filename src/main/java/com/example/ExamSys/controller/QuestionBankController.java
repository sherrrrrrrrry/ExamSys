package com.example.ExamSys.controller;

import com.example.ExamSys.dao.QuestionBankRepository;
import com.example.ExamSys.domain.*;
import com.example.ExamSys.service.QuestionBankService;
import com.example.ExamSys.service.QuestionChoiceService;
import com.example.ExamSys.service.QuestionShortService;
import com.example.ExamSys.service.QuestionJudgmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/questionbank")
public class QuestionBankController {

    @Resource
    private QuestionBankService questionBankService;
    @Autowired
    private QuestionBankRepository questionBankRepository;

    @Resource
    private QuestionShortService questionShortService;

    @Resource
    private QuestionChoiceService questionChoiceService;

    @Resource
    private QuestionJudgmentService questionJudgmentService;

    QuestionBank questionBank = new QuestionBank();
    Map<Integer, Question> questionList = new HashMap<>();



    /**
     * 添加完所有试题后，保存题库
     */
    @RequestMapping("/questionbank_save")
    public String save(HttpServletRequest request){
        questionBank.setLevel(request.getParameter("level").charAt(0));
        questionBankService.save(questionBank);
        return "题库保存成功！";
    }



    @GetMapping(value = "/hello")
    public List<QuestionBank> getBankList(){
        return questionBankRepository.findAll();
    }




    //添加简答题：content = , type = , index =
    @RequestMapping(value ="/questionshort_save", method = RequestMethod.POST, headers = "Accept=application/json")
    public Object saveShort(HttpServletRequest request){
        int index = Integer.parseInt(request.getParameter("index"));
        String type = request.getParameter("type");
        if (!questionList.containsKey(index)) {
            QuestionShort questionShort = new QuestionShort();
            questionShort.setContent(request.getParameter("content"));
            questionShort.setType(type);
            questionShortService.save(questionShort);
            questionBank.getShortQuestions().add(questionShort);
            Long id = questionShort.getId();
            Question question = new Question(id, type);
            questionList.put(index, question);
            return questionShort;
        }
        else{
            Long id = questionList.get(index).getId();
            QuestionShort questionShort = new QuestionShort();
            questionShort.setContent(request.getParameter("content"));
            questionShort.setType(type);
            questionShort.setId(id);
            questionShortService.save(questionShort);
            return questionShort;

        }

    }

    //单选 title= ，1= ，2= ，3= ，4= ， optionNum= , option1= ...... ,choicetype =
    @RequestMapping(value = "/questionchoice_save", method = RequestMethod.POST, headers = "Accept=application/json")
    public Object saveChoice(HttpServletRequest request){

        int index = Integer.parseInt(request.getParameter("index"));
        String type = request.getParameter("type");
        if (!questionList.containsKey(index)) {
            QuestionChoice questionChoice = new QuestionChoice();
            int optionNum = Integer.parseInt(request.getParameter("optionNum"));
            for (int i = 0; i < optionNum; i++) {
                Choice choice = new Choice();
                choice.setContent(request.getParameter("" + i));
                choice.setIndex(i);
                questionChoice.addChoice(choice);
            }
            String answers[] = request.getParameterValues("option");
            String answer = "";
            for (int i = 0; i < answers.length; i++) {
                answer += answers[i];
            }
            questionChoice.setContent(request.getParameter("title"));
            questionChoice.setAnswer(answer);
            questionChoice.setType(request.getParameter("type"));
            questionChoice.setChoicetype(request.getParameter("choicetype"));
            questionChoiceService.save(questionChoice);
            questionBank.getChoiceQuestions().add(questionChoice);

            Long id = questionChoice.getId();
            Question question = new Question(id, type);
            questionList.put(index, question);
            return questionChoice;
        }
        else{
            Long id = questionList.get(index).getId();
            QuestionChoice questionChoice = new QuestionChoice();
            questionChoice.setId(id);
            int optionNum = Integer.parseInt(request.getParameter("optionNum"));
            for (int i = 0; i < optionNum; i++) {
                Choice choice = new Choice();
                choice.setContent(request.getParameter("" + i));
                choice.setIndex(i);
                questionChoice.addChoice(choice);
            }
            String answers[] = request.getParameterValues("option");
            String answer = "";
            for (int i = 0; i < answers.length; i++) {
                answer += answers[i];
            }
            questionChoice.setContent(request.getParameter("title"));
            questionChoice.setAnswer(answer);
            questionChoice.setType(request.getParameter("type"));
            questionChoice.setChoicetype(request.getParameter("choicetype"));
            questionChoiceService.save(questionChoice);
            return questionChoice;
        }
    }

    //判断题：
    @RequestMapping(value = "/questionjudgment_save")
    public Object saveJudgment(HttpServletRequest request){
        int index = Integer.parseInt(request.getParameter("index"));
        String type = request.getParameter("type");
        if (!questionList.containsKey(index)) {
            QuestionJudgment questionJudgment = new QuestionJudgment();
            questionJudgment.setContent(request.getParameter("content"));
            questionJudgment.setType(request.getParameter("type"));
            questionJudgment.setAnswer(request.getParameter("answer"));
            questionJudgmentService.save(questionJudgment);
            questionBank.getQuestionJudgments().add(questionJudgment);
            Long id = questionJudgment.getId();
            Question question = new Question(id, type);
            questionList.put(index, question);
            return questionJudgment;
        }
        else{
            Long id = questionList.get(index).getId();
            QuestionJudgment questionJudgment = new QuestionJudgment();
            questionJudgment.setId(id);
            questionJudgment.setContent(request.getParameter("content"));
            questionJudgment.setType(request.getParameter("type"));
            questionJudgment.setAnswer(request.getParameter("answer"));
            questionJudgmentService.save(questionJudgment);
            return questionJudgment;
        }
    }

//    public Object save

}
