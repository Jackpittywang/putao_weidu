package com.putao.wd.home;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.putao.mtlib.util.NetManager;
import com.putao.wd.GlobalApplication;
import com.putao.wd.IndexActivity;
import com.putao.wd.R;
import com.putao.wd.RedDotReceiver;
import com.putao.wd.account.AccountConstants;
import com.putao.wd.account.AccountHelper;
import com.putao.wd.api.CompanionApi;
import com.putao.wd.base.PTWDFragment;
import com.putao.wd.db.CompanionDBManager;
import com.putao.wd.db.entity.CompanionDB;
import com.putao.wd.home.adapter.CompanionAdapter;
import com.putao.wd.model.Companion;
import com.putao.wd.model.ServiceMessage;
import com.putao.wd.model.ServiceMessageContent;
import com.putao.wd.model.ServiceMessageList;
import com.putao.wd.pt_companion.GameDetailListActivity;
import com.putao.wd.qrcode.CaptureActivity;
import com.putao.wd.user.LoginActivity;
import com.sunnybear.library.controller.ActivityManager;
import com.sunnybear.library.controller.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.PreferenceUtils;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.PullToRefreshLayout;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.recycler.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 陪伴
 * Created by zhanghao on 2016/04/05.
 */
public class PutaoCompanionFragment extends PTWDFragment<GlobalApplication> implements OnItemClickListener<Companion>, View.OnClickListener {
    private CompanionAdapter mCompanionAdapter;
    @Bind(R.id.rv_content)
    BasicRecyclerView rv_content;
    @Bind(R.id.ptl_refresh)
    PullToRefreshLayout ptl_refresh;
    @Bind(R.id.rl_companion_empty)
    RelativeLayout rl_companion_empty;
    @Bind(R.id.rl_no_commpain)
    RelativeLayout rl_no_commpain;
    @Bind(R.id.rl_no_commpain_failure)
    RelativeLayout rl_no_commpain_failure;
    @Bind(R.id.btn_no_data)
    Button btn_no_data;
    @Bind(R.id.iv_no_commpain)
    ImageDraweeView iv_no_commpain;

    private ArrayList<Companion> mCompanion;
    private CountDownTimer mTimer;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_companion;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        addNavigation();
        navigation_bar.setLeftClickable(false);
        navigation_bar.getLeftView().setVisibility(View.GONE);
        checkDevice();
        final int[] picChangeCount = {0};

//        PropertyValuesHolder showAnim = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);
//        PropertyValuesHolder hindAnim = PropertyValuesHolder.ofFloat("alpha", 1f, 0f);
//        ObjectAnimator.ofPropertyValuesHolder(showAnim, hindAnim).setDuration(3000).start();
 /*       final ObjectAnimator showAnim = ObjectAnimator.ofFloat(iv_no_commpain, "alpha", 0, 1f);
        showAnim.setDuration(2000);
        final ObjectAnimator hindAnim = ObjectAnimator.ofFloat(iv_no_commpain, "alpha", 1f, 0);
        hindAnim.setDuration(1000);*/
        final AnimationSet set = new AnimationSet(true);
        AlphaAnimation hindAnim = new AlphaAnimation(1f, 0.1f);
        //hindAnim.setFillAfter(false);
        hindAnim.setDuration(1000);
        hindAnim.setStartOffset(1500);
        AlphaAnimation showAnim = new AlphaAnimation(0.1f, 1f);
        showAnim.setDuration(1500);
        //showAnim.setFillAfter(false);
        set.addAnimation(showAnim);
        set.addAnimation(hindAnim);
        set.setDuration(2500);
        iv_no_commpain.startAnimation(set);
        mTimer = new CountDownTimer(7200 * 1000, 3500) {

            @Override
            public void onTick(long millisUntilFinished) {
                switch (picChangeCount[0] % 4) {
                    case 0:
                        iv_no_commpain.setImageURL(Uri.parse("res://putao/" + R.drawable.img_link_product_01).toString());
                        break;
                    case 1:
                        iv_no_commpain.setImageURL(Uri.parse("res://putao/" + R.drawable.img_link_product_02).toString());
                        break;
                    case 2:
                        iv_no_commpain.setImageURL(Uri.parse("res://putao/" + R.drawable.img_link_product_03).toString());
                        break;
                    case 3:
                        iv_no_commpain.setImageURL(Uri.parse("res://putao/" + R.drawable.img_link_product_04).toString());
                        break;
                }
                iv_no_commpain.startAnimation(set);
                picChangeCount[0]++;
//                picChangeCount;
            }

            @Override
            public void onFinish() {

            }
        };
        mTimer.start();
    }

    private void checkDevice() {
        Logger.d("IS_DEVICE_BIND", PreferenceUtils.getValue(GlobalApplication.IS_DEVICE_BIND + AccountHelper.getCurrentUid(), false) + "");
        Logger.d("AccountHelper.isLogin()", AccountHelper.isLogin() + "");
        if (PreferenceUtils.getValue(GlobalApplication.IS_DEVICE_BIND + AccountHelper.getCurrentUid(), false) && AccountHelper.isLogin()) {
            rl_companion_empty.setVisibility(View.GONE);
            navigation_bar.setVisibility(View.VISIBLE);
            ptl_refresh.setVisibility(View.VISIBLE);
            if (null == mCompanionAdapter)
                mCompanionAdapter = new CompanionAdapter(mActivity, null);
            rv_content.setAdapter(mCompanionAdapter);
            addListener();
            initData();
        } else {
            rl_companion_empty.setVisibility(View.VISIBLE);
            navigation_bar.setVisibility(View.GONE);
            ptl_refresh.setVisibility(View.GONE);
        }
    }

    private void initData() {
        networkRequestCache(CompanionApi.getServiceUserRelation(),
                new SimpleFastJsonCallback<ArrayList<Companion>>(Companion.class, loading) {
                    @Override
                    public void onSuccess(String url, ArrayList<Companion> result) {
                        if (result != null && result.size() > 0) {
                            mCompanion = result;
                            cacheData(url, result);
                            CompanionDBManager dataBaseManager = (CompanionDBManager) mApp.getDataBaseManager(CompanionDBManager.class);
                            for (Companion companion : result) {
                                ServiceMessage serviceMessage = companion.getAuto_reply();
                                if (null != serviceMessage && null != serviceMessage.getLists()) {
                                    for (ServiceMessageList serviceMessageList : serviceMessage.getLists()) {
                                        dataBaseManager.insertFinishDownload(companion.getService_id(), serviceMessageList.getId(), serviceMessageList.getRelease_time() + "", JSON.toJSONString(serviceMessageList.getContent_lists()));
                                    }
                                }
                                ArrayList<String> notDownloadIds = dataBaseManager.getNotDownloadIds(companion.getService_id());
                                try {
                                    CompanionDB companionDB = dataBaseManager.getNearestItem(companion.getService_id());
                                    if (companionDB != null) {
                                        int time = Integer.parseInt(companionDB.getRelease_time());
                                        if (time > 0) {
                                            companion.setRelation_time(time);
                                        }
                                        List<ServiceMessageContent> content_lists = JSON.parseArray(companionDB.getContent_lists(), ServiceMessageContent.class);
                                        if (null != content_lists && content_lists.size() >= 0)
                                            companion.setService_description(content_lists.get(0).getSub_title());
                                    }
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                }
//                            companion.setService_description();
                                if (notDownloadIds.size() > 0) companion.setIsShowRed(true);
                                companion.setNotDownloadIds(notDownloadIds);
                            }
                            mCompanionAdapter.replaceAll(result);
                            rl_no_commpain.setVisibility(View.GONE);
                            ptl_refresh.setVisibility(View.VISIBLE);
                        } else {
                            rl_no_commpain.setVisibility(View.VISIBLE);
                            ptl_refresh.setVisibility(View.GONE);
                        }
                        ptl_refresh.refreshComplete();
                        loading.dismiss();
                    }

                    @Override
                    public void onFailure(String url, int statusCode, String msg) {
                        super.onFailure(url, statusCode, msg);
                        if (mCompanionAdapter.getItemCount() == 0) {
                            rl_no_commpain_failure.setVisibility(View.VISIBLE);
                            ptl_refresh.setVisibility(View.GONE);
                            ptl_refresh.refreshComplete();
                        }
                    }
                }, 600 * 1000);
    }

    @Override
    public void onStart() {
        super.onStart();
        checkDevice();
    }

    private void addListener() {
        mCompanionAdapter.setOnItemClickListener(this);
        ptl_refresh.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
        btn_no_data.setOnClickListener(this);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onItemClick(Companion companion, int position) {
        if (1 == companion.getIs_relation()) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(AccountConstants.Bundle.BUNDLE_COMPANION, companion);
           /* ArrayList<String> notDownloadIds = companion.getNotDownloadIds();
            notDownloadIds.removeAll(notDownloadIds);
            companion.setNotDownloadIds(notDownloadIds);
            mCompanionAdapter.notifyItemChanged(position);*/
            companion.setIsShowRed(false);
            mCompanionAdapter.notifyItemChanged(position);
            startActivity(GameDetailListActivity.class, bundle);
        } else {
            startActivity(CaptureActivity.class);
        }
    }

    @OnClick(R.id.btn_relevance_device)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_relevance_device:
                if (!AccountHelper.isLogin()) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(LoginActivity.TERMINAL_ACTIVITY, CaptureActivity.class);
                    startActivity(LoginActivity.class, bundle);
                } else
                    startActivity(CaptureActivity.class);
                break;
            case R.id.btn_no_data:
                if (NetManager.isNetworkAvailable(mActivity))
                    ToastUtils.showToastShort(mActivity, "获取数据失败");
                else
                    initData();
                break;
        }
    }

    @Override
    public void onRightAction() {
        super.onRightAction();
        startActivity(CaptureActivity.class);
    }

    @Subcriber(tag = AccountConstants.EventBus.EVENT_REFRESH_COMPANION)
    private void refresh_companion(String str) {
        checkDevice();
    }

    @Subcriber(tag = RedDotReceiver.COMPANION_TABBAR)
    private void setCompanionDot(JSONArray accompanyNumber) {
        for (Object object : accompanyNumber) {
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(object));
            String service_id = jsonObject.getString(RedDotReceiver.SERVICE_ID);
            String id = jsonObject.getString(RedDotReceiver.ID);
            for (Companion companion : mCompanion) {
                if (companion.getService_id().equals(service_id)) {
                    ArrayList<String> notDownloadIds = companion.getNotDownloadIds();
                    notDownloadIds.add(id);
                    companion.setIsShowRed(true);
                    companion.setNotDownloadIds(notDownloadIds);
                    mCompanionAdapter.notifyItemChanged(mCompanion.indexOf(companion));
                }
            }
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTimer.cancel();
    }

    @Override
    public void onLeftAction() {
        super.onLeftAction();
        ActivityManager.getInstance().popOtherActivity(IndexActivity.class);
    }
}


