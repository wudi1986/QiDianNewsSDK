package com.news.qidian.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/4/18.
 */
public class UploadLogDataEntity implements Serializable {
    private String nid;
    private String cid;
    private String tid;
    private String stime;
    private String sltime;
    private String from;

    public UploadLogDataEntity(String nid, String cid, String tid, String stime, String sltime, String from) {
        this.nid = nid;
        this.cid = cid;
        this.tid = tid;
        this.stime = stime;
        this.sltime = sltime;
        this.from = from;
    }

    public UploadLogDataEntity() {
    }



    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public String getSltime() {
        return sltime;
    }

    public void setSltime(String sltime) {
        this.sltime = sltime;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
