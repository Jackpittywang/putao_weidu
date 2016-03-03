package com.putao.wd.share;

import android.content.Context;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.wechat.favorite.WechatFavorite;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import cn.sharesdk.wechat.utils.WechatHelper;

/**
 * 分享工具
 * Created by guchenkai on 2015/11/27.
 */
public class ShareTools {
    private OnekeyShare mOnekeyShare;
    private String platfom;

    private ShareTools(String platfom) {
        mOnekeyShare = new OnekeyShare();
        mOnekeyShare.setPlatform(platfom);
    }

    public static ShareTools newInstance(String platfom) {
        return new ShareTools(platfom);
    }

    /**
     * title标题，在印象笔记、邮箱、信息、微信（包括好友、朋友圈和收藏）、
     * 易信（包括好友、朋友圈）、人人网和QQ空间使用，否则可以不提供
     *
     * @param title
     */
    public ShareTools setTitle(String title) {
        mOnekeyShare.setTitle(title);
        return this;
    }

    /**
     * text是分享文本，所有平台都需要这个字段(该字段必须设置,否则分享失败)
     *
     * @param text
     */
    public ShareTools setText(String text) {
        mOnekeyShare.setText(text);
        return this;
    }

    /**
     * imagePath是本地的图片路径，除Linked-In外的所有平台都支持这个字段
     *
     * @param imagePath
     */
    public ShareTools setImagePath(String imagePath) {
        mOnekeyShare.setImagePath(imagePath);
        return this;
    }

    /**
     * imageUrl是图片的网络路径，新浪微博、人人网、QQ空间和Linked-In支持此字段
     *
     * @param imageUrl
     */
    public ShareTools setImageUrl(String imageUrl) {
        mOnekeyShare.setImageUrl(imageUrl);
        return this;
    }

    /**
     * url在微信（包括好友、朋友圈收藏）和易信（包括好友和朋友圈）中使用，否则可以不提供
     *
     * @param url
     */
    public ShareTools setUrl(String url) {
        mOnekeyShare.setUrl(url);
        return this;
    }

    /**
     * filePath是待分享应用程序的本地路劲，仅在微信（易信）好友和Dropbox中使用，否则可以不提供
     *
     * @param filePath
     */
    public ShareTools setFilePath(String filePath) {
        mOnekeyShare.setFilePath(filePath);
        return this;
    }

    /**
     * 执行分享
     *
     * @param context
     */
    public void execute(Context context) {
        mOnekeyShare.setDialogMode();
        mOnekeyShare.disableSSOWhenAuthorize();
//        mOnekeyShare.setShareContentCustomizeCallback(new ReflectableShareContentCustomizeCallback());
        mOnekeyShare.show(context);
    }

    /**
     * 微信网页分享
     */
    public static void wechatWebShare(Context context, boolean isWechat, String title, String text, String imageUrl, String url) {
        WechatHelper.ShareParams params = null;
        if (isWechat)
            params = new Wechat.ShareParams();
        else
            params = new WechatFavorite.ShareParams();
        params.title = title;
        params.text = text;
        params.imageUrl = imageUrl;
        params.url = url;
        params.setShareType(Platform.SHARE_WEBPAGE);

        Platform plat = null;
        if (isWechat)
            plat = ShareSDK.getPlatform(Wechat.NAME);
        else
            plat = ShareSDK.getPlatform(WechatMoments.NAME);
        plat.share(params);
    }

    public static void qqWebShare(Context context, boolean isQQ, String title, String text, String imageUrl, String url) {



        WechatHelper.ShareParams params = null;
        if (isQQ)
            params = new Wechat.ShareParams();
        else
            params = new WechatFavorite.ShareParams();
        params.title = title;
        params.text = text;
        params.imageUrl = imageUrl;
        params.url = url;
        params.setShareType(Platform.SHARE_WEBPAGE);

        Platform plat = null;
        if (isQQ)
            plat = ShareSDK.getPlatform(Wechat.NAME);
        else
            plat = ShareSDK.getPlatform(WechatMoments.NAME);
        plat.share(params);
    }
}
