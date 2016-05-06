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
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            Network[] allNetworks = connectivityManager.getAllNetworks();
            for (int i = 0; i < allNetworks.length; i++) {
                NetworkInfo networkInfo = connectivityManager.getNetworkInfo(allNetworks[i]);
                if (networkInfo != null) {
                    NetworkInfo.State state = networkInfo.getState();
                    if (NetworkInfo.State.CONNECTED == state) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
