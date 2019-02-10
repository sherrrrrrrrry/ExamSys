package com.example.ExamSys.controller;

import com.example.ExamSys.dao.QuestionBankRepository;
import com.example.ExamSys.domain.QuestionBank;
import com.example.ExamSys.domain.QuestionShort;
import com.example.ExamSys.service.QuestionBankService;
import com.example.ExamSys.service.QuestionShortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/questionbank")
public class QuestionBankController {

    @Resource
    private QuestionBankService questionBankService;
    @Autowired
    private QuestionBankRepository questionBankRepository;

    @Resource
    private QuestionShortService questionShortService;


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
    @RequestMapping(value ="/questionshort_savetest", method = RequestMethod.POST, headers = "Accept=application/json")
    public Object saveShort(HttpServletRequest request, @RequestParam String questiontype){

        if(questiontype.equals("0")) {
            QuestionShort questionShort1 = new QuestionShort();
            questionShort1.setContent(request.getParameter("content"));
            questionShort1.setType(request.getParameter("type"));
            questionShortService.save(questionShort1);
            return questionShort1;
        }
        return null;

    }
}