package com.putao.wd.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 活动详情
 * Created by guchenkai on 2015/12/8.
 */
public class GpushMessage implements Serializable {

    private GpushMessageMsgCenter messageCenter;//活动ID
    private ArrayList<GpushMessageAccNumber> accompanyNumber;//活动标题

    public GpushMessageMsgCenter getMessageCenter() {
        return messageCenter;
    }

    public void setMessageCenter(GpushMessageMsgCenter messageCenter) {
        this.messageCenter = messageCenter;
    }

    public ArrayList<GpushMessageAccNumber> getAccompanyNumber() {
        return accompanyNumber;
    }

    public void setAccompanyNumber(ArrayList<GpushMessageAccNumber> accompanyNumber) {
        this.accompanyNumber = accompanyNumber;
    }
}
