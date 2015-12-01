package com.putao.wd.user;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.putao.wd.R;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.util.ResourcesUtils;

import butterknife.Bind;


public class ProtocolFragment extends BasicFragment {

    @Bind(R.id.tv_protocol)
    TextView tv_protocol;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_protocol;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        String protocol = ResourcesUtils.getAssetTextFile(mActivity, "protocol.txt");
        tv_protocol.setText(Html.fromHtml(protocol));
    }


    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}