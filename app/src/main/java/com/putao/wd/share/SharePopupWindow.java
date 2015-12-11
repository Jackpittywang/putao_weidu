package com.putao.wd.share;

import android.content.Context;
import android.view.View;

import com.putao.wd.R;
import com.sunnybear.library.controller.BasicPopupWindow;

import butterknife.OnClick;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * 分享弹出框
 * Created by guchenkai on 2015/11/27.
 */
public class SharePopupWindow extends BasicPopupWindow implements View.OnClickListener {

    public SharePopupWindow(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.popup_share;
    }

    @OnClick({
            R.id.ll_wechat,
            R.id.ll_wechat_friend_circle,
            R.id.ll_qq_friend,
            R.id.ll_qq_zone,
            R.id.ll_sina_weibo,
            R.id.ll_copy_url,
            R.id.tv_cancel
    })
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_wechat://微信
                ShareTools.showShare(mContext, Wechat.NAME,"分享文字");
                break;
            case R.id.ll_wechat_friend_circle://微信朋友圈
                ShareTools.showShare(mContext, WechatMoments.NAME,"分享文字");
                break;
            case R.id.ll_qq_friend://QQ好友

                break;
            case R.id.ll_qq_zone://QQ空间

                break;
            case R.id.ll_sina_weibo://新浪微博

                break;
            case R.id.ll_copy_url://复制链接

                break;
            case R.id.tv_cancel://取消
                dismiss();
                break;
        }
    }
}