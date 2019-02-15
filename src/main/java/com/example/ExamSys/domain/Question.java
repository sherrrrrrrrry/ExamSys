package com.example.ExamSys.domain;

public class Question {

    private Long id;
    private String type;

    public Question(){
        this.id = null;
        this.type = null;
    }
    public Question(Long id, String type){
        this.type = type;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
