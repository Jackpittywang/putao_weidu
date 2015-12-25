package com.putao.wd.model;

import java.io.Serializable;
import java.util.List;

/**
 * 问题列表
 * Created by Administrator on 2015/12/25.
 */
public class Question implements Serializable {
    private QuestionInfo question;
    private List<String> reply;

    public QuestionInfo getQuestion() {
        return question;
    }

    public void setQuestion(QuestionInfo question) {
        this.question = question;
    }

    public List<String> getReply() {
        return reply;
    }

    public void setReply(List<String> reply) {
        this.reply = reply;
    }

    @Override
    public String toString() {
        return "Question{" +
                "question=" + question +
                ", reply=" + reply +
                '}';
    }
}
