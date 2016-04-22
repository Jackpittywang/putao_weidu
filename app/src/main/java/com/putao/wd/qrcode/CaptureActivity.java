package com.putao.wd.qrcode;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dtr.zbar.build.ZBarDecoder;
import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.account.AccountConstants;
import com.putao.wd.account.AccountHelper;
import com.putao.wd.account.YouMengHelper;
import com.putao.wd.api.CompanionApi;
import com.putao.wd.api.ExploreApi;
import com.putao.wd.api.ScanApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.db.CompanionDBManager;
import com.putao.wd.model.ServiceMessage;
import com.putao.wd.model.ServiceMessageList;
import com.putao.wd.pt_companion.GameDetailListActivity;
import com.putao.wd.user.WebLoginActivity;
import com.putao.wd.util.ScanUrlParseUtils;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
import com.sunnybear.library.model.http.callback.JSONObjectCallback;
import com.sunnybear.library.util.DensityUtil;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.PreferenceUtils;
import com.sunnybear.library.util.StringUtils;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.bubble.TooltipView;

import java.lang.reflect.Field;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 二维码识别
 * Created by riven_chris on 2015/11/4.
 */
public class CaptureActivity extends PTWDActivity<GlobalApplication> implements View.OnClickListener {
    private CameraPreview mPreview;
    private Camera mCamera;
    private Handler autoFocusHandler;
    private CameraManager mCameraManager;

    @Bind(R.id.capture_preview)
    FrameLayout scanPreview;
    @Bind(R.id.capture_container)
    RelativeLayout scanContainer;
    @Bind(R.id.capture_crop_view)
    ImageView scanCropView;
    @Bind(R.id.scan_line)
    ImageView scan_line;

    //    @Bind(R.id.ttv_question_1)
//    TooltipView ttv_question_1;
    @Bind(R.id.ttv_question_2)
    TooltipView ttv_question_2;

    private Rect mCropRect = null;
    private boolean barcodeScanned = false;
    private boolean previewing = true;
    private boolean isRequesting = false;

    private Camera.PreviewCallback previewCb = new Camera.PreviewCallback() {
        public void onPreviewFrame(byte[] data, Camera camera) {
            Camera.Size size = camera.getParameters().getPreviewSize();
            // 这里需要将获取的data翻转一下，因为相机默认拿的的横屏的数据
            byte[] rotatedData = new byte[data.length];
            for (int y = 0; y < size.height; y++) {
                for (int x = 0; x < size.width; x++)
                    rotatedData[x * size.height + size.height - y - 1] = data[x + y * size.width];
            }
            // 宽高也要调整
            int tmp = size.width;
            size.width = size.height;
            size.height = tmp;

            initCrop();
            ZBarDecoder zBarDecoder = new ZBarDecoder();
            String result = zBarDecoder
                    .decodeCrop(rotatedData, size.width, size.height, mCropRect.left, mCropRect.top, mCropRect.width(), mCropRect.height());
            if (!TextUtils.isEmpty(result)) {
                barcodeScanned = processor(result);//处理结果
//                if(barcodeScanned) {
//                    previewing = false;
//                    mCamera.setPreviewCallback(null);
//                    mCamera.stopPreview();
//
//                }
                // barcodeScanned = true;
            }
        }
    };
    private Runnable doAutoFocus = new Runnable() {
        public void run() {
            if (previewing)
                mCamera.autoFocus(autoFocusCB);
        }
    };
    // Mimic continuous auto-focusing
    private Camera.AutoFocusCallback autoFocusCB = new Camera.AutoFocusCallback() {
        public void onAutoFocus(boolean success, Camera camera) {
            autoFocusHandler.postDelayed(doAutoFocus, 1000);
        }
    };
    //扫面线动画
    private TranslateAnimation animation;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_capture;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        YouMengHelper.onEvent(mContext, YouMengHelper.Scan_action, "扫一扫");
        addNavigation();
        setMainTitleColor(Color.WHITE);
        initViews();
       /* ScaleAnimation animation = new ScaleAnimation(1.0f, 1.0f, 0.0f, 1.0f);
        animation.setRepeatCount(-1);
        animation.setRepeatMode(Animation.RESTART);
        animation.setInterpolator(new LinearInterpolator());
        animation.setDuration(1200);
        scan_line.startAnimation(animation);*/
        initAnimation();
//        scan_line.startAnimation(animation);
    }

    private void initAnimation() {
        int height = DensityUtil.dp2px(mContext, 150);
     /*   animation = new TranslateAnimation(0, 0, -height, height);
        animation.setDuration(1200);
        animation.setRepeatCount(-1);
        animation.setInterpolator(new LinearInterpolator());*/
        ObjectAnimator rotationX = ObjectAnimator
                .ofFloat(scan_line, "translationY", -height, height);
        rotationX.setRepeatCount(-1);
        rotationX.setRepeatMode(Animation.RESTART);
        rotationX.setDuration(2000);
        rotationX.start();
//        scan_line.animate().translationX(1.0f).
//                .start();

    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @OnClick({R.id.tv_question_2, R.id.capture_container})
//    R.id.tv_question_1,
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.tv_question_1:
//                ttv_question_1.setVisibility(ttv_question_1.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
//                ttv_question_2.setVisibility(View.GONE);
//                break;
            case R.id.tv_question_2:
                ttv_question_2.setVisibility(ttv_question_2.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
//                ttv_question_1.setVisibility(View.GONE);
                break;
            case R.id.capture_container:
//                ttv_question_1.setVisibility(View.GONE);
                ttv_question_2.setVisibility(View.GONE);
                break;
        }
    }

    private void initViews() {
        autoFocusHandler = new Handler();
        mCameraManager = new CameraManager(this);
        try {
            mCameraManager.openDriver();
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showToastShort(mContext, "相机打开失败,请打开相机");
            finish();
            return;
        }
        mCamera = mCameraManager.getCamera();
        mPreview = new com.putao.wd.qrcode.CameraPreview(this, mCamera, previewCb, autoFocusCB);
        scanPreview.addView(mPreview);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPreview == null)
            initViews();
    }

    public void onPause() {
        super.onPause();
        releaseCamera();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCameraManager.closeDriver();
        scan_line.clearAnimation();
    }

    private void releaseCamera() {
        if (mCamera != null) {
            previewing = false;
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
            scanPreview.removeView(mPreview);
            mPreview = null;
        }
    }


    private void stopPreview() {
        if (mCamera != null) {
            try {
                mCamera.stopPreview();
                previewing = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 初始化截取的矩形区域
     */
    private void initCrop() {
        int cameraWidth = mCameraManager.getCameraResolution().y;
        int cameraHeight = mCameraManager.getCameraResolution().x;

        /** 获取布局中扫描框的位置信息 */
        int[] location = new int[2];
        scanCropView.getLocationInWindow(location);

        int cropLeft = location[0];
        int cropTop = location[1] - getStatusBarHeight();

        int cropWidth = scanCropView.getWidth();
        int cropHeight = scanCropView.getHeight();

        /** 获取布局容器的宽高 */
        int containerWidth = scanContainer.getWidth();
        int containerHeight = scanContainer.getHeight();

        /** 计算最终截取的矩形的左上角顶点x坐标 */
        int x = cropLeft * cameraWidth / containerWidth;
        /** 计算最终截取的矩形的左上角顶点y坐标 */
        int y = cropTop * cameraHeight / containerHeight;

        /** 计算最终截取的矩形的宽度 */
        int width = cropWidth * cameraWidth / containerWidth;
        /** 计算最终截取的矩形的高度 */
        int height = cropHeight * cameraHeight / containerHeight;

        /** 生成最终的截取的矩形 */
        mCropRect = new Rect(x, y, width + x, height + y);
    }

    private int getStatusBarHeight() {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 处理结果
     *
     * @param result 扫描结果
     */
    private boolean processor(String result) {
        if (isRequesting == true) return false;
        // Logger.d(result);
//        result = "http://api-resource.start.wang/bind?s=6001&code=5570ca50284ecc";
        String scheme = null;
        try {
            scheme = ScanUrlParseUtils.getScheme(result);
        } catch (Exception e) {
            e.printStackTrace();
            showErrorInfo();
            // finish();
            return false;
        }
        // Logger.d("scheme:" + scheme);
        switch (scheme) {
            case ScanUrlParseUtils.Scheme.PUTAO_LOGIN://扫描登录
                String url = ScanUrlParseUtils.getRequestUrl(result);
                Logger.d("url:" + url);
                isRequesting = true;
                networkRequest(ScanApi.scanLogin(url), new JSONObjectCallback() {
                    @Override
                    public void onSuccess(String url, JSONObject result) {
                        int error_code = result.getInteger("error_code");
                        if (error_code == 0) {
                            Bundle bundle = new Bundle();
                            bundle.putString(WebLoginActivity.URL_LOGIN, url);
                            startActivity(WebLoginActivity.class, bundle);
                        } else {
                            ToastUtils.showToastLong(mContext, "登录失败");
                        }
                        isRequesting = false;
                        loading.dismiss();
                        finish();
                    }

                    @Override
                    public void onCacheSuccess(String url, JSONObject result) {

                    }

                    @Override
                    public void onFailure(String url, int statusCode, String msg) {
                        loading.dismiss();
                        finish();
                        isRequesting = false;
                    }
                });
                break;
            case ScanUrlParseUtils.Scheme.PUTAO_DEVICE:// 扫描添加设备
                //
                String deviceUrl = ScanUrlParseUtils.getDeviceRequestUrl(result);
                Logger.d("proUrl:" + deviceUrl);
                isRequesting = true;
                networkRequest(ExploreApi.addDevice(deviceUrl), new JSONObjectCallback() {
                    @Override
                    public void onSuccess(String url, JSONObject result) {
                        Logger.d(result.toString());
                        int http_code = result.getInteger("http_code");
                        if (http_code == 200) {
                            ToastUtils.showToastLong(mContext, "请更新游戏");
//                            ToastUtils.showToastLong(mContext, "添加成功");
//                            startActivity(GameDetailListActivity.class);
                        } else if (http_code == 4201)
                            ToastUtils.showToastLong(mContext, "重复绑定");
                        else if (http_code == 4200)
                            ToastUtils.showToastLong(mContext, "二维码已过期");
                        else
                            ToastUtils.showToastLong(mContext, "绑定失败");
                        loading.dismiss();
                        isRequesting = false;
                        finish();

                    }

                    @Override
                    public void onCacheSuccess(String url, JSONObject result) {

                    }

                    @Override
                    public void onFailure(String url, int statusCode, String msg) {
                        loading.dismiss();
                        ToastUtils.showToastShort(mContext, msg);
                        isRequesting = false;
                        finish();
                    }
                });
                break;
            case ScanUrlParseUtils.Scheme.HTTP:
                // result = "http://api-resource.start.wang/bind?s=6001&code=5570ca50284ecc";

                // 从url里面获取serverId和code
                final String serverId = ScanUrlParseUtils.getSingleParams(result, "s");
                String code = ScanUrlParseUtils.getSingleParams(result, "code");
                if (StringUtils.isEmpty(serverId) || StringUtils.isEmpty(code)) {
                    showErrorInfo();
                    return false;
                }
                isRequesting = true;
                networkRequest(CompanionApi.bindService(serverId, code), new JSONObjectCallback() {
                    @Override
                    public void onSuccess(String url, JSONObject result) {
                        Logger.d(result.toString());
                        int http_code = result.getInteger("http_code");
                        if (http_code == 200) {
                            try {
                                JSONObject data = result.getJSONObject("data");
                                ServiceMessage serviceMessage = JSON.parseObject(JSON.toJSONString(data), ServiceMessage.class);
                                CompanionDBManager dataBaseManager = (CompanionDBManager) mApp.getDataBaseManager(CompanionDBManager.class);
                                for (ServiceMessageList serviceMessageList : serviceMessage.getLists()) {
                                    dataBaseManager.insertFinishDownload(serverId, serviceMessageList.getId(), serviceMessageList.getRelease_time() + "", JSON.toJSONString(serviceMessageList.getContent_lists()));
                                }
                            } catch (Exception e) {

                            }

                            EventBusHelper.post("", AccountConstants.EventBus.EVENT_REFRESH_COMPANION);
                            // 跳到订阅号列表页面
                            Bundle bundle = new Bundle();
                            bundle.putString(AccountConstants.Bundle.BUNDLE_COMPANION_BIND_SERVICE, serverId);
                            PreferenceUtils.save(GlobalApplication.IS_DEVICE_BIND + AccountHelper.getCurrentUid(), true);
                            startActivity(GameDetailListActivity.class, bundle);
                            ToastUtils.showToastShort(mContext, "添加成功");

                        } else if (http_code == 4201)
                            ToastUtils.showToastShort(mContext, "重复绑定");
                        else if (http_code == 4200)
                            ToastUtils.showToastShort(mContext, "二维码已过期");
                        else {
                            String msg = result.getString("msg");
                            if (msg != null)
                                ToastUtils.showToastShort(mContext, result.getString("msg"));
                            else ToastUtils.showToastShort(mContext, "绑定失败");
                        }
                        loading.dismiss();
                        // isRequesting = false;
                        finish();

                    }

                    @Override
                    public void onCacheSuccess(String url, JSONObject result) {

                    }

                    @Override
                    public void onFailure(String url, int statusCode, String msg) {
                        loading.dismiss();
                        ToastUtils.showToastShort(mContext, msg);
                        isRequesting = false;
                    }
                });
                break;
            default:
                showErrorInfo();
                return false;
        }
        return true;
    }

    private void showErrorInfo() {
        String scaneErrorInfo = "请扫描葡萄产品的二维码";
        ToastUtils.showNoRepeatToast(getApplicationContext(), scaneErrorInfo);
    }


//    // http://www.xxx.com/xxx.html?s=xxx&code=xxx
//    private BarCodeData getBarCodeDataFromUrl(String url) {
//        BarCodeData data = new BarCodeData();
//        if (StringUtils.isEmpty(url)) return data;
//        String value = Uri.parse(url).getQueryParameter("s");
//        if (value == null) value = "";
//        data.setsId(value);
//        value = Uri.parse(url).getQueryParameter("code");
//        if (value == null) value = "";
//        data.setCode(value);
//        return data;
//    }
//
//    /**
//     * 扫描二维码获得的数据
//     */
//    public class BarCodeData {
//        // server id
//        private String sId = "";
//        // code
//        private String code = "";
//
//        public String getsId() {
//            return sId;
//        }
//
//        public void setsId(String sId) {
//            this.sId = sId;
//        }
//
//        public String getCode() {
//            return code;
//        }
//
//        public void setCode(String code) {
//            this.code = code;
//        }
//    }
}
