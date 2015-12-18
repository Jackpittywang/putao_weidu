package com.putao.wd.start.apply;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.api.StartApi;
import com.putao.wd.base.PTWDActivity;
import com.sunnybear.library.eventbus.EventBusHelper;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.CleanableEditText;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 我要参加
 * Created by wango on 2015/12/4.
 *
 */
public class ApplyActivity extends PTWDActivity<GlobalApplication> implements View.OnClickListener {
    @Bind(R.id.tv_parent_relation)
    TextView tv_parent_relation;//家长身份
    @Bind(R.id.et_phone)
    CleanableEditText et_phone;//手机号码
    @Bind(R.id.et_nickname)
    EditText et_nickname;//孩子昵称
    @Bind(R.id.tv_childage)
    TextView tv_childage;//孩子年龄
//    @Bind(R.id.et_activitytimes)    ========== 赞未使用 ==========
//    EditText et_activitytimes;//活动场次
    @Bind(R.id.et_QQorWX)
    EditText et_QQorWX;//QQ/微信
    @Bind(R.id.et_parentname)
    EditText et_parentname;//家长姓名
    @Bind(R.id.et_message)
    EditText et_message;//留言

    private String action_id;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_apply;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        Bundle bundle = getIntent().getExtras();
        action_id = bundle.getString("action_id");
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @OnClick({R.id.iv_parentrelation_selecticon,R.id.iv_child_age})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_parentrelation_selecticon://家长身份
                new AlertDialog.Builder(mContext).setTitle("年龄").setIcon(
                        android.R.drawable.ic_dialog_info).setSingleChoiceItems(
                        new String[] { "爷爷", "奶奶", "外公","外婆","爸爸","妈妈","叔叔","阿姨","舅舅","舅妈","姑姑","姑爹" }, 0,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case 0:tv_parent_relation.setText("爷爷");break;
                                    case 1:tv_parent_relation.setText("奶奶");break;
                                    case 2:tv_parent_relation.setText("外公");break;
                                    case 3:tv_parent_relation.setText("外婆");break;
                                    case 4:tv_parent_relation.setText("爸爸");break;
                                    case 5:tv_parent_relation.setText("妈妈");break;
                                    case 6:tv_parent_relation.setText("叔叔");break;
                                    case 7:tv_parent_relation.setText("阿姨");break;
                                    case 8:tv_parent_relation.setText("舅舅");break;
                                    case 9:tv_parent_relation.setText("舅妈");break;
                                    case 10:tv_parent_relation.setText("姑姑");break;
                                    case 11:tv_parent_relation.setText("姑爹");break;
                                }
                                dialog.dismiss();
                            }
                        }).setNegativeButton("取消", null).show();
                break;
            case R.id.iv_child_age://孩子年龄
                new AlertDialog.Builder(mContext).setTitle("年龄").setIcon(
                        android.R.drawable.ic_dialog_info).setSingleChoiceItems(
                        new String[] { "1岁", "2岁", "3岁","4岁","5岁","6岁","7岁","8岁","9岁","10岁","11岁","12岁" }, 0,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case 0:tv_childage.setText("1岁");break;
                                    case 1:tv_childage.setText("2岁");break;
                                    case 2:tv_childage.setText("3岁");break;
                                    case 3:tv_childage.setText("4岁");break;
                                    case 4:tv_childage.setText("5岁");break;
                                    case 5:tv_childage.setText("6岁");break;
                                    case 6:tv_childage.setText("7岁");break;
                                    case 7:tv_childage.setText("8岁");break;
                                    case 8:tv_childage.setText("9岁");break;
                                    case 9:tv_childage.setText("10岁");break;
                                    case 10:tv_childage.setText("11岁");break;
                                    case 11:tv_childage.setText("12岁");break;
                                }
                                dialog.dismiss();
                            }
                        }).setNegativeButton("取消", null).show();
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
