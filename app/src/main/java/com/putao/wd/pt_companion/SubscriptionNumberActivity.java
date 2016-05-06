package com.putao.wd.pt_companion;

import android.os.Bundle;

import com.putao.wd.R;
import com.putao.wd.account.AccountConstants;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.model.Companion;
import com.putao.wd.pt_companion.adapter.SubscriptionNumberAdapter;
import com.sunnybear.library.view.PullToRefreshLayout;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;
import com.sunnybear.library.view.recycler.listener.OnItemClickListener;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.Bind;

/**
 * 订阅号列表
 * Created by Administrator on 2016/5/5.
 */
public class SubscriptionNumberActivity extends PTWDActivity {

    @Bind(R.id.ptl_refresh)
    PullToRefreshLayout ptl_refresh;
    @Bind(R.id.rv_content)
    LoadMoreRecyclerView rv_content;

    private SubscriptionNumberAdapter adapter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_companion_subribe_list;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        ArrayList<Companion> companions = new ArrayList<>();
        companions.add(new Companion());
        companions.add(new Companion());
        companions.add(new Companion());
        companions.add(new Companion());
        companions.add(new Companion());
        companions.add(new Companion());
        companions.add(new Companion());
        companions.add(new Companion());
        companions.add(new Companion());
        companions.add(new Companion());
        adapter = new SubscriptionNumberAdapter(mContext, companions);
        rv_content.setAdapter(adapter);

        addListener();
    }

    //点击事件
    private void addListener() {
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Serializable serializable, int position) {
                Bundle bundle = new Bundle();
//                bundle.putSerializable(AccountConstants.Bundle.BUNDLE_COMPANION, mCompanion);
                startActivity(OfficialAccountsActivity.class, bundle);
            }
        });
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onLeftAction() {
        super.onLeftAction();
        finish();
    }
}
