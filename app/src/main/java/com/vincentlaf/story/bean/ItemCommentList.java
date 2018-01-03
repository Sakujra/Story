package com.vincentlaf.story.bean;

/**
 * Created by VincentLaf on 2017/12/30.
 */

public class ItemCommentList {

    private String imgUrl;
    private String name;
    private Boolean isLike;
    private int likeCount;
    private String content;
    private String time;


    public void setLike(Boolean like) {
        isLike = like;
    }

    public Boolean getLike() {

        return isLike;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }


    public String getImgUrl() {

        return imgUrl;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }

    public int getLikeCount() {
        return likeCount;
    }

}
