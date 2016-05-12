package com.putao.wd.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/12.
 */
public class ServiceReply implements Serializable {
    private String question;//我提的问题
    private String answer;//服务号给我的回答

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
