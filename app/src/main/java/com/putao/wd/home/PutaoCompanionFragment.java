package com.putao.wd.home;

import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.putao.wd.R;
import com.putao.wd.base.PTWDFragment;
import com.putao.wd.home.adapter.CompanionAdapter;
import com.putao.wd.model.Companion;
import com.putao.wd.pt_companion.BlackboardActivity;
import com.putao.wd.pt_companion.GameDetailActivity;
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
    WebView wv_load;
    @Bind(R.id.sv_load)
    NestScrollView sv_load;
    /*@Bind(R.id.rl_load)
    LinearLayout rl_load;*/

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_companion;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        wv_load.loadDataWithBaseURL("about:blank", "hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhnfdfdfdsfsdf", "text/html", "utf-8", null);
//        wv_load.loadUrl("http://www.linuxidc.com/Linux/2014-03/98847.htm");
        addListener();
    }

    private void addListener() {

       /* rv_content.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                rv_content.noMoreLoading();
            }
        });*/
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
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                /*ViewGroup.LayoutParams layoutParams1 = sv_load.getLayoutParams();
                layoutParams1.height = 3000;
                sv_load.setLayoutParams(layoutParams1);*/
               /* ViewGroup.LayoutParams layoutParams2 = rl_load.getLayoutParams();
                layoutParams2.height = 3000;
                rl_load.setLayoutParams(layoutParams2);*/
            }
        }, 2000);
        /*ViewGroup.LayoutParams layoutParams = wv_load.getLayoutParams();
        layoutParams.height = 1000;
        wv_load.setLayoutParams(layoutParams);*/


    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onItemClick(Serializable serializable, int position) {
        if (0 == position) startActivity(BlackboardActivity.class);
        else startActivity(GameDetailActivity.class);
    }
}


