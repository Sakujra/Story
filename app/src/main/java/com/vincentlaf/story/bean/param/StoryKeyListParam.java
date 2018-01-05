package com.vincentlaf.story.bean.param;

/**
 * Created by Johnson on 2018/1/4.
 */

public class StoryKeyListParam extends QueryListParam {
    private int storyId;
    private int curUserId;
    public int getStoryId() {
        return storyId;
    }

    public void setStoryId(int storyId) {
        this.storyId = storyId;
    }

    public int getCurUserId() {
        return curUserId;
    }

    public void setCurUserId(int curUserId) {
        this.curUserId = curUserId;
    }
}
