package com.news.qidian.net.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.news.qidian.common.HttpConstant;
import com.news.qidian.database.DiggerAlbumDao;
import com.news.qidian.entity.DiggerAlbum;
import com.news.qidian.entity.User;
import com.news.qidian.listener.FetchAlbumListListener;
import com.news.qidian.net.JsonCallback;
import com.news.qidian.net.MyAppException;
import com.news.qidian.net.NetworkRequest;
import com.news.qidian.utils.TextUtil;
import com.news.qidian.utils.manager.SharedPreManager;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

/**
 * Created by fengjigang on 15/8/5.
 * 获取专辑列表
 */
public class FetchAlbumListRequest {
    /**使用用户id,获取专辑列表*/
    public final static String KEY_USERID = "user_id";

    /**
     * 获取专辑列表
     * @param pContext 上下文
     * @param pListener 回调接口
     */
    public static void obtainAlbumList(final Context pContext, final FetchAlbumListListener pListener){
        NetworkRequest request = new NetworkRequest(HttpConstant.URL_FETCH_ALBUM_LIST, NetworkRequest.RequestMethod.POST);
        ArrayList<NameValuePair> params = new ArrayList<>();
        //此处默认用户是已经登录过的
        User user = SharedPreManager.getUser(pContext);
        params.add(new BasicNameValuePair(KEY_USERID,user.getUserId()));
        request.setParams(params);
        request.setCallback(new JsonCallback<ArrayList<DiggerAlbum>>() {
            @Override
            protected void asyncPostRequest(ArrayList<DiggerAlbum> diggerAlbums) {
                //把专辑信息存入数据库
                DiggerAlbumDao diggerAlbumDao = new DiggerAlbumDao(pContext);
                if (!TextUtil.isListEmpty(diggerAlbums)) {
                    for (DiggerAlbum album : diggerAlbums) {
                        DiggerAlbum existAlbum = diggerAlbumDao.queryById(album.getAlbum_id());
                        if (existAlbum == null) {
                            diggerAlbumDao.insert(album);
                        }
                    }
                }
            }

            @Override
            public void success(ArrayList<DiggerAlbum> result) {
                if (pListener != null) {
                    pListener.success(result);
                }
            }

            @Override
            public void failed(MyAppException exception) {
                if (pListener != null) {
                    pListener.failure();
                }
            }
        }.setReturnType(new TypeToken<ArrayList<DiggerAlbum>>() {
        }.getType()));
        request.execute();
    }
}
