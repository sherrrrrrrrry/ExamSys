package com.example.ExamSys.controller;

import com.example.ExamSys.dao.TranscriptRepository;
import com.example.ExamSys.domain.Question;
import com.example.ExamSys.domain.QuestionBank;
import com.google.gson.JsonArray;
import jdk.internal.org.objectweb.asm.util.TraceAnnotationVisitor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("Examhistory")
public class ExamHistoryController {
    private final Logger LOG = LoggerFactory.getLogger(AdminController.class);
    @Autowired
    private TranscriptRepository transcriptRepository;
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
    
}
