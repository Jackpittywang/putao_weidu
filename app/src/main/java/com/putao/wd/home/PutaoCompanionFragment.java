package com.putao.wd.home;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.putao.wd.R;
import com.putao.wd.base.PTWDFragment;
import com.putao.wd.home.adapter.CompanionAdapter;
import com.putao.wd.model.Companion;
import com.putao.wd.pt_companion.BlackboardActivity;
import com.putao.wd.pt_companion.GameDetailListActivity;
import com.sunnybear.library.view.BasicWebView;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;
import com.sunnybear.library.view.recycler.listener.OnItemClickListener;
import com.sunnybear.library.view.scroll.NestScrollView;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.Bind;

/**
 * 精选(首页)
 * Created by zhanghao on 2016/04/05.
 */
public class PutaoCompanionFragment extends PTWDFragment implements OnItemClickListener {
    private CompanionAdapter mCompanionAdapter;
    @Bind(R.id.rv_content)
    LoadMoreRecyclerView rv_content;
    @Bind(R.id.wv_load)
    BasicWebView wv_load;
    @Bind(R.id.sv_load)
    NestScrollView sv_load;
    @Bind(R.id.rl_load)
    LinearLayout rl_load;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_companion;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        addListener();
    }

    private void addListener() {

        rv_content.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                rv_content.noMoreLoading();
            }
        });
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
        mCompanionAdapter = new CompanionAdapter(mActivity, companions);
        rv_content.setAdapter(mCompanionAdapter);
        mCompanionAdapter.setOnItemClickListener(this);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onItemClick(Serializable serializable, int position) {
        if (0 == position) startActivity(BlackboardActivity.class);
        else startActivity(GameDetailListActivity.class);
    }


}


