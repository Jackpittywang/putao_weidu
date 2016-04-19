package com.putao.wd.pt_companion;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.putao.wd.GlobalApplication;
import com.putao.wd.IndexActivity;
import com.putao.wd.R;
import com.putao.wd.account.AccountConstants;
import com.putao.wd.account.AccountHelper;
import com.putao.wd.api.CompanionApi;
import com.putao.wd.api.UserApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.db.CompanionDBManager;
import com.putao.wd.model.Companion;
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
    @Bind(R.id.btn_cancel_associate)
    Button btn_cancel_associate;
    @Bind(R.id.btn_look_history)
    Button btn_look_history;


//    @Bind(R.id.no_cancel)
//    TextView no_cancel;
//    @Bind(R.id.cancel_associate)
//    TextView cancel_associate;


    private View view_custom;
    private AlertDialog mDialog = null;
    private AlertDialog.Builder builder = null;
    private String mServiceId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_officialaccounts;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        Companion companion = (Companion) args.getSerializable(AccountConstants.Bundle.BUNDLE_COMPANION);
        if (companion == null) return;
        setMainTitle(companion.getService_name());
        tv_official_title.setText(companion.getService_name());
        iv_icon.setImageURL(companion.getService_icon());
        tv_recommend.setText(companion.getService_description());
        mServiceId = companion.getService_id();
        if (companion.is_unbunding())
            btn_cancel_associate.setVisibility(View.GONE);
        addListener();
    }

    private void addListener() {
        btn_cancel_associate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        //查看历史文章
        btn_look_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(LookHistoryActivity.HISTORY_SERVICE_ID, mServiceId);
                startActivity(LookHistoryActivity.class, bundle);
            }
        });
    }

    private void showDialog() {
        builder = new AlertDialog.Builder(this);

        final LayoutInflater inflater = OfficialAccountsActivity.this.getLayoutInflater();
        view_custom = inflater.inflate(R.layout.activity_officialaccounts_dialog, null, false);

        builder.setView(view_custom);
        builder.setCancelable(false);

        view_custom.findViewById(R.id.no_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

        view_custom.findViewById(R.id.cancel_associate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelServicce(mServiceId);
            }
        });
        mDialog = builder.show();
        mDialog.show();
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
                    ToastUtils.showToastShort(mContext, msg);
                    mDialog.dismiss();
                    loading.dismiss();
                } else {
                    CompanionDBManager dataBaseManager = (CompanionDBManager) mApp.getDataBaseManager(CompanionDBManager.class);
                    dataBaseManager.deleteContent(service_id);
                    checkInquiryBind(AccountHelper.getCurrentUid());
                }
            }

            @Override
            public void onCacheSuccess(String url, JSONObject result) {

            }

            @Override
            public void onFailure(String url, int statusCode, String msg) {

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
}
