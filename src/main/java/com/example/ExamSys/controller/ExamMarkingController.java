package com.example.ExamSys.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.codec.binary.Base64;
import org.apache.tomcat.jni.Time;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.ExamSys.config.Constants;
import com.example.ExamSys.dao.QuestionAnswerRepository;
import com.example.ExamSys.dao.StudentRepository;
import com.example.ExamSys.dao.TeacherRepository;
import com.example.ExamSys.dao.TranscriptRepository;
import com.example.ExamSys.domain.QuestionAnswer;
import com.example.ExamSys.domain.QuestionBank;
import com.example.ExamSys.domain.QuestionChoice;
import com.example.ExamSys.domain.QuestionJudgment;
import com.example.ExamSys.domain.QuestionList;
import com.example.ExamSys.domain.QuestionShort;
import com.example.ExamSys.domain.QuestionShow;
import com.example.ExamSys.domain.Student;
import com.example.ExamSys.domain.Teacher;
import com.example.ExamSys.domain.Transcript;
import com.example.ExamSys.domain.User;
import com.example.ExamSys.domain.enumeration.QuestionType;
import com.example.ExamSys.service.QuestionAnswerService;
import com.example.ExamSys.service.QuestionBankService;
import com.example.ExamSys.service.QuestionChoiceService;
import com.example.ExamSys.service.QuestionJudgmentService;
import com.example.ExamSys.service.QuestionListService;
import com.example.ExamSys.service.QuestionShortService;
import com.example.ExamSys.service.QuestionShowService;
import com.example.ExamSys.service.TranscriptService;
import com.example.ExamSys.service.UserService;

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
    
    @Autowired
    private TeacherRepository teacherRepository;

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

    @Autowired
    private QuestionAnswerRepository questionAnswerRepository;
    
    @Autowired
    private TranscriptRepository transcriptRepository;

    /**
     * 选择判断自动打分 试卷名：name  用户名：username
     * **/
    @RequestMapping(value = "/Marking_cj", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity ExamMarkingSave(HttpServletRequest request){
//        String name = request.getParameter("name");
//        //确认考生
//        Optional<User> user = userService.findOneByLogin(request.getParameter("username"));
//        if (!user.isPresent()){
//            return ResponseEntity.badRequest().header("User","No such user!").body(null);
//        }
//        Student student = studentRepository.findStuByUser(user.get());
//        if (student== null){
//            return ResponseEntity.badRequest().header("Student","No such student!").body(null);
//        }
//        //确认试卷
//        QuestionBank questionBank = questionBankService.findByName(name);
//        if (questionBank == null){
//            return ResponseEntity.badRequest().header("Exam","No such examination!").body(null);
//        }
//        //确认答案
//        QuestionAnswer questionAnswer = questionAnswerService.findByIDandNumber(questionBank.getId(),0);
//        if (questionAnswer == null){
//            return ResponseEntity.badRequest().header("Answer","No such answer!").body(null);
//        }
//        if (questionAnswer.isMarked()==true){
//            return ResponseEntity.badRequest().header("Marking","The marking is already finished").body(null);
//        }
//
//        String[] stuAnswers = questionAnswer.getAnswer().split(";");//学生答案
//        List<QuestionList> questionList = questionListService.findByName(questionBank.getName());
//        if (questionList==null){
//            return ResponseEntity.badRequest().header("Question","No question is included!").body(null);
//        }
//        Map<String, Integer> score = new HashMap<>();//存放类型和对应的分数
//        Map<String, Integer> totalScore = new HashMap<>();//存放对应类型的总分
//        int index = 0;//题号 (此处的题号区别于试卷题号，此处题号指试卷的第index个选择或判断)
//        for (int i=0; i<questionList.size(); i++){
//            QuestionType questionType = questionList.get(i).getType();
//
//            if (questionType== QuestionType.Choice){
//                if (stuAnswers.length<index){//防止取从数组取答案时越界
//                    break;
//                }
//                QuestionChoice questionChoice = questionChoiceService.findByIndex(questionList.get(i).getQuestion_id());//找到对应的题目
//                String standardAnswer = questionChoice.getAnswer();//找到标准答案
//                String type = questionChoice.getType();
//                if (totalScore.get(type)==null){
//                    totalScore.put(type,2);
//                }
//                else{
//                    totalScore.put(type,totalScore.get(type)+2);
//                }
//                if (standardAnswer.equals(stuAnswers[index])){//如果答案一致，则对应类型正确题数+2，分数+2
//                    if (score.get(type)==null){
//                        score.put(type,2);
//                    }
//                    else{
//                        int Score = score.get(type)+2;
//                        score.put(type,Score);
//                    }
//                }
//
//            }
//            else if (questionType == QuestionType.Judgment){
//
//                if (stuAnswers.length<index){//防止取从数组取答案时越界
//                    break;
//                }
//                QuestionJudgment questionJudgment = questionJudgmentService.findByIndex(questionList.get(i).getQuestion_id());//找到对应的题目
//                String standardAnswer = questionJudgment.getAnswer();//找到标准答案
//                String type = questionJudgment.getType();
//                if (totalScore.get(type)==null){
//                    totalScore.put(type,2);
//                }
//                else{
//                    totalScore.put(type,totalScore.get(type)+2);
//                }
//                if (standardAnswer.equals(stuAnswers[index])){//如果答案一致，则对应类型正确题数+2
//                    if (score.get(type)==null){
//                        score.put(type,2);
//                    }
//                    else{
//                        int Score = score.get(type)+2;
//                        score.put(type,Score);
//                    }
//                }
//            }
//            index++;
//        }
//        //向成绩表中汇总
//        for (String key:totalScore.keySet()){
//            Optional<Transcript> transcript_o = transcriptService.findOne(questionBank.getName(),student.getName(),key);
//            if (transcript_o.isPresent()){
//                Transcript transcript = transcript_o.get();
//                transcript.setQuestionBank(questionBank);
//                transcript.setStudent(student);
//                transcript.setType(key);
//                transcript.setTotalScore(totalScore.get(key)+transcript.getTotalScore());
//                if (score.get(key)!=null){
//                    transcript.setScore(score.get(key)+transcript.getScore());
//                }
//                else
//                {
//                    transcript.setScore(0);
//                }
//
//                transcriptService.save(transcript);
//            }
//            else {
//                Transcript transcript = new Transcript();
//                transcript.setQuestionBank(questionBank);
//                transcript.setStudent(student);
//                transcript.setType(key);
//                transcript.setTotalScore(totalScore.get(key));
//                if (score.get(key)!=null){
//                    transcript.setScore(score.get(key));
//                }
//                else
//                {
//                    transcript.setScore(0);
//                }
//                transcriptService.save(transcript);
//            }
//
//        }
//        questionAnswerService.updateisModified(true,questionAnswer.getId());
        return ResponseEntity.ok().body(60);
    }
    /**
     * 查找所有需要阅卷的试卷*/
    @RequestMapping(value = "/tobeMarkedList", method = RequestMethod.GET)
    public ResponseEntity<String> GetunMarkedExam(){
        HashMap<Integer, HashMap<String,String>> tobeMarkedList = questionAnswerService.getTobeMarked();
        JSONArray jsonArray = new JSONArray();
        if(tobeMarkedList == null){
        	return ResponseEntity.ok().body(jsonArray.toString());
        }
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
        //找出待阅卷的试题
        List<QuestionAnswer> questionAnswerList = questionAnswerService.getTobeMarkedByBankandStu(questionBank.getId(),student.getId());
        if (questionAnswerList == null){
            return ResponseEntity.badRequest().header("questionAnswer","No such answer!").body(null);
        }
//        //找出试卷对应的题目
//        List<QuestionList> questionLists = questionListService.findByName(name);
//        if (questionLists == null){
//            return ResponseEntity.badRequest().header("QuestionBank","No questions are included!").body(null);
//        }
        //用于存储题号,题目和答案
        List<Map<String,String>> answerList = new LinkedList<>();
        QuestionAnswer questionAnswer = new QuestionAnswer();
        int n=0;
        for (QuestionAnswer questionAnswerEntity: questionAnswerList ){
            Map<String, String> answer = new HashMap<>();
            QuestionList questionList;
            try{//根据答案找到对应的原题
                questionList = questionListService.findByNameandNumber(name,questionAnswerEntity.getNumber());
                if(questionList == null)
                	continue;
            }catch (Exception e){
                return ResponseEntity.badRequest().header("No such question!").body(null);
            }
            if (questionList.getType()==QuestionType.Short){
                //获取题目
                String content = "";
                QuestionShort questionShort = questionShortService.findByIndex(questionList.getQuestion_id());
                if (questionShort==null){
                    return ResponseEntity.badRequest().header("Answer","No such answer!").body(null);
                }
                content = questionShort.getContent();

                //确认答案
                int index = questionList.getNumber();
                questionAnswer = questionAnswerService.findByIDandNumber(questionBank.getId(),index, student.getId());
                if (questionAnswer == null){
                    return ResponseEntity.badRequest().header("Answer","No such answer!").body(null);
                }

                if (questionAnswer.isMarked()==true){
                    return ResponseEntity.badRequest().header("Marking","The marking is already finished").body(null);
                }

                answer.put("index", index+"");
                answer.put("content", content);
                answer.put("answer", questionAnswer.getAnswer());
                answer.put("type", questionList.getType().name());
                answerList.add(answer);
                n++;
            }
            else if(questionList.getType()==QuestionType.Show){//作品展示题答案显示
                //获取题目
                String content = "";
                QuestionShow questionShow = questionShowService.findByIndex(questionList.getQuestion_id());
                if (questionShow==null){
                    return ResponseEntity.badRequest().header("Answer","No such answer!").body(null);
                }
                content = questionShow.getContent();

                //确认答案
                int index = questionList.getNumber();
                questionAnswer = questionAnswerService.findByIDandNumber(questionBank.getId(),index, student.getId());
                if (questionAnswer == null){
                    return ResponseEntity.badRequest().header("Answer","No such answer!").body(null);
                }
                if (questionAnswer.isMarked()==true){
                    return ResponseEntity.badRequest().header("Marking","The marking is already finished").body(null);
                }

                answer.put("index", index+"");
                answer.put("content", content);
                String narrativeAnswer = getnarrativeAnswer(questionAnswer.getAnswer());
                String encodeUrls = getUrls(questionAnswer.getAnswer());
                answer.put("narrativeAnswer", narrativeAnswer);
                answer.put("urls",encodeUrls);
                answer.put("type", questionList.getType().name());
                answerList.add(answer);
                n++;
            }
        }

        return ResponseEntity.ok().body(answerList);
    }

    public String getUrls(String answer){
        String[] answers = answer.split("<==>");
        String[] urls = new String[answers.length];
        String encodeUrls ="";
        int num=0;
        for (String temp:answers){
            if (temp.contains(Constants.STUDENT_PRODUCTION_PATH)){//包含作品路径
                urls[num] = temp;
                num++;
            }
        }
        File[] files = new File[num];
        try {
            for (int i = 0; i < num; i++) {
                files[i] = new File(urls[i]);
                FileInputStream inputStream = new FileInputStream(files[i]);

                byte[] bytes = new byte[inputStream.available()];

                inputStream.read(bytes, 0, inputStream.available());
                inputStream.close();

                String data = Base64.encodeBase64String(bytes);
                encodeUrls += data;
                encodeUrls +="<==>";
            }
        }catch(Exception e){
            e.printStackTrace();
            log.info(e.toString());
            return null;
        }
        return encodeUrls;
    }

    public String getnarrativeAnswer(String answer){
        String[] answers = answer.split("<==>");
        String result = null;
        for (String temp:answers){
            if (!temp.contains(Constants.STUDENT_PRODUCTION_PATH)){//不包含作品路径，则为作品展示题答案中的文字描述部分
                result = temp;
                break;
            }
        }
        return result;
    }



    /**
     * 简答和展示题分数保存 试卷名：name  用户名：username 题号：index 分数：score
     * **/
    @Transactional
    @RequestMapping(value = "/Marking_save", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity saveSSAnswer(HttpServletRequest request) {
    	
        String name = request.getParameter("name");
        //确认考生
        Optional<User> user = userService.findOneByLogin(request.getParameter("studentname"));
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
        QuestionAnswer questionAnswer = questionAnswerService.findByIDandNumber(questionBank.getId(),0, student.getId());
        if (questionAnswer == null){
            return ResponseEntity.badRequest().header("Answer","No such answer!").body(null);
        }
        if (questionAnswer.isMarked()==true){
            return ResponseEntity.badRequest().header("Marking","The marking is already finished").body(null);
        }
        Teacher teacher = teacherRepository.findOneByLogin(request.getParameter("teachername"));
        if(teacher == null) {
        	return ResponseEntity.badRequest().header("Student", "No such teacher!").body(null);
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
                    totalScore.put(type,2);
                }
                else{
                    totalScore.put(type,totalScore.get(type)+2);
                }
                if (standardAnswer.equals(stuAnswers[index])){//如果答案一致，则对应类型正确题数+2，分数+2
                    if (score.get(type)==null){
                        score.put(type,2);
                    }
                    else{
                        int Score = score.get(type)+2;
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
                    totalScore.put(type,2);
                }
                else{
                    totalScore.put(type,totalScore.get(type)+2);
                }
                if (standardAnswer.equals(stuAnswers[index])){//如果答案一致，则对应类型正确题数+2
                    if (score.get(type)==null){
                        score.put(type,2);
                    }
                    else{
                        int Score = score.get(type)+2;
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
//        questionAnswerService.updateisModified(true,questionAnswer.getId());
        questionAnswerRepository.updateisModifiedAndTeacher(true, teacher, questionAnswer.getId());
        
        //找出试卷对应的题目

//      int index = Integer.parseInt(request.getParameter("index"));
        
        String indexs = request.getParameter("index");
        String[] indexList = indexs.split(",");
        String scores = request.getParameter("score");
        if (scores==""){
            return ResponseEntity.ok().body(null);
        }
        String[] scoreList = scores.split(",");
//        QuestionList questionList = questionListService.findByNameandNumber(name, index);

        score = new HashMap<>();//存放类型和对应的分数
        totalScore = new HashMap<>();//存放对应类型的总分
        for(int i = 0; i<indexList.length; i++) {
        	int index1 = 0;
        	try {
        		index1 = Integer.parseInt(indexList[i]);
        	} catch(Exception e) {
        		return ResponseEntity.badRequest().header("Number", "Wrong Number!").body(null);
        	}
        	QuestionList questionList1 = questionListService.findByNameandNumber(name, index1);
        	
            if (questionList1 == null) {
                return ResponseEntity.badRequest().header("QuestionBank", "No questions are included!").body(null);
            }
            
            if (questionList1.getType() == QuestionType.Short) {
                QuestionShort questionShort = questionShortService.findByIndex(questionList1.getQuestion_id());
                String type = questionShort.getType();
                if (totalScore.get(type) == null) {
                    totalScore.put(type, 20); //!!!!!默认设置简答分数为20
                } else {
                    totalScore.put(type, totalScore.get(type) + 20);
                }
                if (score.get(type) == null) {
                	try {
                        score.put(type, Integer.parseInt(scoreList[i]));
                	} catch(Exception e) {
                		return ResponseEntity.badRequest().header("Number", "Wrong Number!").body(null);
                	}
                } else {
                	try {
                        score.put(type, score.get(type) + Integer.parseInt(scoreList[i]));                		
                	} catch(Exception e) {
                		return ResponseEntity.badRequest().header("Number", "Wrong Number!").body(null);
                	}
                }
            }
            if (questionList1.getType() == QuestionType.Show) {
                QuestionShow questionShow = questionShowService.findByIndex(questionList1.getQuestion_id());
                String type = questionShow.getType();
                if (totalScore.get(type) == null) {
                    totalScore.put(type, 20); //!!!!!默认设置简答分数为20
                } else {
                    totalScore.put(type, totalScore.get(type) + 20);
                }
                if (score.get(type) == null) {
                	try {
                        score.put(type, Integer.parseInt(scoreList[i]));
                	} catch(Exception e) {
                		return ResponseEntity.badRequest().header("Number", "Wrong Number!").body(null);
                	}
                } else {
                	try {
                        score.put(type, score.get(type) + Integer.parseInt(scoreList[i]));
                	} catch(Exception e) {
                		return ResponseEntity.badRequest().header("Number", "Wrong Number!").body(null);
                	}
                }
            }
        
            QuestionAnswer questionAnswer1 = questionAnswerService.findByIDandNumber(questionBank.getId(), index1, student.getId());
            questionAnswerRepository.updateMarkedAndTeacherAndScore(true, teacher, Integer.parseInt(scoreList[i]), questionAnswer1.getId());
        }

//        Map<String, Integer> score = new HashMap<>();//存放类型和对应的分数
//        Map<String, Integer> totalScore = new HashMap<>();//存放对应类型的总分
//
//        if (questionList.getType() == QuestionType.Short) {
//            QuestionShort questionShort = questionShortService.findByIndex(questionList.getQuestion_id());
//            String type = questionShort.getType();
//            if (totalScore.get(type) == null) {
//                totalScore.put(type, 20); //!!!!!默认设置简答分数为20
//            } else {
//                totalScore.put(type, totalScore.get(type) + 20);
//            }
//            if (score.get(type) == null) {
//                score.put(type, Integer.parseInt(request.getParameter("score")));
//            } else {
//                score.put(type, totalScore.get(type) + Integer.parseInt(request.getParameter("score")));
//            }
//        }
//        if (questionList.getType() == QuestionType.Show) {
//            QuestionShow questionShow = questionShowService.findByIndex(questionList.getQuestion_id());
//            String type = questionShow.getType();
//            if (totalScore.get(type) == null) {
//                totalScore.put(type, 20); //!!!!!默认设置简答分数为20
//            } else {
//                totalScore.put(type, totalScore.get(type) + 20);
//            }
//            if (score.get(type) == null) {
//                score.put(type, Integer.parseInt(request.getParameter("score")));
//            } else {
//                score.put(type, totalScore.get(type) + Integer.parseInt(request.getParameter("score")));
//            }
//        }
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
        ArrayList<Transcript> transcriptList = (ArrayList<Transcript>) transcriptRepository.findAllByQuestionBankIdAndStudentLogin(questionBank.getId(), request.getParameter("studentname"));
        if(transcriptList != null) {
        	int resultsScore = 0;
        	int zongFen = 0;

        	for(Transcript t:transcriptList) {
        		resultsScore += t.getScore();
        		zongFen += t.getTotalScore();
        	}
        	
        	if(resultsScore >= Math.floor(zongFen * 0.6)) {
        		studentRepository.updateLevelById(student.getLevel()+1, student.getId());
        	}
        }
        
        return ResponseEntity.ok().body(null);
    }

}
