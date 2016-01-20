package com.putao.wd.me.address.fragment;

import android.os.Bundle;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.base.PTWDFragment;
import com.putao.wd.db.ProvinceDBManager;
import com.putao.wd.me.address.adapter.CitySelectAdapter;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.recycler.listener.OnItemClickListener;

import java.util.List;

import butterknife.Bind;

/**
 * 省份
 * Created by guchenkai on 2015/12/7.
 */
public class ProvinceFragment extends PTWDFragment<GlobalApplication> {
    @Bind(R.id.rv_province)
    BasicRecyclerView rv_province;

    private CitySelectAdapter adapter;
    private List<String> provinceNames;

    private ProvinceDBManager mProvinceDBManager;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_province;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        addNavigation();

        mProvinceDBManager = (ProvinceDBManager) mApp.getDataBaseManager(ProvinceDBManager.class);
        provinceNames = mProvinceDBManager.getProvinceNames();
        adapter = new CitySelectAdapter(mActivity, provinceNames);
        rv_province.setAdapter(adapter);

        addListener();
    }

    private void addListener() {
        rv_province.setOnItemClickListener(new OnItemClickListener<String>() {
            @Override
            public void onItemClick(String s, int position) {
                Bundle bundle = new Bundle();
                bundle.putString(CityFragment.KEY_PROVINCE_NAME, s);
                startFragment(CityFragment.class, bundle);
            }
        });
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
