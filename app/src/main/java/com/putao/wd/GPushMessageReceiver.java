package com.putao.wd;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.putao.mtlib.util.PTLoger;
import com.putao.ptx.push.core.Constants;
import com.putao.wd.account.AccountHelper;
import com.sunnybear.library.controller.eventbus.EventBusHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yanguoqiang on 16/5/11.
 */
public class GPushMessageReceiver extends BroadcastReceiver {

    public static final String ME_TABBAR = "me_tabbar";
    public static final String COMPANION_TABBAR = "companion_tabbar";
    public static final String ME_MESSAGECENTER = "me_messageCenter";
    public static final String MESSAGECENTER_NOTICE = "messageCenter_notice";
    public static final String MESSAGECENTER_REPLY = "messageCenter_reply";
    public static final String MESSAGECENTER_PRAISE = "messageCenter_praise";
    public static final String MESSAGECENTER_REMIND = "messageCenter_remind";
    public static final String APPPRODUCT_ID = "appProduct_id";

    public static final String MESSAGECENTER = "messageCenter";
    public static final String REPLY = "reply";
    public static final String PRAISE = "praise";
    public static final String REMIND = "remind";
    public static final String NOTICE = "notice";
    public static final String ACCOMPANYNUMBER = "accompanyNumber";
    public static final String SERVICE_ID = "service_id";
    public static final String ID = "id";
    public static final String TYPE = "type";

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            if (!AccountHelper.isLogin())
                return;
            // Log.d("GPUSH", "receive gpush message, message is:" + intent.getExtras().getString(Constants.EXTRA_PAYLOAD));
            //收到的消息例子为： {"accompanyNumber" : [{"id" : 0,"service_id" : 50000,"type" : "message"}],"messageCenter" : null}

            String message = intent.getExtras().getString(Constants.EXTRA_PAYLOAD);
            setResult(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setResult(String result) {
        Pattern p1 = Pattern.compile("\\{\"messageCenter\":null.+?\\]\\}");
        Pattern p2 = Pattern.compile("\\{\"messageCenter\":\\{\".+?null\\}");
        Matcher m1 = p1.matcher(result);
        Matcher m2 = p2.matcher(result);
        while (m1.find()) {
            PTLoger.d(m1.group(0));
            JSONObject object = JSONObject.parseObject(m1.group(0));
            JSONArray accompanyNumber = object.getJSONArray(ACCOMPANYNUMBER);
            //陪伴位置提醒红点
            if (null != accompanyNumber) {
                //if (result.contains("\"id\":234")) return;
                EventBusHelper.post(accompanyNumber, COMPANION_TABBAR);
            }
        }
        while (m2.find()) {
            PTLoger.d(m2.group(0));
            JSONObject object = JSONObject.parseObject(m2.group(0));
            JSONObject messageCenter = object.getJSONObject(MESSAGECENTER);
            if (null != messageCenter) {
                String reply = messageCenter.getString(REPLY);
                String praise = messageCenter.getString(PRAISE);
                String remind = messageCenter.getString(REMIND);
                String notice = messageCenter.getString(NOTICE);
                /*//主页"我"位置红点
                EventBusHelper.post(ME_TABBAR, ME_TABBAR);*/
                //"我"位置"消息中心"红点
//                EventBusHelper.post(ME_MESSAGECENTER, ME_MESSAGECENTER);
                //消息中心通知红点
                if ("1".equals(notice)) {
                    EventBusHelper.post(MESSAGECENTER_NOTICE, MESSAGECENTER);
                }
                //消息中心回复红点
                if ("1".equals(remind)) {
                    EventBusHelper.post(MESSAGECENTER_REMIND, MESSAGECENTER);
                }
                //消息中心赞红点
                if ("1".equals(praise)) {
                    EventBusHelper.post(MESSAGECENTER_PRAISE, MESSAGECENTER);
                }
                //消息中心提醒红点
                if ("1".equals(reply)) {
                    EventBusHelper.post(MESSAGECENTER_REPLY, MESSAGECENTER);
                }
            }
        }
    }

}