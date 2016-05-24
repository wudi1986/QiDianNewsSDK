package com.news.qidian.listener;

import com.news.qidian.net.MyAppException;

/**
 * Created by fiocca on 15/5/15.
 */
public interface SendMessageListener {
     void success(String result);

     void failed(MyAppException exception);
}
