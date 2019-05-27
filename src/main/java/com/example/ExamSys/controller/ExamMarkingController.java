package com.example.ExamSys.controller;

import com.example.ExamSys.dao.StudentRepository;
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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/ExamMarking")
public class ExamMarkingController {
    private final Logger log = LoggerFactory.getLogger(AccountController.class);
    @Resource
    private QuestionListService questionListService;

    @Resource
    private QuestionAnswerService questionAnswerService;

    @Resource
    private QuestionBankService questionBankService;

    @Autowired
    private StudentRepository studentRepository;

    @Resource
    private UserService userService;

    @Resource
    private QuestionChoiceService questionChoiceService;

    @Resource
    private QuestionJudgmentService questionJudgmentService;

    @Resource
    private TranscriptService transcriptService;

    @Resource
    private  QuestionShortService questionShortService;

    @Autowired
    private QuestionShowService questionShowService;



    /**
     * 选择判断自动打分 试卷名：name  用户名：username
     * **/
    @RequestMapping(value = "/Marking_cj", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity ExamMarkingSave(HttpServletRequest request){
        String name = request.getParameter("name");
        //确认考生
        Optional<User> user = userService.findOneByLogin(request.getParameter("username"));
        if (!user.isPresent()){
            return ResponseEntity.badRequest().header("User","No such user!").body(null);
        }
        Student student = studentRepository.findStuByUser(user.get());
        if (student== null){
            return ResponseEntity.badRequest().header("Student","No such student!").body(null);
        }
        //确认试卷
        QuestionBank questionBank = questionBankService.findByName(name);
        if (questionBank == null){
            return ResponseEntity.badRequest().header("Exam","No such examination!").body(null);
        }
        //确认答案
        QuestionAnswer questionAnswer = questionAnswerService.findByIDandNumber(questionBank.getId(),0);
        if (questionAnswer == null){
            return ResponseEntity.badRequest().header("Answer","No such answer!").body(null);
        }
        if (questionAnswer.isMarked()==true){
            return ResponseEntity.badRequest().header("Marking","The marking is already finished").body(null);
        }

        String[] stuAnswers = questionAnswer.getAnswer().split(";");//学生答案
        List<QuestionList> questionList = questionListService.findByName(questionBank.getName());
        if (questionList==null){
            return ResponseEntity.badRequest().header("Question","No question is included!").body(null);
        }
        Map<String, Integer> score = new HashMap<>();//存放类型和对应的分数
        Map<String, Integer> totalScore = new HashMap<>();//存放对应类型的总分
        int index = 0;//题号 (此处的题号区别于试卷题号，此处题号指试卷的第index个选择或判断)
        for (int i=0; i<questionList.size(); i++){
            QuestionType questionType = questionList.get(i).getType();

            if (questionType== QuestionType.Choice){
                if (stuAnswers.length<index){//防止取从数组取答案时越界
                    break;
                }
                QuestionChoice questionChoice = questionChoiceService.findByIndex(questionList.get(i).getQuestion_id());//找到对应的题目
                String standardAnswer = questionChoice.getAnswer();//找到标准答案
                String type = questionChoice.getType();
                if (totalScore.get(type)==null){
                    totalScore.put(type,1);
                }
                else{
                    totalScore.put(type,totalScore.get(type)+1);
                }
                if (standardAnswer.equals(stuAnswers[index])){//如果答案一致，则对应类型正确题数+1，分数+1
                    if (score.get(type)==null){
                        score.put(type,1);
                    }
                    else{
                        int Score = score.get(type)+1;
                        score.put(type,Score);
                    }
                }

            }
            else if (questionType == QuestionType.Judgment){

                if (stuAnswers.length<index){//防止取从数组取答案时越界
                    break;
                }
                QuestionJudgment questionJudgment = questionJudgmentService.findByIndex(questionList.get(i).getQuestion_id());//找到对应的题目
                String standardAnswer = questionJudgment.getAnswer();//找到标准答案
                String type = questionJudgment.getType();
                if (totalScore.get(type)==null){
                    totalScore.put(type,1);
                }
                else{
                    totalScore.put(type,totalScore.get(type)+1);
                }
                if (standardAnswer.equals(stuAnswers[index])){//如果答案一致，则对应类型正确题数+1
                    if (score.get(type)==null){
                        score.put(type,1);
                    }
                    else{
                        int Score = score.get(type)+1;
                        score.put(type,Score);
                    }
                }
            }
            index++;
        }
        //向成绩表中汇总
        for (String key:totalScore.keySet()){
            Optional<Transcript> transcript_o = transcriptService.findOne(questionBank.getName(),student.getName(),key);
            if (transcript_o.isPresent()){
                Transcript transcript = transcript_o.get();
                transcript.setQuestionBank(questionBank);
                transcript.setStudent(student);
                transcript.setType(key);
                transcript.setTotalScore(totalScore.get(key)+transcript.getTotalScore());
                if (score.get(key)!=null){
                    transcript.setScore(score.get(key)+transcript.getScore());
                }
                else
                {
                    transcript.setScore(0);
                }

                transcriptService.save(transcript);
            }
            else {
                Transcript transcript = new Transcript();
                transcript.setQuestionBank(questionBank);
                transcript.setStudent(student);
                transcript.setType(key);
                transcript.setTotalScore(totalScore.get(key));
                if (score.get(key)!=null){
                    transcript.setScore(score.get(key));
                }
                else
                {
                    transcript.setScore(0);
                }
                transcriptService.save(transcript);
            }

        }
        questionAnswerService.updateisModified(true,questionAnswer.getId());
        return ResponseEntity.ok().body(score);
    }
    /**
     * 查找所有需要阅卷的试卷*/
    @RequestMapping(value = "/tobeMarkedList", method = RequestMethod.GET)
    public ResponseEntity<String> GetunMarkedExam(){
        HashMap<Integer, HashMap<String,String>> tobeMarkedList = questionAnswerService.getTobeMarked();
        JSONArray jsonArray = new JSONArray();
        for (int i=0; i<tobeMarkedList.size();i++){
            try{
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("username",tobeMarkedList.get(i).get("username"));
                jsonObject.put("questionbankname",tobeMarkedList.get(i).get("questionbankname"));
                jsonObject.put("level",tobeMarkedList.get(i).get("level"));
                jsonArray.put(jsonObject);
            }catch (Exception e){
                e.printStackTrace();
                log.info(e.toString());
                return ResponseEntity.badRequest().body(null);
            }
        }
        return ResponseEntity.ok().body(jsonArray.toString());
    }


    /**
     * 查找简答和展示题 试卷名：name  用户名：username
     * **/
    @RequestMapping(value = "/Marking_ss", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity ExamAnswerMarking(HttpServletRequest request){
        String name = request.getParameter("name");
        //确认考生
        Optional<User> user = userService.findOneByLogin(request.getParameter("username"));
        if (!user.isPresent()){
            return ResponseEntity.badRequest().header("User","No such user!").body(null);
        }
        Student student = studentRepository.findStuByUser(user.get());
        if (student== null){
            return ResponseEntity.badRequest().header("Student","No such student!").body(null);
        }
        //确认试卷
        QuestionBank questionBank = questionBankService.findByName(name);
        if (questionBank == null){
            return ResponseEntity.badRequest().header("Exam","No such examination!").body(null);
        }
        //找出试卷对应的题目
        List<QuestionList> questionLists = questionListService.findByName(name);
        if (questionLists == null){
            return ResponseEntity.badRequest().header("QuestionBank","No questions are included!").body(null);
        }
        //用于存储题号和答案
        Map<Integer, String> answer = new HashMap<>();
        QuestionAnswer questionAnswer = new QuestionAnswer();
        for (QuestionList questionList: questionLists ){
            if (questionList.getType()==QuestionType.Short){
                //确认答案
                int index = questionList.getNumber();
                questionAnswer = questionAnswerService.findByIDandNumber(questionBank.getId(),index);
                if (questionAnswer == null){
                    return ResponseEntity.badRequest().header("Answer","No such answer!").body(null);
                }
                if (questionAnswer.isMarked()==true){
                    return ResponseEntity.badRequest().header("Marking","The marking is already finished").body(null);
                }

                answer.put(index, questionAnswer.getAnswer());

            }
            else if(questionList.getType()==QuestionType.Show){

            }
        }
        questionAnswerService.updateisModified(true,questionAnswer.getId());
        return ResponseEntity.ok().body(answer);
    }

    /**
     * 简答和展示题分数保存 试卷名：name  用户名：username 题号：index 分数：score
     * **/
    @RequestMapping(value = "/Marking_save", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity saveSSAnswer(HttpServletRequest request) {
        String name = request.getParameter("name");
        //确认考生
        Optional<User> user = userService.findOneByLogin(request.getParameter("username"));
        if (!user.isPresent()) {
            return ResponseEntity.badRequest().header("User", "No such user!").body(null);
        }
        Student student = studentRepository.findStuByUser(user.get());
        if (student == null) {
            return ResponseEntity.badRequest().header("Student", "No such student!").body(null);
        }
        //确认试卷
        QuestionBank questionBank = questionBankService.findByName(name);
        if (questionBank == null) {
            return ResponseEntity.badRequest().header("Exam", "No such examination!").body(null);
        }
        //找出试卷对应的题目
        QuestionList questionList = questionListService.findByNameandNumber(name, Integer.parseInt(request.getParameter("index")));
        if (questionList == null) {
            return ResponseEntity.badRequest().header("QuestionBank", "No questions are included!").body(null);
        }

        Map<String, Integer> score = new HashMap<>();//存放类型和对应的分数
        Map<String, Integer> totalScore = new HashMap<>();//存放对应类型的总分

        if (questionList.getType() == QuestionType.Short) {
            QuestionShort questionShort = questionShortService.findByIndex(questionList.getQuestion_id());
            String type = questionShort.getType();
            if (totalScore.get(type) == null) {
                totalScore.put(type, 20); //!!!!!默认设置简答分数为20
            } else {
                totalScore.put(type, totalScore.get(type) + 20);
            }
            if (score.get(type) == null) {
                score.put(type, Integer.parseInt(request.getParameter("score")));
            } else {
                score.put(type, totalScore.get(type) + Integer.parseInt(request.getParameter("score")));
            }
        }
        if (questionList.getType() == QuestionType.Show) {
            QuestionShow questionShow = questionShowService.findByIndex(questionList.getQuestion_id());
            String type = questionShow.getType();
            if (totalScore.get(type) == null) {
                totalScore.put(type, 20); //!!!!!默认设置简答分数为20
            } else {
                totalScore.put(type, totalScore.get(type) + 20);
            }
            if (score.get(type) == null) {
                score.put(type, Integer.parseInt(request.getParameter("score")));
            } else {
                score.put(type, totalScore.get(type) + Integer.parseInt(request.getParameter("score")));
            }
        }


        //向成绩表中汇总
        for (String key : totalScore.keySet()) {
            Optional<Transcript> transcript_o = transcriptService.findOne(questionBank.getName(), student.getName(), key);
            if (transcript_o.isPresent()) {  //若已存在，则将分数相加
                Transcript transcript = transcript_o.get();
                transcript.setQuestionBank(questionBank);
                transcript.setStudent(student);
                transcript.setType(key);
                transcript.setTotalScore(totalScore.get(key) + transcript.getTotalScore());
                if (score.get(key) != null) {
                    transcript.setScore(score.get(key) + transcript.getScore());
                } else {
                    transcript.setScore(0);
                }

                transcriptService.save(transcript);
            } else {
                Transcript transcript = new Transcript();
                transcript.setQuestionBank(questionBank);
                transcript.setStudent(student);
                transcript.setType(key);
                transcript.setTotalScore(totalScore.get(key));
                if (score.get(key) != null) {
                    transcript.setScore(score.get(key));
                } else {
                    transcript.setScore(0);
                }
                transcriptService.save(transcript);
            }
        }
        return ResponseEntity.ok().body(null);
    }

}
