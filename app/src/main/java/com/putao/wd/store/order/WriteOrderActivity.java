package com.putao.wd.store.order;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListAdapter;

import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.dto.OrderListItem;
import com.putao.wd.dto.ShoppingCar;
import com.putao.wd.store.adapter.ShoppingCarAdapter;
import com.putao.wd.store.order.adapter.OrdersAdapter;
import com.sunnybear.library.view.recycler.BasicRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 填写订单
 * Created by guchenkai on 2015/11/30.
 */
public class WriteOrderActivity extends PTWDActivity implements View.OnClickListener {
    @Bind(R.id.iv_reapte_picbar)//分割图片
    ImageView iv_reapte_picbar;
    @Bind(R.id.brv_write_orderlist)//订单列表
    BasicRecyclerView brv_write_orderlist;

    private OrdersAdapter adapter;
    private int screenWidth=0;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_write_order;
    }



    @Override
    protected void onViewCreateFinish(Bundle saveInstanceState) {
        addNavgation();
        //将小图片x轴循环填充进imageview中
        screenWidth=((WindowManager) mContext.getSystemService(mContext.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
        Resources res = mContext.getResources();
        Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.img_cart_lace_stuff);
        iv_reapte_picbar.setImageBitmap(createRepeater(screenWidth, bitmap));//screenWidth为屏幕宽度（或显示图片的imageview宽度）

        //初始化列表数据
        List<OrderListItem> cars = sort(getTestData());
        adapter = new OrdersAdapter(mContext, cars);
        brv_write_orderlist.setAdapter(adapter);

        //重新设置list高度
//        ViewGroup.LayoutParams mParams = brv_write_orderlist.getLayoutParams();
//        mParams.height = (CommonUtils.getScreenWidthPX(mContext) * 480 / 720 + CommonUtils.dipToPixels(40)) * cars.size() + CommonUtils.dipToPixels(8);
//        mParams.width = CommonUtils.getScreenWidthPX(mContext);
//        brv_write_orderlist.setLayoutParams(mParams);
        //setListViewHeightBasedOnChildren();
    }
    /**
     * 动态设置ListView的高度
     * @param
     */
    public  void setListViewHeightBasedOnChildren() {
//        if(brv_write_orderlist == null) return;
//
//        if (adapter == null) {
//            // pre-condition
//            return;
//        }
//        int totalHeight = 0;
//        for (int i = 0; i < adapter.getItemCount(); i++) {
//            View listItem = adapter.getViewHolder(i, null, brv_write_orderlist);
//            listItem.measure(0, 0);
//            totalHeight += listItem.getMeasuredHeight();
//        }
//        ViewGroup.LayoutParams params = brv_write_orderlist.getLayoutParams();
//        params.height = totalHeight + (brv_write_orderlist.getDividerHeight() * (adapter.getItemCount() - 1));
//        brv_write_orderlist.setLayoutParams(params);
    }

    //将图片在imageview中x轴循环填充需要的bitmap
    public static Bitmap createRepeater(int width, Bitmap src) {
        int count = (width + src.getWidth() - 1) / src.getWidth(); //计算出平铺填满所给width（宽度）最少需要的重复次数
        Bitmap bitmap = Bitmap.createBitmap(src.getWidth()*count, src.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        for (int idx = 0; idx < count; ++idx) {
            canvas.drawBitmap(src, idx * src.getWidth(), 0, null);
        }
        return bitmap;
    }
    private List<OrderListItem> getTestData() {
        List<OrderListItem> list = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            OrderListItem car = new OrderListItem();
            car.setTitle("葡萄探索号.虚拟+现实儿童科技益智玩具");
            car.setColor("塔塔紫");
            car.setSize("均码");
            car.setMoney("399.00");
            car.setCount("2");
            car.setIsNull(i % 2 == 1);
            list.add(car);
        }
        return list;
    }
    private List<OrderListItem> sort(List<OrderListItem> cars) {
        List<OrderListItem> list = new ArrayList<>();
        List<OrderListItem> trueList = new ArrayList<>();
        List<OrderListItem> falseList = new ArrayList<>();
        for (OrderListItem car : cars) {
            if (!car.isNull())
                trueList.add(car);
            else
                falseList.add(car);
        }
        list.addAll(trueList);
        list.addAll(falseList);
        return list;
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onClick(View v) {

    }
}
