package com.example.ExamSys.controller;


import com.example.ExamSys.config.Constants;
import com.example.ExamSys.dao.StudentRepository;
import com.example.ExamSys.domain.*;

import com.example.ExamSys.domain.enumeration.QuestionType;
import com.example.ExamSys.service.*;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/Exam")
public class ExamAnswerController {
    @Autowired
    private QuestionListService questionListService;

    @Autowired
    private QuestionAnswerService questionAnswerService;

    @Autowired
    private QuestionBankService questionBankService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionChoiceService questionChoiceService;

    @Autowired
    private ProductionService productionService;
    /**
     *   备注：此方法用于选择，判断，简答。展示题用saveShow方法
     *   保存答案 题号：index, 用户名：username, 试卷名:name, 答案：answer
     **/
    @RequestMapping(value = "/questionanswer_save", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity save(HttpServletRequest request){
        String name = request.getParameter("name");
        int index = Integer.parseInt(request.getParameter("index"));
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
        //确认试题
        QuestionList questionList = questionListService.findByNameandNumber(name,index);
        if (questionList==null){
            return ResponseEntity.badRequest().header("Question","No such question!").body(null);
        }

        QuestionType questionType = questionList.getType();

        if (questionType == QuestionType.Choice || questionType == QuestionType.Judgment) {
            QuestionAnswer questionAnswer = questionAnswerService.findByIDandNumber(questionBank.getId(),0);
            if (questionAnswer==null) {//如果 在答案表里没找到对应的数据
                String answer = "";
                if (questionType == QuestionType.Choice) {//选择题答案
                    String choicetype = questionChoiceService.findByIndex(questionList.getQuestion_id()).getChoicetype();
                    if (choicetype=="1") {//多选
                        String answers[] = request.getParameterValues("answer");
                        for (int i = 0; i < answers.length; i++) {
                            answer += answers[i];
                        }
                    }
                    else{//单选
                        answer = request.getParameter("answer");
                    }
                    answer += ";";
                } else {//判断题答案
                    answer = request.getParameter("answer");
                    answer += ";";
                }
                questionAnswer = new QuestionAnswer();
                questionAnswer.setNumber(0);//选择判断的题号 统一存成0
                questionAnswer.setQuestiontype("0");//将选择判断的类型设为0
                questionAnswer.setAnswer(answer);
                questionAnswer.setStudent(student);
                questionAnswer.setQuestionBank(questionBank);
                questionAnswer.setMarked(false);
                questionAnswerService.save(questionAnswer);
                return ResponseEntity.ok().header("attention","new answer!").body(questionAnswer);
            }
            else{//如果在答案表里找到了对应的数据，那么需要更改答案
                Long id = questionAnswer.getId();
                String answer = questionAnswer.getAnswer();
                String current_answer = "";
                if (questionType == QuestionType.Choice) {//选择题答案
                    String choicetype = questionChoiceService.findByIndex(questionList.getQuestion_id()).getChoicetype();
                    if (choicetype=="1") {//多选
                        String answers[] = request.getParameterValues("answer");

                        for (int i = 0; i < answers.length; i++) {
                            current_answer += answers[i];
                        }
                    }
                    else{//单选
                        current_answer = request.getParameter("answer");
                    }
                } else {//判断题答案
                    current_answer = request.getParameter("answer");
                }
                String new_answer = answerSave(current_answer,index,answer);
                questionAnswer.setNumber(0);//选择判断的题号 统一存成0
                questionAnswer.setQuestiontype("0");//将选择判断的类型设为0
                questionAnswer.setAnswer(new_answer);//将更新后的答案存入数据库
                questionAnswer.setStudent(student);
                questionAnswer.setQuestionBank(questionBank);
                questionAnswer.setMarked(false);
                questionAnswerService.save(questionAnswer);
                return ResponseEntity.ok().header("attention"," answer is updated!").body(questionAnswer);
            }
        }
        else if (questionType == QuestionType.Short){
            QuestionAnswer questionAnswer = questionAnswerService.findByIDandNumber(questionBank.getId(),index);
            if (questionAnswer==null){
                questionAnswer = new QuestionAnswer();
                questionAnswer.setNumber(index);//简答和展示题的题号
                questionAnswer.setQuestiontype("1");//将简答的类型设为1
                questionAnswer.setAnswer(request.getParameter("answer"));
                questionAnswer.setStudent(student);
                questionAnswer.setQuestionBank(questionBank);
                questionAnswer.setMarked(false);
                questionAnswerService.save(questionAnswer);
                return ResponseEntity.ok().header("attention","new answer!").body(questionAnswer);
            }
            else{
                questionAnswer.setNumber(index);//简答和展示题的题号
                questionAnswer.setQuestiontype("1");//将简答的类型设为1
                questionAnswer.setAnswer(request.getParameter("answer"));
                questionAnswer.setStudent(student);
                questionAnswer.setQuestionBank(questionBank);
                questionAnswer.setMarked(false);
                questionAnswerService.save(questionAnswer);
                return ResponseEntity.ok().header("attention"," answer is updated!").body(questionAnswer);
            }
        }
        else if (questionType == QuestionType.Show){
            QuestionAnswer questionAnswer = questionAnswerService.findByIDandNumber(questionBank.getId(),index);
            if (questionAnswer==null){
                questionAnswer = new QuestionAnswer();
                questionAnswer.setNumber(index);//简答和展示题的题号
                questionAnswer.setQuestiontype("2");//将展示的类型设为2
                questionAnswer.setAnswer(request.getParameter("answer"));
                questionAnswer.setStudent(student);
                questionAnswer.setQuestionBank(questionBank);
                questionAnswer.setMarked(false);
                questionAnswerService.save(questionAnswer);
                return ResponseEntity.ok().header("attention","new answer!").body(questionAnswer);
            }
            else{

                String answer = questionAnswer.getAnswer();
                questionAnswer.setNumber(index);//简答和展示题的题号
                questionAnswer.setQuestiontype("2");//将展示的类型设为2
                questionAnswer.setAnswer(updateAnswer(answer,request.getParameter("answer")));//更新展示题答案中的文字部分
                questionAnswer.setStudent(student);
                questionAnswer.setQuestionBank(questionBank);
                questionAnswer.setMarked(false);
                questionAnswerService.save(questionAnswer);
                return ResponseEntity.ok().header("attention"," answer is updated!").body(questionAnswer);
            }
        }
        else{
            return ResponseEntity.badRequest().header("Questiontype","No such questiontype!").body(null);
        }
    }


    /**
     *   备注：展示题
     *   保存答案 题号：index, 用户名：username, 试卷名:name, 作品：file
     **/
    @RequestMapping(value = "/questionanswer_save_show", method = RequestMethod.POST)
    public ResponseEntity saveShow(@RequestParam("name") String name, @RequestParam("username") String username,@RequestParam("index") int index, @RequestParam("files") MultipartFile[] files){
    	System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    	System.out.println(files.length);
        //确认考生
        Optional<User> user = userService.findOneByLogin(username);
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
        //确认试题
        QuestionList questionList = questionListService.findByNameandNumber(name,index);
        if (questionList==null){
            return ResponseEntity.badRequest().header("Question","No such question!").body(null);
        }


        QuestionAnswer questionAnswer = questionAnswerService.findByIDandNumber(questionBank.getId(),index);
        Student stu = studentRepository.findStuByUsername(username);
        if (questionAnswer==null) {
            String url = "";
            if(files==null){
            	return ResponseEntity.ok().header("attention", "no file!").body(null);
            }
            	
            for (MultipartFile file : files) {
                File upl = null;

                try {
                    upl = File.createTempFile(username + "_", file.getOriginalFilename());
                    IOUtils.copy(file.getInputStream(), new FileOutputStream(upl));
                    url += productionService.upLoadStudentProduction(username, upl);
                    url += "<==>";
                } catch (IOException e) {
                    e.printStackTrace();
                    return ResponseEntity.badRequest().header("File", "File upload failed!").body(null);
                }
            }
            questionAnswer = new QuestionAnswer();
            questionAnswer.setNumber(index);//简答和展示题的题号
            questionAnswer.setQuestiontype("2");//将展示的类型设为2
            questionAnswer.setAnswer(url);//答案即作品存储的地址
            questionAnswer.setStudent(student);
            questionAnswer.setQuestionBank(questionBank);
            questionAnswer.setMarked(false);
            questionAnswerService.save(questionAnswer);
            return ResponseEntity.ok().header("attention", "new answer!").body(questionAnswer);
        }

        else {//已有答案，则覆盖
            String url = "";
            for (MultipartFile file : files) {
                File upl = null;
                try {
                    upl = File.createTempFile(username + "_", file.getOriginalFilename());
                    IOUtils.copy(file.getInputStream(), new FileOutputStream(upl));
                    url += productionService.upLoadStudentProduction(username, upl);
                    url += "<==>";
                } catch (IOException e) {
                    e.printStackTrace();
                    return ResponseEntity.badRequest().header("File", "File upload failed!").body(null);
                }
            }
            questionAnswer.setNumber(index);//简答和展示题的题号
            questionAnswer.setQuestiontype("2");//将展示的类型设为2
            questionAnswer.setAnswer(updateProduction(questionAnswer.getAnswer(),url));
            questionAnswer.setStudent(student);
            questionAnswer.setQuestionBank(questionBank);
            questionAnswer.setMarked(false);
            questionAnswerService.save(questionAnswer);
            return ResponseEntity.ok().header("attention", " answer is updated!").body(questionAnswer);
        }

    }
    public String answerSave(String current_answer, int index, String answer){
        String[] answers = answer.split(";");
        if (answers.length<index){
            answer = answer + current_answer + ";";
            return answer;
        }
        else{
            answers[index-1] = current_answer;
            answer = "";
            for (int i=0; i<answers.length; i++){
                answer += answers[i];
                answer += ";";
            }
            return answer;
        }
    }

    /**
     * 用于更新展示题中的文字部分**/
    public String updateAnswer(String content, String answer){
        String[] answers = content.split("<==>");
        String result = "";
        result+=answer;
        result+="<==>";
        for (String temp:answers){
            if (temp.contains(Constants.STUDENT_PRODUCTION_PATH)){//包含作品路径
                result+=temp;
                result+="<==>";
            }
        }
        return result;
    }

    /**
     * 用于更新展示题中的作品部分**/
    public String updateProduction(String content, String url){
        String[] answers = content.split("<==>");
        String result = "";
        for (String temp:answers){
            if (!temp.contains(Constants.STUDENT_PRODUCTION_PATH)){//不包含作品路径，则为展示题中的文字部分
                result+= temp;
                result+="<==>";
            }
        }
        result+=url;
        return result;
    }


    /**
     * 如果是以做过的试卷，则展示答案
     * 题号：index, 用户名：username, 试卷名:name
     * **/
    @RequestMapping(value = "/questionanswer_show", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity getAnswer(HttpServletRequest request){
        String name = request.getParameter("name");
        int index = Integer.parseInt(request.getParameter("index"));
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
        //确认试题
        QuestionList questionList = questionListService.findByNameandNumber(name,index);
        if (questionList==null){
            return ResponseEntity.badRequest().header("Question","No such question!").body(null);
        }
        QuestionType questionType = questionList.getType();
        Map<String, String> answerMap = new HashMap<>();
        if (questionType == QuestionType.Choice || questionType == QuestionType.Judgment) {
            QuestionAnswer questionAnswer = questionAnswerService.findByIDandNumber(questionBank.getId(),0);
            if (questionAnswer==null){//没找到答案
                return ResponseEntity.badRequest().header("CorJ","No such answer!1").body(null);
            }
            else{
                String answer = questionAnswer.getAnswer();
                String currentAnswer = getAnswer(answer,index);
                if (currentAnswer == null){
                    return ResponseEntity.badRequest().header("CorJ","No such answer!2").body(null);
                }
                else{
                    answerMap.put("answer",currentAnswer);
                    return ResponseEntity.ok().header("CorJ","Answer existing").body(answerMap);
                }
            }
        }
        else if (questionType == QuestionType.Short){
            QuestionAnswer questionAnswer = questionAnswerService.findByIDandNumber(questionBank.getId(),index);
            if (questionAnswer==null){//没找到答案
                return ResponseEntity.badRequest().body(null);
            }
            else{
                String answer = questionAnswer.getAnswer();
                if (answer == null){
                    return ResponseEntity.badRequest().header("short","No such answer!").body(null);
                }
                else{
                    answerMap.put("answer",answer);
                    return ResponseEntity.ok().body(answerMap);
                }
            }
        }
        else if (questionType == QuestionType.Show){
            return null;
        }
        return ResponseEntity.badRequest().body(null);
    }

    public String getAnswer(String answer, int index){
        String[] answers = answer.split(";");
        if (index>answers.length){
            return null;
        }
        return answers[index-1];
    }

}
