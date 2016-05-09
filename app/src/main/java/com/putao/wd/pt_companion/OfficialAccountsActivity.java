package com.putao.wd.pt_companion;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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
import com.putao.wd.model.Companion;
import com.putao.wd.model.SubscribeList;
import com.sunnybear.library.controller.ActivityManager;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
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
    private String mServiceId;
    private String mServiceName;
    private SelectPopupWindow mSelectPopupWindow;
    private boolean isSubscribe;
    private SubscribeList subscribeList;
    private Companion companion;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_officialaccounts;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        isSubscribe = args.getBoolean(AccountConstants.Bundle.BUNDLE_COMPANION_BIND, false);
        if (isSubscribe) {
            subscribeList = (SubscribeList) args.getSerializable(AccountConstants.Bundle.BUNDLE_COMPANION);
            if (subscribeList.is_relation()) {
                isSubscribeCoampin();
            } else {
                navigation_bar.getRightView().setVisibility(View.GONE);
                tv_relation_companion.setText("立即订阅");
            }
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
        } else {
            companion = (Companion) args.getSerializable(AccountConstants.Bundle.BUNDLE_COMPANION);
            if (companion.getIs_relation() == 1) {
                isSubscribeCoampin();
            } else {
                navigation_bar.getRightView().setVisibility(View.GONE);
                tv_relation_companion.setText("关联产品");
            }
            if (companion == null) return;
            setMainTitle(companion.getService_name());
            tv_official_title.setText(companion.getService_name());
            iv_icon.setImageURL(companion.getService_icon());
            tv_recommend.setText(companion.getService_description());
            mServiceId = companion.getService_id();
            mServiceName = companion.getService_name();
            if (companion.is_unbunding()) {
                tv_relation_companion.setVisibility(View.GONE);
                navigation_bar.getRightView().setVisibility(View.GONE);
            }
        }

        addListener();
    }

    private void addListener() {
        tv_relation_companion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSubscribe) {//订阅号（立即订阅)
                    if (subscribeList.is_relation()) {
                        finish();
                    } else {//关联产品

                    }
                } else {//服务号（关联产品）
                    if (companion.getIs_relation() == 1) {
                        finish();
                    } else {

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
//
//    /**
//     * 关注服务号
//     */
//    private void correlationService(String service_id) {
//        networkRequest(CompanionApi.getServiceRelation(service_id), new SimpleFastJsonCallback<>() {
//        });
//    }

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
                    dataBaseManager.deleteContent(service_id);
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
                       /* if (!is_relation) {//未关联
                        } else {//已关联
                            PreferenceUtils.save(GlobalApplication.IS_DEVICE_BIND + AccountHelper.getCurrentUid(), true);
                        }*/
//                        EventBusHelper.post(EVENT_OFFICIAL_URL, EVENT_OFFICIAL_URL);
//                        EventBusHelper.post(LoginActivity.EVENT_LOGIN, LoginActivity.EVENT_LOGIN);
                        ActivityManager.getInstance().popOtherActivity(IndexActivity.class);
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
        navigation_bar.getRightView().setVisibility(View.VISIBLE);
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
}
