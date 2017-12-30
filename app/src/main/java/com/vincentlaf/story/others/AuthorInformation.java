package com.vincentlaf.story.others;

/**
 * Created by DengCun on 2017/12/30.
 */

public class AuthorInformation {

    private String authorName;
    private int img;
    private String title;
    private String content;

    public  AuthorInformation(String authorName,int img,String title,String content){
        setAuthorName(authorName);
        setContent(content);
        setImg(img);
        setTitle(title);
    }
    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
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
}
