package com.putao.mtlib;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.putao.mtlib.model.CS_CONNECT;
import com.putao.mtlib.tcp.OnReceiveMessageListener;
import com.putao.mtlib.tcp.PTMessageConfig;
import com.putao.mtlib.tcp.PTMessageType;
import com.putao.mtlib.tcp.PTRecMessage;
import com.putao.mtlib.tcp.PTSenderManager;
import com.putao.mtlib.util.MD5Util;
import com.putao.mtlib.util.MsgPackUtil;
import com.putao.wd.GlobalApplication;
import com.putao.wd.account.AccountHelper;
import com.sunnybear.library.util.Logger;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 通知服务
 * Created by Administrator on 2015/12/28.
 */
public class NotifyService extends Service {
    private static final String HOST = GlobalApplication.isDebug ? "122.226.100.152" : "10.1.11.31";
    private static final int PORT = GlobalApplication.isDebug ? 8040 : 8083;
    private static final String secret = "499478a81030bb177e578f86410cda8641a22799";
    private static final int appid = 611;

    private HandlerThread mStartThread;
    private Handler mHandler;
    private String mThreadName = NotifyService.class.getSimpleName();
    private PTSenderManager mPTSenderManager;

    @Override
    public void onCreate() {
        super.onCreate();
        mStartThread = new HandlerThread(mThreadName);
        mStartThread.start();
        mHandler = new Handler(mStartThread.getLooper());

        mPTSenderManager = PTSenderManager.sharedInstance();
        mPTSenderManager.setConfig(new PTMessageConfig.Builder()
                .setHost(HOST).setPort(PORT).setHeartSecond(30).build());
        mPTSenderManager.init(getApplicationContext());
        mPTSenderManager.setReceiveMessageListener(new OnReceiveMessageListener() {
            @Override
            public void onResponse(PTRecMessage response) {
                Logger.d("-------------------++++++++++++++++++", response.getMessage());
                switch (response.getType()) {
                    case 2:
                        Logger.d(mThreadName, "连接成功");
                        break;
                   /* case 3:
                        String message = response.getMessage();
                        String result = message.substring(message.indexOf("{"), message.length());
                        Logger.d(mThreadName, result);
                        break;*/
                }
            }
        });
        sendConnectValidate();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(GlobalApplication.Fore_Message);
        intentFilter.addAction(GlobalApplication.Not_Fore_Message);
        registerReceiver(new HomeBroadcastReceiver(), intentFilter);
    }

    /**
     * 发送连接验证
     */
    public void sendConnectValidate() {
        CS_CONNECT connect = new CS_CONNECT();
        connect.setDeviceid(AccountHelper.getCurrentUid());
        connect.setAppid(appid);
        connect.setSign(getSign(connect.getDeviceid(), secret));
        PTSenderManager.sharedInstance().sendMsg(MsgPackUtil.Pack(connect, PTMessageType.CS_CONNECT));
    }

    public String getSign(String deviceid, String secret) {
        return MD5Util.getMD5Str(deviceid + appid + secret).toUpperCase();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 监听程序已经在后台
     */
    private class HomeBroadcastReceiver extends BroadcastReceiver {
        Timer timer;

        @Override
        public void onReceive(Context context, Intent intent) {

            switch (intent.getAction()) {
                case GlobalApplication.Fore_Message:
                    if (null == timer)
                        timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            stopSelf();
                            unregisterReceiver(HomeBroadcastReceiver.this);
                            GlobalApplication.isServiceClose = true;
                            Logger.d("---------++++", "停止服务");
                        }
                    }, 30 * 1000);
                    break;
                case GlobalApplication.Not_Fore_Message:
                    if (null != timer) {
                        timer.cancel();
                        timer = null;
                    }
                    break;
            }
        }
    }
}
