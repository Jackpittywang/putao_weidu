package com.putao.wd.share;

import android.content.Context;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * 微信分享工具
 * Created by guchenkai on 2015/12/6.
 */
public class WechatShareTools {
    private static final String APP_ID = "wx46c90751eea478fe";
    private static IWXAPI api;//第三方app和微信通信OpenApi接口

    /**
     * 注册微信
     */
    public static void regToWX(Context context) {
        api = WXAPIFactory.createWXAPI(context, APP_ID, true);
        api.registerApp(APP_ID);//
    }
}
