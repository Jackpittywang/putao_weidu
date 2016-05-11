package com.putao.wd.model;

import java.io.Serializable;

/**
 * 活动详情
 * Created by guchenkai on 2015/12/8.
 */
public class GpushMessageMsgCenter implements Serializable {

    private boolean reply;
    private boolean praise;
    private boolean remind;
    private boolean notice;

    public boolean isReply() {
        return reply;
    }

    public void setReply(boolean reply) {
        this.reply = reply;
    }

    public boolean isPraise() {
        return praise;
    }

    public void setPraise(boolean praise) {
        this.praise = praise;
    }

    public boolean isRemind() {
        return remind;
    }

    public void setRemind(boolean remind) {
        this.remind = remind;
    }

    public boolean isNotice() {
        return notice;
    }

    public void setNotice(boolean notice) {
        this.notice = notice;
    }
}
