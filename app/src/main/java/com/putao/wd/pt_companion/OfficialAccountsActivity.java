package com.putao.wd.pt_companion;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.putao.wd.GlobalApplication;
import com.putao.wd.IndexActivity;
import com.putao.wd.R;
import com.putao.wd.account.AccountConstants;
import com.putao.wd.account.AccountHelper;
import com.putao.wd.account.YouMengHelper;
import com.putao.wd.api.CompanionApi;
import com.putao.wd.api.UserApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.base.SelectPopupWindow;
import com.putao.wd.db.CompanionDBManager;
import com.putao.wd.model.CompainServiceInfo;
import com.putao.wd.model.Companion;
import com.putao.wd.model.ServiceMessage;
import com.putao.wd.model.SubscribeList;
import com.putao.wd.qrcode.CaptureActivity;
import com.putao.wd.user.LoginActivity;
import com.sunnybear.library.controller.ActivityManager;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
import com.sunnybear.library.controller.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.JSONObjectCallback;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.PreferenceUtils;
import com.sunnybear.library.util.StringUtils;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.image.ImageDraweeView;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/4/9.
 */
public class OfficialAccountsActivity extends PTWDActivity<GlobalApplication> {
    public static final String EVENT_OFFICIAL_URL = "event_official_url";
    public static final String CAPTURE_SERVICE_ID = "service_id";
    public static final String CAPTURE_URL = "capture_url";
    public static final String HIDE_NO_FINISH = "hide_no_finish";
    public static final String HIDE_FINISH = "hide_finish";


    @Bind(R.id.iv_icon)
    ImageDraweeView iv_icon;
    @Bind(R.id.tv_official_title)
    TextView tv_official_title;
    @Bind(R.id.tv_recommend)
    TextView tv_recommend;
    @Bind(R.id.tv_relation_companion)
    TextView tv_relation_companion;
    @Bind(R.id.tv_look_history)
    TextView tv_look_history;
    @Bind(R.id.ll_companion)
    LinearLayout ll_companion;


    private TextView tv_dialog, tv_no_cancel, tv_cancel;
    private View view_custom;
    private AlertDialog mDialog = null;
    private AlertDialog.Builder builder = null;
    private String mServiceId, capture_url;
    private String mServiceName;
    private SelectPopupWindow mSelectPopupWindow;
    private CompainServiceInfo serviceInfo;
    private boolean isSubscribe, isBind, isSubscription;
    private SubscribeList subscribeList;
    private Companion companion;
    private int serviceType;
    private boolean isFromArticle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_officialaccounts;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        //陪伴首页未关联产品传送过来的数据
        isBind = args.getBoolean(AccountConstants.Bundle.BUNDLE_COMPANION_BIND_SERVICE, false);
        //是否订阅
        isSubscription = args.getBoolean(AccountConstants.Bundle.BUNDLE_COMPANION_COLLECTION);
//        serviceType = args.getInt(AccountConstants.Bundle.BUNDLE_COMPANION_NOTNULL, 1);
        mServiceId = args.getString(CAPTURE_SERVICE_ID);
        capture_url = args.getString(CAPTURE_URL);
        isFromArticle = args.getBoolean(AccountConstants.Bundle.BUNDLE_ARTICLE_CLICK);
        if (isFromArticle) {
            setMainTitleFromNetwork(mServiceId);
            isSubscribe = StringUtils.equals("1", args.getString(AccountConstants.Bundle.BUNDLE_SERVICE_SUBSCR_STATE, "")) ? true : false;
        } else if (capture_url == null) {
            //isSubscribe为true则是订阅号传送过来的数据，反之则是服务号传送过来的数据
            isSubscribe = args.getBoolean(AccountConstants.Bundle.BUNDLE_COMPANION_BIND, false);
            if (isSubscribe) {
                subscribeList = (SubscribeList) args.getSerializable(AccountConstants.Bundle.BUNDLE_COMPANION);
                if (subscribeList == null) return;
                setMainTitle(subscribeList.getService_name());
                tv_official_title.setText(subscribeList.getService_name());
                iv_icon.setImageURL(subscribeList.getService_icon());
                tv_recommend.setText(subscribeList.getService_description());
                mServiceId = subscribeList.getService_id();
                mServiceName = subscribeList.getService_name();
                if (subscribeList.is_unbunding()) {
                    tv_relation_companion.setVisibility(View.GONE);
                    navigation_bar.getRightView().setVisibility(View.GONE);
                }
                if (subscribeList.is_relation()) {//是否关注
                    isSubscribeCoampin();
                } else {
                    navigation_bar.getRightView().setVisibility(View.GONE);
                    tv_relation_companion.setText("立即订阅");
                }
            } else {
                companion = (Companion) args.getSerializable(AccountConstants.Bundle.BUNDLE_COMPANION);
                if (companion == null) return;
                setMainTitle(companion.getService_name());
                tv_official_title.setText(companion.getService_name());
                iv_icon.setImageURL(companion.getService_icon());
                tv_recommend.setText(companion.getService_description());
                mServiceId = companion.getService_id();
                mServiceName = companion.getService_name();
                if (companion.is_unbunding()) {
                    navigation_bar.getRightView().setVisibility(View.GONE);
                    tv_relation_companion.setVisibility(View.GONE);
                }

                if (companion.getIs_relation() == 1) {//是否关注
                    isSubscribeCoampin();
                } else {
                    navigation_bar.getRightView().setVisibility(View.GONE);
                    tv_relation_companion.setText("关联产品");
                }
            }
        } else {
            setMainTitleFromNetwork(mServiceId);
        }

        addListener();

    }

    private void addListener() {
        tv_relation_companion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AccountHelper.isLogin()) {
                    Bundle bundle = new Bundle();
                    bundle.putBoolean(AccountConstants.Bundle.BUNDLE_COMPANION_BIND, false);
                    bundle.putSerializable(LoginActivity.TERMINAL_ACTIVITY, IndexActivity.class);
                    startActivity(LoginActivity.class, bundle);
                } else {
                    Bundle bundle = new Bundle();
                    if (capture_url != null) {
                        if (serviceInfo.is_relation()) {//已关注(从扫一扫界面跳转过来)
                            EventBusHelper.post("", AccountConstants.EventBus.EVENT_REFRESH_COMPANION);
                            bundle.putString(AccountConstants.Bundle.BUNDLE_COMPANION_BIND_SERVICE, mServiceId);
                            bundle.putSerializable(AccountConstants.Bundle.BUNDLE_COMPANION_NOT_DOWNLOAD, serviceInfo.getService_icon());
                            PreferenceUtils.save(GlobalApplication.IS_DEVICE_BIND + AccountHelper.getCurrentUid(), true);
                            startActivity(GameDetailListActivity.class, bundle);
                        } else {//未关注
                            correlationService(mServiceId, capture_url);
                        }
                    } else {
                        if (isBind) {//从未关联跳转过来
                            bundle.putSerializable(AccountConstants.Bundle.BUNDLE_COMPANION_BIND_SERVICE, mServiceId);
                            bundle.putSerializable(AccountConstants.Bundle.BUNDLE_SERVICE_NAME, companion.getService_icon());
                            bundle.putSerializable(AccountConstants.Bundle.BUNDLE_COMPANION, companion.getService_name());
                            startActivity(CaptureActivity.class, bundle);
                        } else {
                            if (isSubscribe) {
                                if (subscribeList.is_relation()) {
                                    bundle.putString(AccountConstants.Bundle.BUNDLE_COMPANION_BIND_SERVICE, mServiceId);
                                    bundle.putSerializable(AccountConstants.Bundle.BUNDLE_COMPANION_NOT_DOWNLOAD, subscribeList.getService_icon());
                                    PreferenceUtils.save(GlobalApplication.IS_DEVICE_BIND + AccountHelper.getCurrentUid(), true);
                                    startActivity(GameDetailListActivity.class, bundle);
                                } else {
                                    correlationService(mServiceId, capture_url);
                                }
                            } else {
                                bundle.putSerializable(AccountConstants.Bundle.BUNDLE_COMPANION, companion);
                                startActivity(GameDetailListActivity.class, bundle);
                            }
                        }
                    }
                }
            }
        });
        //查看历史文章
        tv_look_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AccountHelper.isLogin()) {
//                    Bundle bundle = new Bundle();
//                    args.putSerializable(LookHistoryActivity.HISTORY_SERVICE_ID, mServiceId);
//                    args.putString(AccountConstants.Bundle.BUNDLE_SERVICE_NAME, mServiceName);
//                    bundle.putBoolean(AccountConstants.Bundle.BUNDLE_COMPANION_BIND, isBind);
                    args.putSerializable(LoginActivity.TERMINAL_ACTIVITY, OfficialAccountsActivity.class);
                    args.putString(HIDE_FINISH, HIDE_NO_FINISH);
                    startActivity(LoginActivity.class, args);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(AccountConstants.Bundle.BUNDLE_COMPANION_BIND_SERVICE, isSubscribe);
                    bundle.putSerializable(AccountConstants.Bundle.BUNDLE_COMPANION_NOT_DOWNLOAD, isSubscription);
                    bundle.putSerializable(AccountConstants.Bundle.BUNDLE_COMPANION_COLLECTION, isBind);
                    bundle.putSerializable(LookHistoryActivity.HISTORY_SERVICE_ID, mServiceId);
                    bundle.putString(AccountConstants.Bundle.BUNDLE_SERVICE_NAME, mServiceName);
                    startActivity(LookHistoryActivity.class, bundle);
                }
            }
        });
    }

    private void showDialog() {
        builder = new AlertDialog.Builder(this);

        final LayoutInflater inflater = OfficialAccountsActivity.this.getLayoutInflater();
        view_custom = inflater.inflate(R.layout.activity_officialaccounts_dialog, null, false);
        tv_dialog = (TextView) view_custom.findViewById(R.id.tv_dialog);
        tv_no_cancel = (TextView) view_custom.findViewById(R.id.no_cancel);
        tv_cancel = (TextView) view_custom.findViewById(R.id.cancel_associate);


        builder.setView(view_custom);
        builder.setCancelable(false);

        view_custom.findViewById(R.id.no_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

        mDialog = builder.show();
        mDialog.show();
    }


    /**
     * 关注服务号/立即订阅
     */
    private void correlationService(final String service_id, String url) {
        networkRequest(CompanionApi.getServiceRelation(service_id, url), new SimpleFastJsonCallback<ServiceMessage>(ServiceMessage.class, loading) {
                    @Override
                    public void onSuccess(String url, ServiceMessage result) {
                        Bundle bundle = new Bundle();
                        EventBusHelper.post("", AccountConstants.EventBus.EVENT_REFRESH_COMPANION);
                        if (capture_url != null || !isSubscribe) {//从扫一扫页面过来以及不是订阅号
                            bundle.putString(AccountConstants.Bundle.BUNDLE_COMPANION_BIND_SERVICE, service_id);
                            bundle.putSerializable(AccountConstants.Bundle.BUNDLE_COMPANION_NOT_DOWNLOAD, capture_url != null ? serviceInfo.getService_icon() : companion.getService_icon());
                            PreferenceUtils.save(GlobalApplication.IS_DEVICE_BIND + AccountHelper.getCurrentUid(), true);
                            startActivity(GameDetailListActivity.class, bundle);
                            finish();
                        } else {
                            bundle.putString(AccountConstants.Bundle.BUNDLE_COMPANION_BIND_SERVICE, service_id);
                            bundle.putSerializable(AccountConstants.Bundle.BUNDLE_COMPANION_NOT_DOWNLOAD, subscribeList.getService_icon());
                            PreferenceUtils.save(GlobalApplication.IS_DEVICE_BIND + AccountHelper.getCurrentUid(), true);
                            startActivity(GameDetailListActivity.class, bundle);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(String url, int statusCode, String msg) {
                        super.onFailure(url, statusCode, msg);
                        ToastUtils.showToastShort(mContext, isSubscribe ? "订阅失败" : "关联失败");
                    }
                }
        );
    }

    /**
     * 取消绑定服务号
     */
    private void cancelServicce(final String service_id) {
        networkRequest(CompanionApi.cancelService(service_id), new JSONObjectCallback() {
            @Override
            public void onSuccess(String url, JSONObject result) {
                int http_code = result.getInteger("http_code");
                String msg = result.getString("msg");
                if (http_code != 200) {
                    mDialog.dismiss();
                    ToastUtils.showToastShort(mContext, msg);
                } else {
                    CompanionDBManager dataBaseManager = (CompanionDBManager) mApp.getDataBaseManager(CompanionDBManager.class);
                    dataBaseManager.removeData(service_id);
                    checkInquiryBind(AccountHelper.getCurrentUid());
                }
                loading.dismiss();
            }

            @Override
            public void onCacheSuccess(String url, JSONObject result) {

            }

            @Override
            public void onFailure(String url, int statusCode, String msg) {
                ToastUtils.showToastShort(mContext, isSubscribe ? "取消订阅失败" : "取消关联失败");
                mDialog.dismiss();
            }

            @Override
            public void onFinish(String url, boolean isSuccess, String msg) {
                super.onFinish(url, isSuccess, msg);
                loading.dismiss();
            }
        });
    }

    /**
     * 判断是哦否关联产品
     */
    private void checkInquiryBind(String currentUid) {
        networkRequest(UserApi.checkInquiryBind(currentUid),
                new SimpleFastJsonCallback<String>(String.class, loading) {
                    @Override
                    public void onSuccess(String url, String result) {
                        Boolean is_relation = JSONObject.parseObject(result).getBoolean("is_relation");
                        PreferenceUtils.save(GlobalApplication.IS_DEVICE_BIND + AccountHelper.getCurrentUid(), is_relation);
                        EventBusHelper.post("", AccountConstants.EventBus.EVENT_REFRESH_COMPANION);
                        if (isSubscribe) {
                            ActivityManager.getInstance().popOtherActivity(IndexActivity.class);
                        } else {
                            ActivityManager.getInstance().popOtherActivity(IndexActivity.class);
                        }
                        mDialog.dismiss();
                       /* if (!is_relation) {//未关联
                        } else {//已关联
                            PreferenceUtils.save(GlobalApplication.IS_DEVICE_BIND + AccountHelper.getCurrentUid(), true);
                        }*/
//                        EventBusHelper.post(EVENT_OFFICIAL_URL, EVENT_OFFICIAL_URL);
//                        EventBusHelper.post(LoginActivity.EVENT_LOGIN, LoginActivity.EVENT_LOGIN);

//                        startActivity(IndexActivity.class);
//                        finish();
                    }
                });
    }


    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onLeftAction() {
        super.onLeftAction();
        finish();
    }

    @Override
    public void onRightAction() {
        super.onRightAction();
        mSelectPopupWindow.show(ll_companion);
        mSelectPopupWindow.tv_first.setText("清空内容");

//        if (!isSubscribe && (companion != null ? (companion.getService_type() != 2) : true)) {
        if (!isSubscribe && companion.getService_type() != 2) {
            mSelectPopupWindow.tv_second.setText("取消关联");
        } else {
            mSelectPopupWindow.tv_second.setText("取消订阅");
        }
        mSelectPopupWindow.tv_second.setTextColor(0xffcc0000);
    }


    /**
     * 是否已关联/订阅
     */
    private void isSubscribeCoampin() {
        final CompanionDBManager dataBaseManager = (CompanionDBManager) mApp.getDataBaseManager(CompanionDBManager.class);
        mSelectPopupWindow = new SelectPopupWindow(mContext) {
            @Override
            public void onFirstClick(View v) {//清除内容
                YouMengHelper.onEvent(mContext, YouMengHelper.Activity_menu_dessociate, "售后");
                showDialog();
                tv_dialog.setText("将清空所有历史内容");
                tv_cancel.setText("清空内容");
                tv_no_cancel.setText("取消");
                tv_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dataBaseManager.removeData(mServiceId);
                        EventBusHelper.post("", AccountConstants.EventBus.EVENT_REFRESH_COMPANION);
                        Bundle bundle = new Bundle();
                        if (isSubscribe) {
                            bundle.putString(AccountConstants.Bundle.BUNDLE_COMPANION_BIND_SERVICE, mServiceId);
                            bundle.putSerializable(AccountConstants.Bundle.BUNDLE_COMPANION_NOT_DOWNLOAD, subscribeList.getService_icon());
                        } else {
                            EventBusHelper.post("", AccountConstants.Bundle.BUNDLE_COMPANION_SERVICE_MESSAGE_LIST);
                            bundle.putSerializable(AccountConstants.Bundle.BUNDLE_COMPANION, companion);
                        }
                        startActivity(GameDetailListActivity.class, bundle);
                        ToastUtils.showToastShort(mContext, "清除成功");
                        mDialog.dismiss();
                    }
                });
            }

            @Override
            public void onSecondClick(View v) {//取消关联
                YouMengHelper.onEvent(mContext, YouMengHelper.Activity_menu_dessociate, "售后");
                showDialog();
                if (!isSubscribe && companion.getService_type() != 2) {
                    tv_dialog.setText("取消关联产品后，所有信息将会清空。");
                } else {
                    tv_dialog.setText("取消订阅后后，所有信息将会清空。");
                }
                tv_cancel.setText("确定取消");
                tv_no_cancel.setText("暂不取消");
                tv_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cancelServicce(mServiceId);
                    }
                });
            }
        };
        tv_relation_companion.setText("进入");
    }

    /**
     * 查询公众号数据
     */
    private void setMainTitleFromNetwork(String service_id) {
        networkRequest(CompanionApi.getServiceInfo(service_id),
                new SimpleFastJsonCallback<CompainServiceInfo>(CompainServiceInfo.class, loading) {
                    @Override
                    public void onSuccess(String url, CompainServiceInfo result) {
                        if (result != null) {
                            if (isFromArticle) {
                                if (isSubscribe) {
                                    if (subscribeList == null)
                                        subscribeList = new SubscribeList();
                                    subscribeList.setService_icon(result.getService_icon());
                                    subscribeList.setIs_relation(result.is_relation());
                                } else {
                                    if (companion == null)
                                        companion = new Companion();
                                    companion.setService_id(result.getService_id());
                                    companion.setService_name(result.getService_name());
                                    companion.setService_icon(result.getService_icon());
                                    companion.setIs_relation(result.is_relation() ? 1 : 0);
                                    companion.setIs_unbunding(result.is_unbunding());
                                    companion.setService_description(result.getService_description());
                                }
                            }
                            serviceInfo = result;
                            tv_official_title.setText(result.getService_name());
                            navigation_bar.setMainTitle(result.getService_name());
                            iv_icon.setImageURL(result.getService_icon());
                            tv_recommend.setText(result.getService_description());
                            if (result.is_relation()) {//是否关注
                                navigation_bar.getRightView().setVisibility(View.VISIBLE);
                                isSubscribeCoampin();
                            } else {
                                navigation_bar.getRightView().setVisibility(View.GONE);
                                tv_relation_companion.setText(result.isService_type() ? "关联产品" : "立即订阅");
                            }

                            //是否可以解绑
                            if (result.is_unbunding()) {
                                tv_relation_companion.setVisibility(View.GONE);
                                navigation_bar.getRightView().setVisibility(View.GONE);
                            }
                        }
                        loading.dismiss();
                    }

                    @Override
                    public void onFailure(String url, int statusCode, String msg) {
                        super.onFailure(url, statusCode, msg);
                    }
                }, false);
    }

    /**
     * 是否要隐藏该页面
     */
    @Subcriber(tag = AccountConstants.EventBus.EVENT_REFRESH_COMPANION)
    private void refresh_data(String tag) {
//        if (!StringUtils.equals(HIDE_NO_FINISH, tag)) {
        this.finish();
//        } else
//            setMainTitleFromNetwork(mServiceId);
    }
}
