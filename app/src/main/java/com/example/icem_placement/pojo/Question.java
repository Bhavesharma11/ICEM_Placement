package com.example.icem_placement.pojo;

public class Question {

    public String questionID;
    public String question;

    public Question(String questionID, String question) {
        this.questionID = questionID;
        this.question = question;
    }

    public Question() {}

    public String getQuestionID() { return questionID; }

    public void setQuestionID(String questionID) { this.questionID = questionID; }

    public String getQuestion() { return question; }

    public void setQuestion(String question) { this.question = question; }
}
