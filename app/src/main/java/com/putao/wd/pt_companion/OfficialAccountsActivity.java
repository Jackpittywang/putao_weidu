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
    public static final String SUBSCRIBE = "subscribe";

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
    private boolean isSubscribe, isBind;
    private SubscribeList subscribeList;
    private Companion companion;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_officialaccounts;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
//        isBind = args.getBoolean(AccountConstants.Bundle.BUNDLE_COMPANION, false);//陪伴首页未关联产品传送过来的数据
        isBind = args.getBoolean(AccountConstants.Bundle.BUNDLE_COMPANION_BIND_SERVICE, false);
        mServiceId = args.getString(CAPTURE_SERVICE_ID);
        capture_url = args.getString(CAPTURE_URL);

//        if (capture_url == null) {
//            if (isBind) {
//                mServiceId = args.getString(AccountConstants.Bundle.BUNDLE_COMPANION_BIND);
//                navigation_bar.getRightView().setVisibility(View.GONE);
//            } else {
//                isSubscribe = args.getBoolean(AccountConstants.Bundle.BUNDLE_COMPANION_BIND, false);
//                if (isSubscribe) {
//                    subscribeList = (SubscribeList) args.getSerializable(AccountConstants.Bundle.BUNDLE_COMPANION);
//                    if (subscribeList == null) return;
//                    mServiceId = subscribeList.getService_id();
//                    mServiceName = subscribeList.getService_name();
//                } else {
//                    companion = (Companion) args.getSerializable(AccountConstants.Bundle.BUNDLE_COMPANION);
//                    if (companion == null) return;
//                    mServiceId = companion.getService_id();
//                    mServiceName = companion.getService_name();
//                }
//            }
//        }
        if (capture_url == null) {
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
                    bundle.putBoolean(AccountConstants.Bundle.BUNDLE_COMPANION_BIND, true);
                    bundle.putSerializable(LoginActivity.TERMINAL_ACTIVITY, IndexActivity.class);
                    startActivity(LoginActivity.class, bundle);
                } else {
//                    //先判断是否已关注
//                    if (serviceInfo.is_relation()) {//已关注
//                        if (serviceInfo.isService_type()) {//服务号
//                            Bundle bundle = new Bundle();
//                            bundle.putSerializable(AccountConstants.Bundle.BUNDLE_COMPANION, companion);
//                            startActivity(GameDetailListActivity.class, bundle);
//                        } else {//订阅号
//                            startActivity(PutaoSubcribeActivity.class);
//                        }
//                    } else {//未关注
//                        if (serviceInfo.isService_type()) {//服务号
//                            if (isBind) {//从陪伴首页未关联传送过来的数据
//                                startActivity(CaptureActivity.class);
//                            } else {
//                                correlationService(mServiceId, capture_url);
//                            }
//                        } else {//订阅号
//                            correlationService(mServiceId, capture_url);
//                        }
//                    }
                    Bundle bundle = new Bundle();
                    if (capture_url != null) {
                        if (serviceInfo.is_relation()) {//已关注
                            EventBusHelper.post(SUBSCRIBE, SUBSCRIBE);
                            bundle.putString(AccountConstants.Bundle.BUNDLE_COMPANION_BIND_SERVICE, mServiceId);
                            PreferenceUtils.save(GlobalApplication.IS_DEVICE_BIND + AccountHelper.getCurrentUid(), true);
                            startActivity(GameDetailListActivity.class, bundle);
                        } else {//未关注
                            correlationService(mServiceId, capture_url);
                        }
                    } else {
                        if (isBind) {//从未关联跳转过来
                            startActivity(CaptureActivity.class);
                        } else {
                            if (isSubscribe) {
                                if (subscribeList.is_relation()) {
                                    startActivity(PutaoSubcribeActivity.class);
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
                Bundle bundle = new Bundle();
                bundle.putSerializable(LookHistoryActivity.HISTORY_SERVICE_ID, mServiceId);
                bundle.putString(AccountConstants.Bundle.BUNDLE_SERVICE_NAME, mServiceName);
                startActivity(LookHistoryActivity.class, bundle);
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
    private void correlationService(String service_id, String url) {
        networkRequest(CompanionApi.getServiceRelation(service_id, url), new SimpleFastJsonCallback<ServiceMessage>(ServiceMessage.class, loading) {
            @Override
            public void onSuccess(String url, ServiceMessage result) {
                Bundle bundle = new Bundle();
                if (capture_url != null) {
                    EventBusHelper.post(SUBSCRIBE, SUBSCRIBE);
                    bundle.putString(AccountConstants.Bundle.BUNDLE_COMPANION_BIND_SERVICE, mServiceId);
                    PreferenceUtils.save(GlobalApplication.IS_DEVICE_BIND + AccountHelper.getCurrentUid(), true);
                    startActivity(GameDetailListActivity.class, bundle);
                    finish();
                } else {
                    if (isSubscribe) {
                        EventBusHelper.post(SUBSCRIBE, SUBSCRIBE);
                        bundle.putString(AccountConstants.Bundle.BUNDLE_COMPANION_BIND_SERVICE, mServiceId);
                        PreferenceUtils.save(GlobalApplication.IS_DEVICE_BIND + AccountHelper.getCurrentUid(), true);
                        startActivity(GameDetailListActivity.class, bundle);
                        finish();
                    } else {
                        EventBusHelper.post(SUBSCRIBE, SUBSCRIBE);
                        startActivity(PutaoSubcribeActivity.class);
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(String url, int statusCode, String msg) {
                super.onFailure(url, statusCode, msg);
                ToastUtils.showToastShort(mContext, isSubscribe ? "关联失败" : "订阅失败");
            }
        });
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
                            finish();
                        } else {
                            ActivityManager.getInstance().popOtherActivity(IndexActivity.class);
                        }
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
        mSelectPopupWindow.tv_first.setText("清除内容");
        mSelectPopupWindow.tv_second.setText("取消关联");
        mSelectPopupWindow.tv_second.setTextColor(0xffcc0000);
    }


    /**
     * 是否已关联/订阅
     */
    private void isSubscribeCoampin() {
        mSelectPopupWindow = new SelectPopupWindow(mContext) {
            @Override
            public void onFirstClick(View v) {//清除内容
                YouMengHelper.onEvent(mContext, YouMengHelper.Activity_menu_dessociate, "售后");
                showDialog();
                tv_dialog.setText("将清空所有历史内容");
                tv_cancel.setText("清空内容");
                tv_no_cancel.setText("取消");
            }

            @Override
            public void onSecondClick(View v) {//取消关联
                YouMengHelper.onEvent(mContext, YouMengHelper.Activity_menu_dessociate, "售后");
                showDialog();
                tv_dialog.setText(isSubscribe ? "取消订阅后后，所有信息将会清空。" : "取消关联产品后，所有信息将会清空。");
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
                            serviceInfo = result;
                            tv_official_title.setText(result.getService_name());
                            navigation_bar.setMainTitle(result.getService_name());
                            iv_icon.setImageURL(result.getService_icon());
                            tv_recommend.setText(result.getService_description());
                            if (result.is_relation()) {//是否关注
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
        this.finish();
    }

}
