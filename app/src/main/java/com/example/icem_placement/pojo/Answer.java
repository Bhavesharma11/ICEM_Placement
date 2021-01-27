package com.example.icem_placement.pojo;

public class Answer {
    private String question;
    private String questionID;
    private String answer;
    private String answerID;
    private String username;

    public Answer(String question, String questionID, String answer, String answerID, String username) {
        this.question = question;
        this.questionID = questionID;
        this.answer = answer;
        this.answerID = answerID;
        this.username = username;
    }

    public Answer() {}

    public String getQuestion() { return question; }

    public void setQuestion(String question) { this.question = question; }

    public String getQuestionID() { return questionID; }

    public void setQuestionID(String questionID) { this.questionID = questionID; }

    public String getAnswer() { return answer; }

    public void setAnswer(String answer) { this.answer = answer; }

    public String getAnswerID() { return answerID; }

    public void setAnswerID(String answerID) { this.answerID = answerID; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

}
