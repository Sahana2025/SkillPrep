package com.skill.model;

import javax.persistence.*;

@Entity
@Table(name = "quiz_results")
public class QuizResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int questionId;
    @Column(nullable = false)
    private String question;

    @Column(nullable = false)
    private String keywords;

    @Column(nullable = false)
    private String userAnswer;

    @Column(nullable = false)
    private boolean matched;

    // Default constructor
    public QuizResult() {
    }

    // Constructor with parameters
    public QuizResult(String question, String keywords, String userAnswer, boolean matched) {
        this.question = question;
        this.keywords = keywords;
        this.userAnswer = userAnswer;
        this.matched = matched;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
     public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionid) {
        this.questionId = questionid;
    }


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public boolean isMatched() {
        return matched;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
    }
}
