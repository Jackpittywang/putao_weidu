package com.putao.wd.pt_companion;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.ServiceMenu;
import com.sunnybear.library.util.StringUtils;

import org.json.JSONException;

import java.util.ArrayList;

public abstract class PopMenus {
    private ArrayList<ServiceMenu> serviceMenus;
    private Context context;
    private PopupWindow popupWindow;
    private LinearLayout listView;
    private int width, height;
    private View containerView;

    public PopMenus(Context context, ArrayList<ServiceMenu> serviceMenus, int _width, int _height) {
        this.context = context;
        this.serviceMenus = serviceMenus;
        this.width = _width;
        this.height = _height;
        containerView = LayoutInflater.from(context).inflate(R.layout.popmenus, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f);
        containerView.setLayoutParams(lp);
        listView = (LinearLayout) containerView.findViewById(R.id.layout_subcustommenu);
        try {
            setSubMenu();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        listView.setBackgroundResource(R.color.black);
//        listView.setFocusableInTouchMode(true);
        listView.setFocusable(true);

        popupWindow = new PopupWindow(containerView, width == 0 ? LayoutParams.WRAP_CONTENT : width, height == 0 ? LayoutParams.WRAP_CONTENT : height);
        popupWindow.setAnimationStyle(R.style.smart_in_anim_style);
    }

    public void showAsDropDown(View parent) {
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.showAsDropDown(parent);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.update();

        popupWindow.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
            }
        });
    }

    public void showAtLocation(View parent) {
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        containerView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        int[] location = new int[2];
        parent.getLocationOnScreen(location);
        int x = location[0] - 5;
        int y = parent.getHeight() - (parent.getHeight() / 3);
        // Utils.toast(context, y +""); //location[1] - popupHeight -
        // parent.getHeight()
        popupWindow.showAtLocation(parent, Gravity.LEFT | Gravity.BOTTOM, x, y);

        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.update();

        popupWindow.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
            }
        });
    }

    public void dismiss() {
        popupWindow.dismiss();
    }

    private int i;

    void setSubMenu() throws JSONException {
        listView.removeAllViews();
        i = 0;
        for (final ServiceMenu serviceMenu : serviceMenus) {
            LinearLayout layoutItem = (LinearLayout) ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.pomenu_menuitem, null);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f);
            containerView.setLayoutParams(lp);
            layoutItem.setFocusable(true);
            TextView tv_funbtntitle = (TextView) layoutItem.findViewById(R.id.pop_item_textView);
            View pop_item_line = layoutItem.findViewById(R.id.pop_item_line);
            if (i == serviceMenus.size() - 1) {
                pop_item_line.setVisibility(View.GONE);
            }
            tv_funbtntitle.setText(StringUtils.getCutStringByByteCount(serviceMenu.getName(), 8, "..."));
            layoutItem.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    secondMenuClick(serviceMenu, i);
                    dismiss();
                }
            });
            listView.addView(layoutItem);
            i++;
        }
        listView.setVisibility(View.VISIBLE);
    }

    abstract void secondMenuClick(ServiceMenu serviceMenu, int position);
}
