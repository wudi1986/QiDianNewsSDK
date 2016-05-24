//package com.news.qidian.net.request;
//
//import com.news.qidian.common.HttpConstant;
//import com.news.qidian.entity.User;
//import com.news.qidian.listener.UserLoginRequestListener;
//import com.news.qidian.net.MyAppException;
//import com.news.qidian.net.NetworkRequest;
//import com.news.qidian.net.UserCallback;
//import com.news.qidian.utils.DeviceInfoUtil;
//
//import java.util.HashMap;
//
//
///**
// * Created by fengjigang on 15/5/13.
// * 用户登录的网络请求
// */
//public class UserLoginRequest {
//    public static void userLogin(PlatformDb platformDb, final UserLoginRequestListener listener){
//        HashMap<String,Object> params=new HashMap<>();
//        params.put("uuid", DeviceInfoUtil.getUUID());
//        params.put("userId",platformDb.getUserId());
//        params.put("expiresIn",platformDb.getExpiresIn());
//        params.put("expiresTime",platformDb.getExpiresTime());
//        params.put("token",platformDb.getToken());
//        params.put("userGender",platformDb.getUserGender());
//        params.put("userIcon",platformDb.getUserIcon());
//        params.put("userName",platformDb.getUserName());
//        params.put("platformType",platformDb.getPlatformNname());
//        NetworkRequest request=new NetworkRequest(HttpConstant.URL_USER_LOGIN, NetworkRequest.RequestMethod.GET);
//        request.getParams=params;
//        request.setCallback(new UserCallback<User>() {
//            @Override
//            public void success(User user) {
//                if(listener!=null){
//                    listener.success(user);
//                }
//            }
//
//            @Override
//            public void failed(MyAppException exception) {
//                if(listener!=null){
//                    listener.failed(exception);
//                }
//            }
//        }.setReturnClass(User.class));
//        request.execute();
//    }
//}
