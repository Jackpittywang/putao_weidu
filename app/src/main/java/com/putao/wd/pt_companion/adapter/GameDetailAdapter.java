package com.putao.wd.pt_companion.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.putao.wd.R;
import com.putao.wd.account.AccountConstants;
import com.putao.wd.account.AccountHelper;
import com.putao.wd.api.CompanionApi;
import com.putao.wd.model.PicClickResult;
import com.putao.wd.model.PicList;
import com.putao.wd.model.ServiceMessageContent;
import com.putao.wd.model.ServiceMessageList;
import com.putao.wd.pt_companion.ArticleDetailForActivitiesActivity;
import com.putao.wd.start.browse.PictrueBrowseActivity;
import com.putao.wd.util.ImageLoaderUtil;
import com.putao.wd.util.NetworkUtil;
import com.putao.wd.util.UploadFileCallback;
import com.putao.wd.util.UploadLoader;
import com.putao.wd.webview.BaseWebViewActivity;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.DateUtils;
import com.sunnybear.library.util.ImageUtils;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.emoji.EmojiTextView;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.adapter.BasicAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/1/13.
 */
public class GameDetailAdapter extends BasicAdapter<ServiceMessageList, BasicViewHolder> {
    private static final int TYPE_LOCAL = 1;
    private static final int TYPE_REPLY = 2;
    private static final int TYPE_ARTICLE = 3;
    private Context mContent;
    private String mServiceId, imageIcon;
    public final static String UPLOAD_TEXT_TYPE = "upload_text";
    public final static String UPLOAD_IMAGE_TYPE = "upload_image";
    private ArrayList<String> mDates;
    private HashMap<Integer, Integer> mDatesMap;
    private HashMap<Integer, Integer> mSendStateMap;

    public GameDetailAdapter(Context context, List<ServiceMessageList> serviceMessageList) {
        super(context, serviceMessageList);
        mContent = context;
        mDates = new ArrayList<>();
        mDatesMap = new HashMap<>();
        mSendStateMap = new HashMap<>();
    }

    @Override
    public void replaceAll(List<ServiceMessageList> serviceMessageLists) {
        super.replaceAll(serviceMessageLists);
        mDatesMap.clear();
        mSendStateMap.clear();
        int i = 0;
        for (ServiceMessageList serviceMessage : serviceMessageLists) {
            putDate(serviceMessage.getRelease_time(), i);
            mSendStateMap.put(i, serviceMessage.getSend_state());
            i++;
        }
    }

    @Override
    public void add(ServiceMessageList serviceMessageList) {
        super.add(serviceMessageList);
        putDate(serviceMessageList.getRelease_time(), getItemCount() - 1);
        mSendStateMap.put(getItemCount() - 1, serviceMessageList.getSend_state());
    }

    @Override
    public void addAll(List<ServiceMessageList> serviceMessageLists) {
        super.addAll(serviceMessageLists);
        int i = getItemCount() - serviceMessageLists.size();
        for (ServiceMessageList serviceMessage : serviceMessageLists) {
            putDate(serviceMessage.getRelease_time(), i);
            mSendStateMap.put(i, serviceMessage.getSend_state());
            i++;
        }
    }

    private void putDate(int release_time, int position) {
        String date = DateUtils.timeCalculate(release_time);
        if (mDates.contains(date)) {
            mDatesMap.put(position, 2);
        } else {
            mDates.add(date);
            mDatesMap.put(position, 1);
        }
    }

    @Override
    public int getLayoutId(int viewType) {
        switch (viewType) {
            case TYPE_LOCAL:
                return R.layout.activity_question_item_ask;
            case TYPE_REPLY:
                return R.layout.activity_question_item_answer;
            case TYPE_ARTICLE:
                return R.layout.activity_game_detail_list_article_item;
            default:
                return -1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        ServiceMessageList question = getItem(position);
        //判断question状态
        switch (question.getType()) {
            case "text":
                return TYPE_REPLY;
            case "image":
                return TYPE_REPLY;
            case "reply":
                return TYPE_REPLY;
            case "article":
                return TYPE_ARTICLE;
            default:
                return TYPE_LOCAL;
        }
    }

    @Override
    public BasicViewHolder getViewHolder(View itemView, int viewType) {
        switch (viewType) {
            case TYPE_LOCAL:
                return new QuestionLocalViewHolder(itemView);
            case TYPE_REPLY:
                return new QuestionReplyViewHolder(itemView);
            case TYPE_ARTICLE:
                return new GameDetailHolder(itemView);
            default:
                return new BasicViewHolder(itemView);
        }
    }

    @Override
    public void onBindItem(BasicViewHolder holder, final ServiceMessageList serviceMessageList, final int position) {

//        String date = DateUtils.secondToDate(serviceMessageList.getRelease_time(), "yyyy-MM-dd HH:mm");
        String date = DateUtils.timeCalculate(serviceMessageList.getRelease_time());
        if (holder instanceof QuestionReplyViewHolder) {
            QuestionReplyViewHolder askViewHolder = (QuestionReplyViewHolder) holder;
            askViewHolder.question_item_answer_icon.setImageURL(imageIcon);
            if (2 == mDatesMap.get(position)) {
                askViewHolder.question_item_ask_time.setVisibility(View.GONE);
            } else {
                askViewHolder.question_item_ask_time.setVisibility(View.VISIBLE);
                askViewHolder.question_item_ask_time.setText("───    " + date + "    ───");
            }
            switch (serviceMessageList.getType()) {
                case "text":
                    askViewHolder.ll_reply.setVisibility(View.GONE);
                    askViewHolder.rl_item_ask_image.setVisibility(View.GONE);
                    askViewHolder.question_item_answer_context.setVisibility(View.VISIBLE);
                    askViewHolder.question_item_answer_context.setText(serviceMessageList.getMessage());
                    break;
                case "image":
                    askViewHolder.ll_reply.setVisibility(View.GONE);
                    askViewHolder.rl_item_ask_image.setVisibility(View.VISIBLE);
                    askViewHolder.question_item_answer_context.setVisibility(View.GONE);
                    String pic = ImageUtils.getImageSizeUrl(serviceMessageList.getImage().getPic(), ImageUtils.ImageSizeURL.SIZE_360x360);
                    askViewHolder.question_item_ask_image.setImageURL(pic);
                    askViewHolder.question_item_ask_image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            initImage(serviceMessageList, serviceMessageList.getImage().getPic());
                        }
                    });
                    break;
                case "reply":
                    askViewHolder.ll_reply.setVisibility(View.VISIBLE);
                    askViewHolder.rl_item_ask_image.setVisibility(View.GONE);
                    askViewHolder.question_item_answer_context.setVisibility(View.GONE);
                    askViewHolder.tv_ask.setText(serviceMessageList.getReply().getQuestion());
                    askViewHolder.tv_reply.setText(serviceMessageList.getReply().getAnswer());
                    break;

            }
        } else if (holder instanceof GameDetailHolder) {
            GameDetailHolder gameDetailHolder = (GameDetailHolder) holder;
            if (2 == mDatesMap.get(position)) {
                gameDetailHolder.tv_time.setVisibility(View.GONE);
            } else {
                gameDetailHolder.tv_time.setVisibility(View.VISIBLE);
                gameDetailHolder.tv_time.setText("───    " + date + "    ───");
            }
            List<ServiceMessageContent> content_lists = serviceMessageList.getContent_lists();
            gameDetailHolder.tv_title.setVisibility(View.GONE);
            gameDetailHolder.tv_content.setVisibility(View.GONE);
            gameDetailHolder.tv_title_multi.setVisibility(View.GONE);
            gameDetailHolder.rl_article1.setVisibility(View.GONE);
            gameDetailHolder.rl_article2.setVisibility(View.GONE);
            gameDetailHolder.rl_article3.setVisibility(View.GONE);
            gameDetailHolder.rl_article4.setVisibility(View.GONE);
            final ServiceMessageContent serviceMessageContent = content_lists.get(0);
            gameDetailHolder.iv_sign.setImageURL(serviceMessageContent.getCover_pic());
            if (content_lists.size() == 1) {
                gameDetailHolder.tv_title.setVisibility(View.VISIBLE);
                gameDetailHolder.tv_content.setVisibility(View.VISIBLE);
                gameDetailHolder.tv_title.setText(serviceMessageContent.getTitle());
                gameDetailHolder.tv_content.setText(serviceMessageContent.getSub_title());
                gameDetailHolder.ll_main.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onSkipPage(serviceMessageList, serviceMessageContent);
                    }
                });
            } else if (content_lists.size() > 1) {
                gameDetailHolder.tv_title_multi.setVisibility(View.VISIBLE);
                gameDetailHolder.tv_title_multi.setText(content_lists.get(0).getTitle());
                gameDetailHolder.rl_gamedetail_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onSkipPage(serviceMessageList, serviceMessageContent);
                    }
                });
                for (int i = 1; i < content_lists.size(); i++) {
                    final ServiceMessageContent serviceMContent = content_lists.get(i);
                    switch (i) {
                        case 1:
                            gameDetailHolder.rl_article1.setVisibility(View.VISIBLE);
                            gameDetailHolder.tv_article1.setText(serviceMContent.getTitle());
                            gameDetailHolder.iv_article1.setImageURL(serviceMContent.getCover_pic());
                            gameDetailHolder.rl_article1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    EventBusHelper.post(serviceMContent, AccountConstants.EventBus.EVENT_GAME_START_ACTIVITY);
                                    onSkipPage(serviceMessageList, serviceMContent);
                                }
                            });
                            break;
                        case 2:
                            gameDetailHolder.rl_article2.setVisibility(View.VISIBLE);
                            gameDetailHolder.tv_article2.setText(serviceMContent.getTitle());
                            gameDetailHolder.iv_article2.setImageURL(serviceMContent.getCover_pic());
                            gameDetailHolder.rl_article2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    EventBusHelper.post(serviceMContent, AccountConstants.EventBus.EVENT_GAME_START_ACTIVITY);
                                    onSkipPage(serviceMessageList, serviceMContent);
                                }
                            });
                            break;
                        case 3:
                            gameDetailHolder.rl_article3.setVisibility(View.VISIBLE);
                            gameDetailHolder.tv_article3.setText(serviceMContent.getTitle());
                            gameDetailHolder.iv_article3.setImageURL(serviceMContent.getCover_pic());
                            gameDetailHolder.rl_article3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    EventBusHelper.post(serviceMContent, AccountConstants.EventBus.EVENT_GAME_START_ACTIVITY);
                                    onSkipPage(serviceMessageList, serviceMContent);
                                }
                            });

                            break;
                        case 4:
                            gameDetailHolder.rl_article4.setVisibility(View.VISIBLE);
                            gameDetailHolder.tv_article4.setText(serviceMContent.getTitle());
                            gameDetailHolder.iv_article4.setImageURL(serviceMContent.getCover_pic());
                            gameDetailHolder.rl_article4.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    EventBusHelper.post(serviceMContent, AccountConstants.EventBus.EVENT_GAME_START_ACTIVITY);
                                    onSkipPage(serviceMessageList, serviceMContent);
                                }
                            });
                            break;
                    }
                }
            }
        } else if (holder instanceof QuestionLocalViewHolder) {
            final QuestionLocalViewHolder questionLocalViewHolder = (QuestionLocalViewHolder) holder;
            if (2 == mDatesMap.get(position)) {
                questionLocalViewHolder.question_item_ask_time.setVisibility(View.GONE);
            } else {
                questionLocalViewHolder.question_item_ask_time.setVisibility(View.VISIBLE);
                questionLocalViewHolder.question_item_ask_time.setText("───    " + date + "    ───");
            }
            questionLocalViewHolder.question_item_ask_icon.setImageURL(ImageUtils.getImageSizeUrl(AccountHelper.getCurrentUserInfo().getHead_img(), ImageUtils.ImageSizeURL.SIZE_120x120));

            switch (serviceMessageList.getType()) {
                case UPLOAD_TEXT_TYPE:
//                    questionLocalViewHolder.question_item_ask_icon_text.setVisibility(View.VISIBLE);
//                    questionLocalViewHolder.question_item_ask_icon.setVisibility(View.GONE);
//                    questionLocalViewHolder.question_item_ask_icon.setImageURL(headPic);
                    questionLocalViewHolder.img_item_retry_text.setVisibility(View.VISIBLE);
                    questionLocalViewHolder.rl_item_ask_image.setVisibility(View.GONE);
                    questionLocalViewHolder.ll_item_ask_text.setVisibility(View.VISIBLE);
                    questionLocalViewHolder.question_item_ask_context.setText(serviceMessageList.getMessage().trim());
                    switch (mSendStateMap.get(position)) {
                        case 0:
                            questionLocalViewHolder.pb_item_ask_text.setVisibility(View.VISIBLE);
                            questionLocalViewHolder.img_item_retry_text.setVisibility(View.GONE);
                            //请求数据，是否评论成功
                            initServiceQuiz(questionLocalViewHolder, serviceMessageList, serviceMessageList.getMessage(), position);
                            questionLocalViewHolder.img_item_retry_text.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    initServiceQuiz(questionLocalViewHolder, serviceMessageList, serviceMessageList.getMessage(), position);
                                }
                            });
                            break;
                        case 1:
                            questionLocalViewHolder.pb_item_ask_text.setVisibility(View.GONE);
                            questionLocalViewHolder.img_item_retry_text.setVisibility(View.GONE);
                            questionLocalViewHolder.img_item_retry_text.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    initServiceQuiz(questionLocalViewHolder, serviceMessageList, serviceMessageList.getMessage(), position);
                                }
                            });
                            break;
                        case 2:
                            questionLocalViewHolder.pb_item_ask_text.setVisibility(View.GONE);
                            questionLocalViewHolder.img_item_retry_text.setVisibility(View.VISIBLE);
                            break;
                    }
                    break;
                case UPLOAD_IMAGE_TYPE:
//                    questionLocalViewHolder.question_item_ask_icon_text.setVisibility(View.GONE);
//                    questionLocalViewHolder.question_item_ask_icon.setVisibility(View.VISIBLE);
                    questionLocalViewHolder.rl_item_ask_image.setVisibility(View.VISIBLE);
                    questionLocalViewHolder.ll_item_ask_text.setVisibility(View.GONE);
                    if (null == serviceMessageList.getImage()) return;
                    final String pic = serviceMessageList.getImage().getPic();
                    final String picUri = pic.substring(pic.indexOf("//") + 2);
                    if (!TextUtils.isEmpty(serviceMessageList.getImage().getThumb()))
                        ImageLoaderUtil.getInstance(context).displayImage(serviceMessageList.getImage().getThumb(), questionLocalViewHolder.question_item_ask_image);
                    else
                        ImageLoaderUtil.getInstance(context).displayImage(pic, questionLocalViewHolder.question_item_ask_image);

                    //图片点击放大
                    questionLocalViewHolder.question_item_ask_image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            initImage(serviceMessageList, pic);
                        }
                    });
                    switch (mSendStateMap.get(position)) {
                        case 0:
                            questionLocalViewHolder.pb_item_ask_image.setVisibility(View.VISIBLE);
                            questionLocalViewHolder.img_item_retry_image.setVisibility(View.GONE);
                            UploadLoader.getInstance().addUploadFile(picUri, new UploadFileCallback() {
                                @Override
                                protected void onFileUploadSuccess(String ext, String filename, String hash, String filePath) {
                                    mSendStateMap.put(position, 1);
                                    serviceMessageList.setSend_state(1);
                                    questionLocalViewHolder.pb_item_ask_image.setVisibility(View.GONE);
                                    questionLocalViewHolder.img_item_retry_image.setVisibility(View.GONE);
                                    EventBusHelper.post(serviceMessageList, AccountConstants.EventBus.EVENT_UPDATE_UPLOAD);
                                }

                                @Override
                                protected void onFileUploadFail(String filePath) {
                                    mSendStateMap.put(position, 2);
                                    serviceMessageList.setSend_state(2);
                                    questionLocalViewHolder.pb_item_ask_image.setVisibility(View.GONE);
                                    questionLocalViewHolder.img_item_retry_image.setVisibility(View.VISIBLE);
                                    EventBusHelper.post(serviceMessageList, AccountConstants.EventBus.EVENT_UPDATE_UPLOAD);
                                }
                            }).execute();
                            break;
                        case 1:
                            questionLocalViewHolder.pb_item_ask_image.setVisibility(View.GONE);
                            questionLocalViewHolder.img_item_retry_image.setVisibility(View.GONE);

                            break;
                        case 2:
                            questionLocalViewHolder.pb_item_ask_image.setVisibility(View.GONE);
                            questionLocalViewHolder.img_item_retry_image.setVisibility(View.VISIBLE);
                            break;
                    }
                    break;
            }
        }
    }

    static class GameDetailHolder extends BasicViewHolder {
        @Bind(R.id.iv_sign)
        ImageDraweeView iv_sign;
        @Bind(R.id.tv_title)
        TextView tv_title;
        @Bind(R.id.tv_content)
        TextView tv_content;
        @Bind(R.id.tv_time)
        TextView tv_time;
        @Bind(R.id.tv_title_multi)
        TextView tv_title_multi;

        @Bind(R.id.rl_article1)
        RelativeLayout rl_article1;
        @Bind(R.id.tv_article1)
        TextView tv_article1;
        @Bind(R.id.iv_article1)
        ImageDraweeView iv_article1;

        @Bind(R.id.rl_article2)
        RelativeLayout rl_article2;
        @Bind(R.id.tv_article2)
        TextView tv_article2;
        @Bind(R.id.iv_article2)
        ImageDraweeView iv_article2;

        @Bind(R.id.rl_article3)
        RelativeLayout rl_article3;
        @Bind(R.id.tv_article3)
        TextView tv_article3;
        @Bind(R.id.iv_article3)
        ImageDraweeView iv_article3;
        @Bind(R.id.rl_article4)
        RelativeLayout rl_article4;
        @Bind(R.id.tv_article4)
        TextView tv_article4;
        @Bind(R.id.iv_article4)
        ImageDraweeView iv_article4;

        @Bind(R.id.ll_main)
        LinearLayout ll_main;
        @Bind(R.id.rl_gamedetail_image)
        RelativeLayout rl_gamedetail_image;

        public GameDetailHolder(View itemView) {
            super(itemView);
        }
    }

    static class QuestionReplyViewHolder extends BasicViewHolder {
        @Bind(R.id.question_item_ask_time)
        TextView question_item_ask_time;

        @Bind(R.id.question_item_answer_context)
        EmojiTextView question_item_answer_context;

        @Bind(R.id.question_item_answer_icon)
        ImageDraweeView question_item_answer_icon;
        @Bind(R.id.tv_ask)
        TextView tv_ask;
        @Bind(R.id.tv_reply)
        TextView tv_reply;
        @Bind(R.id.question_item_ask_image)
        ImageDraweeView question_item_ask_image;
        @Bind(R.id.rl_item_ask_image)
        RelativeLayout rl_item_ask_image;
        @Bind(R.id.ll_reply)
        LinearLayout ll_reply;

        public QuestionReplyViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class QuestionLocalViewHolder extends BasicViewHolder {
        @Bind(R.id.question_item_ask_time)
        TextView question_item_ask_time;

        @Bind(R.id.question_item_ask_context)
        EmojiTextView question_item_ask_context;

        @Bind(R.id.question_item_ask_icon)
        ImageDraweeView question_item_ask_icon;
//        @Bind(R.id.question_item_ask_icon_text)
//        ImageDraweeView question_item_ask_icon_text;

        @Bind(R.id.question_item_ask_image)
        ImageDraweeView question_item_ask_image;

        @Bind(R.id.img_item_retry_text)
        ImageView img_item_retry_text;
        @Bind(R.id.img_item_retry_image)
        ImageView img_item_retry_image;
        @Bind(R.id.rl_item_ask_image)
        RelativeLayout rl_item_ask_image;
        @Bind(R.id.ll_item_ask_text)
        LinearLayout ll_item_ask_text;
        @Bind(R.id.pb_item_ask_image)
        ProgressBar pb_item_ask_image;
        @Bind(R.id.pb_item_ask_text)
        ProgressBar pb_item_ask_text;

        public QuestionLocalViewHolder(View itemView) {
            super(itemView);
        }
    }


    public void setMsg(String serviceId, String serviceIcon) {
        mServiceId = serviceId;
        imageIcon = serviceIcon;
    }

    /**
     * 加载数据
     */
    private void initServiceQuiz(final QuestionLocalViewHolder questionLocalViewHolder, final ServiceMessageList serviceMessageList, String msg, final int position) {
        NetworkUtil.getInstance().startRequest(CompanionApi.sendServiceQuiz(mServiceId, msg, 1),
                new SimpleFastJsonCallback<String>(String.class, null) {
                    @Override
                    public void onSuccess(String url, String result) {
                        if (!TextUtils.isEmpty(result)) {
                            try {
                                JSONObject jsonObject = JSONObject.parseObject(result);
                                String message = (String) jsonObject.get("message");
                                if (!TextUtils.isEmpty(message)) {
                                    ServiceMessageList serviceMessageList = new ServiceMessageList();
                                    String time = System.currentTimeMillis() / 1000 + "";
                                    serviceMessageList.setRelease_time(Integer.parseInt(time));
                                    serviceMessageList.setType("text");
                                    serviceMessageList.setId(time);
                                    serviceMessageList.setMessage(message);
                                    add(serviceMessageList);
                                    EventBusHelper.post(serviceMessageList, AccountConstants.EventBus.EVENT_UPDATE_UPLOAD);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        serviceMessageList.setSend_state(1);
                        mSendStateMap.put(position, 1);
                        questionLocalViewHolder.pb_item_ask_text.setVisibility(View.GONE);
                        questionLocalViewHolder.img_item_retry_text.setVisibility(View.GONE);
                        EventBusHelper.post(serviceMessageList, AccountConstants.EventBus.EVENT_UPDATE_UPLOAD);
                    }

                    @Override
                    public void onFailure(String url, int statusCode, String msg) {
                        super.onFailure(url, statusCode, msg);
                        serviceMessageList.setSend_state(2);
                        mSendStateMap.put(position, 2);
                        questionLocalViewHolder.pb_item_ask_text.setVisibility(View.GONE);
                        questionLocalViewHolder.img_item_retry_text.setVisibility(View.VISIBLE);
                        EventBusHelper.post(serviceMessageList, AccountConstants.EventBus.EVENT_UPDATE_UPLOAD);
                        ToastUtils.showToastShort(context, "发送失败，请检查您的网络");
                    }
                });
    }

    /**
     * 跳转页面
     */
    private void onSkipPage(ServiceMessageList serviceMessageList, ServiceMessageContent serviceMessageContent) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(AccountConstants.Bundle.BUNDLE_COMPANION_SERVICE_MESSAGE_LIST, serviceMessageList);
        bundle.putString(AccountConstants.Bundle.BUNDLE_SERVICE_ID, mServiceId);
        bundle.putString(AccountConstants.Bundle.BUNDLE_SERVICE_NAME, serviceMessageContent.getTitle());
        bundle.putString(BaseWebViewActivity.URL, serviceMessageContent.getLink_url());
        context.startActivity(ArticleDetailForActivitiesActivity.class, bundle);
    }

    /**
     * 图片点击放大
     */
    private void initImage(ServiceMessageList serviceMessageList, String pic) {
        PicClickResult picClickResult = new PicClickResult();
        picClickResult.setClickIndex(0);
        ArrayList<PicList> picLists = new ArrayList<PicList>();
        PicList picList = new PicList();
        if (!TextUtils.isEmpty(serviceMessageList.getImage().getThumb())) {
            picList.setSrc(serviceMessageList.getImage().getThumb());
        } else {
            picList.setSrc(pic);
        }
        picLists.add(picList);
        picClickResult.setPicList(picLists);

        Bundle bundle = new Bundle();
        bundle.putSerializable(PictrueBrowseActivity.IMAGE_URL, picClickResult);
        Intent intent = new Intent(context, PictrueBrowseActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

}

