package com.vincentlaf.story.bean.result;

import java.util.Date;

/**
 * Created by Johnson on 2018/1/4.
 */

public class Comment {
    private int uid;
    private int sid;
    private String ctt;
    private Date date;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getCtt() {
        return ctt;
    }

    public void setCtt(String ctt) {
        this.ctt = ctt;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
