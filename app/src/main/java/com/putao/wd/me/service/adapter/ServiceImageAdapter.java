package com.putao.wd.me.service.adapter;

import android.content.Context;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.os.Handler;
import android.widget.RelativeLayout;

import com.putao.wd.R;
import com.putao.wd.model.ServiceBackImage;

import java.util.List;

/**
 * 退货图片
 * <p/>
 * Created by yanghx on 2015/12/30.
 */
public class ServiceImageAdapter extends BaseAdapter {

    private String[] mItems;
    private ArrayAdapter<String> mStringArrayAdapter;
    private Context mContext;
    private  List<ServiceBackImage> bitmaps;

    public ServiceImageAdapter(Context context, List<ServiceBackImage> bitmaps) {
        mContext = context;
        this.bitmaps = bitmaps;
    }


    private int selectedPosition = -1;
    private boolean shape;

    public boolean isShape() {
        return shape;
    }

    public void setShape(boolean shape) {
        this.shape = shape;
    }

    public int getCount() {
        if (bitmaps.size() == 3) {
            return 3;
        }
        return (bitmaps.size() + 1);
    }

    public Object getItem(int arg0) {
        return null;
    }

    public long getItemId(int arg0) {
        return 0;
    }

    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    ImageHolder holder = null;

    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.activity_service_image_item, null);
            holder = new ImageHolder();
            holder.btn_image_add = (ImageButton) convertView.findViewById(R.id.btn_image_add);
            holder.btn_image_remove = (Button) convertView.findViewById(R.id.btn_image_remove);
            holder.rl_image = (RelativeLayout) convertView.findViewById(R.id.rl_image);
            holder.iv_no_image = (ImageView) convertView.findViewById(R.id.iv_no_image);
            convertView.setTag(holder);
        } else {
            holder = (ImageHolder) convertView.getTag();
        }

        if (position == bitmaps.size()) {
            holder.rl_image.setVisibility(View.GONE);
            holder.iv_no_image.setVisibility(View.VISIBLE);
            if (position == 5) {
                holder.iv_no_image.setVisibility(View.GONE);
            }
        } else {
            holder.rl_image.setVisibility(View.VISIBLE);
            holder.iv_no_image.setVisibility(View.GONE);
            holder.btn_image_add.setImageBitmap(bitmaps.get(position).getBitmap());
            holder.btn_image_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bitmaps.remove(position);
                    notifyDataSetChanged();
                }
            });
        }

        return convertView;
    }


    static class ImageHolder {
        ImageButton btn_image_add;
        Button btn_image_remove;
        RelativeLayout rl_image;
        ImageView iv_no_image;
    }


    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    notifyDataSetChanged();
                    break;
            }
            super.handleMessage(msg);
        }
    };
}
