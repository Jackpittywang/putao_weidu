package com.putao.wd.share;

import android.content.Context;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;

/**
 * 分享工具
 * Created by guchenkai on 2015/11/27.
 */
public class ShareTools {
    private static OnekeyShare mOnekeyShare = new OnekeyShare();

    public static void showShare(Context context, boolean silent, String platfom) {
        mOnekeyShare.setTitle("分享文字");
        mOnekeyShare.setText("这是一段分享文字");
        mOnekeyShare.setDialogMode();
        mOnekeyShare.disableSSOWhenAuthorize();
        mOnekeyShare.setPlatform(platfom);
        mOnekeyShare.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
            @Override
            public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
                String text = "这是一段分享文字";
                if ("WechatMoments".equals(platform.getName())) {
                    // 改写twitter分享内容中的text字段，否则会超长，
                    // 因为twitter会将图片地址当作文本的一部分去计算长度
                    text += "分享到微信朋友圈";
                    paramsToShare.setText(text);
                }
            }
        });
        mOnekeyShare.show(context);
    }
}
