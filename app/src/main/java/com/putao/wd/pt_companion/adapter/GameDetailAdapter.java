package com.putao.wd.pt_companion.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.account.AccountConstants;
import com.putao.wd.account.AccountHelper;
import com.putao.wd.model.ServiceMessageContent;
import com.putao.wd.model.ServiceMessageList;
import com.putao.wd.util.ImageLoaderUtil;
import com.putao.wd.util.UploadFileCallback;
import com.putao.wd.util.UploadLoader;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
import com.sunnybear.library.util.DateUtils;
import com.sunnybear.library.view.emoji.EmojiTextView;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.adapter.BasicAdapter;

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
    public final static String UPLOAD_TEXT_TYPE = "upload_text";
    public final static String UPLOAD_IMAGE_TYPE = "upload_image";

    public GameDetailAdapter(Context context, List<ServiceMessageList> serviceMessageList) {
        super(context, serviceMessageList);
        mContent = context;
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
    public void onBindItem(BasicViewHolder holder, final ServiceMessageList serviceMessageList, int position) {
        String date = DateUtils.timeCalculate(serviceMessageList.getRelease_time());


        if (holder instanceof QuestionReplyViewHolder) {
            QuestionReplyViewHolder askViewHolder = (QuestionReplyViewHolder) holder;
            askViewHolder.question_item_ask_time.setText("───    " + date + "    ───");
            switch (serviceMessageList.getType()) {
                case "text":
                    askViewHolder.ll_reply.setVisibility(View.GONE);
                    askViewHolder.question_item_ask_image.setVisibility(View.GONE);
                    askViewHolder.question_item_answer_context.setVisibility(View.VISIBLE);
                    askViewHolder.question_item_answer_context.setText(serviceMessageList.getMessage());
                    break;
                case "image":
                    askViewHolder.ll_reply.setVisibility(View.GONE);
                    askViewHolder.question_item_ask_image.setVisibility(View.VISIBLE);
                    askViewHolder.question_item_answer_context.setVisibility(View.GONE);
                    askViewHolder.question_item_ask_image.setImageURL(serviceMessageList.getImage().getPic());
                    break;
                case "reply":
                    askViewHolder.ll_reply.setVisibility(View.VISIBLE);
                    askViewHolder.question_item_ask_image.setVisibility(View.GONE);
                    askViewHolder.question_item_answer_context.setVisibility(View.GONE);
                    askViewHolder.tv_ask.setText(serviceMessageList.getReply().getQuestion());
                    askViewHolder.tv_reply.setText(serviceMessageList.getReply().getAnswer());
                    break;

            }
        } else if (holder instanceof GameDetailHolder) {
            GameDetailHolder gameDetailHolder = (GameDetailHolder) holder;
            gameDetailHolder.tv_time.setText("───    " + date + "    ───");
            List<ServiceMessageContent> content_lists = serviceMessageList.getContent_lists();
            gameDetailHolder.tv_title.setVisibility(View.GONE);
            gameDetailHolder.tv_content.setVisibility(View.GONE);
            gameDetailHolder.tv_title_multi.setVisibility(View.GONE);
            gameDetailHolder.rl_article1.setVisibility(View.GONE);
            gameDetailHolder.rl_article2.setVisibility(View.GONE);
            gameDetailHolder.rl_article3.setVisibility(View.GONE);
            gameDetailHolder.rl_article4.setVisibility(View.GONE);
            ServiceMessageContent serviceMessageContent = content_lists.get(0);
            gameDetailHolder.iv_sign.setImageURL(serviceMessageContent.getCover_pic());
            if (content_lists.size() == 1) {
                gameDetailHolder.tv_title.setVisibility(View.VISIBLE);
                gameDetailHolder.tv_content.setVisibility(View.VISIBLE);
                gameDetailHolder.tv_title.setText(serviceMessageContent.getTitle());
                gameDetailHolder.tv_content.setText(serviceMessageContent.getSub_title());
            } else if (content_lists.size() > 1) {
                gameDetailHolder.tv_title_multi.setVisibility(View.VISIBLE);
                gameDetailHolder.tv_title_multi.setText(content_lists.get(0).getTitle());
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
                                }
                            });
                            break;
                        case 2:
                            gameDetailHolder.rl_article2.setVisibility(View.VISIBLE);
                            gameDetailHolder.tv_article2.setText(serviceMContent.getTitle());
                            gameDetailHolder.iv_article2.setImageURL(serviceMContent.getCover_pic());
                            gameDetailHolder.rl_article1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    EventBusHelper.post(serviceMContent, AccountConstants.EventBus.EVENT_GAME_START_ACTIVITY);
                                }
                            });
                            break;
                        case 3:
                            gameDetailHolder.rl_article3.setVisibility(View.VISIBLE);
                            gameDetailHolder.tv_article3.setText(serviceMContent.getTitle());
                            gameDetailHolder.iv_article3.setImageURL(serviceMContent.getCover_pic());
                            gameDetailHolder.rl_article1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    EventBusHelper.post(serviceMContent, AccountConstants.EventBus.EVENT_GAME_START_ACTIVITY);
                                }
                            });

                            break;
                        case 4:
                            gameDetailHolder.rl_article4.setVisibility(View.VISIBLE);
                            gameDetailHolder.tv_article4.setText(serviceMContent.getTitle());
                            gameDetailHolder.iv_article4.setImageURL(serviceMContent.getCover_pic());
                            gameDetailHolder.rl_article1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    EventBusHelper.post(serviceMContent, AccountConstants.EventBus.EVENT_GAME_START_ACTIVITY);
                                }
                            });
                            break;
                    }
                }
            }
        } else if (holder instanceof QuestionLocalViewHolder) {
            final QuestionLocalViewHolder questionLocalViewHolder = (QuestionLocalViewHolder) holder;
            questionLocalViewHolder.question_item_ask_icon.setImageURL(AccountHelper.getCurrentUserInfo().getHead_img());
            questionLocalViewHolder.question_item_ask_time.setText("───    " + date + "    ───");
            switch (serviceMessageList.getType()) {
                case UPLOAD_TEXT_TYPE:
                    questionLocalViewHolder.rl_item_ask_image.setVisibility(View.GONE);
                    questionLocalViewHolder.question_item_ask_context.setVisibility(View.VISIBLE);
                    questionLocalViewHolder.question_item_ask_context.setText(serviceMessageList.getMessage());
                    switch (serviceMessageList.getSend_state()) {
                        case 0:
                            questionLocalViewHolder.pb_item_ask_text.setVisibility(View.VISIBLE);

                            break;
                        case 1:
                            questionLocalViewHolder.pb_item_ask_text.setVisibility(View.GONE);

                            break;
                        case 2:
                            questionLocalViewHolder.pb_item_ask_text.setVisibility(View.GONE);

                            break;
                    }
                    break;
                case UPLOAD_IMAGE_TYPE:
                    questionLocalViewHolder.rl_item_ask_image.setVisibility(View.VISIBLE);
                    questionLocalViewHolder.question_item_ask_context.setVisibility(View.GONE);
                    questionLocalViewHolder.pb_item_ask_text.setVisibility(View.GONE);
                    String pic = serviceMessageList.getImage().getPic();
                    final String picUri = pic.substring(pic.indexOf("//") + 2);
                    if (!TextUtils.isEmpty(serviceMessageList.getImage().getThumb()))
                        ImageLoaderUtil.getInstance(context).displayImage(serviceMessageList.getImage().getThumb(), questionLocalViewHolder.question_item_ask_image);
                    else
                        ImageLoaderUtil.getInstance(context).displayImage(pic, questionLocalViewHolder.question_item_ask_image);
                    switch (serviceMessageList.getSend_state()) {
                        case 0:
                            questionLocalViewHolder.pb_item_ask_image.setVisibility(View.VISIBLE);
                            UploadLoader.getInstance().addUploadFile(picUri, new UploadFileCallback() {
                                @Override
                                protected void onFileUploadSuccess(String filePath) {
                                    if (filePath.equals(picUri)) {
                                        serviceMessageList.setSend_state(1);
                                        questionLocalViewHolder.pb_item_ask_image.setVisibility(View.GONE);
                                    }
                                }

                                @Override
                                protected void onFileUploadFail(String filePath) {
                                    if (filePath.equals(serviceMessageList.getImage().getPic())) {
                                        serviceMessageList.setSend_state(2);
                                        questionLocalViewHolder.pb_item_ask_image.setVisibility(View.GONE);
                                    }
                                }
                            }).execute();
                            break;
                        case 1:
                            questionLocalViewHolder.pb_item_ask_image.setVisibility(View.GONE);

                            break;
                        case 2:
                            questionLocalViewHolder.pb_item_ask_image.setVisibility(View.GONE);

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

        @Bind(R.id.question_item_ask_image)
        ImageDraweeView question_item_ask_image;
        @Bind(R.id.rl_item_ask_image)
        RelativeLayout rl_item_ask_image;
        @Bind(R.id.pb_item_ask_image)
        ProgressBar pb_item_ask_image;
        @Bind(R.id.pb_item_ask_text)
        ProgressBar pb_item_ask_text;

        public QuestionLocalViewHolder(View itemView) {
            super(itemView);
        }
    }

}

