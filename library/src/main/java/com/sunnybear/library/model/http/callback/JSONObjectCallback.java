package com.sunnybear.library.model.http.callback;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.sunnybear.library.util.JsonUtils;
import com.sunnybear.library.util.Logger;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * 回调转换成json
 * Created by guchenkai on 2015/10/27.
 */
public abstract class JSONObjectCallback implements Callback {
    public static final String TAG = JSONObjectCallback.class.getSimpleName();

    private static final int RESULT_SUCCESS = 0x01;//成功
    private static final int RESULT_FAILURE = 0x02;//失败
//    private static final int RESULT_FINISH = 0x03;//完成
//    private static final int RESULT_ERROR = 0x04;//错误

    private static final String KEY_URL = "url";
    private static final String KEY_JSON = "json";
    private static final String KEY_STATUS_CODE = "statusCode";
    private static final String KEY_FAILURE_MSG = "errorMsg";

    private Handler mHandler;//主线程回调

    public JSONObjectCallback() {
        mHandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case RESULT_SUCCESS://成功
                        Bundle success = (Bundle) msg.obj;
                        String url_success = success.getString(KEY_URL);
                        String json = success.getString(KEY_JSON);
                        onSuccess(url_success, JSON.parseObject(json));
                        break;
                    case RESULT_FAILURE://失败
                        Bundle failure = (Bundle) msg.obj;
                        String url_failure = failure.getString(KEY_URL);
                        int statusCode = failure.getInt(KEY_STATUS_CODE);
                        String failure_msg = failure.getString(KEY_FAILURE_MSG);
                        onFailure(url_failure, statusCode, failure_msg);
                        break;
                }
            }
        };
    }

    /**
     * 请求成功回调
     *
     * @param url    网络地址
     * @param result 请求结果
     */
    public abstract void onSuccess(String url, JSONObject result);

    /**
     * 请求失败回调
     *
     * @param url        网络地址
     * @param statusCode 状态码
     * @param msg        失败错误信息
     */
    public abstract void onFailure(String url, int statusCode, String msg);

    @Override
    public final void onResponse(Response response) throws IOException {
        String url = response.request().urlString();
        String json = response.body().string();
        int statusCode = response.code();

        Logger.d(TAG, "url=" + url + ",状态码=" + statusCode);
        if (response.isSuccessful()) {
            Logger.d(TAG, "请求url:\n" + url + "\n" + "请求成功,请求结果=" + JsonUtils.jsonFormatter(json));
            if (!TextUtils.isEmpty(json)) {
                Bundle bundle = new Bundle();
                bundle.putString(KEY_URL, url);
                bundle.putString(KEY_JSON, json);
                mHandler.sendMessage(Message.obtain(mHandler, RESULT_SUCCESS, bundle));
            }
        } else {
            Bundle bundle = new Bundle();
            bundle.putString(KEY_URL, url);
            bundle.putInt(KEY_STATUS_CODE, statusCode);
            bundle.putString(KEY_FAILURE_MSG, "");

            mHandler.sendMessage(Message.obtain(mHandler, RESULT_FAILURE, bundle));
        }
    }

    /**
     * 请求失败回调
     *
     * @param request request
     * @param e       异常
     */
    @Override
    public final void onFailure(Request request, IOException e) {
        String url = request.urlString();
        Logger.e(TAG, "url=" + url + "\n", e);
        if (e instanceof SocketTimeoutException || e instanceof UnknownHostException) {
            Bundle bundle = new Bundle();
            bundle.putString(KEY_URL, url);
            bundle.putString(KEY_STATUS_CODE, "500");
            bundle.putString(KEY_FAILURE_MSG, "请检查网络后重新尝试");

            mHandler.sendMessage(Message.obtain(mHandler, RESULT_FAILURE, bundle));
        }
    }
}
