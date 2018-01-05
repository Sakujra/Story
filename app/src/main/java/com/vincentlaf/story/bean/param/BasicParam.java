package com.vincentlaf.story.bean.param;

/**
 * Created by Johnson on 2018/1/3.
 * 基础参数 当前位置的经纬度
 */

public class BasicParam {
    private int userId;
    private Double lon;
    private Double lat;
    public Double getLon() {
        return lon;
    }
    public void setLon(Double lon) {
        this.lon = lon;
    }
    public Double getLat() {
        return lat;
    }
    public void setLat(Double lat) {
        this.lat = lat;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}