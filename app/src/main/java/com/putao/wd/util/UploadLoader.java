package com.putao.wd.util;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.putao.wd.api.UploadApi;
import com.putao.wd.api.UserApi;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.sunnybear.library.model.http.OkHttpRequestHelper;
import com.sunnybear.library.model.http.UploadFileTask;
import com.sunnybear.library.model.http.callback.JSONObjectCallback;
import com.sunnybear.library.model.http.callback.RequestCallback;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.FileUtils;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.NetworkLogUtil;
import com.sunnybear.library.util.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class UploadLoader {

    private String uploadToken;//上传token
    private File uploadFile;//上传文件
    private String sha1;//上传文件sha1
    private List<String> upLoadList = new ArrayList<>();

    private String filePath;//文件路径
    private boolean isUploadFinish = true;
    private boolean isUploadAllFinish = true;
    private UploadFileCallback mUploadCallback;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = (Bundle) msg.obj;
            //上传PHP服务器
            upload(bundle.getString("ext"), bundle.getString("filename"), bundle.getString("hash"));
        }
    };
    private Handler mMainHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (upLoadList.size() > 0)
                checkSha1(upLoadList.get(0));
        }
    };


    Runnable mRun = new Runnable() {
        @Override
        public void run() {
            isUploadAllFinish = false;
            while (upLoadList.size() > 0) {
                if (!isUploadFinish) continue;
                isUploadFinish = false;
                mMainHandler.sendEmptyMessage(0);
                try {
                    mHandlerThread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (null != mUploadCallback)
                mUploadCallback.fileUploadFinish();
            isUploadAllFinish = true;
        }
    };
    private HandlerThread mHandlerThread;
    private Handler mLoopHandler;
    private static UploadLoader mUploadLoader;

    private UploadLoader() {
    }

    public static UploadLoader getInstance() {
        if (null == mUploadLoader) {
            mUploadLoader = new UploadLoader();
        }
        return mUploadLoader;
    }

    public UploadLoader addUploadFile(String uploadFilePath) {
        upLoadList.add(uploadFilePath);
        return mUploadLoader;
    }

    public void execute(UploadFileCallback uploadCallback) {
        mUploadCallback = uploadCallback;
        initUpload();
    }

    private void initUpload() {
        if (null == mHandlerThread) {
            mHandlerThread = new HandlerThread("upload");
            mHandlerThread.start();
            Looper looper = mHandlerThread.getLooper();
            mLoopHandler = new Handler(looper);
        }
        if (isUploadFinish)
            mLoopHandler.post(mRun);
    }

    /**
     * 校检sha1
     *
     * @param uploadFilePath 上传文件路径
     */
    private void checkSha1(String uploadFilePath) {
        uploadFile = new File(uploadFilePath);
        sha1 = FileUtils.getSHA1ByFile(uploadFile);
        networkRequest(UploadApi.checkSha1(sha1), new JSONObjectCallback() {
            @Override
            public void onSuccess(String url, JSONObject result) {
                String hash = result.getString("hash");
                if (StringUtils.isEmpty(hash))
                    getUploadToken();
                else
                    upload("jpg", hash, hash);
            }

            @Override
            public void onCacheSuccess(String url, JSONObject result) {
            }

            @Override
            public void onFailure(String url, int statusCode, String msg) {
                setUploadFail();
            }

        });
    }

    private void setUploadFail() {
        upLoadList.remove(0);
        isUploadFinish = true;
        mUploadCallback.fileUploadFail(uploadFile.getAbsolutePath());
    }

    /**
     * 获得上传参数
     */
    private void getUploadToken() {
        networkRequest(UserApi.getUploadToken(), new SimpleFastJsonCallback<String>(String.class, null) {
            @Override
            public void onSuccess(String url, String result) {
                JSONObject jsonObject = JSON.parseObject(result);
                uploadToken = jsonObject.getString("uploadToken");
                Logger.d(uploadToken);
                uploadFile();
            }

            @Override
            public void onFailure(String url, int statusCode, String msg) {
                super.onFailure(url, statusCode, msg);
                setUploadFail();
            }
        });
    }

    /**
     * 上传文件
     */
    private void uploadFile() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                UploadApi.uploadFile(uploadToken, sha1, uploadFile, new UploadFileTask.UploadCallback() {
                    @Override
                    public void onSuccess(JSONObject result) {
                        Logger.d(result.toJSONString());
                        Bundle bundle = new Bundle();
                        bundle.putString("ext", result.getString("ext"));
                        bundle.putString("filename", result.getString("filename"));
                        bundle.putString("hash", result.getString("hash"));
                        //上传PHP服务器
                        mHandler.sendMessage(Message.obtain(mHandler, 0x01, bundle));
                    }
                });
            }
        }).start();
    }


    /**
     * 上传PHP服务器
     */
    private void upload(String ext, String filename, String filehash) {
        networkRequest(UserApi.userEdit(ext, filename, filehash),
                new SimpleFastJsonCallback<String>(String.class, null) {
                    @Override
                    public void onSuccess(String url, String result) {
                        if (null != mUploadCallback) {
                            mUploadCallback.fileUploadSuccess(uploadFile.getAbsolutePath());
                        }
                        isUploadFinish = true;
                        upLoadList.remove(0);
                    }

                    @Override
                    public void onFailure(String url, int statusCode, String msg) {
                        super.onFailure(url, statusCode, msg);
                        setUploadFail();
                    }
                });
    }

    private void networkRequest(Request request, RequestCallback callback) {
        LinkedList<Interceptor> interceptors = new LinkedList<>();
        NetworkLogUtil.addLog(request);
        if (request == null)
            throw new NullPointerException("request为空");
        OkHttpRequestHelper helper = OkHttpRequestHelper.newInstance();
        if (interceptors != null && interceptors.size() > 0)
            helper.addInterceptors(interceptors);
        helper.request(request, callback);
    }
}
