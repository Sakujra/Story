package com.vincentlaf.story.others;

/**
 * Created by DengCun on 2017/12/30.
 * 用于存放marker的附带信息
 */

public class MarkerInfomation {
    private String placeName;

    public MarkerInfomation(String placeName) {
        setPlaceName(placeName);
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }
}
