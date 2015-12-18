package com.putao.wd.share;

import android.content.Context;

import cn.sharesdk.onekeyshare.OnekeyShare;

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
}
