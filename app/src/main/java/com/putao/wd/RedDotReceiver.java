package com.putao.wd;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.putao.mtlib.tcp.PTMessageReceiver;
import com.sunnybear.library.controller.eventbus.EventBusHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhanghao on 2016/3/23.
 * 用于处理推送数据，分发处理结果
 */
public class RedDotReceiver extends PTMessageReceiver {
    public static final String ME_TABBAR = "me_tabbar";
    public static final String ME_MESSAGECENTER = "me_messageCenter";
    public static final String MESSAGECENTER_NOTICE = "messageCenter_notice";
    public static final String MESSAGECENTER_REPLY = "messageCenter_reply";
    public static final String MESSAGECENTER_PRAISE = "messageCenter_praise";
    public static final String MESSAGECENTER_REMIND = "messageCenter_remind";
    public static final String APPPRODUCT_ID = "appProduct_id";

    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getExtras().getString(KeyMessage);
        setResult(message);
        /*String result = message.substring(message.indexOf("{") + 1, message.indexOf("}") + 1);
        result = result.substring(message.indexOf("{"), result.length());
        JSONObject jsonObject = JSONObject.parseObject(result);*/

    }


    private int me = 0;
    private int messagecenter = 0;
    private int notice = 0;
    private int reply = 0;
    private int praise = 0;
    private int remind = 0;

    private String setResult(String result) {
        Pattern p1 = Pattern.compile("\"location_dot\":([^}]*)");
        Matcher m1 = p1.matcher(result);
        String group;
        while (m1.find()) {
            group = m1.group(0).substring(15);//截取json字符串
            JSONObject jsonObject = JSONObject.parseObject(group+"}");
            //主页"我"位置红点
            if (me == 0 && jsonObject.getInteger(ME_TABBAR) == 1) {
                me = 1;
                EventBusHelper.post(ME_TABBAR, ME_TABBAR);
            }
            //"我"位置"消息中心"红点
            if (messagecenter == 1 && jsonObject.getInteger(ME_MESSAGECENTER) == 1) {
                messagecenter = 1;
                EventBusHelper.post(ME_MESSAGECENTER, ME_MESSAGECENTER);
            }
            //消息中心通知红点
            if (notice == 1 && jsonObject.getInteger(MESSAGECENTER_NOTICE) == 1) {
                notice = 1;
                EventBusHelper.post(ME_MESSAGECENTER, ME_MESSAGECENTER);
            }
            //消息中心回复红点
            if (reply == 1 && jsonObject.getInteger(MESSAGECENTER_REPLY) == 1) {
                reply = 1;
                EventBusHelper.post(MESSAGECENTER_REPLY, MESSAGECENTER_REPLY);
            }
            //消息中心赞红点
            if (praise == 1 && jsonObject.getInteger(MESSAGECENTER_PRAISE) == 1) {
                praise = 1;
                EventBusHelper.post(MESSAGECENTER_PRAISE, MESSAGECENTER_PRAISE);
            }
            //消息中心提醒红点
            if (remind == 1 && jsonObject.getInteger(MESSAGECENTER_REMIND) == 1) {
                remind = 1;
                EventBusHelper.post(MESSAGECENTER_REMIND, MESSAGECENTER_REMIND);
            }
            if (!TextUtils.isEmpty(jsonObject.getString(APPPRODUCT_ID))) {
                EventBusHelper.post(ME_TABBAR, ME_TABBAR);
            }
        }
        return result;
    }
}
