package com.vincentlaf.story.util;


import android.support.annotation.Nullable;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.vincentlaf.story.bean.result.Result;
import com.vincentlaf.story.exception.WrongRequestException;

import java.io.IOException;
import java.net.ConnectException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Johnson on 2017/12/29.
 * 网络请求工具
 */

public class RequestUtil {

    private static final String TAG = "RequestUtil";

    //192.168.1.100 wifi地址
    public static final String wifiUrl = "http://192.168.1.100/story/%s.do";
    public static final String headimage = "http://192.168.1.100/story/head/%s.jpg";
    public static final String monitorUrl = "http://10.0.2.2/story/%s.do";
    public static final String testUrl = "http://127.0.0.1/story/%s.do";

    private static String getUrl(String url, String method) {
        return String.format(url, method);

    }

    /**
     * post 方法
     *
     * @param url    主机名
     * @param method 请求方法(请求登录时,此参数为login)
     * @param params json 参数列表
     * @return {@link JSONObject} 回传参数
     */
    private static @Nullable
    Result doPost(String url, String method, JSONObject params) throws IOException, WrongRequestException {
        OkHttpClient client = new OkHttpClient();
        MediaType jsonType = MediaType.parse("application/json;charset=UTF-8");
        RequestBody requestBody = RequestBody.create(jsonType, JSONObject.toJSONString(params));
        Request request = new Request.Builder().url(getUrl(url, method)).post(requestBody).build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new WrongRequestException("请求错误");
        }
        ResponseBody responseBody = response.body();
        if (!responseBody.contentType().equals(jsonType)) {
            throw new WrongRequestException("返回格式错误");
        }
        JSONObject jsonObject = JSONObject.parseObject(responseBody.string());
        return new Result(jsonObject.getJSONObject("data"), jsonObject.getJSONObject("result"));
    }

    /**
     * POST 请求
     *
     * @param url    主机位置 包含在当前类中
     * @param method 请求方法，包含 {@link com.vincentlaf.story.bean.Method} 中
     * @param params 请求参数对象，除登录注册外，所有参数都必须继承于{@link com.vincentlaf.story.bean.param.BasicParam}
     * @return {@link Result} data可能包含{@link com.vincentlaf.story.bean.result.QueryResult} 或者 netbean包下的实体类
     * @throws IOException           网络错误时抛出
     * @throws WrongRequestException 请求错误时抛出
     */
    public static @Nullable
    Result doPost(String url, String method, Object params) throws IOException, WrongRequestException {
        OkHttpClient client = new OkHttpClient();
        MediaType jsonType = MediaType.parse("application/json;charset=UTF-8");
        RequestBody requestBody = RequestBody.create(jsonType, JSONObject.toJSONString(params));
        Request request = new Request.Builder().url(getUrl(url, method)).post(requestBody).build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            Log.d(TAG, "doPost code : " + response.code());
            throw new WrongRequestException("请求错误");
        }
        ResponseBody responseBody = response.body();
        if (!responseBody.contentType().equals(jsonType)) {
            throw new WrongRequestException("返回格式错误");
        }
        JSONObject jsonObject = JSONObject.parseObject(responseBody.string());
        return new Result(jsonObject.getJSONObject("data"), jsonObject.getJSONObject("result"));
    }

    /**
     * 拼接头像地址
     *
     * @param userPhone 用户手机号
     * @return http地址
     */
    public static String getHeadImage(String userPhone) {
        return String.format(headimage, userPhone);
    }
}
