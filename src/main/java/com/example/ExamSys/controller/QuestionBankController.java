package com.example.ExamSys.controller;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.example.ExamSys.dao.QuestionBankRepository;
import com.example.ExamSys.domain.*;
import com.example.ExamSys.domain.enumeration.QuestionType;
import com.example.ExamSys.domain.enumeration.UserType;
import com.example.ExamSys.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/questionbank")
public class QuestionBankController {

    private final Logger log = LoggerFactory.getLogger(AdminController.class);

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

    @Resource
    private QuestionShowService questionShowService;
//    QuestionBank questionBank = new QuestionBank();
//    Map<Integer, Question> questionList = new HashMap<>();


    /**
     * 新建题库
     */
    @RequestMapping(value = "/questionbank_save", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity save(HttpServletRequest request){
        String name = request.getParameter("name");
        try {
            QuestionBank questionBank = questionBankService.findByName(name);
            if (questionBank==null) {
                questionBank = new QuestionBank();
                questionBank.setLevel(Integer.parseInt(request.getParameter("level")));
                //！！！name不能重复！！！
                questionBank.setName(name);
                questionBankService.save(questionBank);
                return ResponseEntity.ok().header("Insert successfully!").body(questionBank);
            }
            else{
                return ResponseEntity.badRequest().header("questionbank","This bank is already existing!").body(null);
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().header("questionbank","Insert failed").body(null);
        }
    }


//    @GetMapping(value = "/hello")
//    public List<QuestionBank> getBankList(){
//        return questionBankRepository.findAll();
//    }



    /**
     * 添加简答题：content = , type = , index =
     */
    @RequestMapping(value ="/questionshort_save", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity saveShort(HttpServletRequest request){
        int index = Integer.parseInt(request.getParameter("index"));
        String questionBankName = request.getParameter("name");
        try{
        //找到对应题库
        QuestionBank questionBank = questionBankService.findByName(questionBankName);
        //找到题库名字对应的map
        QuestionList question = questionListService.findByNameandNumber(questionBankName,index);

        if (question==null) {
            QuestionShort questionShort = new QuestionShort();
            questionShort.setContent(request.getParameter("content"));
            questionShort.setType(request.getParameter("type"));
            questionShortService.save(questionShort);
            //更新到QuestionBank
            questionBank.getShortQuestions().add(questionShort);
            questionBankService.save(questionBank);
            //更新到QuestionList
            Long id = questionShort.getId();
        //    Question question = new Question(id, QuestionType.Short);
            QuestionList q = new QuestionList();
            q.setName(questionBankName);
            q.setType(QuestionType.Short);
            q.setNumber(index);
            q.setQuestion_id(id);
            questionListService.save(q);
        //    return questionShort;
            return ResponseEntity.ok().header("result","Insert Successfully!").body(questionShort);
        }
        else{
            Long id = question.getQuestion_id();
            QuestionShort questionShort = new QuestionShort();
            questionShort.setContent(request.getParameter("content"));
            questionShort.setType(request.getParameter("type"));
            questionShort.setId(id);
            questionShortService.save(questionShort);
            return ResponseEntity.ok().header("result","Insert Successfully!").body(questionShort);
        }
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().header("result","Insert failed!").body(null);
        }

    }

    /**
     * 添加作品展示题：content = , type = , index = , name =
     */
    @RequestMapping(value ="/questionshow_save", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity saveShow(HttpServletRequest request){
        int index = Integer.parseInt(request.getParameter("index"));
        String questionBankName = request.getParameter("name");
        try{
            //找到对应题库
            QuestionBank questionBank = questionBankService.findByName(questionBankName);
            if (questionBank == null){
                return ResponseEntity.badRequest().header("Exam","No such examination!").body(null);
            }
            //找到题库名字对应的map
            QuestionList question = questionListService.findByNameandNumber(questionBankName,index);

            if (question==null) {
                QuestionShow questionShow = new QuestionShow();
                questionShow.setContent(request.getParameter("content"));
                questionShow.setType(request.getParameter("type"));
                questionShowService.save(questionShow);
                //更新到QuestionBank
                questionBank.getShowQuestions().add(questionShow);
                questionBankService.save(questionBank);
                //更新到QuestionList
                Long id = questionShow.getId();
                //    Question question = new Question(id, QuestionType.Short);
                QuestionList q = new QuestionList();
                q.setName(questionBankName);
                q.setType(QuestionType.Show);
                q.setNumber(index);
                q.setQuestion_id(id);
                questionListService.save(q);
                //    return questionShort;
                return ResponseEntity.ok().header("result","Insert Successfully!").body(questionShow);
            }
            else{
                Long id = question.getQuestion_id();
                QuestionShow questionShow = new QuestionShow();
                questionShow.setContent(request.getParameter("content"));
                questionShow.setType(request.getParameter("type"));
                questionShow.setId(id);
                questionShowService.save(questionShow);
                return ResponseEntity.ok().header("result","Insert Successfully!").body(questionShow);
            }
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().header("result","Insert failed!").body(null);
        }

    }

    //单选 title= ，1= ，2= ，3= ，4= ， optionNum= , option= ...... ,choicetype =
    @RequestMapping(value = "/questionchoice_save", method = RequestMethod.POST, headers = "Accept=application/json")
    public Object saveChoice(HttpServletRequest request){
        int index = Integer.parseInt(request.getParameter("index"));
        String questionBankName = request.getParameter("name");
        //找到对应题库
        QuestionBank questionBank = questionBankService.findByName(questionBankName);
        //找到题库名字对应的map
        QuestionList question = questionListService.findByNameandNumber(questionBankName,index);
        if (question==null) {
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
            Set<QuestionChoice> questionChoicesSet = new HashSet<>();
            questionChoicesSet = questionBank.getChoiceQuestions();
            questionChoicesSet.add(questionChoice);
            questionBank.setChoiceQuestions(questionChoicesSet);
            questionBankService.save(questionBank);

            Long id = questionChoice.getId();

            QuestionList q = new QuestionList();
            q.setName(questionBankName);
            q.setType(QuestionType.Choice);
            q.setNumber(index);
            q.setQuestion_id(id);
            questionListService.save(q);
            return questionChoice;
        }
        else{
            Long id = question.getQuestion_id();
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
    @RequestMapping(value = "/questionjudgment_save", method = RequestMethod.POST, headers = "Accept=application/json")
    public Object saveJudgment(HttpServletRequest request){
        int index = Integer.parseInt(request.getParameter("index"));
        String questionBankName = request.getParameter("name");
        //找到对应题库
        QuestionBank questionBank = questionBankService.findByName(questionBankName);
        //找到题库名字对应的map
        QuestionList question = questionListService.findByNameandNumber(questionBankName,index);

        if (question==null) {
            QuestionJudgment questionJudgment = new QuestionJudgment();
            questionJudgment.setContent(request.getParameter("content"));
            questionJudgment.setType(request.getParameter("type"));
            questionJudgment.setAnswer(request.getParameter("answer"));
            questionJudgmentService.save(questionJudgment);
            questionBank.getQuestionJudgments().add(questionJudgment);
            questionBankService.save(questionBank);

            Long id = questionJudgment.getId();
            QuestionList q = new QuestionList();
            q.setName(questionBankName);
            q.setType(QuestionType.Judgment);
            q.setNumber(index);
            q.setQuestion_id(id);
            questionListService.save(q);

            return questionJudgment;
        }
        else{
            Long id = question.getQuestion_id();
            QuestionJudgment questionJudgment = new QuestionJudgment();
            questionJudgment.setId(id);
            questionJudgment.setContent(request.getParameter("content"));
            questionJudgment.setType(request.getParameter("type"));
            questionJudgment.setAnswer(request.getParameter("answer"));
            questionJudgmentService.save(questionJudgment);
            return questionJudgment;
        }
    }

    /**删除试题**/
    @RequestMapping(value = "deleteQuestion", method = RequestMethod.POST, headers = "Accept=application/json")
    public boolean deleteQuestion(HttpServletRequest request){
        int index = Integer.parseInt(request.getParameter("index"));
        String questionBankName = request.getParameter("name");
        //找到对应题库
        QuestionBank questionBank = questionBankService.findByName(questionBankName);
        //找到题库名字对应的map
        QuestionList question = questionListService.findByNameandNumber(questionBankName,index);


        if (question!=null){
            QuestionType type = question.getType();
            Long id = question.getQuestion_id();
            //根据题目类型 去对应表中删除记录
            if (type == QuestionType.Choice){
                questionChoiceService.delete(id);
                questionListService.delete(question.getId());
                return true;
            }
            else if (type == QuestionType.Judgment){
                questionJudgmentService.delete(id);
                questionListService.delete(question.getId());
                return true;
            }
            else if (type == QuestionType.Short){
                questionShortService.delete(id);
                questionListService.delete(question.getId());
                return true;
            }
            else if (type == QuestionType.Show){
                questionShowService.delete(id);
                questionListService.delete(question.getId());
                return true;
            }
            else
                return false;
        }
        else
            return false;

    }


}
