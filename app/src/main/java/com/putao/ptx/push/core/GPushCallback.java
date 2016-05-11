package com.putao.ptx.push.core;

import android.content.Context;
import android.content.Intent;
import android.util.Log;


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
        }


    }
}
