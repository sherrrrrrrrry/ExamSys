package com.example.ExamSys.controller;

import com.example.ExamSys.dao.StudentRepository;
import com.example.ExamSys.domain.*;
import com.example.ExamSys.domain.enumeration.QuestionType;
import com.example.ExamSys.service.*;
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

    /**
     * 选择判断打分 试卷名：name  用户名：username
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

        String[] stuAnswers = questionAnswer.getAnswer().split(";");//学生答案
        List<QuestionList> questionList = questionListService.findByName(questionBank.getName());
        if (questionList==null){
            return ResponseEntity.badRequest().header("Question","No question is included!").body(null);
        }
        Map<String, Integer> score = new HashMap<>();//存放类型和对应的分数
        for (int i=0; i<questionList.size(); i++){
            QuestionType questionType = questionList.get(i).getType();
            if (questionType== QuestionType.Choice){
                int index = questionList.get(i).getNumber();//题号
                if (stuAnswers.length<index){//防止取从数组取答案时越界
                    break;
                }
                QuestionChoice questionChoice = questionChoiceService.findByIndex(questionList.get(i).getQuestion_id());//找到对应的题目
                String standardAnswer = questionChoice.getAnswer();//找到标准答案
                String type = questionChoice.getType();

                if (standardAnswer.equals(stuAnswers[index-1])){//如果答案一致，则对应类型正确题数+1
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
                int index = questionList.get(i).getNumber();//题号
                if (stuAnswers.length<index){//防止取从数组取答案时越界
                    break;
                }
                QuestionJudgment questionJudgment = questionJudgmentService.findByIndex(questionList.get(i).getQuestion_id());//找到对应的题目
                String standardAnswer = questionJudgment.getAnswer();//找到标准答案
                String type = questionJudgment.getType();
                if (standardAnswer.equals(stuAnswers[index-1])){//如果答案一致，则对应类型正确题数+1
                    if (score.get(type)==null){
                        score.put(type,1);
                    }
                    else{
                        int Score = score.get(type)+1;
                        score.put(type,Score);
                    }
                }
            }
        }
        //向成绩表中汇总
        for (String key:score.keySet()){
            Optional<Transcript> transcript_o = transcriptService.findOne(questionBank.getName(),student.getName(),key);
            if (transcript_o.isPresent()){
                Transcript transcript = transcript_o.get();
                transcript.setQuestionBank(questionBank);
                transcript.setStudent(student);
                transcript.setType(key);
                transcript.setScore(score.get(key));
                transcriptService.save(transcript);
                return ResponseEntity.ok().body(transcript);
            }
            else {
                Transcript transcript = new Transcript();
                transcript.setQuestionBank(questionBank);
                transcript.setStudent(student);
                transcript.setType(key);
                transcript.setScore(score.get(key));
                transcriptService.save(transcript);
                return ResponseEntity.ok().body(transcript);
            }

        }
        return ResponseEntity.badRequest().header("Score", "Score saving failed!").body(null);
    }
}
