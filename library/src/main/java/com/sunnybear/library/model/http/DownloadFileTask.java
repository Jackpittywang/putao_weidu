package com.sunnybear.library.model.http;

import android.os.AsyncTask;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.sunnybear.library.BasicApplication;
import com.sunnybear.library.model.http.callback.DownloadFileCallback;
import com.sunnybear.library.util.FileUtils;
import com.sunnybear.library.util.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 文件下载任务
 * Created by guchenkai on 2015/12/18.
 */
public class DownloadFileTask extends AsyncTask<Void, Long, Boolean> {
    private OkHttpClient mOkHttpClient;
    private String mUrl;//现在地址
    private File mTarget;//目标文件
    private String mFileName;//现在文件名
    private DownloadFileCallback mCallback;
    private long mPreviousTime;//开始下载时间，用户计算加载速度

    public DownloadFileTask(String url, DownloadFileCallback callback) {
        mUrl = url;
        mFileName = FileUtils.getDownloadFileName(url);
        mTarget = new File(BasicApplication.sdCardPath + File.separator + mFileName);
        mCallback = callback;
        mOkHttpClient = BasicApplication.getOkHttpClient();

        FileUtils.createFile(mTarget);
        if (mTarget.exists()) mTarget.delete();
    }

    /**
     * 获得下载文件名
     *
     * @return 下载文件名
     */
    public String getDownloadFileName() {
        return mFileName.split("\\.")[0];
    }

    /**
     * 获取下载文件
     *
     * @return 下载文件
     */
    public File getDownloadFile() {
        return mTarget;
    }

    @Override
    protected void onPreExecute() {
        mPreviousTime = System.currentTimeMillis();
        if (mCallback != null) mCallback.onStart();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        //构造请求
        Request request = new Request.Builder().url(mUrl).tag(mUrl).build();
        boolean isSuccess = false;
        try {
            Response response = mOkHttpClient.newCall(request).execute();
            long total = response.body().contentLength();
            saveFile(response);
            if (total == mTarget.length()) isSuccess = true;
        } catch (IOException e) {
            Logger.e(e);
        }
        return isSuccess;
    }

    @Override
    protected void onProgressUpdate(Long... values) {
        if (mCallback != null && values != null && values.length > 2) {
            long sum = values[0];
            long total = values[1];
            int progress = (int) (sum * 100.f / total);
            //计算下载速度
            long totalTime = (System.currentTimeMillis() - mPreviousTime) / 1000;
            if (totalTime == 0) totalTime++;
            long networkSpeed = sum / totalTime;
            if (mCallback != null)
                mCallback.onProgress(progress, networkSpeed);
        }
    }

    @Override
    protected void onPostExecute(Boolean isSuccess) {
        if (mCallback != null)
                mCallback.onFinish(isSuccess);
    }

    /**
     * 取消下载
     */
    public void cancel() {
        mOkHttpClient.cancel(mUrl);
        onPostExecute(false);
    }

    /**
     * 保存下载文件
     *
     * @param response 网络响应
     */
    private String saveFile(Response response) {
        InputStream in = null;
        byte[] buffer = new byte[2048];
        int len = 0;
        FileOutputStream fos = null;
        try {
            in = response.body().byteStream();
            long total = response.body().contentLength();
            long sum = 0;
            FileUtils.createFile(mTarget);
            fos = new FileOutputStream(mTarget);
            while ((len = in.read(buffer)) != -1) {
                sum += len;
                fos.write(buffer, 0, len);
                if (mCallback != null) publishProgress(sum, total);
            }
            fos.flush();
            return mTarget.getAbsolutePath();
        } catch (IOException e) {
            Logger.e(e);
        } finally {
            try {
                if (in != null) in.close();
                if (fos != null) fos.close();
            } catch (IOException e) {
                Logger.e(e);
            }
        }
        return null;
    }
}
