package com.putao.wd.me.address;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.base.PTWDFragment;
import com.putao.wd.db.db.AddressDBManager;
import com.putao.wd.db.entity.AddressDB;
import com.putao.wd.me.address.adapter.AddressAdapter;
import com.sunnybear.library.view.recycler.BasicRecyclerView;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 收货地址列表
 * Created by guchenkai on 2015/11/26.
 */
public class AddressListFragment extends PTWDFragment<GlobalApplication> implements View.OnClickListener {
    @Bind(R.id.rv_addresses)
    BasicRecyclerView rv_addresses;
    @Bind(R.id.rl_no_address)
    RelativeLayout rl_no_address;//没有地址时的布局

    private AddressAdapter adapter;
    private List<AddressDB> addresses;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_address_list;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        addNavgation();
        addresses = AddressDBManager.getInstance(mApp).loadAll();
        if (addresses == null || addresses.size() == 0) {
            rl_no_address.setVisibility(View.VISIBLE);
            return;
        }
        adapter = new AddressAdapter(mActivity, addresses);
        rv_addresses.setAdapter(adapter);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @OnClick(R.id.rl_add_address)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_add_address://新增地址
                Bundle bundle = new Bundle();
                bundle.putBoolean(AddressActivity.KEY_IS_ADD, true);
                startFragment(AddressEditFragment.class, bundle);
                break;
        }
    }
}
