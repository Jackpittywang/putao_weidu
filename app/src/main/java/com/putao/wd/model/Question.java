package com.putao.wd.model;

import java.io.Serializable;
import java.util.List;

/**
 * 问题列表
 * Created by Administrator on 2015/12/25.
 */
public class Question implements Serializable {
    //    private QuestionInfo question;
//    private List<String> reply;
    private String id;//消息ID
    private String message;//内容
    private int type;//0用户提问 1客服回答
    private String create_time;//0用户提问 1客服回答

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id='" + id + '\'' +
                ", message='" + message + '\'' +
                ", type=" + type +
                ", create_time=" + create_time +
                '}';
    }
}
