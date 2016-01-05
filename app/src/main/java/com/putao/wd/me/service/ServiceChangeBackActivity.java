package com.putao.wd.me.service;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.putao.wd.ColorConstant;
import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.api.OrderApi;
import com.putao.wd.api.StoreApi;
import com.putao.wd.api.UploadApi;
import com.putao.wd.api.UserApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.base.SelectPopupWindow;
import com.putao.wd.me.address.AddressListActivity;
import com.putao.wd.me.service.adapter.ChangeBackListAdapter;
import com.putao.wd.model.Address;
import com.putao.wd.model.Order;
import com.putao.wd.model.OrderProduct;
import com.putao.wd.model.ProductData;
import com.putao.wd.model.ServiceBackImage;
import com.sunnybear.library.eventbus.Subcriber;
import com.sunnybear.library.model.http.UploadFileTask;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.FileUtils;
import com.sunnybear.library.util.ImageUtils;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.ResourcesUtils;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.picker.OptionPicker;
import com.sunnybear.library.view.recycler.BasicRecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * 退货列表
 * Created by wangou on 15/12/7.
 */
public class ServiceChangeBackActivity extends PTWDActivity<GlobalApplication> implements View.OnClickListener {

    @Bind(R.id.tv_service_money)
    TextView tv_service_money;//退货金额
    @Bind(R.id.rv_service_back_list)
    BasicRecyclerView rv_service_back_list;//售后列表
    @Bind(R.id.rl_back_detail)
    RelativeLayout rl_back_detail;
    @Bind(R.id.fl_address)
    FrameLayout fl_address;
    @Bind(R.id.ll_receiving_address)
    LinearLayout ll_receiving_address;
    @Bind(R.id.ll_no_receiving_address)
    LinearLayout ll_no_receiving_address;
    @Bind(R.id.tv_name)
    TextView tv_name;
    @Bind(R.id.tv_address)
    TextView tv_address;
    @Bind(R.id.tv_phone)
    TextView tv_phone;

    private String addressId = "";
    private String consignee = "";
    private String mobile = "";

    private final int ALBUM_REQCODE = 1;
    private final int CAMERA_REQCODE = 2;
    private int position;
    private SelectPopupWindow mSelectPopupWindow;
    private ChangeBackListAdapter adapter;
    public static final String ORDER_ID = "orderId";
    private Order mOrder;
    private String mOrderId;
    private ArrayList<OrderProduct> mProducts;
    private String[] mItems;
    List<ServiceBackImage> serviceBackImages;
    private String mServiceWay;
    private double totalPrice;

    private OptionPicker mReasonPicker;//退款理由选择
    //--------上传
    private String uploadToken;//上传token
    private String sha1;//上传文件sha1
    private File uploadFile;//上传文件
    private String filePath;//文件路径
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = (Bundle) msg.obj;
        }
    };


    @Override
    protected int getLayoutId() {
        return R.layout.activity_service_change_back_detail;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        initData();
        addListener();
    }

    private void addListener() {
    }

    private void initView() {
        tv_service_money.setText("￥" + totalPrice);
        adapter = new ChangeBackListAdapter(mContext, mProducts,mServiceWay);
        rv_service_back_list.setAdapter(adapter);
        if ("1".equals(mServiceWay)) {
            navigation_bar.setMainTitle("请填写换货信息");
        } else if ("2".equals(mServiceWay)) {
            navigation_bar.setMainTitle("请填写退货信息");
            fl_address.setVisibility(View.GONE);
        }
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mOrderId = args.getString(ORDER_ID);
        mServiceWay = args.getString(ServiceChooseActivity.SERVICE_WAY);
        mProducts = (ArrayList<OrderProduct>) args.getSerializable(ServiceChooseActivity.SERVICE_PRODUCT);
        mItems = getResources().getStringArray(R.array.change_spinnername);
        getDefaultAddress();
        totalPrice = 0;
        for (OrderProduct product : mProducts) {
            double price = Double.parseDouble(product.getPrice());
            int quantity = Integer.parseInt(product.getQuantity());
            totalPrice += price * quantity;
        }
        initView();
    }

    /**
     * 获取默认地址
     */
    private void getDefaultAddress() {
        networkRequest(StoreApi.getDefaultAddress(), new SimpleFastJsonCallback<Address>(Address.class, loading) {
            @Override
            public void onSuccess(String url, Address result) {
                Logger.d(result.toString());
                if (result != null) {
                    ll_receiving_address.setVisibility(View.VISIBLE);
                    ll_no_receiving_address.setVisibility(View.GONE);
                    setAddress(result);
                }
                loading.dismiss();
            }
        });
    }

    /**
     * 设置地址信息
     */
    private void setAddress(Address address) {
        tv_name.setText(address.getRealname());
        tv_address.setText(setAddressName(address));
        tv_phone.setText(address.getMobile());
        addressId = address.getId();
        consignee = address.getRealname();
        mobile = address.getMobile();
    }

    /**
     * 设置地址
     *
     * @param address
     */
    private String setAddressName(Address address) {
        JSONObject object = JSON.parseObject(address.getAddressName());
        String addr = object.getString(address.getProvince_id()) +
                object.getString(address.getCity_id()) +
                object.getString(address.getArea_id()) +
                address.getAddress();
        return addr;
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }


    //选择图片源
    @Subcriber(tag = ChangeBackListAdapter.CHOOSE_IMAGE)
    public void chooseImage(int position) {
        this.position = position;
        mSelectPopupWindow = new SelectPopupWindow(mContext) {
            @Override
            public void onFirstClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQCODE);
            }

            @Override
            public void onSecondClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, ALBUM_REQCODE);
            }
        };
        mSelectPopupWindow.show(rl_back_detail);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        loading.show();
        filePath = GlobalApplication.sdCardPath + File.separator + "service_back" + position + "a" + mProducts.size() + ".jpg";
        if (resultCode == RESULT_OK) {
            Bitmap bitmap = null;
            switch (requestCode) {
                case CAMERA_REQCODE://相机选择
                    Bundle bundle = data.getExtras();
                    bitmap = (Bitmap) bundle.get("data");

                    break;
                case ALBUM_REQCODE://相册选择
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);

                    Logger.d(picturePath);
                    cursor.close();
                    bitmap = ImageUtils.getSmallBitmap(picturePath, 320, 320);
                    break;
            }
            ImageUtils.bitmapOutSdCard(bitmap, filePath);
            ServiceBackImage serviceBackImage = new ServiceBackImage();
            serviceBackImage.setBitmap(bitmap);
            serviceBackImage.setURL(filePath);
            mProducts.get(position).getServiceBackImage().add(serviceBackImage);
            uploadFile = new File(serviceBackImage.getURL());
            sha1 = FileUtils.getSHA1ByFile(uploadFile);
            serviceBackImage.setSha1(sha1);
            getUploadToken();
            adapter.notifyItemChanged(position);
            loading.dismiss();
        }
    }

    @Override
    public void onRightAction() {
        for (OrderProduct product : mProducts) {
            String reason = product.getReason();
            if (null == reason) {
                if ("1".equals(mServiceWay)) {
                    if (null == addressId || "".equals(addressId)) {
                        ToastUtils.showToastShort(mContext, "请选择收货人地址");
                        return;
                    }
                    ToastUtils.showToastShort(mContext, "请选择换货原因");
                } else {
                    ToastUtils.showToastShort(mContext, "请选择退货原因");
                }
                loading.dismiss();
                return;
            }
            requestBack();
        }
    }

    /**
     * 请求售后
     */
    private void requestBack() {
        String allProductId = "";
        Map<String, ProductData> productDataMap = new HashMap<>();
        String sha1 = new String();
        for (OrderProduct product : mProducts) {
            String reason = product.getReason();
            int i = 0;
            for (String str : stringArray) {
                i++;
                if (str.equals(reason)) break;
            }
            allProductId = allProductId + "," + product.getProduct_id();
            ProductData productData = new ProductData();
            productData.setProduct_id(product.getId());
            productData.setReason(i);
            productData.setDescription("");
            productData.setImage("");
            productData.setQuantity(product.getQuantity());
            for (ServiceBackImage serviceBackImage : product.getServiceBackImage()) {
                sha1 += "," + serviceBackImage.getSha1();
            }
            productData.setImage(sha1.length() > 1 ? sha1.substring(1) : "");
            productDataMap.put(product.getProduct_id() + "", productData);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.putAll(productDataMap);
        Logger.d("-----++" + jsonObject.toJSONString());

        networkRequest(OrderApi.orderSubmitAfterSale(mOrderId, mServiceWay, addressId, allProductId.substring(1), jsonObject.toJSONString()),
                new SimpleFastJsonCallback<String>(String.class, loading) {
                    @Override
                    public void onSuccess(String url, String result) {
                        Logger.d(result.toString());
                        ToastUtils.showToastShort(mContext, "申请提交成功,请等待审核");
                        loading.dismiss();
                        finish();
                    }

                    @Override
                    public void onFailure(String url, int statusCode, String msg) {
                        ToastUtils.showToastShort(mContext, "提交失败");
                    }
                });
    }

    private void getUploadToken() {
        networkRequest(UserApi.getUploadToken(), new SimpleFastJsonCallback<String>(String.class, null) {
            @Override
            public void onSuccess(String url, String result) {
                JSONObject jsonObject = JSON.parseObject(result);
                uploadToken = jsonObject.getString("uploadToken");
                Logger.d(uploadToken);
                uploadFile();
            }
        });
    }

    private void uploadFile() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                UploadApi.uploadFile(uploadToken, sha1, uploadFile, new UploadFileTask.UploadCallback() {
                    @Override
                    public void onSuccess(JSONObject result) {
                        Logger.d(result.toJSONString());
                    }
                });
            }
        });
        thread.start();
    }

    String[] stringArray;

    /**
     * 初始化理由选择器
     */
    private void initFamilyPicker() {

        if ("1".equals(mServiceWay)) {
            stringArray = ResourcesUtils.getStringArray(mContext, R.array.back_spinnername);
        } else {
            stringArray = ResourcesUtils.getStringArray(mContext, R.array.change_spinnername);
        }
        mReasonPicker = new OptionPicker(this, stringArray);
        mReasonPicker.setTextColor(ColorConstant.MAIN_COLOR_SEL, ColorConstant.MAIN_COLOR_NOR);
        mReasonPicker.setLineColor(ColorConstant.MAIN_COLOR_NOR);
        mReasonPicker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(String option) {
                mProducts.get(position).setReason(option);
                adapter.notifyItemChanged(position);
            }
        });
        mReasonPicker.show();
    }

    //选择理由
    @Subcriber(tag = ChangeBackListAdapter.REASON)
    public void chooseReason(int position) {
        this.position = position;
        initFamilyPicker();
    }

    @OnClick({R.id.fl_address})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_address://选择地址
                Bundle bundle = new Bundle();
                bundle.putBoolean(AddressListActivity.BUNDLE_IS_CLOSE, true);
                startActivity(AddressListActivity.class, bundle);
                break;
        }
    }


    @Subcriber(tag = AddressListActivity.EVENT_SELECT_ADDRESS)
    public void eventSelectAddress(Address address) {
        ll_receiving_address.setVisibility(View.VISIBLE);
        ll_no_receiving_address.setVisibility(View.GONE);
        setAddress(address);
    }
}
