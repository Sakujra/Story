package com.vincentlaf.story.bean.result;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

/**
 * Created by Johnson on 2018/1/3.
 * 网络请求结果
 */

public class Result extends JSONObject {

    /**
     * 包含信息
     */
    private JSONObject data;
    /**
     * 包含请求状态码及msg
     */
    private JSONObject result;

    public Result(JSONObject data, JSONObject result) {
        this.data = data;
        this.result = result;
    }


    public String getMsg() {
        return result.getString("msg");
    }

    public int getCode() {
        return result.getInteger("code");
    }

    /**
     * 获取结果集中的实体类对象
     *
     * @param tClass 实体类对象类型
     * @param <T>    泛型
     * @return {@link T}
     */
    public <T> T getEntityData(Class<T> tClass) {
        return data.getObject("data", tClass);
    }

    /**
     * 获取结果集中的list对象
     *
     * @param tClass list对象包含的对象类型
     * @param <T>    泛型
     * @return {@link QueryResult<T>} 结果集
     */
    public <T> QueryResult<T> getList(Class<T> tClass) {
        QueryResult<T> result = new QueryResult<>();
        JSONObject queryResult = data.getJSONObject("data");
        long total = queryResult.getLong("total");
        JSONArray array = queryResult.getJSONArray("rows");
        ArrayList<T> rows = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            JSONObject jobj = array.getJSONObject(i);
            rows.add(JSONObject.toJavaObject(jobj, tClass));
        }
        result.setRows(rows);
        result.setTotal(total);
        return result;
    }

    @Override
    public String toString() {
        return "{" + result.toJSONString() + ",\n" + data.toJSONString() +
                "}";
    }
}
