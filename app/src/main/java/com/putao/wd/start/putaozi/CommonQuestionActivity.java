package com.putao.wd.start.putaozi;

import android.os.Bundle;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;

/**
 * 葡萄籽常见问题详情界面
 * Created by yanghx on 2016/1/8.
 */
public class CommonQuestionActivity extends PTWDActivity<GlobalApplication> {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_common_question;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {

    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
