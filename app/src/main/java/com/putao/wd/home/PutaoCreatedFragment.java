package com.putao.wd.home;

import android.os.Bundle;

import com.putao.wd.R;
import com.putao.wd.base.PTWDFragment;
import com.putao.wd.created.CreatedDetailActivity;
import com.putao.wd.created.FancyActivity;
import com.putao.wd.home.adapter.CreateAdapter;
import com.putao.wd.model.Marketing;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;
import com.sunnybear.library.view.recycler.OnItemClickListener;

import java.io.Serializable;

import butterknife.Bind;

/**
 * 创造(首页)
 * Created by guchenkai on 2016/1/13.
 */
public class PutaoCreatedFragment extends PTWDFragment implements OnItemClickListener {
    @Bind(R.id.rv_created)
    LoadMoreRecyclerView rv_created;

    private CreateAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_created;
    }

    @Override
    public void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        adapter = new CreateAdapter(mActivity, null);
        rv_created.setAdapter(adapter);
        adapter.add(new Marketing());
        adapter.add(new Marketing());
        adapter.add(new Marketing());
        rv_created.setOnItemClickListener(this);
        rv_created.noMoreLoading();
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onItemClick(Serializable serializable, int position) {
        startActivity(CreatedDetailActivity.class);
    }

    @Override
    public void onRightAction() {
        startActivity(FancyActivity.class);
    }
}
