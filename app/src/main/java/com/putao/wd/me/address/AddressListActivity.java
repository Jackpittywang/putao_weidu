package com.putao.wd.me.address;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.db.db.AddressDBManager;
import com.putao.wd.db.entity.AddressDB;
import com.putao.wd.me.address.adapter.AddressAdapter;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.recycler.OnItemClickListener;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 收货地址列表
 * Created by guchenkai on 2015/11/26.
 */
public class AddressListActivity extends PTWDActivity<GlobalApplication> implements View.OnClickListener {
    public static final String KEY_IS_ADD = "isAdd";
    @Bind(R.id.rv_addresses)
    BasicRecyclerView rv_addresses;
    @Bind(R.id.rl_no_address)
    RelativeLayout rl_no_address;//没有收货地址时的布局

    private AddressAdapter adapter;
    private List<AddressDB> addresses;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_address_list;
    }

    @Override
    protected void onViewCreateFinish(Bundle saveInstanceState) {
        addNavgation();
        addresses = AddressDBManager.getInstance(mApp).loadAll();
        if (addresses == null || addresses.size() == 0) {
            rl_no_address.setVisibility(View.VISIBLE);
            return;
        }
        adapter = new AddressAdapter(mContext, addresses);
        rv_addresses.setAdapter(adapter);
        //点击item
        rv_addresses.setOnItemClickListener(new OnItemClickListener<AddressDB>() {

            @Override
            public void onItemClick(AddressDB addressDB, int position) {

            }
        });
        //网络请求Demo
//        networkRequest("自己组合的request", new SimpleFastJsonCallback<"自己的接收model">() {
//            @Override
//            public void onSuccess("自己的接收model" url, String result) {
//
//            }
//        });
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
                bundle.putBoolean(AddressListActivity.KEY_IS_ADD, true);
                startActivity(AddressEditActivity.class, bundle);
                break;
        }
    }
}
