package com.vincentlaf.story.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.vincentlaf.story.bean.ItemStoryList;

import java.util.List;

/**
 * Created by VincentLaf on 2017/12/29.
 */

public class StoryListAdapter extends BaseQuickAdapter<ItemStoryList, BaseViewHolder> {
    public StoryListAdapter(int resId, List data) {
        super(resId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ItemStoryList item) {
//        helper.setText(R.id.text, item.getTitle());
//        helper.setImageResource(R.id.icon, item.getImageResource());
//        // 加载网络图片
//        Glide.with(mContext).load(item.getUserAvatar()).crossFade().into((ImageView) helper.getView(R.id.iv));
    }
}


