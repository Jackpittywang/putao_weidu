package com.putao.wd.pt_companion;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.api.CompanionApi;
import com.putao.wd.api.StartApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.model.ActionDetail;
import com.putao.wd.model.ArticleDetail;
import com.putao.wd.model.RegUser;
import com.putao.wd.share.SharePopupWindow;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.ListUtils;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.BasicWebView;
import com.sunnybear.library.view.SwitchButton;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.select.TitleBar;
import com.sunnybear.library.view.select.TitleItem;
import com.sunnybear.library.view.sticky.StickyHeaderLayout;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/4/11.
 */
public class ArticlesDetailActivity extends PTWDActivity<GlobalApplication> implements View.OnClickListener, TitleBar.OnTitleItemSelectedListener  {

    @Bind(R.id.iv_author_icon)
    ImageDraweeView iv_author_icon;
    @Bind(R.id.tv_author_name)
    TextView tv_author_name;
    @Bind(R.id.tv_author_time)
    TextView tv_author_time;
    @Bind(R.id.sticky_layout)
    StickyHeaderLayout sticky_layout;
    @Bind(R.id.iv_articlesdetail_header)
    ImageDraweeView iv_articlesdetail_header;
    @Bind(R.id.tv_articlesdetail_resume)
    TextView tv_articlesdetail_resume;
    @Bind(R.id.rv_articlesdetail_applyusers)
    BasicRecyclerView rv_articlesdetail_applyusers;
    @Bind(R.id.ll_praise_count)
    LinearLayout ll_praise_count;
    @Bind(R.id.tv_praise_count)
    TextView tv_praise_count;
    @Bind(R.id.tv_amount_comment)
    TextView tv_amount_comment;
    @Bind(R.id.rv_others_comment)
    BasicRecyclerView rv_others_comment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_article_detail;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();

//        networkRequest(StartApi.getActionDetail(action_id, sb_cool_icon.getState()), new SimpleFastJsonCallback<ActionDetail>(ActionDetail.class, loading) {
//            @Override
//            public void onSuccess(String url, ActionDetail result) {
//                actionDetail = result;
//                mIsUserLike = result.is_user_like();
//                ll_cool.setClickable(true);//释放点赞按钮点击
//                sb_cool_icon.setState(mIsUserLike); //设置是不是被赞的按钮样式
//                iv_actionssdetail_header.setImageURL(result.getBanner_url());
//                tv_actionsdetail_status.setText(result.getStatus());
//                tv_actionsdetail_title.setText(result.getLabel());
//                tv_actionsdetail_resume.setText(result.getTitle());
//                tv_count_cool.setText(result.getCount_cool() != 0 ? result.getCount_cool() + "" : "赞");
//                tv_count_comment.setText(result.getCount_comment() != 0 ? result.getCount_comment() + "" : "评论");
//                tv_personinfo_address.setText(result.getLocation());
//                List<RegUser> reg_user = result.getReg_user();
//                if (reg_user.size() > 4)
//                    reg_user = ListUtils.cutOutList(reg_user, 0, 4);
//                Logger.i("reg_user = " + reg_user.toString());
//                adapter.replaceAll(reg_user);
//                tv_actionsdetail_applycount.setText(result.getRegistration_number() + "");
//                loadHtml(action_id, action_type);
//                setJoinStyle(actionDetail);
//                loading.dismiss();
//            }
//        });

        //  TODO 文章id
        networkRequest(CompanionApi.getCompanyArticle("2"), new SimpleFastJsonCallback<ArticleDetail>(ArticleDetail.class,loading) {
            @Override
            public void onSuccess(String url, ArticleDetail result) {
                iv_author_icon.setImageURL(result.getArticle_icon());//TODO 用户图片的获取
                tv_author_name.setText("需要值");
                tv_author_time.setText("tv_author_time");

                iv_articlesdetail_header.setImageURL(result.getArticle_icon());
                tv_articlesdetail_resume.setText(result.getArticle_contents());

                ll_praise_count.setVisibility(result.getIs_like() == 0 ? View.GONE:View.VISIBLE);
                if(result.getIs_like() != 0)
                tv_praise_count.setText(String.valueOf(result.getLike_count()));
            }
        });

    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onTitleItemSelected(TitleItem item, int position) {

    }

    @Override
    public void onLeftAction() {
        super.onLeftAction();
        finish();
    }

    /**
     * 分享  TODO
     */
    @Override
    public void onRightAction() {
        super.onRightAction();
    }
}
