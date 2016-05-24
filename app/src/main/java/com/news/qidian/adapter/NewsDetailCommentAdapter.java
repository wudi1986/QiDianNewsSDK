//package com.news.qidian.adapter;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.text.Html;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.WindowManager;
//import android.view.animation.DecelerateInterpolator;
//import android.widget.Button;
//import android.widget.FrameLayout;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.PopupWindow;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.news.qidian.R;
//import com.news.qidian.adapter.abslistview.CommonAdapter;
//import com.news.qidian.adapter.abslistview.CommonViewHolder;
//import com.news.qidian.database.NewsDetailCommentDao;
//import com.news.qidian.entity.NewsDetailComment;
//import com.news.qidian.entity.NewsFeed;
//import com.news.qidian.pages.NewsDetailAty2;
//import com.news.qidian.utils.DensityUtil;
//import com.nineoldandroids.animation.Animator;
//import com.nineoldandroids.animation.AnimatorSet;
//import com.nineoldandroids.animation.ObjectAnimator;
//
//import java.text.DateFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//
///**
// * Created by xiao on 2016/5/9.
// */
//public class NewsDetailCommentAdapter extends CommonAdapter<NewsDetailComment>{
//    private int daoHeight;
//    private TextView clip_pic;
//    public static final String KEY_NEWS_FEED = "key_news_feed";
//    public void setClip_pic(TextView clip_pic) {
//        this.clip_pic = clip_pic;
//    }
//    private OnDataIsNullListener onDataIsNullListener;
//
//
//
//    private ArrayList<NewsDetailComment> mDatas = new ArrayList<NewsDetailComment>();
//    private Context mContext;
//    private NewsDetailCommentDao newsDetailCommentDao = null;
//
//    public NewsDetailCommentDao getNewsDetailCommentDao() {
//        return newsDetailCommentDao;
//    }
//
//    public void setNewsDetailCommentDao(NewsDetailCommentDao newsDetailCommentDao) {
//        this.newsDetailCommentDao = newsDetailCommentDao;
//    }
//
//    public NewsDetailCommentAdapter(int layoutId, Context context, ArrayList<NewsDetailComment> datas){
//        super(layoutId,context,datas);
//        mDatas = datas;
//        mContext = context;
//    }
//    @Override
//    public void convert(CommonViewHolder holder,final NewsDetailComment newsDetailCommentItem, int positon) {
//        TextView pub_time = holder.getView(R.id.pub_time);
//        pub_time.setText(convertTime(newsDetailCommentItem.getCreate_time()));
//        TextView comment_content = holder.getView(R.id.comment_item_comment_content);
//        String string = newsDetailCommentItem.getContent();
//        comment_content.setText(string);
//        TextView original = holder.getView(R.id.original);
//        CharSequence originalStr = Html.fromHtml("<b>【原文】</b>"+newsDetailCommentItem.getOriginal());
//        original.setText(originalStr);
//        ImageButton love_imagebt = holder.getView(R.id.love_imagebt);
//        if(newsDetailCommentItem.isPraise()){
//            love_imagebt.setImageResource(R.drawable.list_icon_gif_nor_icon_heart_selected);
//        }else {
//            love_imagebt.setImageResource(R.drawable.list_icon_gif_nor_icon_heart_nor);
//        }
//        int love_num = newsDetailCommentItem.getLove();
//        final TextView love_count = holder.getView(R.id.love_count);
//        if(love_num > 0){
//            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) love_imagebt.getLayoutParams();
//            params.rightMargin = DensityUtil.dip2px(mContext,25);
//            love_imagebt.setLayoutParams(params);
//            love_count.setVisibility(View.VISIBLE);
//            love_count.setText(love_num+"");
//        }else{
//            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) love_imagebt.getLayoutParams();
//            params.rightMargin = DensityUtil.dip2px(mContext,19);
//            love_imagebt.setLayoutParams(params);
//            love_count.setVisibility(View.GONE);
//        }
//
//        ImageView del_icon = holder.getView(R.id.del_icon);
//        original.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(mContext, NewsDetailAty2.class);
//                NewsFeed newsFeed = newsDetailCommentItem.getNewsFeed();
//                intent.putExtra(KEY_NEWS_FEED, newsFeed);
//                mContext.startActivity(intent);
//            }
//        });
//        del_icon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                showPopupWindow(v,newsDetailCommentItem);
////                Toast.makeText(mContext, "删除该条评论",
////                        Toast.LENGTH_SHORT).show();
////                newsDetailCommentDao.delete(newsDetailCommentItem);
////                mDatas.remove(newsDetailCommentItem);
////                notifyDataSetChanged();
//            }
//        });
//        love_imagebt.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(final View view) {
//
//                newsDetailCommentItem.setPraise(!newsDetailCommentItem.isPraise());
//                if(newsDetailCommentItem.isPraise()){
//                    newsDetailCommentItem.setLove(newsDetailCommentItem.getLove()+1);
//                    ((ImageButton)view).setImageResource(R.drawable.list_icon_gif_nor_icon_heart_selected);
//                    int[] location = new int[2];
//                    view.getLocationOnScreen(location);
//                    AnimatorSet set = new AnimatorSet();
//                    set.playTogether(ObjectAnimator.ofFloat(view,
//                            "scaleX", 1, 2, 1), ObjectAnimator
//                            .ofFloat(view, "scaleY", 1, 2, 1));
//                    set.setDuration(1 * 1000).start();
//                    clip_pic.setVisibility(View.VISIBLE);
//                    int l =location[0] + view.getMeasuredWidth()/ 2;
//                    int t =location[1] - daoHeight-60;
//                    int r =location[0]+ view.getMeasuredWidth()/ 2+ clip_pic.getMeasuredWidth();
//                    int b =location[1]+ clip_pic.getMeasuredHeight()- daoHeight-30;
////                    Toast.makeText(mContext, "l="+l+"  t="+t+"  r="+r+"  b="+b, Toast.LENGTH_SHORT).show();
////                    Log.e("xzj","l="+l+"  t="+t+"  r="+r+"  b="+b);
//                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                    layoutParams.leftMargin = l;
//                    layoutParams.topMargin = t;
//                    clip_pic.setLayoutParams(layoutParams);
//                    clip_pic.requestLayout();
////                    clip_pic.layout(l,t,r,b);
////                    clip_pic.requestLayout();
//
////                    clip_pic.layout(
////                            location[0] + view.getMeasuredWidth()
////                                    / 2,
////                            location[1] - daoHeight,
////                            location[0]
////                                    + view.getMeasuredWidth()
////                                    / 2
////                                    + clip_pic
////                                    .getMeasuredWidth(),
////                            location[1]
////                                    + clip_pic
////                                    .getMeasuredHeight()
////                                    - daoHeight);
//
//                    AnimatorSet set1 = new AnimatorSet();
//                    set1.addListener(new Animator.AnimatorListener() {
//                        @Override
//                        public void onAnimationStart(Animator animation) {
//                            //动画开始时将按钮设置成不可点击，防止用户频繁点击
//                            view.setClickable(false);
//                        }
//
//                        @Override
//                        public void onAnimationEnd(Animator animation) {
//                            //动画结束后恢复按钮可点击
//                            notifyDataSetChanged();
//                            view.setClickable(true);
//                        }
//
//                        @Override
//                        public void onAnimationCancel(Animator animation) {
//
//                        }
//
//                        @Override
//                        public void onAnimationRepeat(Animator animation) {
//
//                        }
//                    });
//                    set1.playTogether(ObjectAnimator.ofFloat(
//                            clip_pic, "translationY", 0, -100),
//                            ObjectAnimator.ofFloat(clip_pic,
//                                    "alpha", 1, 0));
//                    set1.setInterpolator(new DecelerateInterpolator());
//                    set1.setDuration(1 * 1000).start();
//
//
//                    set1 = null;
//
//
//
//                }else {
//                    newsDetailCommentItem.setLove(newsDetailCommentItem.getLove()-1);
//
//                    ((ImageButton)view).setImageResource(R.drawable.list_icon_gif_nor_icon_heart_nor);
//                    notifyDataSetChanged();
//                }
//
//
//                newsDetailCommentDao.update(newsDetailCommentItem);
//
//
//            }
//        });
//    }
//
//
//    private void showPopupWindow(View view, final NewsDetailComment newsDetailCommentItem){
//        backgroundAlpha(0.5f);
//        // 一个自定义的布局，作为显示的内容
//        View contentView = LayoutInflater.from(mContext).inflate(
//                R.layout.popup, null);
//        // 设置按钮的点击事件
//        Button confirm = (Button) contentView.findViewById(R.id.popup_confirm);
//        Button cancel = (Button) contentView.findViewById(R.id.popup_cancle);
//
//
//        final PopupWindow popupWindow = new PopupWindow(contentView,
//                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
//
//        popupWindow.setTouchable(true);
//
//        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                return false;
//                // 这里如果返回true的话，touch事件将被拦截
//                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
//            }
//        });
//
//        popupWindow.setBackgroundDrawable(mContext.getResources().getDrawable(
//                R.drawable.bg_comment_del_popup));
//        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
//        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//
//            @Override
//            public void onDismiss() {
//                // TODO Auto-generated method stub
//                backgroundAlpha(1.0f);
//            }
//        });
//
//        confirm.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                popupWindow.dismiss();
//                backgroundAlpha(1.0f);
//                Toast.makeText(mContext, "删除该条评论",
//                        Toast.LENGTH_SHORT).show();
//                newsDetailCommentDao.delete(newsDetailCommentItem);
//                mDatas.remove(newsDetailCommentItem);
//                if (mDatas.size()==0){
//                    onDataIsNullListener.onChangeLayout();
//                }
//                notifyDataSetChanged();
//            }
//        });
//
//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                backgroundAlpha(1.0f);
//                popupWindow.dismiss();
//            }
//        });
//
//    }
//
//
//
//
//    private static String convertTime(String oldTime){
//        String temp;
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date old = null;
//        try {
//            old = dateFormat.parse(oldTime);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        long timeGap = System.currentTimeMillis()-old.getTime();
//        DateFormat sdf = new SimpleDateFormat("MM月dd日");
//
//
//        if(timeGap<60000){//一分钟
//            temp = "刚刚";
//        }else if(timeGap<60*60000){//一小时
//            temp = (timeGap/60000)+"分钟前";
//        }else if(timeGap<24*60*60000){
//            temp = (timeGap/(60*60000))+"小时前";
//        }else if(timeGap<yesterday(1)){
//            temp = "昨天";
//        }else if(timeGap<yesterday(2)){
//            temp = "前天";
//        }else{
//            temp = sdf.format(old);
//        }
//
//
//        return temp;
//    }
//
//    private static long yesterday(int i){
//        long l = 0;
//        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date date = new Date();
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.DATE, -i);
//        String yesterday = new SimpleDateFormat( "yyyy-MM-dd ").format(calendar.getTime());
//        try {
//            date = sdf.parse(yesterday);
//        } catch (ParseException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        l = System.currentTimeMillis() - date.getTime();
//
//        return l;
//    }
//
//    public void backgroundAlpha(float bgAlpha){
//        WindowManager.LayoutParams lp = ((Activity)mContext).getWindow().getAttributes();
//        lp.alpha = bgAlpha;
//        ((Activity)mContext).getWindow().setAttributes(lp);
//    }
//
//    public interface OnDataIsNullListener{
//        void onChangeLayout();
//    }
//
//    public void setOnDataIsNullListener(OnDataIsNullListener onDataIsNullListener) {
//        this.onDataIsNullListener = onDataIsNullListener;
//    }
//    public int getDaoHeight() {
//        return daoHeight;
//    }
//
//    public void setDaoHeight(int daoHeight) {
//        this.daoHeight = daoHeight;
//    }
//
//}
