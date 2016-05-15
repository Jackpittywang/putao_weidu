package com.putao.wd.webview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.putao.wd.GlobalApplication;
import com.putao.wd.account.AccountConstants;
import com.putao.wd.model.PicClickResult;
import com.putao.wd.pt_companion.GameDetailListActivity;
import com.putao.wd.pt_companion.OfficialAccountsActivity;
import com.putao.wd.start.browse.PictrueBrowseActivity;
import com.sunnybear.library.util.StringUtils;

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

    //putao://openAccNo/{"type":1,"id":50008}
    public static String OPEN_ACC_NO = "openAccNo";

    public static boolean parseUrl(Context context, String scheme, JSONObject jsonObj) {
        // Log.i(TAG, "url called:" + jsonObj.toJSONString());
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
                //PAGE_SETTING不在此处做处理
                return true;
            } else if (OPEN_ACC_NO.equals(scheme)) {
                String type = jsonObj.getInteger("type") + "";
                String id = jsonObj.getInteger("id") + "";
                if(GlobalApplication.isServiceIdBind(id)){
                    Bundle bundle = new Bundle();
                    bundle.putString(AccountConstants.Bundle.BUNDLE_COMPANION_BIND_SERVICE,id);
                    Intent intent = new Intent(context, GameDetailListActivity.class);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }else{
                    Bundle bundle = new Bundle();
                    bundle.putBoolean(AccountConstants.Bundle.BUNDLE_COMPANION_BIND_SERVICE, false);
                    bundle.putString(OfficialAccountsActivity.CAPTURE_SERVICE_ID, id);
                    bundle.putBoolean(AccountConstants.Bundle.BUNDLE_ARTICLE_CLICK, true);
                    bundle.putString(AccountConstants.Bundle.BUNDLE_SERVICE_SUBSCR_STATE, type);
                    Intent intent = new Intent(context,OfficialAccountsActivity.class);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }

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
