package com.putao.wd.pt_companion;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.account.AccountConstants;
import com.putao.wd.account.AccountHelper;
import com.putao.wd.album.activity.PhotoAlbumActivity;
import com.putao.wd.album.model.ImageInfo;
import com.putao.wd.api.ExploreApi;
import com.putao.wd.api.UploadApi;
import com.putao.wd.api.UserApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.base.SelectPopupWindow;
import com.putao.wd.model.ArticleDetailComment;
import com.putao.wd.model.ArticleDetailCommentList;
import com.putao.wd.pt_companion.adapter.ArticleCommentAdapter;
import com.putao.wd.start.action.ActionsDetailActivity;
import com.putao.wd.start.comment.EmojiFragment;
import com.putao.wd.start.comment.adapter.CommentAdapter;
import com.putao.wd.start.comment.adapter.EmojiFragmentAdapter;
import com.putao.wd.util.BottomPanelUtil;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
import com.sunnybear.library.controller.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.JSONObjectCallback;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.model.http.callback.UploadCallback;
import com.sunnybear.library.util.FileUtils;
import com.sunnybear.library.util.ImageUtils;
import com.sunnybear.library.util.KeyboardUtils;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.StringUtils;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.PullToRefreshLayout;
import com.sunnybear.library.view.emoji.Emoji;
import com.sunnybear.library.view.emoji.EmojiEditText;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;
import com.sunnybear.library.view.recycler.listener.OnItemClickListener;
import com.sunnybear.library.view.recycler.listener.OnItemLongClickListener;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/4/11.
 */
public class CommentForArticleActivity extends PTWDActivity implements View.OnClickListener {
    public static final String EVENT_COUNT_ARTICLEID = "event_count_articleid";
    public static final String EVENT_COUNT_SERVICEID = "event_count_serviceid";
    public static final String EVENT_COUNT_COMMENT = "event_count_comment";
    public static final String EVENT_COUNT_COOL = "event_count_cool";
    public static final String EVENT_ADD_CREAT_COMMENT = "event_add_creat_comment";
    public static final String EVENT_DELETE_CREAT_COMMENT = "event_delete_creat_comment";

    @Bind(R.id.rl_main)
    RelativeLayout rl_main;
    @Bind(R.id.ptl_refresh)
    PullToRefreshLayout ptl_refresh;
    @Bind(R.id.rv_content)
    LoadMoreRecyclerView rv_content;
    @Bind(R.id.ll_comment_edit)
    LinearLayout ll_comment_edit;

    @Bind(R.id.iv_upload_pic)
    ImageDraweeView iv_upload_pic;
    @Bind(R.id.vp_emojis)
    ViewPager vp_emojis;
    @Bind(R.id.et_msg)
    EmojiEditText et_msg;


    private SelectPopupWindow mSelectPopupWindow;
    private ArticleCommentAdapter adapter;
    private Map<String, String> emojiMap;
    private List<Emoji> emojis;
    private String wd_mid, sid;
    private boolean isShowEmoji = false;
    private int mPosition;
    private int mSuperPosition;
    private boolean isReply;
    private boolean hasComment;
    private int page = 1;
    private int mMinLenght;
    public final static String COOL = "CommentCool";//是否赞过
    public final static String POSITION = "position";

    private boolean is_pic = false;// 是否可以发表图片
    private boolean is_comment = false;// 是否可以评论
    private boolean is_becommented = false;//是否可以对评论进行回复

    @Override
    protected int getLayoutId() {
        return R.layout.activity_comment_for_activities;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        mMinLenght = 0;
        adapter = new ArticleCommentAdapter(this, null);
        rv_content.setAdapter(adapter);
        wd_mid = args.getString(ActionsDetailActivity.BUNDLE_ACTION_ID);
        mSuperPosition = args.getInt(POSITION);


        Bundle data = getIntent().getExtras();
        if (data.containsKey(EVENT_COUNT_ARTICLEID)) {
            wd_mid = data.getString(EVENT_COUNT_ARTICLEID);
        }
        if (data.containsKey(AccountConstants.Bundle.BUNDLE_SERVICE_ID)) {
            sid = data.getString(AccountConstants.Bundle.BUNDLE_SERVICE_ID);
        }

        refreshView();
        refreshCommentList();
        addListener();
        emojiMap = mApp.getEmojis();
        emojis = new ArrayList<>();
        if (emojiMap == null)
            emojiMap = new HashMap<>();
        for (Map.Entry<String, String> entry : emojiMap.entrySet()) {
            emojis.add(new Emoji(entry.getKey(), entry.getValue()));
        }
        vp_emojis.setAdapter(new EmojiFragmentAdapter(getSupportFragmentManager(), emojis, 20));

        mSelectPopupWindow = new SelectPopupWindow(mContext) {
            @Override
            public void onFirstClick(View v) {
                final ArticleDetailComment item = adapter.getItem(mPosition);
                if (AccountHelper.getCurrentUid().equals(item.getUid())) {
                    //删除评论
                    String comment_id = item.getComment_id();
                    networkRequest(ExploreApi.deleteArticleComment(comment_id), new SimpleFastJsonCallback<String>(String.class, loading) {

                        @Override
                        public void onSuccess(String url, String result) {
//                            adapter.delete(item);
                            refreshCommentList();
                            EventBusHelper.post(mSuperPosition, EVENT_DELETE_CREAT_COMMENT);
                        }
                    });
                } else {
                    ToastUtils.showToastShort(mContext, "感谢您的举报，我们会尽快处理");
                }
            }

            @Override
            public void onSecondClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        reply();
                    }
                }, 200);
            }
        };
        mSelectPopupWindow.tv_second.setVisibility(View.GONE);
        mSelectPopupWindow.tv_second.setText("回复");

    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    /**
     * 刷新方法
     */
    private void refresh() {
        ptl_refresh.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshCommentList();
            }
        });
    }

    /**
     * 添加监听器
     */
    private void addListener() {
        refresh();

        rv_content.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                getCommentList();
            }
        });
        rv_content.setOnItemClickListener(new OnItemClickListener<ArticleDetailComment>() {
            @Override
            public void onItemClick(ArticleDetailComment comment, int position) {
                mPosition = position;
                //TODO
                Intent intent = new Intent(mContext, ArticlesDetailActivity.class);
                intent.putExtra("wd_mid", wd_mid);
                intent.putExtra("sid", sid);
                intent.putExtra("pcid", comment.getComment_id());
                startActivity(intent);

            }
        });

        rv_content.setOnItemLongClickListener(new OnItemLongClickListener<ArticleDetailComment>() {
            @Override
            public void onItemLongClick(ArticleDetailComment comment, int position) {
                mPosition = adapter.getItems().indexOf(comment);
                if (AccountHelper.getCurrentUid().equals(comment.getUid())) {
                    mSelectPopupWindow.tv_first.setText("删除");
                    mSelectPopupWindow.tv_first.setTextColor(0xff6666CC);
                } else {
                    mSelectPopupWindow.tv_first.setText("举报");
                    mSelectPopupWindow.tv_first.setTextColor(0xffcc0000);
                }
                mSelectPopupWindow.show(rl_main);
                isShowEmoji = false;//关闭表情包
                vp_emojis.setVisibility(View.GONE);
                KeyboardUtils.closeKeyboard(mContext, et_msg);//关闭软键盘
            }
        });

        et_msg.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL && mMinLenght > et_msg.length()) {
                    et_msg.setText("");
                    isReply = false;
                    mMinLenght = 0;
                    return true;
                }
                return false;
            }
        });
    }

    @OnClick({R.id.tv_emojis, R.id.tv_send, R.id.et_msg, R.id.iv_upload_pic})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_emojis://点击表情栏
                KeyboardUtils.closeKeyboard(mContext, et_msg);
                isShowEmoji = isShowEmoji ? false : true;
                vp_emojis.setVisibility(isShowEmoji ? View.VISIBLE : View.GONE);
                break;
            case R.id.tv_send://点击发送
                sendComment(hasPic);
                break;
            case R.id.et_msg://点击文本输入框
                isShowEmoji = false;
                vp_emojis.setVisibility(View.GONE);
                break;
            case R.id.iv_upload_pic:
                KeyboardUtils.closeKeyboard(mContext, et_msg);//关闭软键盘
                if (!hasPic) {
                    //选择图片
                    BottomPanelUtil.showBottomFunctionPanel(mContext, new String[]{"相机", "从手机相册选择"}, new BottomPanelUtil.FunctionPanelCallBack[]{new BottomPanelUtil.FunctionPanelCallBack() {
                        @Override
                        public void doFunction() {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, 0x900);
                        }
                    }, new BottomPanelUtil.FunctionPanelCallBack() {
                        @Override
                        public void doFunction() {
                            Intent intent = new Intent(mContext, PhotoAlbumActivity.class);
                            intent.putExtra("MAX_COUNT", 1);
                            startActivity(intent);
                        }
                    }}, null);
                } else {
                    //选择图片
                    BottomPanelUtil.showBottomFunctionPanel(mContext, new String[]{"重新拍照", "重新选取", "删除"}, new BottomPanelUtil.FunctionPanelCallBack[]{new BottomPanelUtil.FunctionPanelCallBack() {
                        @Override
                        public void doFunction() {
                            bitmap = null;
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, 0x900);
                        }
                    }, new BottomPanelUtil.FunctionPanelCallBack() {
                        @Override
                        public void doFunction() {
                            Intent intent = new Intent(mContext, PhotoAlbumActivity.class);
                            intent.putExtra("MAX_COUNT", 1);
                            startActivity(intent);
                        }
                    }, new BottomPanelUtil.FunctionPanelCallBack() {
                        @Override
                        public void doFunction() {
                            bitmap = null;
                            iv_upload_pic.setDefaultImage(R.drawable.btn_30_p_selector);
//                            iv_upload_pic.setImageURL(Uri.parse("res://putao/" + R.drawable.btn_30_p_selector).toString());
                            hasPic = false;
                        }
                    }}, null);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 0x900) {
            Bundle bundle = data.getExtras();
            bitmap = (Bitmap) bundle.get("data");
            iv_upload_pic.setDefaultImage(bitmap);
            String filePath = GlobalApplication.sdCardPath + File.separator + "upload.jpg";
            ImageUtils.bitmapOutSdCard(bitmap, filePath);
            uploadPic = filePath;
            hasPic = true;
            iv_upload_pic.setImageURI(Uri.parse("file://putao/" + filePath));
        }
    }

    private void resetMsg() {
        isReply = false;
        et_msg.setText("");
        mMinLenght = 0;
        vp_emojis.setVisibility(View.GONE);
    }


    private void refreshView() {
        if (is_comment)
            ll_comment_edit.setVisibility(View.VISIBLE);
        else
            ll_comment_edit.setVisibility(View.GONE);
        if (is_pic)
            iv_upload_pic.setVisibility(View.VISIBLE);
        else
            iv_upload_pic.setVisibility(View.GONE);
        if (is_becommented) ;
    }

    /**
     * 刷新评论列表
     */
    private void refreshCommentList() {
        page = 1;
        rv_content.reset();
        networkRequest(ExploreApi.getArticleCommentList(wd_mid, String.valueOf(page), sid), new SimpleFastJsonCallback<ArticleDetailCommentList>(ArticleDetailCommentList.class, loading) {
                    @Override
                    public void onSuccess(String url, ArticleDetailCommentList result) {
                        if (result != null) {
                            is_pic = result.is_pic();
                            is_comment = result.is_comment();
                            is_becommented = result.is_becommented();
                            refreshView();

                            List<ArticleDetailComment> comments = result.getComment_lists();
                            if (comments != null && comments.size() > 0) {
                        //        checkLiked(comments);
                                adapter.replaceAll(comments);
                                hasComment = true;
                                rv_content.loadMoreComplete();
                                page++;
                            } else {
                                adapter.clear();
                                rv_content.noMoreLoading();
                                hasComment = false;
                            }
                        }
                        loading.dismiss();
                        ptl_refresh.refreshComplete();
                    }
                }

        );
    }

    /**
     * 获取评论列表
     */

    private void getCommentList() {
        networkRequest(ExploreApi.getArticleCommentList(wd_mid, String.valueOf(page), sid), new SimpleFastJsonCallback<ArticleDetailCommentList>(ArticleDetailCommentList.class, loading) {
            @Override
            public void onSuccess(String url, ArticleDetailCommentList result) {
                Logger.i("活动评论列表请求成功");
                Logger.i(url);
                List<ArticleDetailComment> comments = result.getComment_lists();
                if (comments != null && comments.size() > 0) {
           //         checkLiked(comments);
                    adapter.addAll(comments);
                    hasComment = true;
                    rv_content.loadMoreComplete();
                    page++;
                } else {
                    rv_content.noMoreLoading();
                    hasComment = false;
                }
                loading.dismiss();
            }
        });
    }

    private void checkLiked(List<ArticleDetailComment> comments) {
        int i = 0;
        for (ArticleDetailComment comment : comments) {
            if (Boolean.parseBoolean(mDiskFileCacheHelper.getAsString(COOL + comment.getComment_id())))
                comments.get(i).setIs_like(true);
            i++;
        }
    }

    @Subcriber(tag = CommentAdapter.EVENT_COMMENT_EDIT)
    public void eventClickComment(int currPosition) {
        mPosition = currPosition;
        //
        ArticleDetailComment comment = adapter.getItem(currPosition);
        Intent intent = new Intent(mContext, ArticlesDetailActivity.class);
        intent.putExtra("wd_mid", wd_mid);
        intent.putExtra("sid", sid);
        intent.putExtra("pcid", comment.getUid());
        startActivity(intent);
    }

    private void reply() {
        ArticleDetailComment comment = adapter.getItem(mPosition);
        String username = comment.getUid() + ": ";
        SpannableString ss = new SpannableString("回复 " + username);
        ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.text_color_gray)), 0, username.length() + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mMinLenght = ss.length();
        et_msg.setText(ss);
        et_msg.setSelection(mMinLenght);
        et_msg.setFocusableInTouchMode(true);
        et_msg.requestFocus();
        InputMethodManager inputManager =
                (InputMethodManager) et_msg.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(et_msg, 0);
        isReply = true;
    }

    //点赞提交
    @Subcriber(tag = CommentAdapter.EVENT_COMMIT_COOL)
    public void eventClickCool(final int currPosition) {
        final ArticleDetailComment comment = adapter.getItem(currPosition);
        networkRequest(ExploreApi.addArticleLike(wd_mid, comment.getComment_id(), sid),
                new SimpleFastJsonCallback<String>(String.class, loading) {
                    @Override
                    public void onSuccess(String url, String result) {
//                        adapter.notifyItemChanged(currPosition);
//                        mDiskFileCacheHelper.put(COOL + comment.getComment_id(), "true");
                        EventBusHelper.post(true, EVENT_COUNT_COOL);
                    }
                });
    }

    //二级评论页面的点赞提交
    @Subcriber(tag = ArticlesDetailActivity.EVENT_COUNT_COOL)
    public void eventCountCool(int superPosition){
        refreshCommentList();
    }

    @Subcriber(tag = EmojiFragment.EVENT_CLICK_EMOJI)
    public void eventClickEmoji(Emoji emoji) {
        et_msg.append(emoji.getName());
    }

    @Subcriber(tag = EmojiFragment.EVENT_DELETE_EMOJI)
    public void eventDeleteEmoji(Emoji emoji) {
        et_msg.delete();
    }

    @Subcriber(tag = ArticlesDetailActivity.EVENT_DELETE_CREATE_COMMENT)
    private void removeData(int tag) {
        refreshCommentList();
    }

    //图片选择后的处理
    @Subcriber(tag = AccountConstants.EventBus.EVENT_ALBUM_SELECT)
    public void eventSelectPic(List<ImageInfo> selectPhotos) {
        if (selectPhotos != null && selectPhotos.size() > 0 && selectPhotos.get(0) != null) {
            String picStr = selectPhotos.get(0)._DATA;
            if (!StringUtils.isEmpty(selectPhotos.get(0).THUMB_DATA))
                iv_upload_pic.setImageURI(Uri.parse("file://putao/" + selectPhotos.get(0).THUMB_DATA));
            else
                iv_upload_pic.setImageURI(Uri.parse("file://putao/" + selectPhotos.get(0)._DATA));

            if (!StringUtils.isEmpty(picStr)) {
                hasPic = true;
                uploadPic = picStr;
            } else {
                hasPic = false;
                uploadPic = "";
            }
        }

    }

    //=====================图片上传相关===========================
    private String uploadPic;
    private boolean hasPic = false;//评论是否带图片
    private String uploadToken;//上传token
    private File uploadFile;//上传文件
    private String sha1;//上传文件sha1
    private Bitmap bitmap;//bitmap

    /**
     * 校检sha1
     *
     * @param uploadFilePath 上传文件路径
     */
    private void checkSha1(String uploadFilePath) {
        uploadFile = new File(uploadFilePath);
        sha1 = FileUtils.getSHA1ByFile(uploadFile);
        networkRequest(UploadApi.checkSha1(sha1), new JSONObjectCallback() {
            @Override
            public void onSuccess(String url, JSONObject result) {
                String hash = result.getString("hash");
                if (StringUtils.isEmpty(hash))
                    getUploadToken();
                else
                    sendCommentMsg(hash);
            }

            @Override
            public void onCacheSuccess(String url, JSONObject result) {

            }

            @Override
            public void onFailure(String url, int statusCode, String msg) {

            }
        });
    }

    /**
     * 获得上传参数
     */
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


    /**
     * 上传文件
     */
    private void uploadFile() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                UploadApi.uploadFile(uploadToken, sha1, uploadFile, new UploadCallback() {
                    @Override
                    public void onSuccess(JSONObject result) {
                        Logger.d(result.toJSONString());
                        Bundle bundle = new Bundle();
                        bundle.putString("ext", result.getString("ext"));
                        bundle.putString("filename", result.getString("filename"));
                        bundle.putString("hash", result.getString("hash"));
                        mHandler.sendMessage(Message.obtain(mHandler, 0x01, bundle));
                    }
                });
            }
        }).start();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = (Bundle) msg.obj;
            //发送评论
            sendCommentMsg(bundle.getString("filename") + "." + bundle.getString("ext"));
        }
    };


    private void sendComment(boolean hasPic) {
        if (hasPic)
            checkSha1(uploadPic);
        else
            sendCommentMsg(null);
    }


    private void sendCommentMsg(@Nullable String picName) {
        String msg = et_msg.getText().toString();
        if (msg.trim().isEmpty()) {
            ToastUtils.showToastShort(mContext, "评论不能为空");
            return;
        }
        String pics = "";
        if (picName != null)
            pics = picName;

        networkRequest(ExploreApi.addArticleComment(wd_mid, msg, sid, pics),
                new SimpleFastJsonCallback<String>(String.class, loading) {
                    @Override
                    public void onSuccess(String url, String result) {
                        Logger.i("评论与回复提交成功");
                        resetMsg();
                        refreshCommentList();
                        EventBusHelper.post(mSuperPosition, EVENT_ADD_CREAT_COMMENT);

                        hasPic = false;
                        iv_upload_pic.setDefaultImage(R.drawable.btn_30_p_selector);
                    }

                    @Override
                    public void onFailure(String url, int statusCode, String msg) {
                        super.onFailure(url, statusCode, msg);
                        ToastUtils.showToastShort(mContext, "评论发送失败，请检查您的网络");
                    }
                });
    }
}
