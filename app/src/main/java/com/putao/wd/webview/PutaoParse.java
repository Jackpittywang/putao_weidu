package com.putao.wd.webview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.putao.wd.model.PicClickResult;
import com.putao.wd.start.browse.PictrueBrowseActivity;

/**
 * Created by yanguoqiang on 16/4/11.
 */
public class PutaoParse {

    private static String TAG = PutaoParse.class.getName();
    // 开新的webview
    // putao://openWebview/{'title':'xxx', 'url':'xxx'}
    public static String OPEN_WEBVIEW = "openWebView";
    // 打开画廊显示一组图片
    // putao://viewPics/{'title':'xxx','clickIndex':'0','picList':[{'src':'1111.jpg', 'text':'xxxx'},{'src':'222.jpg','text':'xxxx'},{'src':'3333.png', 'text':'xxxx'}}
    public static String VIEW_PIC = "viewPics";
    // 文章详情页面是否显示评论以及评论和赞数量
    // putao://pageSetting/{'isComment':'1','commentNumber':'xxx','zanNumber':'xxx'}
    public static String PAGE_SETTING = "pageSetting";

    public static boolean parseUrl(Context context, String scheme, JSONObject jsonObj) {
        Log.i(TAG, "url called:" + jsonObj.toJSONString());
        try {
            if (OPEN_WEBVIEW.equals(scheme)) {
                String title = jsonObj.getString("title");
                String jumpUrl = jsonObj.getString("url");
                Bundle bundle = new Bundle();
                bundle.putString(BaseWebViewActivity.TITLE, title);
                bundle.putString(BaseWebViewActivity.URL, jumpUrl);
                Intent intent = new Intent(context, BaseWebViewActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
                return true;
            } else if (VIEW_PIC.equals(scheme)) {
                PicClickResult picClickResult = JSONObject.parseObject(jsonObj.toJSONString(), PicClickResult.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(PictrueBrowseActivity.IMAGE_URL, picClickResult);
                Intent intent = new Intent(context, PictrueBrowseActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
                return true;
            } else if (PAGE_SETTING.equals(scheme)) {

                return true;

            } else {
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return false;

    }

}
