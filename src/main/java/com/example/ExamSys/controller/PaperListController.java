package com.example.ExamSys.controller;

import com.example.ExamSys.domain.Question;
import com.example.ExamSys.domain.QuestionBank;
import com.example.ExamSys.service.QuestionBankService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/paperListAdmin")
public class PaperListController {

    private final Logger log = LoggerFactory.getLogger(PaperListController.class);
    @Autowired
    QuestionBankService questionBankService;

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
}
