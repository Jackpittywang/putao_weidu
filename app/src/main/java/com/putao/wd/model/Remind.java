package com.putao.wd.model;

import java.util.List;

/**
 * 通知--提醒
 * Created by Administrator on 2015/12/25.
 */
public class Remind extends Page {
//    private List<RemindDetail> list;
//
//    public List<RemindDetail> getList() {
//        return list;
//    }
//
//    public void setList(List<RemindDetail> data) {
//        this.list = data;
//    }
//
//    @Override
//    public String toString() {
//        return "Remind{" +
//                "list=" + list +
//                '}';
//    }

    private int question_id;//评论id
    private String question;//问题
    private String nick_name;//葡萄籽
    private String head_img;//葡萄籽图片
    private String answer;//回复
    private String release_time;//回复时间

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public String getRelease_time() {
        return release_time;
    }

    public void setRelease_time(String release_time) {
        this.release_time = release_time;
    }

    @Override
    public String toString() {
        return "Remind{" +
                "answer='" + answer + '\'' +
                ", question_id=" + question_id +
                ", question='" + question + '\'' +
                ", nick_name='" + nick_name + '\'' +
                ", head_img='" + head_img + '\'' +
                ", release_time='" + release_time + '\'' +
                '}';
    }
}
