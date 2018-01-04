package com.vincentlaf.story.bean.param;

/**
 * Created by Johnson on 2018/1/3.
 * 带页数的参数
 */

public class QueryListParam extends BasicParam {
    private int page;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
