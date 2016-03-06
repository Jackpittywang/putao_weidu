package com.putao.wd.me.order;

import android.os.Bundle;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.Express;
import com.sunnybear.library.controller.BasicFragment;

import java.util.ArrayList;

import butterknife.Bind;


public class OrderShipmentDetailFragment extends BasicFragment {
    private static final String KEY_CONTENT = "OrderShipmentDetailFragment:Content";


    public static final String EXPRESS = "express";
    public static final String PACKAGECOUNT = "packageCount";
    public static final String PACKAGINDEX = "packageIndex";
    /*  @Bind(R.id.hsv_package_list)
      HorizontalScrollView hsv_package_list;// 包裹列表的horizontalscrollview*/
  /*  @Bind(R.id.tv_order_goods_text)
    TextView tv_order_goods_text;
    @Bind(R.id.img_goods)
    ImageDraweeView img_goods;//图片
    @Bind(R.id.tv_name)
    TextView tv_name;
    @Bind(R.id.tv_number)
    TextView tv_number;
    @Bind(R.id.tv_shipment_text)
    TextView tv_shipment_text;
    @Bind(R.id.rl_product)
    RelativeLayout rl_product;
    @Bind(R.id.tv_package_status)
    TextView tv_package_status;
    @Bind(R.id.ll_package_list)
    LinearLayout ll_package_list;
    @Bind(R.id.rg_title_bar)
    RadioGroup rg_title_bar;
    @Bind(R.id.sc_shipment)
    ScrollView sc_shipment;
    @Bind(R.id.rl_no_shipment)
    RelativeLayout rl_no_shipment;
    @Bind(R.id.rv_shipment_detail)
    RecyclerView rv_shipment_detail;
*/
    private Express express;
    private ArrayList<Express> expresses;
    private int packageCount;
    private int packageIndex;
    private int id = 2356890;



    public static OrderShipmentDetailFragment newInstance(String content) {
        OrderShipmentDetailFragment fragment = new OrderShipmentDetailFragment();

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 20; i++) {
            builder.append(content).append(" ");
        }
        builder.deleteCharAt(builder.length() - 1);
        fragment.mContent = builder.toString();

        return fragment;
    }

    private String mContent = "???";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
            mContent = savedInstanceState.getString(KEY_CONTENT);
        }
    }

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView text = new TextView(getActivity());
        text.setGravity(Gravity.CENTER);
        text.setText(mContent);
        text.setTextSize(20 * getResources().getDisplayMetrics().density);
        text.setPadding(20, 20, 20, 20);

        LinearLayout layout = new LinearLayout(getActivity());
        layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        layout.setGravity(Gravity.CENTER);
        layout.addView(text);

        return layout;
    }*/

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_CONTENT, mContent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ordershipmentdetail;
    }

    @Bind(R.id.tv_no_data)
    TextView tv_no_data;
    @Override
    public void onViewCreatedFinish(Bundle saveInstanceState) {
        tv_no_data.setText(mContent);
       /* Logger.d("MessageCenterActivity", "PraiseFragment启动");
        tv_message_empty.setText("还没有赞");
        adapter = new PraiseAdapter(mActivity, null);
        rv_content.setAdapter(adapter);
        getNotifyList();

        addListener();*/
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }


}
