package com.putao.mtlib;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.putao.wd.GlobalApplication;
import com.sunnybear.library.util.Logger;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 程序进入后台恢复前台监听
 */
public class HomeBroadcastReceiver extends BroadcastReceiver {
    Timer timer;
    private static HomeBroadcastReceiver mHomeBroadcastReceiver;

    public static HomeBroadcastReceiver getInstance() {
        if (null == mHomeBroadcastReceiver) {
            mHomeBroadcastReceiver = new HomeBroadcastReceiver();
        }
        return mHomeBroadcastReceiver;
    }

    @Override
    public void onReceive(final Context context, Intent intent) {

        switch (intent.getAction()) {
            case GlobalApplication.IN_FORE_MESSAGE:
//                inFore();
                if (null != timer) {
                    timer.cancel();
                    timer = null;
                }
                if (GlobalApplication.isServiceClose) {
                    context.startService(GlobalApplication.redServiceIntent);
                    GlobalApplication.isServiceClose = true;
                }
                break;
            case GlobalApplication.OUT_FORE_MESSAGE:
//                outFore();
                if (null == timer)
                    timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        GlobalApplication.isServiceClose = true;
                        Logger.d("ptl-----------", "停止服务");
                        context.stopService(GlobalApplication.redServiceIntent);
                        GlobalApplication.isServiceClose = true;
//                        stopSelf();
                    }
                }, 300 * 1000);
                break;
        }
    }

//    abstract void inFore();
//
//    abstract void outFore();
}
