package com.putao.wd.start.apply;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.putao.wd.ColorConstant;
import com.putao.wd.R;
import com.putao.wd.api.StartApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.start.action.ActionsDetailActivity;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.ResourcesUtils;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.CleanableEditText;
import com.sunnybear.library.view.picker.OptionPicker;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 我要参加
 * Created by wango on 2015/12/4.
 */
public class ApplyActivity extends PTWDActivity implements View.OnClickListener {
    @Bind(R.id.tv_parent_relation)
    TextView tv_parent_relation;//家长身份
    @Bind(R.id.et_phone)
    CleanableEditText et_phone;//手机号码
    @Bind(R.id.et_nickname)
    EditText et_nickname;//孩子昵称
    @Bind(R.id.tv_childage)
    TextView tv_childage;//孩子年龄
    @Bind(R.id.et_QQorWX)
    EditText et_QQorWX;//QQ/微信
    @Bind(R.id.et_parentname)
    EditText et_parentname;//家长姓名
    @Bind(R.id.et_message)
    EditText et_message;//留言

    private String action_id;

    private OptionPicker mFamilyPicker;//家长身份
    private OptionPicker mAgesPicker;//孩子年龄

    @Override
    protected int getLayoutId() {
        return R.layout.activity_apply;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        action_id = args.getString(ActionsDetailActivity.BUNDLE_ACTION_ID);

        initFamilyPicker();
        initAgesPicker();
    }

    /**
     * 初始化身份选择器
     */
    private void initFamilyPicker() {
        mFamilyPicker = new OptionPicker(this, ResourcesUtils.getStringArray(mContext, R.array.family));
        mFamilyPicker.setTextColor(ColorConstant.MAIN_COLOR_SEL, ColorConstant.MAIN_COLOR_NOR);
        mFamilyPicker.setLineColor(ColorConstant.MAIN_COLOR_NOR);
        mFamilyPicker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(String option) {
                tv_parent_relation.setText(option);
            }
        });
    }

    /**
     * 初始化年龄选择器
     */
    private void initAgesPicker() {
        mAgesPicker = new OptionPicker(this, ResourcesUtils.getStringArray(mContext, R.array.ages));
        mAgesPicker.setTextColor(ColorConstant.MAIN_COLOR_SEL, ColorConstant.MAIN_COLOR_NOR);
        mAgesPicker.setLineColor(ColorConstant.MAIN_COLOR_NOR);
        mAgesPicker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(String option) {
                tv_childage.setText(option);
            }
        });
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @OnClick({R.id.tv_parent_relation, R.id.tv_childage})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_parent_relation://家长身份
                mFamilyPicker.show();
                break;
            case R.id.tv_childage://孩子年龄
                mAgesPicker.show();
                break;
        }
    }

    @Override
    public void onRightAction() {
        String parentRelation = tv_parent_relation.getText().toString();
        String phone = et_phone.getText().toString();
        String childNickName = et_nickname.getText().toString();
        String childAge = tv_childage.getText().toString();
        //String activityTimes = et_activitytimes.getText().toString();
        String wechat = et_QQorWX.getText().toString();
        String parentName = et_parentname.getText().toString();
        String message = et_message.getText().toString();
        networkRequest(StartApi.participateAdd(action_id, parentRelation, phone, childNickName, childAge, wechat, parentName, message),
                new SimpleFastJsonCallback<String>(String.class, loading) {
                    @Override
                    public void onSuccess(String url, String result) {
                        ToastUtils.showToastShort(mContext, "参加成功");
                        loading.dismiss();
                    }
                });
    }
}
