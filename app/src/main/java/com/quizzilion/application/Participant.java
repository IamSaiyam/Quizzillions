package com.quizzilion.application;

public class Participant {
    String pName;
    String pEmail;
    Float score;
    Integer correct;
    Integer incorrect;

    public Participant(String pName, String pEmail, Float score, Integer correct, Integer incorrect) {
        this.pName = pName;
        this.pEmail = pEmail;
        this.score = score;
        this.correct = correct;
        this.incorrect = incorrect;
    }

    public Participant() {
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpEmail() {
        return pEmail;
    }

    public void setpEmail(String pEmail) {
        this.pEmail = pEmail;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Integer getCorrect() {
        return correct;
    }

    public void setCorrect(Integer correct) {
        this.correct = correct;
    }

    public Integer getIncorrect() {
        return incorrect;
    }

    public void setIncorrect(Integer incorrect) {
        this.incorrect = incorrect;
    }
}
