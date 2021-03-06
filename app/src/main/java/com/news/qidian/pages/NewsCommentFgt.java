package com.news.qidian.pages;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.news.qidian.R;
import com.news.qidian.common.BaseFragment;
import com.news.qidian.common.CommonConstant;
import com.news.qidian.common.HttpConstant;
import com.news.qidian.entity.NewsDetailComment;
import com.news.qidian.entity.NewsFeed;
import com.news.qidian.entity.User;
import com.news.qidian.net.volley.NewsCommentRequest;
import com.news.qidian.net.volley.NewsLoveRequest;
import com.news.qidian.utils.DateUtil;
import com.news.qidian.utils.Logger;
import com.news.qidian.utils.TextUtil;
import com.news.qidian.utils.manager.SharedPreManager;
import com.news.qidian.widget.TextViewExtend;
//import com.news.qidian.widget.UserCommentDialog;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by fengjigang on 16/3/31.
 * 新闻评论页
 */
public class NewsCommentFgt extends BaseFragment {

    public static final int REQUEST_CODE = 1030;
    public static final String KEY_NEWS_DOCID = "key_news_docid";
    public static final String KEY_NEWS_FEED = "key_news_feed";
    private PullToRefreshListView mNewsCommentList;
    private ArrayList<NewsDetailComment> mComments = new ArrayList<>();
    private CommentsAdapter mCommentsAdapter;
    private int mPageIndex = 1;
    private RefreshPageBroReceiber mRefreshReceiber;
    private RelativeLayout bgLayout;
    private User mUser;
    private NewsDetailComment mComment;
    private Holder mHolder;
    private LinearLayout news_comment_NoCommentsLayout;
    private NewsFeed mNewsFeed;
    private SharedPreferences mSharedPreferences;

    /**
     * 通知新闻详情页和评论fragment刷新评论
     */
    public class RefreshPageBroReceiber extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent) {
            if (CommonConstant.CHANGE_TEXT_ACTION.equals(intent.getAction())) {
                Logger.e("aaa", "详情页===文字的改变！！！");
                mCommentsAdapter.notifyDataSetChanged();
                news_comment_Title.setTextSize(mSharedPreferences.getInt("textSize", CommonConstant.TEXT_SIZE_NORMAL) + 2);
            } else {
                Logger.e("jigang", "detailaty refresh br");
//                NewsDetailComment comment = (NewsDetailComment) intent.getSerializableExtra(UserCommentDialog.KEY_ADD_COMMENT);
//                mComments.add(0, comment);
                news_comment_NoCommentsLayout.setVisibility(View.GONE);
                mCommentsAdapter.setData(mComments);
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        mNewsFeed = (NewsFeed) arguments.getSerializable(KEY_NEWS_FEED);
        mSharedPreferences = getActivity().getSharedPreferences("showflag", 0);
        if (mRefreshReceiber == null) {
            mRefreshReceiber = new RefreshPageBroReceiber();
            IntentFilter filter = new IntentFilter(NewsDetailAty2.ACTION_REFRESH_COMMENT);
            filter.addAction(CommonConstant.CHANGE_TEXT_ACTION);
            getActivity().registerReceiver(mRefreshReceiber, filter);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mRefreshReceiber != null) {
            getActivity().unregisterReceiver(mRefreshReceiber);
        }
    }

    private TextView news_comment_Title, news_comment_content;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fgt_news_comment, null);
        mNewsCommentList = (PullToRefreshListView) rootView.findViewById(R.id.mNewsCommentList);
        bgLayout = (RelativeLayout) rootView.findViewById(R.id.bgLayout);
        mNewsCommentList.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        mCommentsAdapter = new CommentsAdapter(getActivity());
        mNewsCommentList.setAdapter(mCommentsAdapter);
        View mCommentHeaderView = inflater.inflate(R.layout.news_comment_fragment_headerview, null);
        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        mCommentHeaderView.setLayoutParams(layoutParams);
        ListView lv = mNewsCommentList.getRefreshableView();
        lv.addHeaderView(mCommentHeaderView);

        news_comment_Title = (TextView) mCommentHeaderView.findViewById(R.id.news_comment_Title);
        news_comment_content = (TextView) mCommentHeaderView.findViewById(R.id.news_comment_content);
        news_comment_Title.setText(mNewsFeed.getTitle());
        if ("0".equals(mNewsFeed.getCommentsCount())) {
            news_comment_content.setText(mNewsFeed.getPubName() + "  " + DateUtil.getMonthAndDay(mNewsFeed.getPubTime()));
        } else {
            news_comment_content.setText(mNewsFeed.getPubName() + "  " + DateUtil.getMonthAndDay(mNewsFeed.getPubTime()) + "  " + mNewsFeed.getCommentsCount() + "评");
        }
        news_comment_NoCommentsLayout = (LinearLayout) mCommentHeaderView.findViewById(R.id.news_comment_NoCommentsLayout);
        if ("0".equals(mNewsFeed.getCommentsCount())) {
            news_comment_NoCommentsLayout.setVisibility(View.VISIBLE);
        } else {
            news_comment_NoCommentsLayout.setVisibility(View.GONE);
        }

        mNewsCommentList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                loadData();
            }
        });
        loadData();
        return rootView;
    }

    private void loadData() {
        Logger.e("jigang", "fetch comments url=" + HttpConstant.URL_FETCH_COMMENTS + "docid=" + mNewsFeed.getDocid());
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        NewsCommentRequest<ArrayList<NewsDetailComment>> feedRequest = null;
        try {
            feedRequest = new NewsCommentRequest<ArrayList<NewsDetailComment>>(Request.Method.GET, new TypeToken<ArrayList<NewsDetailComment>>() {
            }.getType(), HttpConstant.URL_FETCH_COMMENTS + "docid=" + URLEncoder.encode(mNewsFeed.getDocid(), "utf-8") + "&page=" + (mPageIndex++), new Response.Listener<ArrayList<NewsDetailComment>>() {

                @Override
                public void onResponse(ArrayList<NewsDetailComment> result) {
                    if (bgLayout.getVisibility() == View.VISIBLE) {
                        bgLayout.setVisibility(View.GONE);
                    }
                    mNewsCommentList.onRefreshComplete();
                    Logger.e("jigang", "network success, comment" + result);

                    if (!TextUtil.isListEmpty(result)) {
                        mComments.addAll(result);
                        mCommentsAdapter.setData(mComments);
                        Logger.d("aaa", "评论加载完毕！！！！！！");
                        news_comment_NoCommentsLayout.setVisibility(View.GONE);
                    } else {
                        if (mComments.size() == 0) {
                            news_comment_NoCommentsLayout.setVisibility(View.VISIBLE);
                        } else {
                            news_comment_NoCommentsLayout.setVisibility(View.GONE);
                        }

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    mNewsCommentList.onRefreshComplete();
                    if (bgLayout.getVisibility() == View.VISIBLE) {
                        bgLayout.setVisibility(View.GONE);
                    }
                    Logger.e("jigang", "network fail");
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        feedRequest.setRetryPolicy(new DefaultRetryPolicy(15000, 0, 0));
        requestQueue.add(feedRequest);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == LoginAty.REQUEST_CODE && data != null) {
//            mUser = (User) data.getSerializableExtra(LoginAty.KEY_USER_LOGIN);
//            addNewsLove(mUser, mComment, mHolder);
//        }
    }

    class CommentsAdapter extends BaseAdapter {

        private Context mContext;
        private ArrayList<NewsDetailComment> comments;

        CommentsAdapter(Context context) {
            mContext = context;
        }

        public void setData(ArrayList<NewsDetailComment> comments) {
            this.comments = comments;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return comments == null ? 0 : comments.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final Holder holder;
            if (convertView == null) {
                holder = new Holder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_list_comment1, null, false);
                holder.tvContent = (TextViewExtend) convertView.findViewById(R.id.tv_comment_content);
                holder.ivHeadIcon = (SimpleDraweeView) convertView.findViewById(R.id.iv_user_icon);
                holder.tvName = (TextViewExtend) convertView.findViewById(R.id.tv_user_name);
//                holder.tvTime = (TextViewExtend) convertView.findViewById(R.id.tv_time);
                holder.ivPraise = (ImageView) convertView.findViewById(R.id.iv_praise);
                holder.tvPraiseCount = (TextViewExtend) convertView.findViewById(R.id.tv_praise_count);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            holder.tvContent.setTextSize(mSharedPreferences.getInt("textSize", CommonConstant.TEXT_SIZE_NORMAL));
            final NewsDetailComment comment = comments.get(position);
            final User user = SharedPreManager.getUser(mContext);
            mComment = comment;
            mHolder = holder;
//            setNewsTime(holder.tvTime, comment.getCreate_time());
            if (!TextUtil.isEmptyString(comment.getProfile())) {
                holder.ivHeadIcon.setImageURI(Uri.parse(comment.getProfile()));
            }
            holder.tvName.setText(comment.getNickname());
            int count = comment.getLove();
            if (count == 0) {
                holder.tvPraiseCount.setVisibility(View.INVISIBLE);
            } else {
                holder.tvPraiseCount.setVisibility(View.VISIBLE);
                holder.tvPraiseCount.setText(comment.getLove() + "");
            }

            holder.tvContent.setText(comment.getContent());
            if (!mComment.isPraise()) {
                holder.ivPraise.setImageResource(R.drawable.bg_normal_praise);
            } else {
                holder.ivPraise.setImageResource(R.drawable.bg_praised);
            }

            if (user != null && user.getUserId().equals(comment.getUuid())) {
                holder.ivPraise.setVisibility(View.GONE);
            } else {
                holder.ivPraise.setVisibility(View.VISIBLE);

            }
            holder.ivPraise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//
//                    if (user == null) {
//                        Intent loginAty = new Intent(mContext, LoginAty.class);
//                        startActivityForResult(loginAty, REQUEST_CODE);
//                    } else {
//                        addNewsLove(user, comment, holder);
//                        comments.get(position).setPraise(true);
//                        comments.get(position).setLove(comment.getLove() + 1);
//                    }

                }
            });
            return convertView;
        }
    }

    private void setNewsTime(TextViewExtend tvTime, String updateTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = dateFormat.parse(updateTime);
            long between = System.currentTimeMillis() - date.getTime();
            if (between >= (24 * 3600000)) {
                tvTime.setText("");
            } else if (between < (24 * 3600000) && between >= (1 * 3600000)) {
                tvTime.setText("");
            } else {
                int time = (int) (between * 60 / 3600000);
                if (time > 0)
                    tvTime.setText(between * 60 / 3600000 + "分钟前");
                else
                    tvTime.setText(between * 60 * 60 / 3600000 + "秒前");
//                if (between / 3600000 / 60 == 0) {
//                    tvTime.setText("刚刚");
//                } else {
//                    tvTime.setText(between / 3600000 / 60 + "分钟前");
//                }
            }
        } catch (ParseException e) {
            tvTime.setText(updateTime);
            e.printStackTrace();
        }

    }

    private void addNewsLove(User user, NewsDetailComment comment, final Holder holder) {
        try {
            String name = URLEncoder.encode(user.getUserName(), "utf-8");
            String cid = URLEncoder.encode(comment.getId(), "utf-8");
            user.setUserName(name);
            comment.setId(cid);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Logger.e("jigang", "love url=" + HttpConstant.URL_LOVE_COMMENT + "cid=" + comment.getId() + "&uuid=" + user.getUserId() + "&unam=" + user.getUserName());
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        NewsLoveRequest<String> loveRequest = new NewsLoveRequest<String>(Request.Method.PUT, new TypeToken<String>() {
        }.getType(), HttpConstant.URL_LOVE_COMMENT + "cid=" + comment.getId() + "&uuid=" + user.getUserId() + "&unam=" + user.getUserName(), new Response.Listener<String>() {

            @Override
            public void onResponse(String result) {
                mNewsCommentList.onRefreshComplete();
                Logger.e("jigang", "network success, love" + result);
                if (!TextUtil.isEmptyString(result)) {
                    holder.ivPraise.setImageResource(R.drawable.bg_praised);
                    holder.tvPraiseCount.setText(result);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mNewsCommentList.onRefreshComplete();
                Logger.e("jigang", "network fail");
            }
        });
        loveRequest.setRetryPolicy(new DefaultRetryPolicy(15000, 0, 0));
        requestQueue.add(loveRequest);
    }


    class Holder {
        SimpleDraweeView ivHeadIcon;
        TextViewExtend tvName;
        TextViewExtend tvContent;
        TextViewExtend tvPraiseCount;
        TextViewExtend tvTime;
        ImageView ivPraise;
    }
}
