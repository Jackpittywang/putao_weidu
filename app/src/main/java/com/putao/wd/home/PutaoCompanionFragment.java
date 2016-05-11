package com.putao.wd.home;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.MyViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.putao.wd.account.YouMengHelper;
import com.putao.wd.api.CompanionApi;
import com.putao.wd.base.PTWDFragment;
import com.putao.wd.db.CompanionDBManager;
import com.putao.wd.db.entity.CompanionDB;
import com.putao.wd.home.adapter.CompanionAdapter;
import com.putao.wd.model.Companion;
import com.putao.wd.model.GpushMessageAccNumber;
import com.putao.wd.model.ServiceMessage;
import com.putao.wd.model.ServiceMessageContent;
import com.putao.wd.model.ServiceMessageList;
import com.putao.wd.pt_companion.GameDetailListActivity;
import com.putao.wd.pt_companion.OfficialAccountsActivity;
import com.putao.wd.pt_companion.PutaoSubcribeActivity;
import com.putao.wd.pt_companion.SubscriptionNumberActivity;
import com.putao.wd.qrcode.CaptureActivity;
import com.putao.wd.user.LoginActivity;
import com.sunnybear.library.controller.ActivityManager;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
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
    @Bind(R.id.rl_compain_navigation)
    RelativeLayout rl_compain_navigation;
    @Bind(R.id.btn_no_data)
    Button btn_no_data;
    @Bind(R.id.iv_no_commpain)
    ImageDraweeView iv_no_commpain;
    @Bind(R.id.img_compain_menu)
    ImageView img_compain_menu;
    @Bind(R.id.tv_later_relevance)
    TextView tv_later_relevance;


    private ArrayList<Companion> mCompanion;
    private int mPicChangeCount;
    private AnimationSet mSet;
    private PopupWindow popupWindow;
    private View contentView;
    private LinearLayout ll_ScanCode, ll_Subscribe;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_companion;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        addNavigation();
        navigation_bar.setLeftClickable(false);
        navigation_bar.getLeftView().setVisibility(View.GONE);
        //弹框
        onCompainPopupWindow();
        checkDevice();
        mPicChangeCount = 1;
        startAnim();
    }


    private void checkDevice() {
        Logger.d("IS_DEVICE_BIND", PreferenceUtils.getValue(GlobalApplication.IS_DEVICE_BIND + AccountHelper.getCurrentUid(), false) + "");
        Logger.d("AccountHelper.isLogin()", AccountHelper.isLogin() + "");
//        PreferenceUtils.getValue(GlobalApplication.IS_DEVICE_BIND + AccountHelper.getCurrentUid(), false) &&
        if (AccountHelper.isLogin()) {
            rl_companion_empty.setVisibility(View.GONE);
            rl_compain_navigation.setVisibility(View.VISIBLE);
            ptl_refresh.setVisibility(View.VISIBLE);
            if (null == mCompanionAdapter)
                mCompanionAdapter = new CompanionAdapter(mActivity, null);
            rv_content.setAdapter(mCompanionAdapter);
            addListener();
            initData();
        } else {
            rl_companion_empty.setVisibility(View.VISIBLE);
            ptl_refresh.setVisibility(View.GONE);
            rl_compain_navigation.setVisibility(View.GONE);
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
                                    if (companionDB != null && !TextUtils.isEmpty(companionDB.getContent_lists())) {
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
                        if (mCompanionAdapter.getItemCount() <= 1) {
                            rl_no_commpain_failure.setVisibility(View.VISIBLE);
                            ptl_refresh.setVisibility(View.GONE);
                            ptl_refresh.refreshComplete();
                        }
                    }
                }, 0);
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
        img_compain_menu.setOnClickListener(this);
        tv_later_relevance.setOnClickListener(this);
        ll_ScanCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(AccountConstants.Bundle.BUNDLE_COMPANION_BIND, true);
                startActivity(CaptureActivity.class, bundle);
                popupWindow.dismiss();
            }
        });
        ll_Subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SubscriptionNumberActivity.class);
                popupWindow.dismiss();
            }
        });
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onItemClick(Companion companion, int position) {
        YouMengHelper.onEvent(mActivity, YouMengHelper.Activity_list_detail);
        YouMengHelper.onEvent(mActivity, YouMengHelper.AccompanyHome_app_game, companion.getService_name());
        /*switch (companion.getService_name()) {
            case "葡萄黑板报":
                YouMengHelper.onEvent(mActivity, YouMengHelper.AccompanyHome_app_game, "葡萄活动");
                break;
            case "Hello编程":
                YouMengHelper.onEvent(mActivity, YouMengHelper.AccompanyHome_app_game, "Hello编程");
                break;
            case "淘淘向右走":
                YouMengHelper.onEvent(mActivity, YouMengHelper.AccompanyHome_app_game, "淘淘向右走");
                break;
            case "旋转吧，魔方":
                YouMengHelper.onEvent(mActivity, YouMengHelper.AccompanyHome_app_game, "旋转吧，魔方");
                break;
            case "班得瑞的奇幻花园":
                YouMengHelper.onEvent(mActivity, YouMengHelper.AccompanyHome_app_game, "班得瑞的奇幻花园");
                break;
            case "麦斯丝":
                YouMengHelper.onEvent(mActivity, YouMengHelper.AccompanyHome_app_game, "麦斯丝");
                break;
            case "哈泥海洋":
                YouMengHelper.onEvent(mActivity, YouMengHelper.AccompanyHome_app_game, "哈尼海洋");
                break;
            case "涂涂世界":
                YouMengHelper.onEvent(mActivity, YouMengHelper.AccompanyHome_app_game, "涂涂世界");
                break;
        }*/
        Bundle bundle = new Bundle();
        if (1 == companion.getIs_relation()) {
            bundle.putSerializable(AccountConstants.Bundle.BUNDLE_COMPANION, companion);
           /* ArrayList<String> notDownloadIds = companion.getNotDownloadIds();
            notDownloadIds.removeAll(notDownloadIds);
            companion.setNotDownloadIds(notDownloadIds);
            mCompanionAdapter.notifyItemChanged(position);*/
            if (companion.getService_type() == 0) {
                startActivity(PutaoSubcribeActivity.class, bundle);
            } else {
                companion.setIsShowRed(false);
                boolean isShowTabDot = false;
                for (Companion compan : mCompanion) {
                    isShowTabDot = compan.isShowRed() || isShowTabDot;
                    if (isShowTabDot) break;
                }
                if (!isShowTabDot)
                    EventBusHelper.post("", AccountConstants.EventBus.EVENT_CANCEL_COMPANION_TAB);
                mCompanionAdapter.notifyItemChanged(position);
                startActivity(GameDetailListActivity.class, bundle);
            }
        } else {
            bundle.putSerializable(AccountConstants.Bundle.BUNDLE_COMPANION_BIND_SERVICE, true);
            bundle.putSerializable(AccountConstants.Bundle.BUNDLE_COMPANION, companion);
            startActivity(OfficialAccountsActivity.class, bundle);
        }
    }

    @OnClick({R.id.btn_relevance_device, R.id.img_compain_menu, R.id.tv_later_relevance})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_relevance_device:
                if (!AccountHelper.isLogin()) {
                    Bundle bundle = new Bundle();
                    bundle.putBoolean(AccountConstants.Bundle.BUNDLE_COMPANION_BIND, true);
                    bundle.putSerializable(LoginActivity.TERMINAL_ACTIVITY, IndexActivity.class);
                    startActivity(LoginActivity.class, bundle);
                } else
                    startActivity(CaptureActivity.class);
                break;
            case R.id.btn_no_data://加载失败时，点击刷新数据
                if (NetManager.isNetworkAvailable(mActivity))
                    ToastUtils.showToastShort(mActivity, "获取数据失败");
                else
                    initData();
                break;
            case R.id.img_compain_menu://点击“+”号，弹出关于扫一扫和订阅号的菜单框
                popupWindow.showAsDropDown(img_compain_menu);
                break;
            case R.id.tv_later_relevance://稍后关联
                if (!AccountHelper.isLogin()) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(LoginActivity.TERMINAL_ACTIVITY, IndexActivity.class);
                    startActivity(LoginActivity.class, bundle);
                } else {
                    //当已登录时，请求数据
                    checkDevice();
                }
                break;
        }
    }


    @Subcriber(tag = AccountConstants.EventBus.EVENT_REFRESH_COMPANION)
    private void refresh_companion(String str) {
        checkDevice();
    }

    @Subcriber(tag = RedDotReceiver.COMPANION_TABBAR)
    private void setCompanionDot(ArrayList<GpushMessageAccNumber> accompanyNumber) {
        for (GpushMessageAccNumber gpushMessageAccNumber : accompanyNumber) {
            String service_id = gpushMessageAccNumber.getService_id();
            String id = gpushMessageAccNumber.getId();
            for (Companion companion : mCompanion) {
                if (companion.getService_id().equals(service_id)) {
                    ArrayList<String> notDownloadIds = companion.getNotDownloadIds();
                    notDownloadIds.add(id);
                    companion.setIsShowRed(true);
                    companion.setNotDownloadIds(notDownloadIds);
                    mCompanionAdapter.notifyItemChanged(mCompanion.indexOf(companion));
                } else if (1 != companion.getService_type()) {
                    for (Companion SecondCompanion : companion.getSecond_level_lists()) {
                        if (companion.getService_id().equals(service_id)) {
                            ArrayList<String> notDownloadIds = SecondCompanion.getNotDownloadIds();
                            notDownloadIds.add(id);
                            SecondCompanion.setIsShowRed(true);
                            SecondCompanion.setNotDownloadIds(notDownloadIds);
                            mCompanionAdapter.notifyItemChanged(mCompanion.indexOf(companion));
                        }
                    }
                }
            }
        }

    }

    private void startAnim() {
        mSet = new AnimationSet(true);
        AlphaAnimation hindAnim = new AlphaAnimation(1f, 0f);
        hindAnim.setDuration(1500);
        hindAnim.setStartOffset(2000);
        AlphaAnimation showAnim = new AlphaAnimation(0f, 1f);
        showAnim.setDuration(2000);
        mSet.addAnimation(showAnim);
        mSet.addAnimation(hindAnim);
        mSet.setDuration(3500);
        iv_no_commpain.startAnimation(mSet);
        mSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                switch (mPicChangeCount % 4) {
                    case 0:
                        iv_no_commpain.setDefaultImage(R.drawable.img_link_product_01);
                        break;
                    case 1:
                        iv_no_commpain.setDefaultImage(R.drawable.img_link_product_02);
                        break;
                    case 2:
                        iv_no_commpain.setDefaultImage(R.drawable.img_link_product_03);
                        break;
                    case 3:
                        iv_no_commpain.setDefaultImage(R.drawable.img_link_product_04);
                        break;
                }
                iv_no_commpain.startAnimation(mSet);
                mPicChangeCount++;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSet.cancel();
    }

    @Override
    public void onLeftAction() {
        super.onLeftAction();
        ActivityManager.getInstance().popOtherActivity(IndexActivity.class);
    }


    /**
     * 弹出扫一扫和订阅号的弹框
     */
    private void onCompainPopupWindow() {
        contentView = mActivity.getLayoutInflater().inflate(R.layout.activity_compain_menulist, null);
        popupWindow = new PopupWindow(contentView, MyViewPager.LayoutParams.WRAP_CONTENT, MyViewPager.LayoutParams.WRAP_CONTENT);
        ll_ScanCode = (LinearLayout) contentView.findViewById(R.id.ll_ScanCode);
        ll_Subscribe = (LinearLayout) contentView.findViewById(R.id.ll_Subscribe);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
    }


}


