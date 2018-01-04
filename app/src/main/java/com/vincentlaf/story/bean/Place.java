package com.vincentlaf.story.bean;

/**
 * Created by Johnson on 2018/1/4.
 * 地点
 */

public class Place {
    private int pId;
    private Double lon;
    private Double lat;
    private String name;

    public int getpId() {
        return pId;
    }
    public void setpId(int pId) {
        this.pId = pId;
    }
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
    public String getName() {
        return name==null?"Unkown":name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
