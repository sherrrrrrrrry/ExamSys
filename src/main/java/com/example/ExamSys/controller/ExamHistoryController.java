package com.example.ExamSys.controller;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ExamSys.config.Constants;
import com.example.ExamSys.dao.QuestionAnswerRepository;
import com.example.ExamSys.dao.StudentRepository;
import com.example.ExamSys.dao.TranscriptRepository;
import com.example.ExamSys.domain.QuestionAnswer;
import com.example.ExamSys.domain.QuestionBank;
import com.example.ExamSys.domain.Student;
import com.example.ExamSys.domain.Transcript;
import com.example.ExamSys.domain.User;
import com.example.ExamSys.service.QuestionBankService;
import com.example.ExamSys.service.UserService;

@RestController
@RequestMapping("/Examhistory")
public class ExamHistoryController {
    private final Logger LOG = LoggerFactory.getLogger(AdminController.class);
    @Autowired
    private TranscriptRepository transcriptRepository;
    
    @Autowired
    QuestionBankService questionBankService;
    
    @Autowired
    UserService userService;
    
    @Autowired
    StudentRepository studentRepository;
    
    @Autowired
    QuestionAnswerRepository questionAnswerRepository;
    /**
     * 获取学生历次考试成绩**/
    @RequestMapping(value = "/list", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity getExamHistory(HttpServletRequest request){

        Set<QuestionBank> questionBankSet = transcriptRepository.findQuestionBank(request.getParameter("username"));
        if (questionBankSet==null){
            return ResponseEntity.ok().header("Exam","No exam!").body(null);
        }
        else{
            JSONArray questionBankArray = new JSONArray();
            for (QuestionBank questionBank:questionBankSet){
                try{
                    JSONObject questionObj = new JSONObject();
                    questionObj.put("questionBankName",questionBank.getName());
                    questionObj.put("questionBankLevel",questionBank.getLevel());
                    questionBankArray.put(questionObj);
                }catch (Exception e){
                    LOG.error("Creat JsonObject Error!");
                    return ResponseEntity.badRequest().header("Json","Creat JsonObject Error!").body(null);
                }
            }
            return ResponseEntity.ok().body(questionBankArray.toString());
        }

    }
    
    /**
     * 雷达图参数
     */
    @RequestMapping(value = "/getRedarMap", method = RequestMethod.GET)
    public ResponseEntity<String> getRedarMap(@RequestParam(value = "name") String name, @RequestParam(value = "username") String username){
    	
    	 Optional<User> user = userService.findOneByLogin(username);
         if (!user.isPresent()){
             return ResponseEntity.badRequest().header("User","No such user!").body(null);
         }
         Student student = studentRepository.findStuByUser(user.get());
         if (student== null){
             return ResponseEntity.badRequest().header("Student","No such student!").body(null);
         }
         
    	QuestionBank questionBank = questionBankService.findByName(name);
        if (questionBank == null){
            return ResponseEntity.badRequest().header("Exam","No such examination!").body(null);
        }
                
    	ArrayList<Transcript> list = (ArrayList<Transcript>) transcriptRepository.findAllByQuestionBankIdAndStudentId(questionBank.getId(), student.getId());

		JSONObject json = new JSONObject();
    	
    	for(Transcript transcript : list) {
    		float score = transcript.getScore();
    		try {
        		json.put(transcript.getType(), (int)Math.ceil(score/transcript.getTotalScore() * 100));
    		} catch(Exception e) {
    			return ResponseEntity.badRequest().header("Json","Construct json data wrong!").body(null);
    		}
    	}
    	
    	for(String type : Constants.ABILITY_TYPE) {
    		try {
				if(!json.has(type)) {
					json.put(type, 0);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return ResponseEntity.badRequest().header("Json","Construct json data wrong!").body(null);
			}
    	}
    	
    	return ResponseEntity.ok().body(json.toString());
    }
    
    @RequestMapping(value = "/getMarkedPapers", method = RequestMethod.GET)
    public ResponseEntity<String> getMarkedPapers(@RequestParam(value = "studentname") String studentName){
    	ArrayList<QuestionAnswer> questionAnswerList = (ArrayList<QuestionAnswer>) questionAnswerRepository.findAllMarkedByLogin(studentName);
    	JSONArray jsonArr = new JSONArray();
    	try {
	    	for(QuestionAnswer questionAnswer : questionAnswerList) {
	    		JSONObject json = new JSONObject();
	    		json.put("paperName", questionAnswer.getQuestionBank().getName());
	    		json.put("paperLevel", questionAnswer.getQuestionBank().getLevel());
	    		json.put("teacher", questionAnswer.getTeacher().getUser().getLogin());
	    		ArrayList<Transcript> transcriptList = (ArrayList<Transcript>)transcriptRepository.findAllByQuestionBankIdAndStudentLogin(questionAnswer.getQuestionBank().getId(), studentName);
	    		int score = 0;
	    		for(Transcript transcript : transcriptList) {
	    			score += transcript.getScore();
	    		}
	    		json.put("score", score);
	    		jsonArr.put(json);
	    	}
    	} catch(Exception e) {
    		e.printStackTrace();
    		return ResponseEntity.badRequest().header("Json","Construct json data wrong!").body(null);
    	}
    	
    	return ResponseEntity.ok().body(jsonArr.toString());
    }
    
}
