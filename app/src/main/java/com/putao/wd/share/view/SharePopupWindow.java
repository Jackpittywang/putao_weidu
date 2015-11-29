package com.putao.wd.share.view;

import android.content.Context;
import android.view.View;

import com.putao.wd.R;
import com.sunnybear.library.view.BasicPopupWindow;

import butterknife.OnClick;

/**
 * 分享弹出框
 * Created by guchenkai on 2015/11/27.
 */
public class SharePopupWindow extends BasicPopupWindow implements View.OnClickListener {
    private OnShareListener mOnShareListener;


    public SharePopupWindow(Context context, boolean isClickOtherClosePopupWindow) {
        super(context, isClickOtherClosePopupWindow);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.popup_share;
    }

    public void setOnShareListener(OnShareListener onShareListener) {
        mOnShareListener = onShareListener;
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
        if (v.getId() == R.id.tv_cancel) {//取消
            dismiss();
            return;
        }
        if (mOnShareListener != null)
            switch (v.getId()) {
                case R.id.ll_wechat://微信
                    mOnShareListener.onWechatShare();
                    break;
                case R.id.ll_wechat_friend_circle://微信朋友圈
                    mOnShareListener.onWechatFriendCircleShare();
                    break;
                case R.id.ll_qq_friend://QQ好友
                    mOnShareListener.onQQFriendShare();
                    break;
                case R.id.ll_qq_zone://QQ空间
                    mOnShareListener.onQQZoneShare();
                    break;
                case R.id.ll_sina_weibo://新浪微博
                    mOnShareListener.onSinaWeiBoShare();
                    break;
                case R.id.ll_copy_url://复制链接
                    mOnShareListener.onCopyUrlShare();
                    break;
            }
    }

    /**
     * 点击分享按钮监听
     */
    public interface OnShareListener {

        /**
         * 微信分享
         */
        void onWechatShare();

        /**
         * 微信朋友圈分享
         */
        void onWechatFriendCircleShare();

        /**
         * QQ好友分享
         */
        void onQQFriendShare();

        /**
         * QQ空间分享
         */
        void onQQZoneShare();

        /**
         * 新浪微博分享
         */
        void onSinaWeiBoShare();

        /**
         * 复制链接
         */
        void onCopyUrlShare();
    }
}
