package com.putao.ptx.push.core;

import android.util.Log;

/**
 * Created by yanguoqiang on 16/5/6.
 */
public class GPushCallback {

    public void callback(final String appId, final String payload) {


        Log.d("GPUSH", "gush callback called!!! appid : " + appId + " payload is:" + payload);

    }
}
