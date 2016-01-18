package com.sunnybear.library.model.http.interceptor;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.sunnybear.library.BasicApplication;
import com.sunnybear.library.R;
import com.sunnybear.library.util.AppUtils;
import com.sunnybear.library.util.ToastUtils;

import java.io.IOException;

/**
 * 判断是否有网络
 * Created by sunnybear on 16/1/18.
 */
public class NetworkInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();//拦截Request
        if (!AppUtils.isNetworkReachable(BasicApplication.getInstance())) {
            ToastUtils.showToastLong(BasicApplication.getInstance(),
                    BasicApplication.getInstance().getResources().getString(R.string.not_network));
            return null;
        }
        return chain.proceed(request);
    }
}
