package com.putao.wd;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.putao.wd.util.HtmlUtils;
import com.sunnybear.library.controller.BasicFragmentActivity;
import com.sunnybear.library.controller.task.SuperTask;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.select.DynamicTitleBar;
import com.sunnybear.library.view.select.TitleBar;
import com.sunnybear.library.view.select.TitleItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by guchenkai on 2015/12/8.
 */
@Deprecated
public class TestActivity extends BasicFragmentActivity<GlobalApplication> implements View.OnClickListener {
    String html = "<font color=646464 size=14>昨日30分钟拼完</font>" +
            "<br>" +
            "<font color=313131 size=30>100</font><font color=313131 size=14>个</font><font color=646464 size=14>\\n七巧板</font>" +
            "<br>" +
            "<font color=959595 size=12>平均速度</font><font color=646464 size=12 bold>超越了90%</font><font color=959595 size=12>的孩子</font>";
    @Bind(R.id.tv_1)
    TextView tv_1;
    @Bind(R.id.tv_2)
    TextView tv_2;
    @Bind(R.id.tv_3)
    TextView tv_3;
    //    @Bind(R.id.tv_emoji)
//    EmojiTextView tv_emoji;
    @Bind(R.id.tb_bar)
    DynamicTitleBar tb_bar;
    @Bind(R.id.ed_msg)
    EditText ed_msg;

    private String[] titles = new String[]{"牛人说", "玩物志", "葡萄+"};

//    private List<HtmlInfo> htmlInfos = new ArrayList<>();

    private List<SpannableStringBuilder> builders = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.test_activity_test;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        builders = HtmlUtils.getTexts(html);
        tv_1.setText(builders.get(0));
        tv_2.setText(builders.get(1));
        tv_3.setText(builders.get(2));

//        tv_emoji.setText("[呲牙][呲牙][呲牙]呲牙[大笑][萌]");

        tb_bar.addTitles(Arrays.asList(titles), 0);
        tb_bar.setOnTitleItemSelectedListener(new TitleBar.OnTitleItemSelectedListener() {
            @Override
            public void onTitleItemSelected(TitleItem item, int position) {
                ToastUtils.showToastLong(mContext, "点击了第" + position + "项");
            }
        });
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @OnClick(R.id.ll_show)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_show:
//                loading.show();
                SuperTask.with(this)
                        .assign(new SuperTask.TaskDescription<String>() {
                            @Override
                            public String onBackground() {
                                Logger.d("onBackground:" + Thread.currentThread().getName());
                                SuperTask.post(Message.obtain(SuperTask.handler(), 123));
                                return "你个傻叉";
                            }
                        })
                        .handle(new SuperTask.MessageListener() {
                            @Override
                            public void handleMessage(@NonNull Message message) {
                                Logger.d("handleMessage:" + Thread.currentThread().getName());
                                switch (message.what) {
                                    case 123:
                                        Logger.d("接受到Handler消息");
                                        break;
                                }
                            }
                        })
                        .finish(new SuperTask.FinishListener<String>() {
                            @Override
                            public void onFinish(@Nullable String result) {
                                Logger.d("onFinish:" + Thread.currentThread().getName());
                                Logger.d(result);
                                ed_msg.setText(result);
                            }
                        })
                        .broken(new SuperTask.BrokenListener() {
                            @Override
                            public void onBroken(@NonNull Exception e) {

                            }
                        }).execute();

//                Bundle bundle = new Bundle();
//                bundle.putString(VideoPlayerActivity.BUNDLE_VIDEO_URL, Environment.getExternalStorageDirectory().getAbsolutePath() + "/test.mp4");
//                bundle.putString(VideoPlayerActivity.BUNDLE_VIDEO_URL,
//                        "http://movie.ks.js.cn/flv/other/2014/06/20-2.flv");
//                startActivity(VideoPlayerActivity.class, bundle);
//                bundle.putString(YoukuVideoPlayerActivity.BUNDLE_VID, "XMTMxNTI0Mzg5Mg==");
//                startActivity(YoukuVideoPlayerActivity.class, bundle);

//                OkHttpRequestHelper.newInstance()
//                        .cacheType(CacheType.NETWORK_ELSE_CACHE)
//                        .request(ExploreApi.getArticleList(),
//                                new SimpleFastJsonSerializableCallback<ArrayList<ExploreIndex>>(loading) {
//                                    @Override
//                                    public void onSuccess(String url, ArrayList<ExploreIndex> result) {
//                                        Logger.d("网络请求结果:" + result.toString());
//                                    }
//
//                                    @Override
//                                    public void onCacheSuccess(String url, ArrayList<ExploreIndex> result) {
//                                        Logger.d("缓存请求结果:" + result.toString());
//                                    }
//
//                                    @Override
//                                    public void onFailure(String url, int statusCode, String msg) {
//                                        Logger.e(msg);
//                                    }
//                                });

//                OkHttpRequestHelper.newInstance()
//                        .addInterceptor(new ProgressInterceptor(new ProgressResponseListener() {
//                            @Override
//                            public void onResponseProgress(long bytesRead, long contentLength, boolean done) {
//                                Logger.d("---" + bytesRead + "----" + contentLength + "-----" + done);
//                            }
//                        }))
//                        .download("https://codeload.github.com/Yalantis/uCrop/zip/master",
//                                mApp.getSdCardPath() + File.separator + "uCrop.zip",
//                                new DownloadCallback() {
//                                    @Override
//                                    public void onStart() {
//                                        loading.show();
//                                    }
//
//                                    @Override
//                                    public void onDownloadSuccess(String url, File file) {
//                                        Logger.i("下载成功===" + file.getAbsolutePath());
//                                    }
//
//                                    @Override
//                                    public void onDownloadFailure(String url, int statusCode, String msg) {
//                                        Logger.e("下载失败");
//                                    }
//
//                                    @Override
//                                    public void onFinish(String url, boolean isSuccess, String msg) {
//                                        Logger.d(msg);
//                                        if (isSuccess) loading.dismiss();
//                                    }
//                                });
                break;
        }
    }
}
