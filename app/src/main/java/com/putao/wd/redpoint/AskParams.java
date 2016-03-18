package com.putao.wd.redpoint;

/**
 * Created by zhanghao on 2016/3/18.
 */
public class AskParams {
    private static final long serialVersionUID = 1L;
    private String auth;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }
}
