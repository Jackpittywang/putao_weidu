package com.putao.ptx.push.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by yanguoqiang on 16/5/6.
 */
public class NetworkReceiver extends BroadcastReceiver {

    private static final String TAG = NetworkReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isConnected = NetworkUtil.isNetworkAvailable(context);
        if (isConnected) {
            Log.d(TAG, "网络状态:连接");
            Intent i = new Intent(context, GPushService.class);
            i.putExtra(Constants.EXTRA_ACTION, Constants.ACTION_GPUSH_START);
            context.startService(i);
        } else {
            Log.d(TAG, "网络状态:断开");
            Intent i = new Intent(context, GPushService.class);
            i.putExtra(Constants.EXTRA_ACTION, Constants.ACTION_GPUSH_STOP);
            context.startService(i);
        }

    }
}
