package com.news.qidian.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Ariesymark on 2015/3/25.
 */

@DatabaseTable(tableName = "tb_news_feed")
public class NewsFeed implements Serializable {

    public static final int NO_PIC = 0;
    public static final int ONE_AND_TWO_PIC = 1;
    public static final int THREE_PIC = 2;
    public static final int TIME_LINE = 3;

    public static final String COLUMN_CHANNEL_ID = "channelId";
    public static final String COLUMN_NEWS_ID = "url";
    public static final String COLUMN_UPDATE_TIME = "pubTime";
    /**
     * 新闻发布时间
     */
    @DatabaseField
    private String pubTime;
    /**
     * 新闻来源地址,tips:此字段要当着获取详情的id使用
     */
    @DatabaseField(id = true)
    private String url;
    @DatabaseField
    private String docid;
    /**
     * 新闻评论
     */
    @DatabaseField
    private String commentsCount;
    /**
     * 新闻来源名称
     */
    @DatabaseField
    private String pubName;
    @DatabaseField
    private String pubUrl;
    /**
     * 新闻样式;0:没有图片,1:一张图片,2:两张图片,3:3张图片
     */
    @DatabaseField
    private String imgStyle;
    @DatabaseField
    private String title;
    @DatabaseField(dataType = DataType.SERIALIZABLE)
    private ArrayList<String> imgList;
    @DatabaseField
    private String channelId;
    /**
     *  这个参数是收藏用的单张图
     */
    private String imageUrl;
    /**
     * 用户是否看过
     */
    @DatabaseField(dataType = DataType.BOOLEAN)
    private boolean isRead;
    /**
     * 用户是否删除这条收藏数据
     */
    private boolean isFavorite = false;

    @Override
    public String toString() {
        return "NewsFeed{" +
                "pubTime='" + pubTime + '\'' +
                ", url='" + url + '\'' +
                ", docid='" + docid + '\'' +
                ", commentsCount='" + commentsCount + '\'' +
                ", pubName='" + pubName + '\'' +
                ", pubUrl='" + pubUrl + '\'' +
                ", imgStyle='" + imgStyle + '\'' +
                ", title='" + title + '\'' +
                ", imgList=" + imgList +
                ", channelId='" + channelId + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", isRead=" + isRead +
                '}';
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(String commentsCount) {
        this.commentsCount = commentsCount;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public ArrayList<String> getImgList() {
        return imgList;
    }

    public void setImgList(ArrayList<String> imgList) {
        this.imgList = imgList;
    }

    public String getImgStyle() {
        return imgStyle;
    }

    public void setImgStyle(String imgStyle) {
        this.imgStyle = imgStyle;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getPubName() {
        return pubName;
    }

    public void setPubName(String pubName) {
        this.pubName = pubName;
    }

    public String getPubTime() {
        return pubTime;
    }

    public void setPubTime(String pubTime) {
        this.pubTime = pubTime;
    }

    public String getPubUrl() {
        return pubUrl;
    }

    public void setPubUrl(String pubUrl) {
        this.pubUrl = pubUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
