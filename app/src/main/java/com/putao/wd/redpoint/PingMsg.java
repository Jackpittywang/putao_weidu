package com.putao.wd.redpoint;

/**
 * Created by Administrator on 2016/3/18.
 * 心跳检测的消息类型
 */
public class PingMsg extends BaseMsg {
    public PingMsg() {
        super();
        setType(MsgType.PING);
    }
}
