package com.putao.wd.share.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.putao.wd.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 分享弹出框
 * Created by guchenkai on 2015/11/26.
 */
public class SharePopupWindow extends RelativeLayout implements View.OnClickListener {
    private Animation in_from_down;//进入动画
    private Animation out_from_down;//退出动画

    private OnShareListener mOnShareListener;

    public void setOnShareListener(OnShareListener onShareListener) {
        mOnShareListener = onShareListener;
    }

    public SharePopupWindow(Context context) {
        this(context, null, 0);
    }

    public SharePopupWindow(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SharePopupWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initAnimation(context);
    }

    private void initView(Context context) {
        View mRootView = LayoutInflater.from(context).inflate(R.layout.popup_share, null);
        ButterKnife.bind(this, mRootView);
        addView(mRootView);
    }

    private void initAnimation(Context context) {
        in_from_down = AnimationUtils.loadAnimation(context, R.anim.in_from_down);
        out_from_down = AnimationUtils.loadAnimation(context, R.anim.out_from_down);
    }

    /**
     * 分享栏弹出
     */
    public void toggle() {
        if (getVisibility() == View.GONE) {
            setVisibility(VISIBLE);
            startAnimation(in_from_down);
        } else if (getVisibility() == View.VISIBLE) {
            setVisibility(GONE);
            startAnimation(out_from_down);
        }
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
                case R.id.tv_cancel://取消
                    toggle();
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
