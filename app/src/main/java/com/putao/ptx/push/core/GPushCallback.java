package com.putao.ptx.push.core;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.putao.wd.GlobalApplication;
import com.putao.wd.db.CompanionDBManager;
import com.putao.wd.db.entity.CompanionDB;
import com.putao.wd.model.GpushMessage;
import com.putao.wd.model.GpushMessageAccNumber;
import com.putao.wd.model.GpushMessageMsgCenter;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
import com.youku.analytics.utils.FileOperation;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by yanguoqiang on 16/5/6.
 */
public class GPushCallback {

    public static Context mContext = null;
    public static final String ME_TABBAR = "me_tabbar";
    public static final String COMPANION_TABBAR = "companion_tabbar";
    public static final String COMPANION_DOT = "companion_dot";
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

    public void callback(final String appId, final String payload) {

        // Log.d("GPUSH", "gpush callback called!!! appid : " + appId + " payload is:" + payload);

        if (payload != null && "".equals(payload) == false) {
           /* Intent i = new Intent(Constants.ACTION_PUSH);
            i.putExtra(Constants.EXTRA_APPID, appId);
            i.putExtra(Constants.EXTRA_PAYLOAD, payload);
            if (mContext != null) mContext.sendBroadcast(i);*/
            Log.d("GPUSH", "send gpush broadcast");
            String fpath = Environment.getExternalStorageDirectory().getPath() + "/gushlog.txt";
            saveLog(fpath, payload);

            GpushMessage gpushMessage = JSONObject.parseObject(payload, GpushMessage.class);
            //陪伴位置提醒红点
            if (null != gpushMessage && gpushMessage.getAccompanyNumber() != null && gpushMessage.getAccompanyNumber().size() > 0) {
                ArrayList<GpushMessageAccNumber> accompanyNumber = gpushMessage.getAccompanyNumber();
                CompanionDBManager dataBaseManager = (CompanionDBManager) GlobalApplication.getDataBaseManager(CompanionDBManager.class);
                for (GpushMessageAccNumber gpushMessageAccNumber : accompanyNumber) {
                    CompanionDB companInfoById = dataBaseManager.getCompanInfoById(gpushMessageAccNumber.getId());
                    EventBusHelper.post(accompanyNumber, COMPANION_DOT);
                    /*if (null != companInfoById) {
                        dataBaseManager.insertFixDownload(gpushMessageAccNumber.getService_id(), gpushMessageAccNumber.getId(), System.currentTimeMillis());
                    }*/
                }
//            EventBusHelper.post(true, COMPANION_TABBAR);

            }
            if (null != gpushMessage.getMessageCenter()) {
                GpushMessageMsgCenter messageCenter = gpushMessage.getMessageCenter();

                //主页 "我" 位置红点
                EventBusHelper.post(ME_TABBAR, ME_TABBAR);
                //消息中心通知红点
                if (messageCenter.isNotice()) {
                    EventBusHelper.post(MESSAGECENTER_NOTICE, MESSAGECENTER);
                }
                //消息中心红点
                if (messageCenter.isRemind()) {
                    EventBusHelper.post(MESSAGECENTER_REMIND, MESSAGECENTER);
                }
                //消息中心赞红点
                if (messageCenter.isPraise()) {
                    EventBusHelper.post(MESSAGECENTER_PRAISE, MESSAGECENTER);
                }
                //消息中心回复红点
                if (messageCenter.isReply()) {
                    EventBusHelper.post(MESSAGECENTER_REPLY, MESSAGECENTER);
                }
            }
        }

    }

    /**
     * 保存推送日志到sd卡gpushlig.txt
     *
     * @param fPath
     * @param str
     */
    public void saveLog(String fPath, String str) {
        File file = new File(fPath);
        String contentstr = "";
        if (file.exists() == true) {
            contentstr = readFile(fPath);
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(new Date(System
                .currentTimeMillis()));
        str = dateString + " " + str;
        contentstr = str + "\n" + contentstr;
        if (contentstr.length() > 5000)
            contentstr = contentstr.substring(0, 5000);
        saveFile(fPath, contentstr);
        formatter = null;
    }

    public static void saveFile(String fileName, String str) {
        File file = new File(fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            byte[] bytes = str.getBytes();
            fos.write(bytes);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String readFile(String fileName) {
        String res = "";
        try {
            File fileDir = new File(fileName);
            FileInputStream is = new FileInputStream(fileDir);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] array = new byte[1024];
            int len = -1;

            while ((len = is.read(array)) != -1) {
                bos.write(array, 0, len);
            }
            bos.close();
            is.close();
            res = bos.toString();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;

    }
}
