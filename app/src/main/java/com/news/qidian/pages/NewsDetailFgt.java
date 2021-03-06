package com.news.qidian.pages;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
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
import com.news.qidian.adapter.NewsDetailFgtAdapter;
import com.news.qidian.common.BaseFragment;
import com.news.qidian.common.CommonConstant;
import com.news.qidian.common.HttpConstant;
import com.news.qidian.database.NewsDetailCommentDao;
import com.news.qidian.entity.NewsDetail;
import com.news.qidian.entity.NewsDetailComment;
import com.news.qidian.entity.RelatedEntity;
import com.news.qidian.entity.RelatedItemEntity;
import com.news.qidian.entity.User;
import com.news.qidian.net.volley.NewsCommentRequest;
import com.news.qidian.net.volley.NewsDetailRequest;
import com.news.qidian.net.volley.NewsLoveRequest;
import com.news.qidian.utils.DateUtil;
import com.news.qidian.utils.Logger;
import com.news.qidian.utils.TextUtil;
//import com.news.qidian.utils.helper.ShareSdkHelper;
import com.news.qidian.utils.manager.SharedPreManager;
import com.news.qidian.widget.TextViewExtend;
//import com.news.qidian.widget.UserCommentDialog;
import com.news.qidian.widget.webview.LoadWebView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
//
//import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * Created by fengjigang on 16/3/31.
 * 新闻详情页
 */
public class NewsDetailFgt extends BaseFragment {
    public static final String KEY_DETAIL_RESULT = "key_detail_result";
    private LoadWebView mDetailWebView;
    private NewsDetail mResult;
    private SharedPreferences mSharedPreferences;
    private PullToRefreshListView mNewsDetailList;
    private NewsDetailFgtAdapter mAdapter;
    private boolean isListRefresh;
    private User user;
    private RelativeLayout bgLayout;
    private String mDocid, mTitle, mPubName, mPubTime, mCommentCount, mNewID;
    private ArrayList<NewsDetailComment> mComments = new ArrayList<>();
    public static final String KEY_NEWS_DOCID = "key_news_docid";
    public static final String KEY_NEWS_ID = "key_news_id";
    public static final String KEY_NEWS_TITLE = "key_news_title";
    public static final int REQUEST_CODE = 1030;
    private LinearLayout detail_shared_FriendCircleLayout,
            detail_shared_CareForLayout,
            mCommentLayout,
             mNewsDetailHeaderView;

    private TextView detail_shared_PraiseText,
            detail_shared_Text,
            detail_shared_hotComment;
    private RelativeLayout detail_shared_ShareImageLayout,detail_shared_MoreComment,
            detail_shared_CommentTitleLayout,
            detail_shared_ViewPointTitleLayout;
    private ImageView detail_shared_AttentionImage;
    private int CommentType = 0;
    private LayoutInflater inflater;
    ViewGroup container;
    private RefreshPageBroReceiber mRefreshReceiber;
    private boolean isWebSuccess,isCommentSuccess, isCorrelationSuccess;
    private TextView mDetailSharedHotComment;
    boolean isNoHaveBean ;
    private final int LOAD_MORE = 0;
    private final int LOAD_BOTTOM = 1;
    private boolean isLike;
    private NewsDetailCommentDao mNewsDetailCommentDao;

    private TextView footView_tv;
    private ProgressBar footView_progressbar;
    private boolean isBottom;

    private boolean isLoadDate;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        MobclickAgent.onEvent(getActivity(),"qidian_user_enter_detail_page");
        Bundle arguments = getArguments();
        mDocid = arguments.getString(KEY_NEWS_DOCID);
        mNewID = arguments.getString(KEY_NEWS_ID);
        mTitle = arguments.getString(KEY_NEWS_TITLE);
        Logger.e("aaa", "mTitle==" + mTitle);
        mResult = (NewsDetail) arguments.getSerializable(KEY_DETAIL_RESULT);
        mSharedPreferences = getActivity().getSharedPreferences("showflag", 0);

        if (mRefreshReceiber == null) {
            mRefreshReceiber = new RefreshPageBroReceiber();
            IntentFilter filter = new IntentFilter(NewsDetailAty2.ACTION_REFRESH_COMMENT);
            filter.addAction(CommonConstant.CHANGE_TEXT_ACTION);
            getActivity().registerReceiver(mRefreshReceiber, filter);
        }

    }



    private int oldLastPositon;
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fgt_news_detail_listview, null);
        this.inflater = inflater;
        this.container = container;
        user = SharedPreManager.getUser(getActivity());
        mNewsDetailList = (PullToRefreshListView) rootView.findViewById(R.id.fgt_new_detail_PullToRefreshListView);
        bgLayout = (RelativeLayout) rootView.findViewById(R.id.bgLayout);

        mNewsDetailList.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        mNewsDetailList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if(isLoadDate){
                    return;
                }

                isLoadDate = true;
                if (MAXPage > viewpointPage) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            beanList.addAll(beanPageList.get(viewpointPage));
                            viewpointPage++;
                            mAdapter.setNewsFeed(beanList);
                            mAdapter.notifyDataSetChanged();
                            mNewsDetailList.onRefreshComplete();
                            if(MAXPage <= viewpointPage){
                                mNewsDetailList.setMode(PullToRefreshBase.Mode.DISABLED);
                                footView_tv.setText("内容加载完毕");
                            }

                        }
                    }, 1000);
                }
            }
        });
        mNewsDetailList.setOnStateListener(new PullToRefreshBase.onStateListener() {
            @Override
            public void getState(PullToRefreshBase.State mState) {
                if(!isBottom){
                    return;
                }
                boolean isVisisyProgressBar = false;
                switch (mState) {
                    case RESET://初始
                        isVisisyProgressBar = false;
                        footView_tv.setText("上拉获取更多文章");
                        break;
                    case PULL_TO_REFRESH://更多推荐
                        isVisisyProgressBar = false;
                        footView_tv.setText("上拉获取更多文章");
                        break;
                    case RELEASE_TO_REFRESH://松开推荐
                        isVisisyProgressBar = false;
                        footView_tv.setText("松手获取更多文章");
                        break;
                    case REFRESHING:
                    case MANUAL_REFRESHING://推荐中
                        isVisisyProgressBar = true;
                        footView_tv.setText("正在获取更多文章...");
                        break;
                    case OVERSCROLLING:
                        // NO-OP
                        break;
                }
                if(isVisisyProgressBar){
                    footView_progressbar.setVisibility(View.VISIBLE);
                }else{
                    footView_progressbar.setVisibility(View.GONE);
                }
                mNewsDetailList.setFooterViewInvisible();
            }
        });

        mNewsDetailList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    // 当不滚动时
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        // 判断滚动到底部
                        if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
                            Log.e("aaa", "滑动到底部");
                            isBottom = true;


                        }else{
                            isBottom = false;
                            Logger.e("aaa","在33333isBottom =="+isBottom);
                        }
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int i1, int i2) {
//                if (beanList.size() == 0) {
//
//                    return;
//                }
//                int lastPositon =  absListView.getLastVisiblePosition();
//                Logger.e("aaa", "lastPositon====" + lastPositon);
//                Message msg = new Message();
//                if(lastPositon -2 ==beanList.size()-1){
//                    if (MAXPage > viewpointPage) {
//                        if(oldLastPositon == lastPositon){
//                            return;
//                        }
//                        msg.what = LOAD_MORE;
//                        mHandler.sendMessage(msg);
//                    }else{
//                        msg.what = LOAD_BOTTOM;
//                        mHandler.sendMessage(msg);
//
//                    }
//                }
//                oldLastPositon = lastPositon;
            }
        });
        mAdapter = new NewsDetailFgtAdapter(getActivity());

        mNewsDetailList.setAdapter(mAdapter);
        addHeadView(inflater, container);
        loadData();

        return rootView;
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case LOAD_MORE:
                    beanList.addAll(beanPageList.get(viewpointPage));
                    viewpointPage++;
                    mAdapter.setNewsFeed(beanList);
                    mAdapter.notifyDataSetChanged();
                    mNewsDetailList.onRefreshComplete();
                    break;
                case LOAD_BOTTOM:
                    if(isNoHaveBean){
                        return;
                    }

                    isNoHaveBean = true;

                    AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
                    ListView lv = mNewsDetailList.getRefreshableView();
                    LinearLayout mNewsDetailFootView = (LinearLayout) inflater.inflate(R.layout.detail_footview_layout, container, false);
                    mNewsDetailFootView.setLayoutParams(layoutParams);
                    lv.addFooterView(mNewsDetailFootView);
                    break;
            }
        }
    };
    @Override
    public void onDetach() {
        super.onDetach();
        if (mRefreshReceiber != null) {
            getActivity().unregisterReceiver(mRefreshReceiber);
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        mDetailWebView.pauseTimers();
    }

    @Override
    public void onResume() {
        super.onResume();
        mDetailWebView.resumeTimers();
    }
    public void addHeadView(LayoutInflater inflater, ViewGroup container) {
        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ListView lv = mNewsDetailList.getRefreshableView();
        //第1部分的WebView
        mNewsDetailHeaderView = (LinearLayout) inflater.inflate(R.layout.fgt_news_detail, container, false);
        mNewsDetailHeaderView.setLayoutParams(layoutParams);
        lv.addHeaderView(mNewsDetailHeaderView);
        mNewsDetailHeaderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logger.e("aaa", "webView的点击");
            }
        });

        mDetailWebView = (LoadWebView) mNewsDetailHeaderView.findViewById(R.id.mDetailWebView);
//        if (Build.VERSION.SDK_INT >= 19) {//防止视频加载不出来。
//            mDetailWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
//        } else {
//            mDetailWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//        }
        mDetailWebView.setBackgroundColor(getActivity().getResources().getColor(R.color.transparent));
        mDetailWebView.getSettings().setJavaScriptEnabled(true);
        mDetailWebView.getSettings().setDatabaseEnabled(true);
        mDetailWebView.getSettings().setDomStorageEnabled(true);
        mDetailWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        mDetailWebView.getSettings().setLoadsImagesAutomatically(false);
        mDetailWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
//        mDetailWebView.loadData(TextUtil.genarateHTML(mResult, mSharedPreferences.getInt("textSize", CommonConstant.TEXT_SIZE_NORMAL)), "text/html;charset=UTF-8", null);
        mDetailWebView.loadDataWithBaseURL(null,TextUtil.genarateHTML(mResult, mSharedPreferences.getInt("textSize", CommonConstant.TEXT_SIZE_NORMAL)),
                "text/html;charset=UTF-8", "utf-8", null);
//        mDetailWebView.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//
//            }
//
//        });
        mDetailWebView.setDf(new LoadWebView.PlayFinish() {
            @Override
            public void After() {
                Log.e("aaa","22222");
                isWebSuccess = true;
                mDetailWebView.getSettings().setLoadsImagesAutomatically(true);
                isBgLayoutSuccess();
//                Log.e("aaa","1111");
            }
        });
        mDetailWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Log.d("aaa", "newProgress==" + newProgress);
            }
        });
        //第2部分的CommentTitle
        View mCommentTitleView = inflater.inflate(R.layout.detail_shared_layout, container, false);
        mCommentTitleView.setLayoutParams(layoutParams);
        mNewsDetailHeaderView.addView(mCommentTitleView);
        detail_shared_FriendCircleLayout = (LinearLayout) mCommentTitleView.findViewById(R.id.detail_shared_FriendCircleLayout);
        detail_shared_CareForLayout = (LinearLayout) mCommentTitleView.findViewById(R.id.detail_shared_PraiseLayout);
        mDetailSharedHotComment = (TextView) mCommentTitleView.findViewById(R.id.detail_shared_hotComment);
        detail_shared_PraiseText = (TextView) mCommentTitleView.findViewById(R.id.detail_shared_PraiseText);
        detail_shared_AttentionImage = (ImageView) mCommentTitleView.findViewById(R.id.detail_shared_AttentionImage);
        mCommentLayout = (LinearLayout) mCommentTitleView.findViewById(R.id.detail_shared_Layout);
        detail_shared_CommentTitleLayout = (RelativeLayout) mCommentTitleView.findViewById(R.id.detail_shared_TitleLayout);

        detail_shared_FriendCircleLayout.getParent().requestDisallowInterceptTouchEvent(true);
        detail_shared_FriendCircleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logger.e("aaa", "点击朋友圈");
//                ShareSdkHelper.ShareToPlatformByNewsDetail(getActivity(), WechatMoments.NAME,mTitle , mNewID, "1");
//                MobclickAgent.onEvent(getActivity(),"qidian_detail_middle_share_weixin");
            }
        });
        detail_shared_CareForLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                MobclickAgent.onEvent(getActivity(),"qidian_detail_middle_like");
                Logger.e("aaa", "点击点赞");
                if(isLike){
                    isLike = false;
                    detail_shared_AttentionImage.setImageResource(R.drawable.bg_normal_attention);
                }else{
                    isLike = true;
                    mShowCareforLayout.show();
                    detail_shared_AttentionImage.setImageResource(R.drawable.bg_attention);
                }


            }
        });
        ////第3部分的CommentContent(这个内容是动态的获取数据后添加)

        //第4部分的viewPointContent
        View mViewPointLayout = inflater.inflate(R.layout.detail_shared_layout, container, false);
        mViewPointLayout.setLayoutParams(layoutParams);
        mNewsDetailHeaderView.addView(mViewPointLayout);


        detail_shared_ShareImageLayout = (RelativeLayout) mViewPointLayout.findViewById(R.id.detail_shared_ShareImageLayout);
        detail_shared_Text = (TextView) mViewPointLayout.findViewById(R.id.detail_shared_Text);
        detail_shared_MoreComment = (RelativeLayout) mViewPointLayout.findViewById(R.id.detail_shared_MoreComment);
        detail_shared_hotComment = (TextView) mViewPointLayout.findViewById(R.id.detail_shared_hotComment);
        detail_shared_ViewPointTitleLayout = (RelativeLayout) mViewPointLayout.findViewById(R.id.detail_shared_TitleLayout);

        detail_shared_ShareImageLayout.setVisibility(View.GONE);
        detail_shared_Text.setVisibility(View.GONE);
        detail_shared_MoreComment.setVisibility(View.VISIBLE);
        detail_shared_MoreComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewsDetailAty2 mActivity = (NewsDetailAty2) getActivity();
                if (!mActivity.isCommentPage) {
                    mActivity.isCommentPage = true;
                    mActivity.mNewsDetailViewPager.setCurrentItem(1);
                    mActivity.mDetailCommentPic.setImageResource(R.drawable.btn_detail_switch_comment);
                    mActivity.mDetailCommentNum.setVisibility(View.GONE);
                }
            }
        });

        detail_shared_hotComment.setText("相关观点");


        final LinearLayout footerView = (LinearLayout) inflater.inflate(R.layout.footerview_layout, null);
        lv.addFooterView(footerView);
        footView_tv = (TextView) footerView.findViewById(R.id.footerView_tv);
        footView_progressbar = (ProgressBar) footerView.findViewById(R.id.footerView_pb);





    }



    //    addNewsLoveListener addNewsLoveListener = new addNewsLoveListener() {
//        @Override
//        public void addLove(NewsDetailComment comment, int position) {
//            addNewsLove(comment);
//        }
//    };



    private void loadData() {

        Logger.e("jigang", "fetch comments url=" + HttpConstant.URL_FETCH_COMMENTS + "docid=" + mDocid);
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        NewsCommentRequest<ArrayList<NewsDetailComment>> feedRequest = null;
        NewsDetailRequest<RelatedEntity> related = null;
        try {
            feedRequest = new NewsCommentRequest<ArrayList<NewsDetailComment>>(Request.Method.GET, new TypeToken<ArrayList<NewsDetailComment>>() {
            }.getType(), HttpConstant.URL_FETCH_COMMENTS + "docid=" + URLEncoder.encode(mDocid, "utf-8") + "&page=" + (1), new Response.Listener<ArrayList<NewsDetailComment>>() {

                @Override
                public void onResponse(ArrayList<NewsDetailComment> result) {
                    isCommentSuccess = true;
                    isBgLayoutSuccess();
                    mNewsDetailList.onRefreshComplete();
                    Logger.e("jigang", "network success, comment" + result);

                    if (!TextUtil.isListEmpty(result)) {
                        mComments = result;
//                        mAdapter.setCommentList(mComments);
//                        mAdapter.notifyDataSetChanged();
                        Logger.d("aaa", "评论加载完毕！！！！！！");
                        //同步服务器上的评论数据到本地数据库
                      //  addCommentInfoToSql(mComments);
                        mDetailSharedHotComment.setText("热门评论("+mResult.getCommentSize()+")");
                        addCommentContent(result);
                    } else {
                        detail_shared_CommentTitleLayout.setVisibility(View.GONE);
                        detail_shared_MoreComment.setVisibility(View.GONE);

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    isCommentSuccess = true;
                    isBgLayoutSuccess();
                    mNewsDetailList.onRefreshComplete();
                    detail_shared_CommentTitleLayout.setVisibility(View.GONE);
                    detail_shared_MoreComment.setVisibility(View.GONE);
                    Logger.e("jigang", "network fail");
                }
            });
            related = new NewsDetailRequest<RelatedEntity>(Request.Method.GET,
                    new TypeToken<RelatedEntity>() {
                    }.getType(),
                    HttpConstant.URL_NEWS_RELATED + "url=" + TextUtil.getBase64(mNewID),
                    new Response.Listener<RelatedEntity>() {
                        @Override
                        public void onResponse(RelatedEntity response) {
                            isCorrelationSuccess = true;
                            isBgLayoutSuccess();
                            ArrayList<RelatedItemEntity> relatedItemEntities = response.getSearchItems();
                            Logger.e("jigang", "network success RelatedEntity~~" + response);

                            if (!TextUtil.isListEmpty(relatedItemEntities)) {
                                setBeanPageList(relatedItemEntities);
                                detail_shared_ViewPointTitleLayout.setVisibility(View.VISIBLE);
                            } else {
                                RelatedItemEntity entity = new RelatedItemEntity();
                                entity.setUrl("-1");
                                relatedItemEntities.add(entity);
                                mAdapter.setNewsFeed(relatedItemEntities);
                                mAdapter.notifyDataSetChanged();
                                detail_shared_ViewPointTitleLayout.setVisibility(View.GONE);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            isCorrelationSuccess = true;
                            isBgLayoutSuccess();
                            Logger.e("jigang", "network error~~");
                        }
                    });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        feedRequest.setRetryPolicy(new DefaultRetryPolicy(15000, 0, 0));
        related.setRetryPolicy(new DefaultRetryPolicy(15000, 0, 0));

        requestQueue.add(feedRequest);
        requestQueue.add(related);


    }

    private void addCommentInfoToSql(ArrayList<NewsDetailComment> mComments) {
        if (TextUtil.isListEmpty(mComments)){
            int commentNum = mComments.size();
            List<NewsDetailComment> newsDetailCommentItems = new ArrayList<>();
            String[] commentIds = new String[commentNum];
            for (int i=0;i<commentNum;i++){
                commentIds[i] = mComments.get(i).getComment_id();
            }
            mNewsDetailCommentDao = new NewsDetailCommentDao(getActivity());
            newsDetailCommentItems = mNewsDetailCommentDao.qureyByIds(commentIds);
            if(newsDetailCommentItems==null||newsDetailCommentItems.size()==0){
                return;
            }else{
                for(NewsDetailComment ndc:newsDetailCommentItems){
                    mNewsDetailCommentDao.update(ndc);
                }
            }
        }
    }

    ArrayList<ArrayList<RelatedItemEntity>> beanPageList = new ArrayList<ArrayList<RelatedItemEntity>>();
    ArrayList<RelatedItemEntity> beanList = new ArrayList<RelatedItemEntity>();
    int viewpointPage = 0;
    int pageSize = 6;
    int MAXPage;

    public void setBeanPageList(ArrayList<RelatedItemEntity> relatedItemEntities) {
        Logger.e("aaa", "time:================比较前=================");
        for (int i = 0; i < relatedItemEntities.size(); i++) {
            Logger.e("aaa", "time:===" + relatedItemEntities.get(i).getUpdateTime());
        }
        Collections.sort(relatedItemEntities);
        Logger.e("aaa", "time:================比较====后=================");
        for (int i = 0; i < relatedItemEntities.size(); i++) {
            Logger.e("aaa", "time:===" + relatedItemEntities.get(i).getUpdateTime());
        }
        int listSice = relatedItemEntities.size();
        int page = (listSice / pageSize) + (listSice % pageSize == 0 ? 0 : 1);
        MAXPage = page;
        int mYear = 0;
        for (int i = 0; i < page; i++) {
            ArrayList<RelatedItemEntity> listBean = new ArrayList<RelatedItemEntity>();
            for (int j = 0; j < pageSize; j++) {
                int itemPosition = j + i * pageSize;
                if (itemPosition + 1 > listSice) {
                    break;
                }
                Logger.e("aaa", "page:" + itemPosition);
                Calendar calendar = DateUtil.strToCalendarLong(relatedItemEntities.get(itemPosition).getUpdateTime());

                int thisYear = calendar.get(Calendar.YEAR);//获取年份
                if(thisYear != mYear){
                    mYear = thisYear;
                    relatedItemEntities.get(itemPosition).setYearFrist(true);
                }

                listBean.add(relatedItemEntities.get(itemPosition));
            }
            beanPageList.add(listBean);
        }
        beanList.addAll(beanPageList.get(viewpointPage));
        viewpointPage++;
        mAdapter.setNewsFeed(beanList);
        mAdapter.notifyDataSetChanged();
        if(MAXPage <= viewpointPage){
            mNewsDetailList.setMode(PullToRefreshBase.Mode.DISABLED);
            footView_tv.setText("内容加载完毕");
        }
    }

    private void addNewsLove(NewsDetailComment comment, final int position, final CommentHolder holder) {
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
                mNewsDetailList.onRefreshComplete();
                Logger.e("jigang", "network success, love" + result);
                if (!TextUtil.isEmptyString(result)) {
                    mComments.get(position).setPraise(true);
                    mComments.get(position).setLove(Integer.parseInt(result));
                    holder.ivPraise.setImageResource(R.drawable.bg_praised);
                    holder.tvPraiseCount.setText(result);
//                    viewList.get(position).invalidate();//刷新界面
//                    mAdapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mNewsDetailList.onRefreshComplete();
                Logger.e("jigang", "network fail");
            }
        });
        loveRequest.setRetryPolicy(new DefaultRetryPolicy(15000, 0, 0));
        requestQueue.add(loveRequest);
    }

    ArrayList<CommentHolder> holderList = new ArrayList<CommentHolder>();
    ArrayList<View> viewList = new ArrayList<View>();
    private View mCCView;

    public void addCommentContent(final ArrayList<NewsDetailComment> result) {
        int listSice = result.size();
        if (listSice == 0) {
            CommentType = 0;
            detail_shared_CommentTitleLayout.setVisibility(View.GONE);
            detail_shared_MoreComment.setVisibility(View.GONE);
//        }else if(listSice == 1){
//            ShowCommentBar();
//            mCCView = inflater.inflate(R.layout.adapter_list_comment1,container,false);
//            CommentHolder holder = new CommentHolder(mCCView);
//            holderList.add(holder);
//
//        }else if(listSice == 2){
//            ShowCommentBar();
//            for(int i = 0; i<listSice ;i++){
//                mCCView = inflater.inflate(R.layout.adapter_list_comment1,container,false);
//                CommentHolder holder = new CommentHolder(mCCView);
//                holderList.add(holder);
//            }
        } else {
            ShowCommentBar();
            for(int i = 0; i<listSice&&i<3 ;i++){
                CommentType = i+1;
                mCCView = inflater.inflate(R.layout.adapter_list_comment1,container,false);
                View mSelectCommentDivider = mCCView.findViewById(R.id.mSelectCommentDivider);
                CommentHolder holder = new CommentHolder(mCCView);

                int position = i;
                NewsDetailComment comment = result.get(i);

                UpdateCCView(holder, comment, position);
                holderList.add(holder);
                viewList.add(mCCView);
                if (i == 2){
                    mSelectCommentDivider.setVisibility(View.GONE);
                }
                mCommentLayout.addView(mCCView);

            }
        }
    }


    public void ShowCommentBar() {
        if (detail_shared_CommentTitleLayout.getVisibility() == View.GONE) {
            detail_shared_CommentTitleLayout.setVisibility(View.VISIBLE);
        }
        Logger.e("aaa","mComments.size() = " + mComments.size());
        if (mComments.size() > 3) {
            if (detail_shared_MoreComment.getVisibility() == View.GONE) {
                detail_shared_MoreComment.setVisibility(View.VISIBLE);
            }
        }else{
            if (detail_shared_MoreComment.getVisibility() == View.VISIBLE) {
                detail_shared_MoreComment.setVisibility(View.GONE);
            }
        }
    }

    class CommentHolder {
        SimpleDraweeView ivHeadIcon;
        TextViewExtend tvName;
        TextViewExtend tvContent;
        TextViewExtend tvPraiseCount;
        ImageView ivPraise;

        public CommentHolder(View convertView) {
            tvContent = (TextViewExtend) convertView.findViewById(R.id.tv_comment_content);
            ivHeadIcon = (SimpleDraweeView) convertView.findViewById(R.id.iv_user_icon);
            tvName = (TextViewExtend) convertView.findViewById(R.id.tv_user_name);
            ivPraise = (ImageView) convertView.findViewById(R.id.iv_praise);
            tvPraiseCount = (TextViewExtend) convertView.findViewById(R.id.tv_praise_count);
        }
    }

    /**
     * 通知新闻详情页和评论fragment刷新评论
     */
    public class RefreshPageBroReceiber extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent) {
            if (CommonConstant.CHANGE_TEXT_ACTION.equals(intent.getAction())) {
                Logger.e("aaa","详情页===文字的改变！！！");
//                int size = intent.getIntExtra("textSize", CommonConstant.TEXT_SIZE_NORMAL);
//                mSharedPreferences.edit().putInt("textSize", size).commit();
                mDetailWebView.loadDataWithBaseURL(null,TextUtil.genarateHTML(mResult, mSharedPreferences.getInt("textSize", CommonConstant.TEXT_SIZE_NORMAL)),
                        "text/html;charset=UTF-8", "utf-8", null);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT , LinearLayout.LayoutParams.WRAP_CONTENT);
                mDetailWebView.setLayoutParams(params);
                mAdapter.notifyDataSetChanged();
                CCViewNotifyDataSetChanged();

            }else{
//                Logger.e("jigang", "detailaty refresh br");
//                NewsDetailComment comment = (NewsDetailComment) intent.getSerializableExtra(UserCommentDialog.KEY_ADD_COMMENT);
//                mComments.add(0, comment);
//                UpdateCCOneData();
            }

        }
    }

    public void UpdateCCOneData() {
        if (CommentType == 3) {
            CCViewNotifyDataSetChanged();
            ShowCommentBar();
        } else {
            CommentType = CommentType + 1;
            mCCView = inflater.inflate(R.layout.adapter_list_comment1, container, false);
            CommentHolder holder = new CommentHolder(mCCView);
            holderList.add(holder);
            viewList.add(mCCView);
            CCViewNotifyDataSetChanged();
            mCommentLayout.addView(mCCView);
            ShowCommentBar();
        }

    }

    public void CCViewNotifyDataSetChanged() {
        for (int i = 0; i < CommentType; i++) {
            CommentHolder holder = holderList.get(i);
            NewsDetailComment newsDetailComment = mComments.get(i);
            UpdateCCView(holder, newsDetailComment, i);
        }
    }


    public void UpdateCCView(final CommentHolder holder, final NewsDetailComment comment, final int position) {
        final User user = SharedPreManager.getUser(getActivity());
        if (!TextUtil.isEmptyString(comment.getProfile())) {
            holder.ivHeadIcon.setImageURI(Uri.parse(comment.getProfile()));
        }
        holder.tvName.setText(comment.getNickname());
        holder.tvPraiseCount.setText(comment.getLove() + "");

        holder.tvContent.setTextSize(mSharedPreferences.getInt("textSize", CommonConstant.TEXT_SIZE_NORMAL));
        holder.tvContent.setText(comment.getContent());
        holder.tvContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logger.e("aaa", "点击内容");
            }
        });
        if (!comment.isPraise()) {
            holder.ivPraise.setImageResource(R.drawable.bg_normal_praise);
        } else {
            holder.ivPraise.setImageResource(R.drawable.bg_praised);
        }
        String commentUserid = comment.getUuid();
        if (commentUserid != null && commentUserid.length() != 0) {
            if (user.getUserId().equals(comment.getUuid())) {
                holder.ivPraise.setVisibility(View.GONE);
            } else {
                holder.ivPraise.setVisibility(View.VISIBLE);
            }
        }

        holder.ivPraise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (user == null) {
//                    Intent loginAty = new Intent(getActivity(), LoginAty.class);
//                    startActivityForResult(loginAty, REQUEST_CODE);
//                } else {
//                    addNewsLove(comment, position, holder);
//
//                }

            }
        });
    }

    public void isBgLayoutSuccess() {
        if (isCommentSuccess && isWebSuccess && isCorrelationSuccess && bgLayout.getVisibility() == View.VISIBLE) {
            bgLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mNewsDetailHeaderView.removeView(mDetailWebView);
        mDetailWebView.destroy();
    }

    public interface  ShowCareforLayout{
         void show();
    }

    ShowCareforLayout mShowCareforLayout;
    public void setShowCareforLayout(ShowCareforLayout showCareforLayout){
        mShowCareforLayout = showCareforLayout;
    }
}
