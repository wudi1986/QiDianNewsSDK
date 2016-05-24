package com.news.qidian.entity;

import com.google.gson.reflect.TypeToken;
import com.news.qidian.utils.GsonUtil;

import java.io.Serializable;

/**
 * Created by fengjigang on 15/5/13.
 */
public class User implements Serializable {
    private String uuid;
    private String userId;
    //token的有效时常（单位：秒）
    private long expiresIn;
    //token的有效截止时间（单位：毫秒）
    private long expiresTime;
    private String token;
    private String userGender;
    private String userIcon;
    private String userName;
    private String platformType;
    private long firstLoginTime;
    private long lastLoginTime;

    @Override
    public String toString() {
        return "User{" +
                "uuid='" + uuid + '\'' +
                ", userId='" + userId + '\'' +
                ", expiresIn=" + expiresIn +
                ", expiresTime=" + expiresTime +
                ", token='" + token + '\'' +
                ", userGender='" + userGender + '\'' +
                ", userIcon='" + userIcon + '\'' +
                ", userName='" + userName + '\'' +
                ", platformType='" + platformType + '\'' +
                ", firstLoginTime=" + firstLoginTime +
                ", lastLoginTime=" + lastLoginTime +
                '}';
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUserId() {
        return userId+"";
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public long getExpiresTime() {
        return expiresTime;
    }

    public void setExpiresTime(long expiresTime) {
        this.expiresTime = expiresTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPlatformType() {
        return platformType;
    }

    public void setPlatformType(String platformType) {
        this.platformType = platformType;
    }

    public long getFirstLoginTime() {
        return firstLoginTime;
    }

    public void setFirstLoginTime(long firstLoginTime) {
        this.firstLoginTime = firstLoginTime;
    }

    public long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
     * 把json 反序列化为 User 对象
     * @param userStr
     * @return
     */
    public static User parseUser(String userStr) {
        return GsonUtil.deSerializedByType(userStr, new TypeToken<User>() {
        }.getType());
    }

    /**
     * 把User 对象序列化 json
     * @return
     */
    public String toJsonString(){
        return GsonUtil.serialized(this);
    }
}
