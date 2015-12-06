package com.putao.wd.user;

import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.base.PTWDFragment;
import com.sunnybear.library.util.ResourcesUtils;

import butterknife.Bind;


public class ProtocolFragment extends PTWDFragment implements View.OnClickListener, TextWatcher {

    @Bind(R.id.tv_protocol)
    TextView tv_protocol;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_protocol;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        addNavigation();
        String protocol = ResourcesUtils.getAssetTextFile(mActivity, "protocol.txt");
        tv_protocol.setText(Html.fromHtml(protocol));
    }


    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}