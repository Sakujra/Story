package com.vincentlaf.story.bean;

/**
 * Created by Johnson on 2018/1/4.
 * 故事内容
 */
public class Content {
    private int cId;
    private String title;
    private String content;
    private String cPic;
    public int getcId() {
        return cId;
    }
    public void setcId(int cId) {
        this.cId = cId;
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
    public String getcPic() {
        return cPic;
    }
    public void setcPic(String cPic) {
        this.cPic = cPic;
    }

}
