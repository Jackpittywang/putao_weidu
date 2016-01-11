package com.putao.wd.video;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.putao.wd.R;
import com.sunnybear.library.controller.BasicFragmentActivity;
import com.sunnybear.library.util.DateUtils;
import com.sunnybear.library.util.ToastUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.Bind;
import butterknife.OnClick;
import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.VideoView;

/**
 * 视频播放页面
 * Created by guchenkai on 2016/1/11.
 */
public class VideoPlayerActivity extends BasicFragmentActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    public static final String BUNDLE_VIDEO_URL = "video_url";
    @Bind(R.id.vv_video)
    VideoView vv_video;
    @Bind(R.id.pb_progress)
    ProgressBar pb_progress;
    @Bind(R.id.rl_control)
    RelativeLayout rl_control;
    @Bind(R.id.iv_mode)
    ImageView iv_mode;
    @Bind(R.id.tv_current_time)
    TextView tv_current_time;
    @Bind(R.id.tv_duration_time)
    TextView tv_duration_time;
    @Bind(R.id.seek_bar)
    SeekBar seek_bar;

    private String video_url;//视频地址
    //此boolean为true 工作线程会一直运行 在ondestroy的时候设置为false 让工作线程正常结束 防止内存泄漏
    private boolean isShowCurrentTime = true;
    //控制面板是否正在显示
    private boolean isControlShowing = false;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_video;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        if (!LibsChecker.checkVitamioLibs(this)) {//初始化vitamio
            ToastUtils.showToastLong(mContext, "视频播放器初始化失败");
            finish();
            return;
        }
        video_url = args.getString(BUNDLE_VIDEO_URL);

        initVideoView();
        addListener();
        initThread();
    }

    /**
     * initVideoView
     */
    private void initVideoView() {
//        vv_video.setSoundEffectsEnabled(true);
        vv_video.setBufferSize(4096);//视频缓冲速率
        vv_video.setMediaBufferingIndicator(pb_progress);//视频缓冲进度
        vv_video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Log.d("VideoActivity", "开始播放");
                //视频加载完成后 获取视频时长
                int duration = (int) (vv_video.getDuration() / 1000);
                tv_duration_time.setText(DateUtils.generateTime(vv_video.getDuration()));
                seek_bar.setMax(duration);
            }
        });
        vv_video.setVideoURI(Uri.parse(video_url));
//        vv_video.setVideoLayout(VideoView.VIDEO_LAYOUT_FIT_PARENT, 0);//全屏
    }

    /**
     * 初始化线程 更新播放进度条和播放时间
     */
    private void initThread() {
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            @Override
            public void run() {
                while (isShowCurrentTime) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            updateProgressBar();
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        isShowCurrentTime = false;//否定工作线程 while循环条件 使线程正常死亡
        super.onDestroy();
    }

    /**
     * 更新当前进度条和时间
     */
    private void updateProgressBar() {
        if (vv_video == null) return;
        int currentTime = (int) (vv_video.getCurrentPosition() / 1000);
        tv_current_time.setText(DateUtils.generateTime(vv_video.getCurrentPosition()));
        seek_bar.setProgress(currentTime);
    }

    private void addListener() {
        seek_bar.setOnSeekBarChangeListener(this);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @OnClick({R.id.iv_back, R.id.iv_mode})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_mode:
                boolean isPlaying = vv_video.isPlaying();
                if (isPlaying) {
                    vv_video.pause();
                    iv_mode.setSelected(true);
                } else {
                    vv_video.start();
                    iv_mode.setSelected(false);
                }
                break;
        }
    }

    /**
     * 显示控制view
     */
    private void showControlView() {
        rl_control.setVisibility(View.VISIBLE);
        isControlShowing = true;
    }

    /**
     * 关闭控制view
     */
    private void dismissControlView() {
        rl_control.setVisibility(View.GONE);
        isControlShowing = false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
            if (isControlShowing)
                dismissControlView();
            else
                showControlView();
        return super.onTouchEvent(event);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (!fromUser) return;//只处理用户滑动
        vv_video.seekTo(progress * 100);//改变视频进度
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
