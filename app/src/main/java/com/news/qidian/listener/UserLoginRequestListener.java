package com.news.qidian.listener;

import com.news.qidian.entity.User;
import com.news.qidian.net.MyAppException;

/**
 * Created by fengjigang on 15/5/13.
 * 用户登录请求回调接口
 */
public interface UserLoginRequestListener {
    void success(User user);
    void failed(MyAppException exception);
}
