package com.putao.wd.pt_me.address.fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.base.PTWDFragment;
import com.putao.wd.db.CityDBManager;
import com.putao.wd.pt_me.address.adapter.CitySelectAdapter;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.recycler.listener.OnItemClickListener;

import java.util.List;

import butterknife.Bind;

/**
 * 城市选择
 * Created by guchenkai on 2015/12/7.
 */
public class CityFragment extends PTWDFragment<GlobalApplication> {
    public static final String KEY_PROVINCE_NAME = "province_name";
    private String province_name;

    @Bind(R.id.tv_province_name)
    TextView tv_province_name;
    @Bind(R.id.rv_city)
    BasicRecyclerView rv_city;

    private CitySelectAdapter adapter;
    private List<String> cityNames;
    private CityDBManager mCityDBManager;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_city;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        addNavigation();

        province_name = args.getString(KEY_PROVINCE_NAME);
        tv_province_name.setText(province_name);

        mCityDBManager = (CityDBManager) mApp.getDataBaseManager(CityDBManager.class);
        cityNames = mCityDBManager.getCityNamesByProvinceName(province_name);
        adapter = new CitySelectAdapter(mActivity, cityNames);
        rv_city.setAdapter(adapter);

        addListener();
    }

    private void addListener() {
        rv_city.setOnItemClickListener(new OnItemClickListener<String>() {
            @Override
            public void onItemClick(String s, int position) {
                Bundle bundle = new Bundle();
                bundle.putString(DistrictFragment.KEY_PROVINCE_NAME, province_name);
                bundle.putString(DistrictFragment.KEY_CITY_NAME, s);
                startFragment(DistrictFragment.class, bundle);
            }
        });
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
