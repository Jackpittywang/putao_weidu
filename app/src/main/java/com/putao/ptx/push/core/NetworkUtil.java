package com.putao.ptx.push.core;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

/**
 * Created by yanguoqiang on 16/5/6.
 */
public class NetworkUtil {

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo == null) return false;
        return mNetworkInfo.isAvailable();
    }
}
