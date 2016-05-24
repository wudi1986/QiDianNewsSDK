package com.news.qidian.common;

/**
 * Created by fengjigang on 15/1/21.
 */
public class HttpConstant {
    /**
     * 服务器api 域名
     */
    public static final String URL_SERVER_HOST = "http://api.deeporiginalx.com";

    public static final String URL_PRAISE = URL_SERVER_HOST + "/news/baijia/praise";

    public static final String URL_GET_NEWS_CONTENT = URL_SERVER_HOST + "/news/baijia/point";

    /**
     * 谷歌今日焦点
     */
    public static final String URL_USER_LOGIN = URL_SERVER_HOST + "/news/baijia/fetchLogin";

    public static final String URL_UPLOAD_JPUSHID = URL_SERVER_HOST + "/news/baijia/fetchImUser?";

    public static final String URL_UPLOAD_UMENGPUSHID = URL_SERVER_HOST + "/news/baijia/uploadUmengPushId?";

    public static final String URL_SEND_MESSAGE = URL_SERVER_HOST + "/news/baijia/fetchIm?";

    public static final String URL_GET_HISTORY_MESSAGE = URL_SERVER_HOST + "/news/baijia/fetchImContent?";

    public static final String URL_GET_MESSAGE_LIST = URL_SERVER_HOST + "/news/baijia/fetchImList?";

    /**
     * 获取专辑列表接口
     */
    public static final String URL_FETCH_ALBUM_LIST = URL_SERVER_HOST + "/news/baijia/fetchAlbumList?";

    /**
     * 挖掘机创建专辑接口
     */
    public static final String URL_CREATE_DIGGER_ALBUM = URL_SERVER_HOST + "/news/baijia/createAlbum?";

    /**
     * 往专辑中添加挖掘内容
     */
    public static final String URL_DIGGER_ALBUM = "http://60.28.29.37:8080/excavator?";

    /**
     * 开始挖掘新闻
     */
    public static final String URL_DIGGER_NEWS = URL_SERVER_HOST + "/news/baijia/dredgeUpStatusforiOS";

    /**
     * 获取指定专辑中的挖掘内容
     */
    public static final String URL_FETCH_ALBUM_SUBITEMS = URL_SERVER_HOST + "/news/baijia/dredgeUpStatus?";

    /**
     * 获取热点话题标签
     */
    public static final String URL_FETCH_ELEMENTS = URL_SERVER_HOST + "/news/baijia/fetchElementary";

    /**
     * 获取新闻详情 api
     */
    public static final String URL_POST_NEWS_DETAIL = URL_SERVER_HOST + "/news/baijia/fetchDetail";

    /**
     * feed流上拉加载更多
     */
    public static final String URL_FEED_LOAD_MORE = URL_SERVER_HOST + "/bdp/news/load?";

    /**
     * feed流下拉刷新
     */
    public static final String URL_FEED_PULL_DOWN = URL_SERVER_HOST + "/bdp/news/refresh?";

    /**
     * 获取新闻详情页
     */
    public static final String URL_FETCH_CONTENT = URL_SERVER_HOST + "/bdp/news/content?";

    /**
     * 获取新闻评论
     */
    public static final String URL_FETCH_COMMENTS = URL_SERVER_HOST + "/bdp/news/comment/ydzx?";

    /**
     * 新闻评论点赞
     */
    public static final String URL_LOVE_COMMENT = URL_SERVER_HOST + "/bdp/news/comment/ydzx/love?";

    /**
     * 添加新闻评论
     */
    public static final String URL_ADD_COMMENT = URL_SERVER_HOST + "/bdp/news/comment/ydzx";
    /**
     * 日志上传
     */
    public static final String URL_UPLOAD_LOG =  "http://bdp.deeporiginalx.com/rep?";

    /**
     * 新闻客户端-新闻相关属性集
     */
    public static final String URL_NEWS_RELATED = URL_SERVER_HOST + "/bdp/news/related?";

}
