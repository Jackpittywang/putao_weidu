package com.putao.wd.me.address.fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.base.PTWDFragment;
import com.putao.wd.db.DistrictDBManager;
import com.putao.wd.me.address.adapter.CitySelectAdapter;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.recycler.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 城区选择
 * Created by guchenkai on 2015/12/7.
 */
public class DistrictFragment extends PTWDFragment<GlobalApplication> {
    public static final String EVENT_DISTRICT_SELECT = "disrict_select";

    public static final String KEY_PROVINCE_NAME = "province_name";
    private String province_name;
    public static final String KEY_CITY_NAME = "city_name";
    private String city_name;

    @Bind(R.id.tv_province_name)
    TextView tv_province_name;
    @Bind(R.id.tv_city_name)
    TextView tv_city_name;
    @Bind(R.id.rv_district)
    BasicRecyclerView rv_district;

    private CitySelectAdapter adapter;
    private List<String> districtNames;
    private DistrictDBManager mDistrictDBManager;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_district;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        province_name = args.getString(KEY_PROVINCE_NAME);
        city_name = args.getString(KEY_CITY_NAME);

        tv_province_name.setText(province_name);
        tv_city_name.setText(city_name);

        mDistrictDBManager = (DistrictDBManager) mApp.getDataBaseManager(DistrictDBManager.class);
        districtNames = mDistrictDBManager.getDistrictNamesByCityName(city_name);
        adapter = new CitySelectAdapter(mActivity, districtNames);
        rv_district.setAdapter(adapter);

        addListener();
    }

    private void addListener() {
        rv_district.setOnItemClickListener(new OnItemClickListener<String>() {
            @Override
            public void onItemClick(String s, int position) {
                List<String> list = new ArrayList<>();
                list.add(province_name);
                list.add(city_name);
                list.add(s);
                EventBusHelper.post(list, EVENT_DISTRICT_SELECT);
                mActivity.finish();
            }
        });
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
