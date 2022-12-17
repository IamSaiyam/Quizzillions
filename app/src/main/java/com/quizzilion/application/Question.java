package com.quizzilion.application;

import java.util.ArrayList;
import java.util.HashMap;

public class Question {
    private String question;
    private ArrayList<String> options = new ArrayList<>();
    private String correctAnswer;

    public Question(String q, String correct, ArrayList<String> option){
        question = q;
        options = option;
        correctAnswer = correct;
    }

    public Question() {
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
