package com.putao.ptx.push.core;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import com.youku.analytics.utils.FileOperation;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by yanguoqiang on 16/5/6.
 */
public class GPushCallback {

    public static Context mContext = null;

    public void callback(final String appId, final String payload) {

        // Log.d("GPUSH", "gpush callback called!!! appid : " + appId + " payload is:" + payload);

        if (payload != null && "".equals(payload) == false) {
            Intent i = new Intent(Constants.ACTION_PUSH);
            i.putExtra(Constants.EXTRA_APPID, appId);
            i.putExtra(Constants.EXTRA_PAYLOAD, payload);
            if (mContext != null) mContext.sendBroadcast(i);
            Log.d("GPUSH", "send gpush broadcast");
            String fpath = Environment.getExternalStorageDirectory().getPath() + "/gushlog.txt";
            saveLog(fpath, payload);
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
