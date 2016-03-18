package com.putao.wd.redpoint;

/**
 * Created by Administrator on 2016/3/18.
 */
public class ReplyClientBody extends ReplyBody{
    private String clientInfo;

    public ReplyClientBody(String clientInfo) {
        this.clientInfo = clientInfo;
    }

    public String getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(String clientInfo) {
        this.clientInfo = clientInfo;
    }
}
