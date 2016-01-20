package com.putao.wd.explore;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
import com.sunnybear.library.view.recycler.adapter.BasicAdapter;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.listener.OnItemClickListener;


import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;

/**
 * 探索--前7
 * Created by yanghx on 2016/1/11.
 */
public class SmartListFragment extends BasicFragment {
    @Bind(R.id.rv_smart_list)
    BasicRecyclerView rv_smart_list;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_smart_list;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        rv_smart_list.setAdapter(new SmartListAdapter(mActivity, Arrays.asList(getResources().getStringArray(R.array.smart_list))));
        rv_smart_list.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Serializable serializable, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt(SmartDetailFragment.CONTENT_NUM, position);
                EventBusHelper.post(bundle, SmartActivity.SMART_LIST);
            }
        });
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }


    class SmartListAdapter extends BasicAdapter<String, SmartListFragment.SmartViewHolder> {


        public SmartListAdapter(Context context, List<String> strings) {
            super(context, strings);
        }

        @Override
        public int getLayoutId(int viewType) {
            return R.layout.fragment_smart_list_item;
        }

        @Override
        public SmartViewHolder getViewHolder(View itemView, int viewType) {
            return new SmartViewHolder(itemView);
        }

        @Override
        public void onBindItem(SmartViewHolder holder, String s, int position) {
            holder.tv_smart_item.setText(s);
        }

    }

    static class SmartViewHolder extends BasicViewHolder {
        @Bind(R.id.tv_smart_item)
        TextView tv_smart_item;

        public SmartViewHolder(View itemView) {
            super(itemView);
        }
    }

}
