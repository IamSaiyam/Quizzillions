package com.quizzilion.application;

import java.util.ArrayList;
import java.util.HashMap;

public class Quiz {
    private String name;
    private Boolean isPrivate;
    private Integer noOfQuestions;
    private Integer timeLimit;
    private String id;
    private String creator;

    private ArrayList<Question> questionSet = new ArrayList<>();
    private ArrayList<Participant> Participants = new ArrayList<>();

    public Quiz(String name, Boolean isPrivate, Integer noOfQuestions, Integer timeLimit, String id, String creator, ArrayList<Question> questionSet, ArrayList<Participant> participants) {
        this.name = name;
        this.isPrivate = isPrivate;
        this.noOfQuestions = noOfQuestions;
        this.timeLimit = timeLimit;
        this.id = id;
        this.creator = creator;
        this.questionSet = questionSet;
        Participants = participants;
    }

    public Quiz() {
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public ArrayList<Participant> getParticipants() {
        return Participants;
    }

    public void setParticipants(ArrayList<Participant> participants) {
        this.Participants = participants;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getPrivate() {
        return isPrivate;
    }

    public void setPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public Integer getNoOfQuestions() {
        return noOfQuestions;
    }

    public void setNoOfQuestions(Integer noOfQuestions) {
        this.noOfQuestions = noOfQuestions;
    }

    public Integer getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(Integer timeLimit) {
        this.timeLimit = timeLimit;
    }

    public ArrayList<Question> getQuestionSet() {
        return questionSet;
    }

    public void setQuestionSet(ArrayList<Question> questionSet) {
        this.questionSet = questionSet;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
