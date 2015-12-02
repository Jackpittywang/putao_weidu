package com.putao.wd.user;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.ImageView;

import com.putao.wd.R;
import com.putao.wd.base.PTWDFragment;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.controller.intent.FragmentIntent;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.CleanableEditText;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 重置密码
 * Created by wango on 2015/12/1.
 */
public class ResetPasswordFragment extends PTWDFragment implements View.OnClickListener {
    @Bind(R.id.et_password)
    CleanableEditText et_password;//密码
    @Bind(R.id.iv_lock)
    ImageView iv_lock;//密码是否可见图标

    private boolean isPasswordVisible = false;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_reset_password;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {

    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    //-------|完成---------------|设置密码是否可见layout可点击区域
    @OnClick({R.id.btn_complete, R.id.ll_password_visible})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_complete://完成

//                if(args.getString(ValidationFragment.VALIDATECODE)!=null) {
//                    //忘记密码验证登录
//                    if(args.getString(PasswordFragment.FORFET)!=null){
//                        ToastUtils.showToastShort(mActivity, "忘记密码验证登录");
//                        FindPasswordToLogin();
//                    }else {//注册登录
//                        ToastUtils.showToastShort(mActivity,"注册登录");
//                        RegisterToLogin();
//                    }
//                }else{//直接登录
//                    ToastUtils.showToastShort(mActivity,"直接登录");
//                    RememberPasswordToLogin();
//                }
                //startActivity(new Intent(getActivity(), HomeActivity.class));//具体跳转逻辑有数据了以后改

                break;
            case R.id.ll_password_visible://是否显示密码
                if (!isPasswordVisible) { //设置为隐藏
                    et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isPasswordVisible = true;
                    iv_lock.setImageResource(R.drawable.icon_20_10);
                } else {//设置为可见
                    et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isPasswordVisible = false;
                    iv_lock.setImageResource(R.drawable.icon_20_11);
                }
                break;

        }
    }
}