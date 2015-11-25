package com.sunnybear.library.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.sunnybear.library.BasicApplication;
import com.sunnybear.library.R;
import com.sunnybear.library.controller.handler.WeakHandler;
import com.sunnybear.library.controller.intent.FragmentIntent;
import com.sunnybear.library.eventbus.EventBusHelper;
import com.sunnybear.library.util.DiskFileCacheHelper;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.loading.LoadingHUD;

import butterknife.ButterKnife;

/**
 * 基础FragmentActivity
 * Created by guchenkai on 2015/11/19.
 */
public abstract class BasicFragmentActivity<App extends BasicApplication> extends AppCompatActivity {
    private static final int WHAT_ON_HOME_CLICK = 0x1;
    protected Context mContext;
    protected App mApp;
    private OkHttpClient mOkHttpClient;
    protected LoadingHUD loading;

    protected Bundle args;
    private static WeakHandler mWeakHandler;
    private HomeBroadcastReceiver mReceiver = new HomeBroadcastReceiver();//监听Home键

    protected DiskFileCacheHelper mDiskFileCacheHelper;

    /**
     * 设置布局id
     *
     * @return 布局id
     */
    protected abstract int getLayoutId();

    /**
     * 布局初始化完成的回调
     *
     * @param saveInstanceState 保存状态
     */
    protected abstract void onViewCreateFinish(Bundle saveInstanceState);

    /**
     * 收集本Activity请求时的url
     *
     * @return url
     */
    protected abstract String[] getRequestUrls();

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);

        mContext = this;
        mApp = (App) getApplication();
        mOkHttpClient = mApp.getOkHttpClient();
        this.loading = LoadingHUD.getINSTANCE(mContext);
        this.args = getIntent().getExtras() != null ? getIntent().getExtras() : new Bundle();
        mDiskFileCacheHelper = mApp.getDiskFileCacheHelper();
        mWeakHandler = new WeakHandler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case WHAT_ON_HOME_CLICK:
                        onHomeClick();
                        break;
                }
                return false;
            }
        });
        ActivityManager.getInstance().addActivity(this);//添加当前Activity到管理堆栈
        //监听Home键
        registerReceiver(mReceiver, Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        EventBusHelper.register(this);//注册EventBus
        //布局初始化完成的回调
        onViewCreateFinish(savedInstanceState);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Activity停止时取消所有请求
        String[] urls = getRequestUrls();
        for (String url : urls) {
            mOkHttpClient.cancel(url);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusHelper.unregister(this);//反注册EventBus
        unregisterReceiver(mReceiver);
    }

    @Override
    public void finish() {
        ActivityManager.getInstance().removeCurrentActivity();
        super.finish();
    }

    /**
     * 注册广播接收器
     *
     * @param receiver 广播接收器
     * @param actions  广播类型
     */
    protected void registerReceiver(BroadcastReceiver receiver, String... actions) {
        IntentFilter intentFilter = new IntentFilter();
        for (String action : actions) {
            intentFilter.addAction(action);
        }
        registerReceiver(receiver, intentFilter);
    }

    /**
     * 网络请求
     *
     * @param request  request主体
     * @param callback 请求回调(建议使用SimpleFastJsonCallback)
     */
    protected void networkRequest(Request request, Callback callback) {
        if (request == null)
            throw new NullPointerException("request为空");
        loading.show();
        mOkHttpClient.newCall(request).enqueue(callback);
    }

    /**
     * 启动Fragment
     *
     * @param intent Fragment意图
     */
    protected void startFragment(FragmentIntent intent) {
        Class<? extends Fragment> targetFragmentClazz = intent.getTargetFragmentClazz();
        Bundle args = intent.getExtras();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container,
                        Fragment.instantiate(mContext, targetFragmentClazz.getName(), args), targetFragmentClazz.getName()).commit();
    }

    /**
     * 点击Home键,程序退回后台
     */
    protected void onHomeClick() {

    }

    /**
     * 监听Home键广播接收器
     */
    private static class HomeBroadcastReceiver extends BroadcastReceiver {
        private String SYSTEM_REASON = "reason";
        private String SYSTEM_HOME_KEY = "homekey";
        private String SYSTEM_HOME_KEY_LONG = "recentapps";

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(action)) {
                String reason = intent.getStringExtra(SYSTEM_REASON);
                if (TextUtils.equals(reason, SYSTEM_HOME_KEY))//表示按了home键,程序到了后台
                    mWeakHandler.sendEmptyMessage(WHAT_ON_HOME_CLICK);
                else if (TextUtils.equals(reason, SYSTEM_HOME_KEY_LONG))//表示长按home键,显示最近使用的程序列表
                    Logger.d("长按Home键,显示最近使用的程序列表");
            }
        }
    }
}
