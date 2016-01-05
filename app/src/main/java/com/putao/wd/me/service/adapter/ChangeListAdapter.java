package com.putao.wd.me.service.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.OrderProduct;
import com.putao.wd.model.ServiceBackImage;
import com.sunnybear.library.eventbus.EventBusHelper;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicAdapter;
import com.sunnybear.library.view.recycler.BasicViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 退货列表适配器
 *
 * Created by yanghx on 2015/12/30.
 */
public class ChangeListAdapter extends BasicAdapter<OrderProduct, ChangeListAdapter.ServiceViewHolder> {

    public static final String CHOOSE_IMAGE = "choose_image";

    private String[] mItems;
    private ArrayAdapter<String> mStringArrayAdapter;
    private ServiceImageAdapter mServiceImageAdapter;
    private List<ServiceBackImage> mList;
    public ChangeListAdapter(Context context, List<OrderProduct> orderProducts) {
        super(context, orderProducts);
        mList = new ArrayList<>();
        mItems = context.getResources().getStringArray(R.array.back_spinnername);
        mStringArrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, mItems);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.activity_service_back_list_item;
    }

    @Override
    public ServiceViewHolder getViewHolder(View itemView, int viewType) {
        return new ServiceViewHolder(itemView);
    }

    @Override
    public void onBindItem(final ServiceViewHolder holder, final OrderProduct orderProduct, final int position) {
        holder.iv_car_icon.setImageURL(orderProduct.getReal_icon());
        holder.tv_title.setText(orderProduct.getProduct_name());
        holder.tv_money.setText(orderProduct.getPrice());
        holder.tv_count.setText(orderProduct.getQuantity());
        holder.tv_color.setText(orderProduct.getSku());
        holder.service_spinner.setAdapter(mStringArrayAdapter);
        mList = orderProduct.getServiceBackImage();
        mServiceImageAdapter = new ServiceImageAdapter(context,mList);
        holder.gv_service_back_image.setAdapter(mServiceImageAdapter);
        holder.gv_service_back_image.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int imgPosition, long id) {
                if (imgPosition == mList.size()) {
                    EventBusHelper.post(position, CHOOSE_IMAGE);
                } else {
                }
            }
        });
        holder.service_spinner.setOnHierarchyChangeListener(new ViewGroup.OnHierarchyChangeListener() {
            @Override
            public void onChildViewAdded(View parent, View child) {
                orderProduct.setReason( holder.service_spinner.getSelectedItemPosition());
            }

            @Override
            public void onChildViewRemoved(View parent, View child) {

            }
        });
    }

    static class ServiceViewHolder extends BasicViewHolder {
        @Bind(R.id.iv_car_icon)
        ImageDraweeView iv_car_icon;
        @Bind(R.id.tv_title)
        TextView tv_title;
        @Bind(R.id.tv_money)
        TextView tv_money;
        @Bind(R.id.tv_count)
        TextView tv_count;
        @Bind(R.id.tv_color)
        TextView tv_color;
        @Bind(R.id.service_spinner)
        Spinner service_spinner;
        @Bind(R.id.gv_service_back_image)
        GridView gv_service_back_image;

        public ServiceViewHolder(View itemView) {
            super(itemView);
        }
    }
}
