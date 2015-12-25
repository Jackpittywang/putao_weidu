package com.putao.wd.model;

import java.io.Serializable;

/**
 * 问题信息
 * Created by Administrator on 2015/12/25.
 */
public class QuestionInfo implements Serializable {
    private String qa_id;
    private String message;
    private String create_time;

    public String getQa_id() {
        return qa_id;
    }

    public void setQa_id(String qa_id) {
        this.qa_id = qa_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    @Override
    public String toString() {
        return "QuestionInfo{" +
                "qa_id='" + qa_id + '\'' +
                ", message='" + message + '\'' +
                ", create_time='" + create_time + '\'' +
                '}';
    }
}
