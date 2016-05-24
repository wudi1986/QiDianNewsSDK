package com.news.qidian.net.request;

import com.news.qidian.common.HttpConstant;
import com.news.qidian.entity.Message;
import com.news.qidian.listener.SendMessageListener;
import com.news.qidian.net.MyAppException;
import com.news.qidian.net.NetworkRequest;
import com.news.qidian.net.StringCallback;

import java.util.HashMap;

/**
 * Created by fiocca on 15/5/15.
 * 发送消息的网络请求
 */
public class SendMessageRequest {
    public static void sendMessage(Message message,final SendMessageListener listener) {
        NetworkRequest request = new NetworkRequest(HttpConstant.URL_SEND_MESSAGE, NetworkRequest.RequestMethod.GET);
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("message",message.toJsonString());
        request.getParams = hashMap;
        request.setCallback(new StringCallback() {
            @Override
            public void success(String result) {
                if (listener != null) {
                    listener.success(result);
                }
            }

            @Override
            public void failed(MyAppException exception) {
                if (listener != null) {
                    listener.failed(exception);
                }
            }
        });
        request.execute();
    }
}
