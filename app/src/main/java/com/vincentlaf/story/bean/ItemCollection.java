package com.vincentlaf.story.bean;



/**
 * Created by DengCun on 2018/1/3.
 */

public class ItemCollection {
    private String imgUrl;
    private String title;
    private String time;
    private int likeCount;
    private int commentCount;

    public ItemCollection(){

    }
    public ItemCollection(String imgUrl,String title,String time,int likeCount,int commentCount){
        setImgUrl(imgUrl);
        setCommentCount(commentCount);
        setLikeCount(likeCount);
        setTime(time);
        setTitle(title);
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
}
