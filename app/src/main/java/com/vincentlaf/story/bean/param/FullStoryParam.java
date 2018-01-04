package com.vincentlaf.story.bean.param;

import java.util.Date;

/**
 * Created by Johnson on 2018/1/4.
 */

public class FullStoryParam extends BasicParam {
    private int uid;
    /** 发布故事请求时可不用传入此参数*/
    private Date date;
    private String placeName;
    private String title;
    private String content;
    private String pic;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
    public void setContent(String title,String content,String pic){
        this.title=title;
        this.content=content;
        this.pic =pic;
    }
    public void setPlace(Double lon,Double lat,String name){
        this.setLon(lon);
        this.setLat(lat);
        this.placeName=name;
    }
}
