package com.example.ExamSys.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "question_judgment")
public class QuestionJudgment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "type")
    private String type;

    @Column(name = "answer")
    private String answer;

    @ManyToMany(mappedBy = "shortQuestions", fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    private Set<QuestionBank> questionBankSet = new HashSet<>();


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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAnswer() { return answer; }

    public void setAnswer(String answer) { this.answer = answer; }

    public Set<QuestionBank> getQuestionBankSet() {
        return questionBankSet;
    }

    @JsonBackReference
    public void setQuestionBankSet(Set<QuestionBank> questionBankSet) {
        this.questionBankSet = questionBankSet;
    }
}
