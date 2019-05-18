package com.example.ExamSys.domain;

import com.example.ExamSys.domain.enumeration.QuestionType;


/**
 * 临时*/
public class Question {

    private Long id;
    private QuestionType type;

    public Question(){
        this.id = null;
        this.type = null;
    }
    public Question(Long id, QuestionType type){
        this.type = type;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QuestionType getType() {
        return type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }
}
