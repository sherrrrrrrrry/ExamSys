package com.example.ExamSys.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "question_choice_multi")
public class QuestionChoice_multi implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @OneToMany(mappedBy = "questionChoice_multi",cascade=CascadeType.REMOVE,orphanRemoval = true)
//    private Set<Choice> choices = new HashSet<>();

//    @ManyToMany(mappedBy = "multi_choiceQuestions", fetch = FetchType.LAZY)
//    private Set<QuestionBank> questionBankSet = new HashSet<>();

    @Column(name = "content")
    private String content;

    @Column(name = "answer")
    private String answer;

    @Column(name = "type")
    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public Set<Choice> getChoices() {
//        return choices;
//    }
//
//    public void setChoices(Set<Choice> choices) {
//        this.choices = choices;
//    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

//    public void addChoice(Choice choice) {
//        choice.setQuestionChoice_multi(this);
//        this.choices.add(choice);
//    }

//    public Set<QuestionBank> getQuestionBankSet() {
//        return questionBankSet;
//    }
//
//    public void setQuestionBankSet(Set<QuestionBank> questionBankSet) {
//        this.questionBankSet = questionBankSet;
//    }
}
