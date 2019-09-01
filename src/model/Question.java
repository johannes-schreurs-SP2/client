package model;

import org.json.JSONArray;

import java.util.ArrayList;

public class Question {

    private int id;
    private String question;
    private Boolean hasMultipleAnswer;


    public Question() {

    }

    public Question(int id, String question, Boolean hasMultipleAnswer) {
        this.id = id;
        this.question = question;
        this.hasMultipleAnswer = hasMultipleAnswer;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Boolean getHasMultipleAnswer() {
        return hasMultipleAnswer;
    }

    public void setHasMultipleAnswer(Boolean hasMultipleAnswer) {
        this.hasMultipleAnswer = hasMultipleAnswer;
    }

}
