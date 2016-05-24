package com.news.qidian.listener;

import com.news.qidian.entity.User;

/**
 * Created by fengjigang on 16/4/6.
 * 用户授权登录回调
 */
public interface UserAuthorizeListener {
    void success(User user);
    void failure(String message);
    void cancel();
}
