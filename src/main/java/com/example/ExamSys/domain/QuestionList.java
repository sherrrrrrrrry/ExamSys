package com.example.ExamSys.domain;

import com.example.ExamSys.domain.enumeration.QuestionType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Map;

@Entity
@Table(name = "question_list")
public class QuestionList implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

//    @Column(name = "mapoflist")
//    private Map<Integer, Question> list;

    @Column(name = "number")
    public int number;

    @Column(name = "type")
    public QuestionType type;

    @Column(name = "question_id")
    public Long question_id;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public int getNumber() { return number; }

    public void setNumber(int number) { this.number = number; }

    public QuestionType getType() { return type; }

    public void setType(QuestionType type) { this.type = type; }

    public Long getQuestion_id() { return question_id; }

    public void setQuestion_id(Long question_id) { this.question_id = question_id; }
}
