package com.putao.wd.video;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.putao.wd.R;
import com.sunnybear.library.controller.BasicFragmentActivity;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.StringUtils;
import com.youku.player.ApiManager;
import com.youku.player.VideoQuality;
import com.youku.player.base.YoukuBasePlayerManager;
import com.youku.player.base.YoukuPlayer;
import com.youku.player.base.YoukuPlayerView;

import butterknife.Bind;

/**
 * 优酷视频播放器
 * Created by guchenkai on 2016/1/14.
 */
public class YoukuVideoPlayerActivity extends BasicFragmentActivity {
    public static final String BUNDLE_VID = "vid";
    public static final String BUNDLE_VIDEO_URL = "bundle_video_url";
    public static final String BUNDLE_LOCAL_VID = "local_vid";
    public static final String BUNDLE_IS_FROM_LOCAL = "is_from_local";

    @Bind(R.id.fl_player)
    YoukuPlayerView fl_player;

    private YoukuBasePlayerManager mBasePlayerManager;
    // 需要播放的视频id
    private String vid;
    // YoukuPlayer实例，进行视频播放控制
    private YoukuPlayer mPlayer;
    // 标示是否播放的本地视频
    private boolean isFromLocal = false;
    // 需要播放的本地视频的id
    private String local_vid;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_youku_video;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        mBasePlayerManager = new YoukuBasePlayerManager(this) {
            @Override
            public void setPadHorizontalLayout() {

            }

            @Override
            public void onInitializationSuccess(YoukuPlayer player) {
                // 初始化成功后需要添加该行代码
                addPlugins();
                //实例化YoukuPlayer实例
                mPlayer = player;
                //开始播放
                goPlay();
//                setSharpness(VideoQuality.STANDARD);//设置清晰度(高清)
                fl_player.getIMediaPlayerDelegate().goFullScreen();
            }

            @Override
            public void onFullscreenListener() {

            }

            @Override
            public void onSmallscreenListener() {

            }
        };
        mBasePlayerManager.onCreate();
        getIntentData();
        if (StringUtils.isEmpty(vid)) {
            Logger.d("视频vid为空");
            onBackPressed();
        }
        // 控制竖屏和全屏时候的布局参数。这两句必填。
        fl_player.setSmallScreenLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        fl_player.setFullScreenLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        //初始化播放器相关数据
        fl_player.initialize(mBasePlayerManager);

        //点击视频返回
        fl_player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Logger.d("onBackPressed before super");
        super.onBackPressed();
        Logger.d("onBackPressed");
        mBasePlayerManager.onBackPressed();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mBasePlayerManager.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBasePlayerManager.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
            return true;
        }
        if (mBasePlayerManager.shouldCallSuperKeyDown())
            return super.onKeyDown(keyCode, event);
        else
            return mBasePlayerManager.onKeyDown(keyCode, event);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mBasePlayerManager.onLowMemory();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBasePlayerManager.onPause();
    }

    @Override
    public boolean onSearchRequested() {
        return mBasePlayerManager.onSearchRequested();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mBasePlayerManager.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mBasePlayerManager.onStop();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        getIntentData();
        goPlay();
    }

    /**
     * 设置清晰度
     */
    private void setSharpness(VideoQuality quality) {
        try {
            // 通过ApiManager实例更改清晰度设置，返回值（1):成功；（0): 不支持此清晰度
            // 接口详细信息可以参数使用文档
            int result = ApiManager.getInstance().changeVideoQuality(quality, mBasePlayerManager);
            if (result == 0)
                Toast.makeText(mContext, "不支持此清晰度", 2000).show();
        } catch (Exception e) {
            Toast.makeText(mContext, e.getMessage(), 2000).show();
        }
    }

    /**
     * 获取上个页面传递过来的数据
     */
    private void getIntentData() {
        // 判断是不是本地视频
        isFromLocal = args.getBoolean(BUNDLE_IS_FROM_LOCAL, false);
        if (isFromLocal) {  // 播放本地视频
            local_vid = args.getString(BUNDLE_LOCAL_VID);
        } else { // 在线播放
            vid = args.getString(BUNDLE_VID);
        }
    }

    /**
     * 开始播放
     */
    private void goPlay() {
        if (isFromLocal)
            mPlayer.playLocalVideo(local_vid);
//            mPlayer.playLocalVideo("", local_vid, "");
        else
            mPlayer.playVideo(vid);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
