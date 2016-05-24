package com.news.qidian.listener;

import cn.sharesdk.framework.PlatformDb;

/**
 * Created by fengjigang on 15/5/8.
 * 用户登陆和注销接口
 */
public interface UserLoginListener {
    void userLogin(String platform, PlatformDb platformDb);
    void userLogout();
}
