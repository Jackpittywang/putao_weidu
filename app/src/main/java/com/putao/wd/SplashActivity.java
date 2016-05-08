package com.putao.wd;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.WindowManager;

import com.putao.wd.account.AccountHelper;
import com.putao.wd.created.CreateBasicDetailActivity;
import com.putao.wd.explore.ExploreMoreDetailActivity;
import com.putao.wd.pt_me.order.OrderDetailActivity;
import com.putao.wd.pt_me.service.ServiceDetailActivity;
import com.putao.wd.pt_store.product.ProductDetailActivity;
import com.putao.wd.util.DistrictUtils;
import com.sunnybear.library.BasicApplication;
import com.sunnybear.library.controller.BasicFragmentActivity;
import com.sunnybear.library.controller.task.SuperTask;
import com.sunnybear.library.model.http.OkHttpRequestHelper;
import com.sunnybear.library.model.http.callback.DownloadCallback;
import com.sunnybear.library.util.FileUtils;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.PreferenceUtils;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import cn.sharesdk.framework.ShareSDK;

/**
 * 闪屏页面
 * Created by guchenkai on 2015/12/11.
 */
public class SplashActivity extends BasicFragmentActivity<GlobalApplication> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        ShareSDK.initSDK(mContext);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //        if (!PreferenceUtils.getValue(GlobalApplication.PREFERENCE_KEY_IS_FIRST, false))
        SuperTask.with(this)
                .assign(new SuperTask.TaskDescription<ConcurrentHashMap<String, String>>() {
                    @Override
                    public ConcurrentHashMap<String, String> onBackground() {
                        try {
                            File setFile = new File(BasicApplication.sdCardPath + File.separator + "patch/biaoqing/set.txt");
                            if (!setFile.exists())
                                FileUtils.unZipInAsset(getApplicationContext(), "patch_10002_10003.zip", "patch", true);
                            DistrictUtils.insertRegion();
                        } catch (IOException e) {
                            Logger.e("初始数据异常----" + e);
                            e.printStackTrace();
                        }
                        return parseEmoji();
                    }
                })
                .finish(new SuperTask.FinishListener<ConcurrentHashMap<String, String>>() {
                    @Override
                    public void onFinish(@Nullable ConcurrentHashMap<String, String> result) {
                        Logger.d("表情包设置完成");
                        mDiskFileCacheHelper.put(GlobalApplication.MAP_EMOJI, result);
                        GlobalApplication.setEmojis(result);
                        if (AccountHelper.isLogin()) {
                            Intent intent = new Intent(GlobalApplication.IN_FORE_MESSAGE);
                            sendBroadcast(intent);
                        }
                        if (!PreferenceUtils.getValue(GlobalApplication.PREFERENCE_KEY_IS_FIRST, false))
                            startActivity(GuidanceActivity.class);
                        else {
                            startActivity(IndexActivity.class);
                        }
                        finish();
                    }
                }).execute();
//        else
//            startActivity(IndexActivity.class);
//        updateResource();
    }

    /**
     * 请求资源更新
     *
     * @param url      资源下载地址
     * @param filePath 保存文件的路径
     */
    private void updateResource(String url, String filePath) {
        OkHttpRequestHelper.newInstance()
                /*.addInterceptor(new ProgressInterceptor(new ProgressResponseListener() {
                    @Override
                    public void onResponseProgress(long bytesRead, long contentLength, boolean done) {

                    }
                }))*/.download(url, filePath, new DownloadCallback() {
            @Override
            public void onDownloadSuccess(String url, File file) {

            }
        });
    }
//        networkRequestNoLoading(UserApi.resourceUpload(mContext), new JSONObjectCallback() {
//                    @Override
//                    public void onSuccess(String url, JSONObject result) {
//                        Logger.d(result.toJSONString());
//                        if (!StringUtils.equals(result.getString("status"), "200")) {
//                            GlobalApplication.setEmojis((ConcurrentHashMap<String, String>) mDiskFileCacheHelper.getAsSerializable(GlobalApplication.MAP_EMOJI));
//                            new Handler().postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    startActivity(IndexActivity.class);
//                                    finish();
//                                }
//                            }, 3 * 1000);
//                            return;
//                        }
//                        JSONObject object = result.getJSONObject("data");
//                        String last_resource_version = object.getString("last_resource_version");
//                        String last_version = object.getString("last_version");
//                        PreferenceUtils.save("resource_version", last_resource_version);
//                        if (!StringUtils.equals(last_version, last_resource_version)) {
//                            String downloadUrl = "http://static.uzu.wang/source/app_5/resource/patch_" + last_version + "_" + last_resource_version + ".zip";
//                            task = new DownloadFileTask(downloadUrl,
//                                    new DownloadFileCallback() {
//                                        @Override
//                                        public void onProgress(int progress, long networkSpeed) {
//                                            Logger.d("progress:" + progress + ",networkSpeed:" + networkSpeed);
//                                        }
//
//                                        @Override
//                                        public void onFinish(boolean isSuccess) {
//                                            Logger.i(isSuccess ? "下载成功" : "下载失败");
//                                            if (isSuccess)
//                                                try {
//                                                    FileUtils.unZipInSdCard(task.getDownloadFile().getAbsolutePath(), "patch", true);
//                                                    FileUtils.delete(task.getDownloadFile());
//                                                    SuperTask.with(SplashActivity.this)
//                                                            .assign(new SuperTask.TaskDescription<ConcurrentHashMap<String, String>>() {
//                                                                @Override
//                                                                public ConcurrentHashMap<String, String> onBackground() {
//                                                                    DistrictUtils.insertRegion();
//                                                                    return parseEmoji();
//                                                                }
//                                                            })
//                                                            .finish(new SuperTask.FinishListener<ConcurrentHashMap<String, String>>() {
//                                                                @Override
//                                                                public void onFinish(@Nullable ConcurrentHashMap<String, String> result) {
//                                                                    Logger.d("表情包设置完成");
//                                                                    mDiskFileCacheHelper.put(GlobalApplication.MAP_EMOJI, result);
//                                                                    GlobalApplication.setEmojis(result);
//                                                                    startActivity(IndexActivity.class);
//                                                                    finish();
//                                                                }
//                                                            }).execute();
//                                                } catch (IOException e) {
//                                                    e.printStackTrace();
//                                                }
//                                        }
//                                    });
//                            task.execute();
//                        } else {
//                            GlobalApplication.setEmojis((ConcurrentHashMap<String, String>) mDiskFileCacheHelper.getAsSerializable(GlobalApplication.MAP_EMOJI));
//                            new Handler().postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    startActivity(IndexActivity.class);
//                                    finish();
//                                }
//                            }, 3 * 1000);
//                        }
//                    }
//
//                    @Override
//                    public void onCacheSuccess(String url, JSONObject result) {
//
//                    }
//
//                    @Override
//                    public void onFailure(String url, int statusCode, String msg) {
//                        GlobalApplication.setEmojis((ConcurrentHashMap<String, String>) mDiskFileCacheHelper.getAsSerializable(GlobalApplication.MAP_EMOJI));
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                startActivity(IndexActivity.class);
//                                finish();
//                            }
//                        }, 3 * 1000);
//                    }
//                }
//        );


    /**
     * 解析表情
     *
     * @return
     */
    private ConcurrentHashMap<String, String> parseEmoji() {
        ConcurrentHashMap<String, String> result = new ConcurrentHashMap<>();
        File emoji = new File(GlobalApplication.resourcePath + File.separator + "biaoqing", "set.txt");
        String source = FileUtils.readFile(emoji).replace("\uFEFF", "");
        Logger.d(source);
        String[] sources = source.split("\\n");
        for (String s : sources) {
            String[] s1 = s.split(",");
            result.put(s1[0], GlobalApplication.resourcePath + File.separator + "biaoqing" + File.separator + s1[1]);
        }
        return result;
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
