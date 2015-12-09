package com.putao.wd.user;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.sunnybear.library.util.ResourcesUtils;

import butterknife.Bind;

/**
 * 用户服务协议
 * Created by guchenkai on 2015/11/29.
 */
public class ProtocolActivity extends PTWDActivity implements View.OnClickListener {
    @Bind(R.id.tv_protocol)
    TextView tv_protocol;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_protocol;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        addNavigation();
        String protocol = ResourcesUtils.getAssetTextFile(mContext, "protocol.txt");
        tv_protocol.setText(Html.fromHtml(protocol));
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onClick(View v) {

    }
}