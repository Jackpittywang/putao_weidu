package com.putao.wd.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/10.
 */
public class ServiceType implements Serializable {
    private String thumb;
    private String pic;
    private String question;
    private String answer;

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
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
