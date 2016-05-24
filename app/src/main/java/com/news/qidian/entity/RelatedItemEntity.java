package com.news.qidian.entity;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/4/22.
 */
public class RelatedItemEntity implements Serializable,Comparable {
    private String url;
    private int rank;
    private String sourceSite;
    private String searchFrom;
    private String imgUrl;
    private String title;
    private String abs;
    private String updateTime;
    /**
     * 莫一年的第一天
     */
    private boolean yearFrist;

    public RelatedItemEntity(String url, int rank, String sourceSite, String searchFrom, String imgUrl, String title, String abs, String updateTime) {
        this.url = url;
        this.rank = rank;
        this.sourceSite = sourceSite;
        this.searchFrom = searchFrom;
        this.imgUrl = imgUrl;
        this.title = title;
        this.abs = abs;
        this.updateTime = updateTime;
    }

    public RelatedItemEntity() {
    }
    @Override
    public int compareTo(Object o) {
        RelatedItemEntity itemEntity = (RelatedItemEntity) o;
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = format.parse(itemEntity.getUpdateTime());
            date2 = format.parse(this.updateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int flag = (date1.getTime() + "").compareTo((date2.getTime() + ""));


        return flag;
    }
    @Override
    public String toString() {
        return "RelatedItemEntity{" +
                "url='" + url + '\'' +
                ", rank=" + rank +
                ", sourceSite='" + sourceSite + '\'' +
                ", searchFrom='" + searchFrom + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", title='" + title + '\'' +
                ", abs='" + abs + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }

    public String toTimeString() {
        return "RelatedItemEntity{" +
                "updateTime='" + updateTime + '\'' +
                '}';
    }

    public boolean getYearFrist() {
        return yearFrist;
    }

    public void setYearFrist(boolean yearFrist) {
        this.yearFrist = yearFrist;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getSourceSite() {
        return sourceSite;
    }

    public void setSourceSite(String sourceSite) {
        this.sourceSite = sourceSite;
    }

    public String getSearchFrom() {
        return searchFrom;
    }

    public void setSearchFrom(String searchFrom) {
        this.searchFrom = searchFrom;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbs() {
        return abs;
    }

    public void setAbs(String abs) {
        this.abs = abs;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }


}
