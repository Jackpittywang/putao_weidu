package com.putao.wd.home.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.putao.wd.R;
import com.putao.wd.account.AccountHelper;
import com.putao.wd.companion.DiaryActivity;
import com.putao.wd.companion.PlotPreviewDialog;
import com.putao.wd.model.Diary;
import com.putao.wd.model.DiaryQuestion;
import com.putao.wd.model.DiaryTitle;
import com.putao.wd.model.ExploreProductPlot;
import com.putao.wd.video.YoukuVideoPlayerActivity;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
import com.sunnybear.library.util.DateUtils;
import com.sunnybear.library.util.DiskFileCacheHelper;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.SmallBang;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.adapter.LoadMoreAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * 探索号适配器
 * Created by yanghx on 2015/12/9.
 */
public class ExploreAdapter extends LoadMoreAdapter<Diary, BasicViewHolder> {
    public static final String EVENT_DISPLAY = "display";
    public static final String EVENT_PLAYER = "display";

    private static final int TYPE_CHALLENGE = 1;//家长挑战
    private static final int TYPE_CHALLENGE_PIC = 2;//家长挑战(图片)
    private static final int TYPE_TASKS = 3;//普通条目
    public static final String EVENT_DIARY_SHARE = "event_diary_share";
//    private static final int TYPE_PLAY = 3;//游戏玩法
//    private static final int TYPE_TASKS = 2;//家长任务
//    private static final int TYPE_EDUC = 4;//教育理念
//    private static final int TYPE_GROW = 5;//孩子成长

    private Animation alphaAnimation;
    private Animation scaleAnimation;
    private AnimationSet animationSet;
    //    private final int TYPE_PICTURE_ONE = 1;
//    private final int TYPE_PICTURE_FOUR = 4;
//    private final int TYPE_PICTURE_NINE = 9;
    private final String DATE_PATTERN = "yyyy/MM/dd";
    private List<String> mDate;
    private Map<Integer, Boolean> mIsShowDate;
    SimpleDateFormat mSdf;
    private SmallBang smallBang;
    private DiaryActivity mActivity;
    private DiskFileCacheHelper mDiskCacheHelper;
//    private List<SpannableStringBuilder> builders = new ArrayList<>();

    private ExploreDetailAdapter adapter;

    private PlotPreviewDialog dialog;

    public ExploreAdapter(Context context, List<Diary> diary) {
        super(context, diary);
        mActivity = (DiaryActivity) context;
        mDiskCacheHelper = mActivity.getDiskCacheHelper();
        smallBang = SmallBang.attach2Window((Activity) context);
        mIsShowDate = new HashMap<>();
        mDate = new ArrayList<>();
        mSdf = new SimpleDateFormat(DATE_PATTERN);
        scaleAnimation = new ScaleAnimation(3.0f, 1.0f, 3.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(1000);
        alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(1000);
        animationSet = new AnimationSet(true);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(alphaAnimation);
        animationSet.setFillAfter(true);
        animationSet.setFillEnabled(true);
    }

    @Override
    public int getMultiItemViewType(int position) {
        Diary diary = getItem(position);
        if (diary.getTag_type() == 2)
            return TYPE_TASKS;
        if (diary.getTag_type() == 1) {
            if (diary.getOption_type() == 1)
                return TYPE_CHALLENGE;
            if (diary.getOption_type() == 2)
                return TYPE_CHALLENGE_PIC;
        }
        return TYPE_TASKS;
    }

    @Override
    public int getLayoutId(int viewType) {
        switch (viewType) {
            case TYPE_CHALLENGE:
                return R.layout.activity_diary_challenge_item;
            case TYPE_CHALLENGE_PIC:
                return R.layout.activity_diary_challenge_pic_item;
            case TYPE_TASKS:
                return R.layout.activity_diary_tasks_item;
        }
        return R.layout.activity_diary_tasks_item;
    }

    @Override
    public BasicViewHolder getViewHolder(View itemView, int viewType) {
        switch (viewType) {
            case TYPE_CHALLENGE:
                return new DiaryChallengeViewHolder(itemView);
            case TYPE_TASKS:
                return new DiaryBasicViewHolder(itemView);
            case TYPE_CHALLENGE_PIC:
                return new DiaryChallengePicViewHolder(itemView);
            default:
                return new DiaryBasicViewHolder(itemView);
        }
    }

    @Override
    public void onBindItem(final BasicViewHolder holder, final Diary diary, int position) {
        Logger.i("ExploreProduct === " + diary.toString());
        DiaryBasicViewHolder basicHolder = (DiaryBasicViewHolder) holder;
        boolean isNewDate = true;
        String format = DateUtils.secondToDate(diary.getCreate_time(), DATE_PATTERN);
        if (!mIsShowDate.containsKey(position)) {
            if (mDate.contains(format)) {
                isNewDate = false;
                mIsShowDate.put(position, false);
            }
            if (isNewDate) {
                mDate.add(format);
                mIsShowDate.put(position, true);
            }
        }
        final DiaryTitle diaryTitle = JSONObject.parseObject(diary.getTitle(), DiaryTitle.class);
        basicHolder.iv_check.setVisibility(View.GONE);
        if (null != diaryTitle.getImg() && diaryTitle.getImg().length() > 0) {
            basicHolder.iv_image.setImageURL(diaryTitle.getImg());
            basicHolder.rl_image.setVisibility(View.VISIBLE);
        } else {
            basicHolder.rl_image.setVisibility(View.GONE);
        }
        if (null != diaryTitle.getVideo() && diaryTitle.getVideo().length() > 0) {
            basicHolder.iv_player.setVisibility(View.VISIBLE);
            basicHolder.iv_player.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString(YoukuVideoPlayerActivity.BUNDLE_VID, diaryTitle.getVideo());
                    EventBusHelper.post(bundle, EVENT_PLAYER);
                }
            });
        }
        basicHolder.ll_date.setVisibility(mIsShowDate.get(position) ? View.VISIBLE : View.GONE);
        basicHolder.tv_date.setText(format);
        basicHolder.tv_sign.setText(diary.getTag_name());
        basicHolder.tv_device.setText(diary.getDevice_name());
        basicHolder.tv_title.setText(diaryTitle.getText());

        if (diary.getTag_type() == 1 || diary.getTag_type() == 2)
            basicHolder.tv_share.setVisibility(View.GONE);
        else {
            basicHolder.tv_share.setVisibility(View.VISIBLE);
            basicHolder.tv_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ExploreProductPlot exploreProductPlot = new ExploreProductPlot();
                    exploreProductPlot.setContent(diaryTitle.getText());
                    exploreProductPlot.setImg_url(diaryTitle.getImg());
                    EventBusHelper.post(exploreProductPlot, EVENT_DIARY_SHARE);
                }
            });
        }

        /**
         * 家长挑战
         */{
            if (holder instanceof DiaryChallengeViewHolder) {
                final DiaryChallengeViewHolder viewHolder = (DiaryChallengeViewHolder) holder;
                DiaryQuestion diaryQuestion = JSONObject.parseObject(diary.getOption(), DiaryQuestion.class);
                viewHolder.tv_title.setText(diary.getAsk());
                Diary cacheDiary = (Diary) mDiskCacheHelper.getAsSerializable(diary.getConfig_id() + AccountHelper.getCurrentUid());
                Diary finalCacheDiary = cacheDiary == null ? diary : cacheDiary;
                if (finalCacheDiary.isFinish()) {
                    viewHolder.iv_check.setVisibility(View.VISIBLE);
                    initAnswer(viewHolder, finalCacheDiary.getAnswer(), finalCacheDiary.isTrue());
                    if (finalCacheDiary.isTrue())
                        viewHolder.iv_check.setImageResource(R.drawable.img_p_choose_c);
                    else {
                        viewHolder.iv_check.setImageResource(R.drawable.img_p_choose_r);
                        addFalse(viewHolder, finalCacheDiary.getFalseAnswer());
                    }
                    setClickable(viewHolder, false);
                } else {
                    viewHolder.iv_answer1.setImageResource(R.drawable.icon_20_p_choose_01);
                    viewHolder.iv_answer2.setImageResource(R.drawable.icon_20_p_choose_02);
                    viewHolder.iv_answer3.setImageResource(R.drawable.icon_20_p_choose_03);
                    viewHolder.iv_answer4.setImageResource(R.drawable.icon_20_p_choose_04);
                    viewHolder.iv_check.setVisibility(View.GONE);
                    setClickable(viewHolder, true);
                }
                if (null != diaryQuestion.getA()) {
                    viewHolder.rl_answer1.setVisibility(View.VISIBLE);
                    viewHolder.tv_answer1.setText(diaryQuestion.getA());
                    viewHolder.rl_answer1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            boolean isTrue = checkAnswer(viewHolder, diary.getAnswer(), "A");
                            if (!isTrue)
                                diary.setFalseAnswer("A");
                            diary.setIsFinish(true);
                            diary.setIsTrue(isTrue);
                            mDiskCacheHelper.put(diary.getConfig_id() + AccountHelper.getCurrentUid(), diary);
                        }
                    });
                } else viewHolder.rl_answer1.setVisibility(View.GONE);
                if (null != diaryQuestion.getB()) {
                    viewHolder.v_answer2.setVisibility(View.VISIBLE);
                    viewHolder.rl_answer2.setVisibility(View.VISIBLE);
                    viewHolder.tv_answer2.setText(diaryQuestion.getB());
                    viewHolder.rl_answer2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            boolean isTrue = checkAnswer(viewHolder, diary.getAnswer(), "B");
                            if (!isTrue)
                                diary.setFalseAnswer("B");
                            diary.setIsFinish(true);
                            diary.setIsTrue(isTrue);
                            mDiskCacheHelper.put(diary.getConfig_id() + AccountHelper.getCurrentUid(), diary);
                        }
                    });
                } else viewHolder.rl_answer2.setVisibility(View.GONE);
                if (null != diaryQuestion.getC()) {
                    viewHolder.v_answer3.setVisibility(View.VISIBLE);
                    viewHolder.rl_answer3.setVisibility(View.VISIBLE);
                    viewHolder.tv_answer3.setText(diaryQuestion.getC());
                    viewHolder.rl_answer3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            boolean isTrue = checkAnswer(viewHolder, diary.getAnswer(), "C");
                            if (!isTrue)
                                diary.setFalseAnswer("C");
                            diary.setIsFinish(true);
                            diary.setIsTrue(isTrue);
                            mDiskCacheHelper.put(diary.getConfig_id() + AccountHelper.getCurrentUid(), diary);
                        }
                    });
                } else viewHolder.rl_answer3.setVisibility(View.GONE);
                if (null != diaryQuestion.getD()) {
                    viewHolder.v_answer4.setVisibility(View.VISIBLE);
                    viewHolder.rl_answer4.setVisibility(View.VISIBLE);
                    viewHolder.tv_answer4.setText(diaryQuestion.getD());
                    viewHolder.rl_answer4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            boolean isTrue = checkAnswer(viewHolder, diary.getAnswer(), "D");
                            if (!isTrue)
                                diary.setFalseAnswer("D");
                            diary.setIsFinish(true);
                            diary.setIsTrue(isTrue);
                            mDiskCacheHelper.put(diary.getConfig_id() + AccountHelper.getCurrentUid(), diary);
                        }
                    });
                } else viewHolder.rl_answer4.setVisibility(View.GONE);
            }
        }

        /**
         * 家长挑战(图片)
         */
        if (holder instanceof DiaryChallengePicViewHolder) {
            final DiaryChallengePicViewHolder viewHolder = (DiaryChallengePicViewHolder) holder;
            DiaryQuestion diaryQuestion = JSONObject.parseObject(diary.getOption(), DiaryQuestion.class);
            Diary cacheDiary = (Diary) mDiskCacheHelper.getAsSerializable(diary.getConfig_id() + AccountHelper.getCurrentUid());
            Diary finalCacheDiary = cacheDiary == null ? diary : cacheDiary;
            viewHolder.tv_title.setText(diary.getAsk());
            if (diary.isFinish()) {
                viewHolder.iv_check.setVisibility(View.VISIBLE);
                initPicAnswer(viewHolder, finalCacheDiary.getAnswer(), finalCacheDiary.isTrue());
                if (finalCacheDiary.isTrue())
                    viewHolder.iv_check.setImageResource(R.drawable.img_p_choose_c);
                else {
                    viewHolder.iv_check.setImageResource(R.drawable.img_p_choose_r);
                    addPicFalse(viewHolder, finalCacheDiary.getFalseAnswer());
                }
                setPicClickable(viewHolder, false);
            } else {
                viewHolder.iv_answer1.setImageResource(R.drawable.icon_20_p_choose_01);
                viewHolder.iv_answer2.setImageResource(R.drawable.icon_20_p_choose_02);
                viewHolder.iv_answer3.setImageResource(R.drawable.icon_20_p_choose_03);
                viewHolder.iv_answer4.setImageResource(R.drawable.icon_20_p_choose_04);
                viewHolder.iv_check.setVisibility(View.GONE);
                setPicClickable(viewHolder, true);
            }
            if (null != diaryQuestion.getA()) {
                viewHolder.rl_answer1.setVisibility(View.VISIBLE);
                viewHolder.idv_answer1.setImageURL(diaryQuestion.getA());
                viewHolder.rl_answer1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isTrue = checkPicAnswer(viewHolder, diary.getAnswer(), "A");
                        if (!isTrue)
                            diary.setFalseAnswer("A");
                        diary.setIsFinish(true);
                        diary.setIsTrue(isTrue);
                        mDiskCacheHelper.put(diary.getConfig_id() + AccountHelper.getCurrentUid(), diary);
                    }
                });
            } else viewHolder.rl_answer1.setVisibility(View.GONE);
            if (null != diaryQuestion.getB()) {
                viewHolder.rl_answer2.setVisibility(View.VISIBLE);
                viewHolder.idv_answer2.setImageURL(diaryQuestion.getB());
                viewHolder.rl_answer2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isTrue = checkPicAnswer(viewHolder, diary.getAnswer(), "B");
                        if (!isTrue)
                            diary.setFalseAnswer("B");
                        diary.setIsFinish(true);
                        diary.setIsTrue(isTrue);
                        mDiskCacheHelper.put(diary.getConfig_id() + AccountHelper.getCurrentUid(), diary);
                    }
                });
            } else viewHolder.rl_answer2.setVisibility(View.GONE);

            if (null != diaryQuestion.getC()) {
                viewHolder.rl_answer3.setVisibility(View.VISIBLE);
                viewHolder.idv_answer3.setImageURL(diaryQuestion.getC());
                viewHolder.rl_answer3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isTrue = checkPicAnswer(viewHolder, diary.getAnswer(), "C");
                        if (!isTrue)
                            diary.setFalseAnswer("C");
                        diary.setIsFinish(true);
                        diary.setIsTrue(isTrue);
                        mDiskCacheHelper.put(diary.getConfig_id() + AccountHelper.getCurrentUid(), diary);
                    }
                });
            } else viewHolder.rl_answer3.setVisibility(View.GONE);

            if (null != diaryQuestion.getD()) {
                viewHolder.rl_answer4.setVisibility(View.VISIBLE);
                viewHolder.idv_answer4.setImageURL(diaryQuestion.getD());
                viewHolder.rl_answer4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isTrue = checkPicAnswer(viewHolder, diary.getAnswer(), "D");
                        if (!isTrue)
                            diary.setFalseAnswer("D");
                        diary.setIsFinish(true);
                        diary.setIsTrue(isTrue);
                        mDiskCacheHelper.put(diary.getConfig_id() + AccountHelper.getCurrentUid(), diary);
                    }
                });
            } else viewHolder.rl_answer4.setVisibility(View.GONE);
        }

    }

    private void setClickable(DiaryChallengeViewHolder viewHolder, boolean b) {
        viewHolder.rl_answer1.setEnabled(b);
        viewHolder.rl_answer2.setEnabled(b);
        viewHolder.rl_answer3.setEnabled(b);
        viewHolder.rl_answer4.setEnabled(b);
    }

    private void setPicClickable(DiaryChallengePicViewHolder viewHolder, boolean b) {
        viewHolder.rl_answer1.setEnabled(b);
        viewHolder.rl_answer2.setEnabled(b);
        viewHolder.rl_answer3.setEnabled(b);
        viewHolder.rl_answer4.setEnabled(b);
    }

    private void initAnswer(DiaryChallengeViewHolder holder, String answer, boolean isTrue) {
        holder.iv_answer1.setImageResource(answer.equals("A") ? isTrue ? R.drawable.icon_20_p_choose_06 : R.drawable.icon_20_p_choose_05 : R.drawable.icon_20_p_choose_07);
        holder.iv_answer2.setImageResource(answer.equals("B") ? isTrue ? R.drawable.icon_20_p_choose_06 : R.drawable.icon_20_p_choose_05 : R.drawable.icon_20_p_choose_07);
        holder.iv_answer3.setImageResource(answer.equals("C") ? isTrue ? R.drawable.icon_20_p_choose_06 : R.drawable.icon_20_p_choose_05 : R.drawable.icon_20_p_choose_07);
        holder.iv_answer4.setImageResource(answer.equals("D") ? isTrue ? R.drawable.icon_20_p_choose_06 : R.drawable.icon_20_p_choose_05 : R.drawable.icon_20_p_choose_07);
    }

    private void initPicAnswer(DiaryChallengePicViewHolder holder, String answer, boolean isTrue) {
        holder.iv_answer1.setImageResource(answer.equals("A") ? isTrue ? R.drawable.icon_20_p_choose_06 : R.drawable.icon_20_p_choose_05 : R.drawable.icon_20_p_choose_07);
        holder.iv_answer2.setImageResource(answer.equals("B") ? isTrue ? R.drawable.icon_20_p_choose_06 : R.drawable.icon_20_p_choose_05 : R.drawable.icon_20_p_choose_07);
        holder.iv_answer3.setImageResource(answer.equals("C") ? isTrue ? R.drawable.icon_20_p_choose_06 : R.drawable.icon_20_p_choose_05 : R.drawable.icon_20_p_choose_07);
        holder.iv_answer4.setImageResource(answer.equals("D") ? isTrue ? R.drawable.icon_20_p_choose_06 : R.drawable.icon_20_p_choose_05 : R.drawable.icon_20_p_choose_07);
    }

    private void addFalse(DiaryChallengeViewHolder holder, String falseAnswer) {
        switch (falseAnswer) {
            case "A":
                holder.iv_answer1.setImageResource(R.drawable.icon_20_p_choose_08);
                break;
            case "B":
                holder.iv_answer2.setImageResource(R.drawable.icon_20_p_choose_08);
                break;
            case "C":
                holder.iv_answer3.setImageResource(R.drawable.icon_20_p_choose_08);
                break;
            case "D":
                holder.iv_answer4.setImageResource(R.drawable.icon_20_p_choose_08);
                break;
        }
    }

    private void addPicFalse(DiaryChallengePicViewHolder holder, String falseAnswer) {
        switch (falseAnswer) {
            case "A":
                holder.iv_answer1.setImageResource(R.drawable.icon_20_p_choose_08);
                break;
            case "B":
                holder.iv_answer2.setImageResource(R.drawable.icon_20_p_choose_08);
                break;
            case "C":
                holder.iv_answer3.setImageResource(R.drawable.icon_20_p_choose_08);
                break;
            case "D":
                holder.iv_answer4.setImageResource(R.drawable.icon_20_p_choose_08);
                break;
        }
    }


    private boolean checkAnswer(DiaryChallengeViewHolder holder, String answer, String
            myAnswer) {
        boolean isTrue;
        isTrue = answer.equals(myAnswer);
        initAnswer(holder, answer, isTrue);
        if (!isTrue) addFalse(holder, myAnswer);
        holder.iv_check.setImageResource(isTrue ? R.drawable.img_p_choose_c : R.drawable.img_p_choose_r);
        holder.iv_check.setAnimation(animationSet);
        holder.iv_check.setVisibility(View.VISIBLE);
        animationSet.startNow();
        setClickable(holder, false);
        switch (answer) {
            case "A":
                smallBang.bang(holder.iv_answer1);
                break;
            case "B":
                smallBang.bang(holder.iv_answer2);
                break;
            case "C":
                smallBang.bang(holder.iv_answer3);
                break;
            case "D":
                smallBang.bang(holder.iv_answer4);
                break;
        }
        return isTrue;
    }

    private boolean checkPicAnswer(DiaryChallengePicViewHolder holder, String answer, String
            myAnswer) {
        boolean isTrue;
        isTrue = answer.equals(myAnswer);
        initPicAnswer(holder, answer, isTrue);
        holder.iv_check.setImageResource(isTrue ? R.drawable.img_p_choose_c : R.drawable.img_p_choose_r);
        initPicAnswer(holder, answer, isTrue);
        if (!isTrue) addPicFalse(holder, myAnswer);
        holder.iv_check.setAnimation(animationSet);
        holder.iv_check.setVisibility(View.VISIBLE);
        animationSet.startNow();
        setPicClickable(holder, false);
        switch (answer) {
            case "A":
                smallBang.bang(holder.iv_answer1);
                break;
            case "B":
                smallBang.bang(holder.iv_answer2);
                break;
            case "C":
                smallBang.bang(holder.iv_answer3);
                break;
            case "D":
                smallBang.bang(holder.iv_answer4);
                break;
        }
        return isTrue;
    }

    /**
     * 家长挑战
     */
    static class DiaryChallengeViewHolder extends DiaryBasicViewHolder {
        @Bind(R.id.iv_answer1)
        ImageView iv_answer1;
        @Bind(R.id.iv_answer2)
        ImageView iv_answer2;
        @Bind(R.id.iv_answer3)
        ImageView iv_answer3;
        @Bind(R.id.iv_answer4)
        ImageView iv_answer4;
        @Bind(R.id.rl_answer1)
        RelativeLayout rl_answer1;
        @Bind(R.id.rl_answer2)
        RelativeLayout rl_answer2;
        @Bind(R.id.rl_answer3)
        RelativeLayout rl_answer3;
        @Bind(R.id.rl_answer4)
        RelativeLayout rl_answer4;
        @Bind(R.id.v_answer2)
        View v_answer2;
        @Bind(R.id.v_answer3)
        View v_answer3;
        @Bind(R.id.v_answer4)
        View v_answer4;
        @Bind(R.id.tv_answer1)
        TextView tv_answer1;
        @Bind(R.id.tv_answer2)
        TextView tv_answer2;
        @Bind(R.id.tv_answer3)
        TextView tv_answer3;
        @Bind(R.id.tv_answer4)
        TextView tv_answer4;

        public DiaryChallengeViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 家长挑战
     */
    static class DiaryChallengePicViewHolder extends DiaryBasicViewHolder {
        @Bind(R.id.iv_answer1)
        ImageView iv_answer1;
        @Bind(R.id.iv_answer2)
        ImageView iv_answer2;
        @Bind(R.id.iv_answer3)
        ImageView iv_answer3;
        @Bind(R.id.iv_answer4)
        ImageView iv_answer4;
        @Bind(R.id.rl_answer1)
        RelativeLayout rl_answer1;
        @Bind(R.id.rl_answer2)
        RelativeLayout rl_answer2;
        @Bind(R.id.rl_answer3)
        RelativeLayout rl_answer3;
        @Bind(R.id.rl_answer4)
        RelativeLayout rl_answer4;
        @Bind(R.id.idv_answer1)
        ImageDraweeView idv_answer1;
        @Bind(R.id.idv_answer2)
        ImageDraweeView idv_answer2;
        @Bind(R.id.idv_answer3)
        ImageDraweeView idv_answer3;
        @Bind(R.id.idv_answer4)
        ImageDraweeView idv_answer4;

        public DiaryChallengePicViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class DiaryBasicViewHolder extends BasicViewHolder {
        @Bind(R.id.tv_sign)
        TextView tv_sign;
        @Bind(R.id.tv_device)
        TextView tv_device;
        @Bind(R.id.tv_title)
        TextView tv_title;
        @Bind(R.id.ll_date)
        LinearLayout ll_date;
        @Bind(R.id.tv_date)
        TextView tv_date;
        @Bind(R.id.iv_check)
        ImageView iv_check;
        @Bind(R.id.tv_count_comment)
        TextView tv_share;
        @Bind(R.id.rl_image)
        RelativeLayout rl_image;
        @Bind(R.id.iv_image)
        ImageDraweeView iv_image;
        @Bind(R.id.iv_player)
        ImageView iv_player;

        public DiaryBasicViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 替换html中的<param>标签
     *
     * @param data
     * @param html
     * @return
     */
    private String replaceHtml(List<String> data, String html) {
        boolean hasNull = false;
        for (String str : data) {
            if (str == null)
                hasNull = true;
        }
        if (hasNull)
            for (int i = 0; i < data.size(); i++) {
                if (i < data.size() - 1)
                    html = html.replace("<param" + i + ">", data.get(i + 1));
            }
        else
            for (int i = 0; i < data.size(); i++) {
                html = html.replace("<param" + i + ">", data.get(i));
            }
        return html;
    }
}





