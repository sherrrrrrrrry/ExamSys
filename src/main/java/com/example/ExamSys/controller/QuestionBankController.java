package com.example.ExamSys.controller;

import com.example.ExamSys.dao.QuestionBankRepository;
import com.example.ExamSys.domain.Choice;
import com.example.ExamSys.domain.QuestionBank;
import com.example.ExamSys.domain.QuestionChoice;
import com.example.ExamSys.domain.QuestionShort;
import com.example.ExamSys.service.QuestionBankService;
import com.example.ExamSys.service.QuestionChoiceService;
import com.example.ExamSys.service.QuestionShortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    QuestionBank questionBank = new QuestionBank();

    @RequestMapping("/save")
    public String save(){
        QuestionBank questionBank = new QuestionBank();
        questionBank.setLevel(1);
        questionBankService.save(questionBank);
        return "题库保存成功！";
    }


    @GetMapping(value = "/hello")
    public List<QuestionBank> getBankList(){
        return questionBankRepository.findAll();
    }

    /**
     * 添加一个学生记录
    */
//@PostMapping(value = "/hello")
//public Student addStu(@RequestParam("name") String name,@RequestParam("age") Integer age){
//        Student stu=new Student();
//        stu.setName(name);
//        stu.setAge(age);
//        return studentResitory.save(stu);
//        }
//}

    //添加简答题
    @RequestMapping(value ="/questionshort_save", method = RequestMethod.POST, headers = "Accept=application/json")
    public Object saveShort(HttpServletRequest request){
            QuestionShort questionShort = new QuestionShort();
            questionShort.setContent(request.getParameter("content"));
            questionShort.setType(request.getParameter("type"));
            questionShortService.save(questionShort);
//            questionBank.setLevel(1);
            questionBank.getShortQuestions().add(questionShort);
//            questionBankService.save(questionBank);
            return questionShort;
    }

    //单选 title= ，1= ，2= ，3= ，4= ， optionNum= , answer=
    @RequestMapping(value = "/questionchoice_save", method = RequestMethod.POST, headers = "Accept=application/json")
    public Object saveChoice(HttpServletRequest request){

        QuestionChoice questionChoice = new QuestionChoice();
        int optionNum = Integer.parseInt(request.getParameter("optionNum"));
        for (int i =1;i <=optionNum; i++){
            Choice choice = new Choice();
            choice.setContent(request.getParameter(""+ i));
            choice.setIndex((char)('A'+ (i-1)));
            questionChoice.addChoice(choice);
        }

        String answer = "";
        for (int i =1; i<=optionNum; i++){
            if (request.getParameter("option"+i).equals("on")){
                answer = answer + i;
            }
        }

        questionChoice.setContent(request.getParameter("title"));
        questionChoice.setAnswer(answer);
        questionChoice.setType(request.getParameter("type"));
        questionChoice.setChoicetype(request.getParameter("choicetype"));
        questionChoiceService.save(questionChoice);
        questionBank.getChoiceQuestions().add(questionChoice);
        return questionChoice;
    }
}
