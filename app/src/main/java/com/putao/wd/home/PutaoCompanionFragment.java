package com.putao.wd.home;

import android.content.ComponentName;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.MyViewPager;
import android.text.TextUtils;
import android.view.View;
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
import com.putao.ptx.push.core.GPushCallback;
import com.putao.ptx.push.core.NetworkUtil;
import com.putao.wd.GlobalApplication;
import com.putao.wd.IndexActivity;
import com.putao.wd.R;
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
import com.putao.wd.model.ServiceMessageListReply;
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
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.recycler.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    /*@Bind(R.id.ptl_refresh)
    PullToRefreshLayout ptl_refresh;*/
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
    @Bind(R.id.iv_step1_first)
    ImageView iv_step1_first;


    private ArrayList<Companion> mCompanion;
    private int mPicChangeCount;
    private AnimationSet mSet;
    private PopupWindow popupWindow;
    private View contentView;
    private LinearLayout ll_ScanCode, ll_Subscribe;
    private CompanionDBManager mDataBaseManager;
    private List<Companion> mSubscriptCompanion;  //葡萄订阅的serviceI

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_companion;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        addNavigation();
        iv_no_commpain.resize(500, 250);
        iv_no_commpain.setImageURL("res://putao/" + R.drawable.img_link_product_01);
        if (!PreferenceUtils.getValue(GlobalApplication.PREFERENCE_STEP1_IS_FIRST, false))
            iv_step1_first.setVisibility(View.VISIBLE);
        else {
            iv_step1_first.setVisibility(View.GONE);
        }
        navigation_bar.setLeftClickable(false);
        navigation_bar.getLeftView().setVisibility(View.GONE);
        //弹框
        mDataBaseManager = (CompanionDBManager) mApp.getDataBaseManager(CompanionDBManager.class);
        onCompainPopupWindow();
//        checkDevice();
        mPicChangeCount = 1;
        startAnim();
    }


    private void checkDevice() {
        Logger.d("IS_DEVICE_BIND", PreferenceUtils.getValue(GlobalApplication.IS_DEVICE_BIND + AccountHelper.getCurrentUid(), false) + "");
        Logger.d("AccountHelper.isLogin()", AccountHelper.isLogin() + "");
        EventBusHelper.post(false, GPushCallback.COMPANION_TABBAR);
//        PreferenceUtils.getValue(GlobalApplication.IS_DEVICE_BIND + AccountHelper.getCurrentUid(), false) &&
        if (AccountHelper.isLogin()) {
            rl_companion_empty.setVisibility(View.GONE);
            rl_compain_navigation.setVisibility(View.VISIBLE);
            rv_content.setVisibility(View.VISIBLE);
            if (null == mCompanionAdapter)
                mCompanionAdapter = new CompanionAdapter(mActivity, null);
            rv_content.setAdapter(mCompanionAdapter);
            addListener();
            initData();
        } else {
            rl_companion_empty.setVisibility(View.VISIBLE);
            rv_content.setVisibility(View.GONE);
            rl_compain_navigation.setVisibility(View.GONE);
        }
    }

    private void initData() {
        final StepComparator stepComparator = new StepComparator();
        networkRequestCache(CompanionApi.getServiceUserRelation(),
                new SimpleFastJsonCallback<ArrayList<Companion>>(Companion.class, loading) {
                    @Override
                    public void onSuccess(String url, ArrayList<Companion> result) {
                        if (result != null && result.size() > 0) {
                            mSubscriptCompanion = new ArrayList<>();
                            mCompanion = result;
                            GlobalApplication.serviceBindMap.clear();
                            cacheData(url, result);
                            for (Companion companion : result) {
                                companion.setSubstr(companion.getService_description());
                                if (0 == companion.getService_type() && companion.getSecond_level_lists() != null) {
                                    for (Companion companionReply : companion.getSecond_level_lists()) {
                                        mSubscriptCompanion.add(companionReply);
                                        checkResult(companionReply);
                                        if (companionReply.isShowRed()) {
                                            companion.setIsShowRed(true);
                                        }
                                    }
                                    Companion min = Collections.min(mSubscriptCompanion, stepComparator);
                                    companion.setReceiver_time(min.getReceiver_time());
                                    companion.setSubstr(min.getService_name() + ":" + (min.getSubstr() != null ? min.getSubstr() : ""));
                                    continue;
                                } else
                                    checkResult(companion);
                            }
                            /*for(int i =0; i<result.size(); i++){
                                if(result.get(i).getSort() == "9999"){ result.get(i).setIs_unbunding(true);
                            }*/
                            Collections.sort(result, stepComparator);
                            mCompanionAdapter.replaceAll(result);
                            rl_no_commpain.setVisibility(View.GONE);
                            rv_content.setVisibility(View.VISIBLE);
                        } else {
                            rl_no_commpain.setVisibility(View.VISIBLE);
                            rv_content.setVisibility(View.GONE);
                        }

                        loading.dismiss();
                    }

                    @Override
                    public void onFailure(String url, int statusCode, String msg) {
                        super.onFailure(url, statusCode, msg);

                        try {
                            if (mCompanionAdapter.getItemCount() == 0) {
                                rl_no_commpain_failure.setVisibility(View.VISIBLE);
                                rv_content.setVisibility(View.GONE);
                            }
//                            ptl_refresh.refreshComplete();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        // token 过期
                        if (statusCode == 601) {
                            AccountHelper.logout();
                            ToastUtils.showToastShort(mActivity, "长时间未登录，请重新登录");
                        }
                    }
                }, 0);
    }

    public class StepComparator implements Comparator<Companion> {


        @Override
        public int compare(Companion lhs, Companion rhs) {
            if (lhs.getSort() < rhs.getSort()) return 1;
            else if (lhs.getSort() > rhs.getSort()) return -1;
            if (lhs.getReceiver_time() < rhs.getReceiver_time())
                return 1;
            else if (lhs.getReceiver_time() > rhs.getReceiver_time())
                return -1;
            return 0;
        }
    }

    private void checkResult(Companion companion) {
        GlobalApplication.serviceBindMap.put(companion.getService_id() + AccountHelper.getCurrentUid(), 0 == companion.getIs_relation() ? false : true);
        ServiceMessage serviceMessage = companion.getAuto_reply();
        if (null != serviceMessage && null != serviceMessage.getLists()) {
            for (ServiceMessageList serviceMessageList : serviceMessage.getLists()) {
                mDataBaseManager.insertFinishDownload(companion.getService_id(), serviceMessageList.getId(), serviceMessageList.getRelease_time() + "", JSON.toJSONString(serviceMessageList.getContent_lists()), System.currentTimeMillis());
            }
        }
//        ArrayList<String> notDownloadIds = mDataBaseManager.getNotDownloadIds(companion.getService_id());
        try {
            CompanionDB companionDB = mDataBaseManager.getNearestItem(companion.getService_id());
            String receiver_time = companionDB.getReceiver_time();
            if (!TextUtils.isEmpty(receiver_time)) {
                companion.setReceiver_time(Long.parseLong(receiver_time));
            }
            if (companionDB != null) {
                switch (companionDB.getType()) {
                    case "text":
                        companion.setSubstr(companionDB.getMessage());
                        break;
                    case "image":
                        companion.setSubstr("[图片]");
                        break;
                    case "reply":
                        ServiceMessageListReply serviceMessageListReply = JSON.parseObject(companionDB.getReply(), ServiceMessageListReply.class);
                        companion.setSubstr(serviceMessageListReply.getAnswer());
                        break;
                    case "upload_text":
                        companion.setSubstr(companionDB.getMessage());
                        break;
                    case "upload_image":
                        companion.setSubstr("[图片]");
                        break;
                }
                if (companionDB != null && !TextUtils.isEmpty(companionDB.getContent_lists())) {
                    List<ServiceMessageContent> content_lists = JSON.parseArray(companionDB.getContent_lists(), ServiceMessageContent.class);
                    if ("article".equals(companionDB.getType()) && null != content_lists && content_lists.size() >= 0)
                        companion.setSubstr(content_lists.get(0).getTitle());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//                            companion.setService_description();

        //这里判断库中空Id是否比lastPullID小 防止红点永久不能删除
       /* ArrayList<String> realNotDownload = null;
        if (notDownloadIds.size() > 0) {
            for (String notDownloadId : notDownloadIds) {
                int lastId = Integer.parseInt(companion.getLast_pull_id());
                if (Integer.parseInt(notDownloadId) <= lastId || lastId == 0)
                    mDataBaseManager.removeDataWithId(notDownloadId);
                else realNotDownload.add(notDownloadId);
            }
        }*/
        Integer value = PreferenceUtils.getValue(companion.getService_id() + AccountHelper.getCurrentUid(), 0);
        if (0 != value) {
            companion.setNotDownloadCount(value);
            companion.setIsShowRed(true);
            EventBusHelper.post(true, GPushCallback.COMPANION_TABBAR);
        }
    }

    private void addListener() {
        mCompanionAdapter.setOnItemClickListener(this);
//        ptl_refresh.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                initData();
//            }
//        });
        btn_no_data.setOnClickListener(this);
        img_compain_menu.setOnClickListener(this);
        tv_later_relevance.setOnClickListener(this);
        ll_ScanCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YouMengHelper.onEvent(mActivity, YouMengHelper.Assocaite_product);
                Bundle bundle = new Bundle();
                bundle.putSerializable(AccountConstants.Bundle.BUNDLE_COMPANION_BIND, true);
                startActivity(CaptureActivity.class, bundle);
                popupWindow.dismiss();
            }
        });
        ll_Subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YouMengHelper.onEvent(mActivity, YouMengHelper.Activity_home_putaoSubscription_subscriberClick);
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
                YouMengHelper.onEvent(mActivity, YouMengHelper.Activity_home_destination);
                startActivity(PutaoSubcribeActivity.class, bundle);
            } else {
//                mDataBaseManager.removeEmptyData(companion.getService_id());
                PreferenceUtils.save(companion.getService_id() + AccountHelper.getCurrentUid(), 0);
                companion.setIsShowRed(false);
                companion.setNotDownloadCount(0);
                boolean isShowTabDot = false;
                for (Companion compan : mCompanion) {
                    isShowTabDot = compan.isShowRed() || isShowTabDot;
                    if (isShowTabDot) break;
                }
                if (!isShowTabDot)
                    EventBusHelper.post(false, GPushCallback.COMPANION_TABBAR);
                mCompanionAdapter.notifyItemChanged(position);
                startActivity(GameDetailListActivity.class, bundle);
            }
        } else {
            YouMengHelper.onEvent(mActivity, YouMengHelper.Assocaite_product);
            bundle.putSerializable(AccountConstants.Bundle.BUNDLE_COMPANION_BIND_SERVICE, true);
            bundle.putSerializable(AccountConstants.Bundle.BUNDLE_COMPANION, companion);
            startActivity(OfficialAccountsActivity.class, bundle);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        checkDevice();
    }

    @OnClick({R.id.btn_relevance_device, R.id.img_compain_menu, R.id.tv_later_relevance, R.id.iv_step1_first})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_relevance_device:

                YouMengHelper.onEvent(mActivity, YouMengHelper.Assocaite_product);
                iv_step1_first.setVisibility(View.GONE);
                PreferenceUtils.save(GlobalApplication.PREFERENCE_STEP1_IS_FIRST, true);
                if (!AccountHelper.isLogin()) {
                    Bundle bundle = new Bundle();
                    bundle.putBoolean(AccountConstants.Bundle.BUNDLE_COMPANION_BIND, true);
                    bundle.putSerializable(LoginActivity.TERMINAL_ACTIVITY, CaptureActivity.class);
                    startActivity(LoginActivity.class, bundle);
                } else
                    startActivity(CaptureActivity.class);
                break;
            case R.id.btn_no_data://加载失败时，点击刷新数据
                if (!NetworkUtil.isNetworkAvailable(mActivity))
                    ToastUtils.showToastShort(mActivity, "获取数据失败");
                else
                    checkDevice();
                break;
            case R.id.img_compain_menu://点击“+”号，弹出关于扫一扫和订阅号的菜单框
                YouMengHelper.onEvent(mActivity, YouMengHelper.Assocaite_product);
                if (isVisible)
                    popupWindow.showAsDropDown(img_compain_menu);
                break;
            case R.id.iv_step1_first:
                iv_step1_first.setVisibility(View.GONE);
                break;
            case R.id.tv_later_relevance://稍后关联

                YouMengHelper.onEvent(mActivity, YouMengHelper.Activity_home_associate_later);
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


    class RequestData {
        Handler mLoadHandler = new Handler();
        private String mCancelUrl;
        private int mFailCount;
        private String mServiceId;

        public RequestData(String mServiceId) {
            this.mServiceId = mServiceId;
        }

        Runnable mLoadRun = new Runnable() {
            @Override
            public void run() {
                android.app.ActivityManager am = (android.app.ActivityManager) mActivity.getSystemService(Context.ACTIVITY_SERVICE);
                ComponentName cn = am.getRunningTasks(2).get(0).topActivity;
                if (cn != null) {
                    if (getClass().getName().contains(cn.getClassName())) {
                        getLastestArticle();
                    }
                }
            }
        };

        /**
         * 获取文章数据
         */
        private void getLastestArticle() {
            String lastPullId = getLastPullIdByService(mServiceId);
            networkRequest(CompanionApi.getServicesLists(mServiceId, lastPullId), new SimpleFastJsonCallback<ServiceMessage>(ServiceMessage.class, null) {
                @Override
                public void onSuccess(String url, ServiceMessage result) {
                    try {
                        if (result != null) {
                            mFailCount = 0;
                            ArrayList<ServiceMessageList> lists = result.getLists();
                            int size = lists.size();
                            if (null != lists && lists.size() > 0) {
                                for (ServiceMessageList serviceMessageList : lists) {
                                    serviceMessageList.setReceiver_time(System.currentTimeMillis());
                                    if (null != mDataBaseManager.getCompanInfoById(serviceMessageList.getId())) {
                                        size--;
                                    } else {
                                        mDataBaseManager.insertObject(mServiceId, serviceMessageList);
                                    }
                                }
                                setLastPullIdByService(mServiceId, lists.get(lists.size() - 1).getId(), size);
                                EventBusHelper.post("", AccountConstants.EventBus.EVENT_REFRESH_COMPANION);
                                mLoadHandler.postDelayed(mLoadRun, 1000);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(String url, int statusCode, String msg) {
                    super.onFailure(url, statusCode, msg);
                    if ("data数据返回错误".equals(msg)) return;
                    mFailCount++;
                    if (mFailCount < 3)
                        mLoadHandler.postDelayed(mLoadRun, 2000);
                    else
                        mCancelUrl = url;
                }
            }, false);
        }

        private String getLastPullIdByService(String serviceId) {
            for (Companion companion : mCompanion) {
                if (companion.getService_id().equals(serviceId))
                    return companion.getLast_pull_id();
            }
            return "0";
        }

        private void setLastPullIdByService(String serviceId, String lastPullId, int size) {
            if (0 == size)
                return;
            for (Companion companion : mCompanion) {
                if (companion.getService_id().equals(serviceId)) {
                    companion.setLast_pull_id(lastPullId);
                    companion.setIsShowRed(true);
                    companion.setNotDownloadCount(companion.getNotDownloadCount() + size);
                    mCompanionAdapter.notifyItemChanged(mCompanion.indexOf(companion));
//                    companion.setNotDownloadCount(companion.getNotDownloadCount() + size);
                    PreferenceUtils.save(companion.getService_id() + AccountHelper.getCurrentUid(), companion.getNotDownloadCount());
                }
                if (companion.getService_type() == 0) {
                    ArrayList<Companion> second_level_lists = companion.getSecond_level_lists();
                    for (Companion compan : second_level_lists) {
                        if (compan.getService_id().equals(serviceId)) {
                            compan.setLast_pull_id(lastPullId);
                            compan.setIsShowRed(true);
                            compan.setNotDownloadCount(compan.getNotDownloadCount() + size);
                            PreferenceUtils.save(compan.getService_id() + AccountHelper.getCurrentUid(), compan.getNotDownloadCount());
                            companion.setSecond_level_lists(second_level_lists);
//                            mCompanionAdapter.notifyItemChanged(mCompanion.indexOf(companion));
                        }
                    }
                }
                EventBusHelper.post(companion, AccountConstants.EventBus.EVENT_REFRESH_COMPANION);
            }
        }

    }


    @Subcriber(tag = AccountConstants.EventBus.EVENT_REFRESH_COMPANION)
    private void refresh_companion(String str) {
        checkDevice();
    }

    @Subcriber(tag = GPushCallback.COMPANION_DOT)
    private void setCompanionDot(ArrayList<GpushMessageAccNumber> accompanyNumber) {
        for (GpushMessageAccNumber gpushMessageAccNumber : accompanyNumber) {
            String service_id = gpushMessageAccNumber.getService_id();
            RequestData requestData = new RequestData(service_id);
            requestData.getLastestArticle();
//            String id = gpushMessageAccNumber.getId();
            EventBusHelper.post(true, GPushCallback.COMPANION_TABBAR);
            /*for (Companion companion : mCompanion) {
                if (companion.getService_id().equals(service_id)) {
                    ArrayList<String> notDownloadIds = companion.getNotDownloadIds();
                    notDownloadIds.add(id);
                    companion.setIsShowRed(true);
                    companion.setNotDownloadIds(notDownloadIds);
                    mCompanionAdapter.notifyItemChanged(mCompanion.indexOf(companion));
                } else if (1 != companion.getService_type()) {
                    for (Companion secondCompanion : companion.getSecond_level_lists()) {
                        if (secondCompanion.getService_id().equals(service_id)) {
                            ArrayList<String> notDownloadIds = secondCompanion.getNotDownloadIds();
                            notDownloadIds.add(id);
                            companion.setIsShowRed(true);
                            secondCompanion.setIsShowRed(true);
                            secondCompanion.setNotDownloadIds(notDownloadIds);
                            mCompanionAdapter.notifyItemChanged(mCompanion.indexOf(companion));
                        }
                    }
                }
            }*/
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
                        iv_no_commpain.setImageURL("res://putao/" + R.drawable.img_link_product_01);
                        break;
                    case 1:
                        iv_no_commpain.setImageURL("res://putao/" + R.drawable.img_link_product_02);
                        break;
                    case 2:
                        iv_no_commpain.setImageURL("res://putao/" + R.drawable.img_link_product_03);
                        break;
                    case 3:
                        iv_no_commpain.setImageURL("res://putao/" + R.drawable.img_link_product_04);
                        break;
                }
                if (null != mSet)
                    iv_no_commpain.startAnimation(mSet);
                mPicChangeCount++;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        mSet.cancel();
    }

    @Override
    public void onResume() {
        super.onResume();
        mSet.reset();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSet.cancel();
        mSet = null;
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

    @Subcriber(tag = AccountConstants.EventBus.EVENT_UPDATE_UPLOAD)
    private void insertUpload(ServiceMessageList serviceMessageList) {
        mDataBaseManager.insertObject(serviceMessageList);
    }

    @Subcriber(tag = AccountConstants.EventBus.EVENT_REFRESH_SUBSCRIBE)
    private void refreshSubscribe(Companion companion) {
        companion.setIsShowRed(false);
        boolean isShowTabDot = false;
        for (Companion compan : mCompanion) {
            if (0 == compan.getService_type()) {
                compan.setIsShowRed(false);
                compan.setNotDownloadCount(0);
                for (Companion compani : compan.getSecond_level_lists()) {
                    compani.setIsShowRed(false);
                    compani.setNotDownloadCount(0);
                }
            }
            isShowTabDot = compan.isShowRed() || isShowTabDot;
            if (isShowTabDot) break;
        }
        if (!isShowTabDot) {
            EventBusHelper.post(false, GPushCallback.COMPANION_TABBAR);
        }
        mCompanionAdapter.notifyDataSetChanged();
    }

    @Subcriber(tag = AccountConstants.EventBus.EVENT_COMPANION_POP_DISMISS)
    private void refreshSubscribe(String str) {
        popupWindow.dismiss();
    }
}


