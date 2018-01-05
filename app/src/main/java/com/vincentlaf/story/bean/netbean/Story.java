package com.vincentlaf.story.bean.netbean;

import java.util.Date;

/**
 * @author Johnson
 */
public class Story {
    private int storyId;
    private int uid;
    private int cid;
    private String title;
    private String content;
    private String pic;
    private Date date;
    private int likeNum;
    private int favNum;
    private int commentNum;
    private String placeName;
    private Double lon;
    private Double lat;
    private boolean isLike;
    private boolean isFav;

    public boolean isLike() {
        return isLike;
    }
    public void setLike(boolean isLike) {
        this.isLike = isLike;
    }
    public boolean isFav() {
        return isFav;
    }
    public void setFav(boolean isFav) {
        this.isFav = isFav;
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
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public int getFavNum() {
        return favNum;
    }
    public void setFavNum(int favNum) {
        this.favNum = favNum;
    }
    public int getCommentNum() {
        return commentNum;
    }
    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }
    public int getStoryId() {
        return storyId;
    }
    public void setStoryId(int storyId) {
        this.storyId = storyId;
    }
    public int getUid() {
        return uid;
    }
    public void setUid(int uid) {
        this.uid = uid;
    }
    public String getPlaceName() {
        return placeName;
    }
    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }
    public int getCid() {
        return cid;
    }
    public void setCid(int cid) {
        this.cid = cid;
    }
    public int getLikeNum() {
        return likeNum;
    }
    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }
}

