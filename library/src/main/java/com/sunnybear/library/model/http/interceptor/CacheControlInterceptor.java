package com.sunnybear.library.model.http.interceptor;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * 缓存拦截器
 * Created by guchenkai on 2016/1/19.
 */
public class CacheControlInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String url = request.urlString();
        Response response = chain.proceed(request);
        return response;
    }
}
