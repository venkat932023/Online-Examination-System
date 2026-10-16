package com.exam.model;

import java.util.ArrayList;
import java.util.List;

public class Question {
    private String id;
    private String questionText;
    private List<String> options;
    private String correctAnswer;
    private int marks;

    public Question() {
        this.options = new ArrayList<>();
    }

    public Question(String id, String questionText, List<String> options, String correctAnswer, int marks) {
        this.id = id;
        this.questionText = questionText;
        this.options = new ArrayList<>(options);
        this.correctAnswer = correctAnswer.toUpperCase();
        this.marks = marks;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getQuestionText() { return questionText; }
    public void setQuestionText(String questionText) { this.questionText = questionText; }
    public List<String> getOptions() { return options; }
    public void setOptions(List<String> options) { this.options = new ArrayList<>(options); }
    public String getCorrectAnswer() { return correctAnswer; }
    public void setCorrectAnswer(String correctAnswer) { this.correctAnswer = correctAnswer.toUpperCase(); }
    public int getMarks() { return marks; }
    public void setMarks(int marks) { this.marks = marks; }
}
