package com.putao.wd.util;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.sunnybear.library.model.http.OkHttpRequestHelper;
import com.sunnybear.library.model.http.callback.RequestCallback;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.NetworkLogUtil;

import java.io.Serializable;
import java.util.LinkedList;


public class NetworkUtil {
    private static NetworkUtil mNetworkUtil;

    public static NetworkUtil getInstance() {
        if (null == mNetworkUtil) {
            mNetworkUtil = new NetworkUtil();
        }
        return mNetworkUtil;
    }


    public <T extends Serializable> void startRequest(Request request, SimpleFastJsonCallback<T> simpleFastJsonCallback) {
        networkRequest(request, simpleFastJsonCallback);
    }


    private void networkRequest(Request request, RequestCallback callback) {
        LinkedList<Interceptor> interceptors = new LinkedList<>();
        NetworkLogUtil.addLog(request);
        if (request == null)
            throw new NullPointerException("request为空");
        OkHttpRequestHelper helper = OkHttpRequestHelper.newInstance();
        if (interceptors != null && interceptors.size() > 0)
            helper.addInterceptors(interceptors);
        helper.request(request, callback);
    }

}
