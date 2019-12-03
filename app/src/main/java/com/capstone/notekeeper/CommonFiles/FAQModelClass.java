package com.capstone.notekeeper.CommonFiles;

public class FAQModelClass {

    String question;
    String answer;

    public FAQModelClass() {
    }

    public FAQModelClass(String pQuestion, String pAnswer) {
        question = pQuestion;
        answer = pAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
