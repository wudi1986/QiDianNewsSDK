package com.news.qidian.net.volley;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.news.qidian.utils.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fengjigang on 16/2/26.
 */
public class NewsLoveRequest<T> extends GsonRequest<T> {
    private HashMap mParams;

    public NewsLoveRequest(int method, Class clazz, String url, Response.Listener successListener, Response.ErrorListener listener) {
        super(method, clazz, url, successListener, listener);
    }

    public NewsLoveRequest(int method, Type reflectType, String url, Response.Listener successListener, Response.ErrorListener listener) {
        super(method, reflectType, url, successListener, listener);
    }
    public void setRequestParams(HashMap params){
        this.mParams = params;
    }
    @Override
    protected String checkJsonData(String data, NetworkResponse response) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            String code = jsonObject.optString("code", "");
            String message = jsonObject.optString("message", "");
            Logger.e("jigang","code = "+code + ",message=" + message);
            if ("0".equals(code) && "success".equals(message)){
                return jsonObject.optString("data","");
            } else {
                return "";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mParams;
    }
}
