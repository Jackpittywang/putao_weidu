package com.putao.wd.start.action;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.api.StartApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.model.ActionDetail;
import com.putao.wd.start.comment.CommentActivity;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.BasicWebView;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.select.TitleBar;
import com.sunnybear.library.view.select.TitleItem;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * 活动详情
 * Created by wango on 2015/12/4.
 */
public class ActionsDetailActivity extends PTWDActivity<GlobalApplication> implements View.OnClickListener, TitleBar.TitleItemSelectedListener {
    @Bind(R.id.iv_actionssdetail_header)
    ImageDraweeView iv_actionssdetail_header;
    @Bind(R.id.tv_actionsdetail_status)
    TextView tv_actionsdetail_status;
    @Bind(R.id.tv_actionsdetail_title)
    TextView tv_actionsdetail_title;
    @Bind(R.id.tv_actionsdetail_resume)
    TextView tv_actionsdetail_resume;
    @Bind(R.id.wb_html_content)
    BasicWebView wb_html_content;
    @Bind(R.id.ll_comment)
    LinearLayout ll_comment;
    @Bind(R.id.stickyHeaderLayout_sticky)
    TitleBar ll_title;

    private Bundle bundle;
    private String action_id;
    //H5请求URL:http://static.uzu.wang/weidu_event/view/active_info.html?id=1&device=m&nav=0
    //id和nav值分别对应活动ID和活动类型
    private final String BASE_HTML_URL = "http://static.uzu.wang/weidu_event/view/active_info.html?id=";
    private final String HTML_Mid = "&device=m&nav=";
    private int action_type;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_actions_detail;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();

        bundle = getIntent().getExtras();
        action_id = bundle.getString("action_id");
        networkRequest(StartApi.getActionDetail(action_id), new SimpleFastJsonCallback<ActionDetail>(ActionDetail.class, loading) {
            @Override
            public void onSuccess(String url, ActionDetail result) {
                Logger.i("动详情列表请求成功" + result.toString());
                iv_actionssdetail_header.setImageURL(result.getBanner_url());
                tv_actionsdetail_status.setText(result.getStatus());
                tv_actionsdetail_title.setText(result.getLabel());
                tv_actionsdetail_resume.setText(result.getTitle());
                loadHtml(action_id, action_type);
                loading.dismiss();
            }
        });
        ll_title.setTitleItemSelectedListener(this);
    }

    /**
     * @param action_id 活动ID
     * @param action_type 活动分类
     */
    private void loadHtml(String action_id, int action_type) {
        String url = BASE_HTML_URL + action_id + HTML_Mid + action_type;
        wb_html_content.loadUrl(url);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @OnClick({R.id.ll_comment})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_comment:
                startActivity(CommentActivity.class, bundle);
                break;
        }
    }

    @Override
    public void onTitleItemSelected(TitleItem item, int position) {
        switch (item.getId()) {
            case R.id.ll_all://活动说明
                action_type = 0;
                loadHtml(action_id, action_type);
                break;
            case R.id.ll_ing://活动现场
                action_type = 1;
                loadHtml("2", 0);
                break;
        }
    }
}
