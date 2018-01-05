package com.vincentlaf.story.adapter;


import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.vincentlaf.story.R;
import com.vincentlaf.story.bean.ItemStoryList;
import com.vincentlaf.story.bean.netbean.StoryListInfo;
import com.vincentlaf.story.util.RequestUtil;
import com.vincentlaf.story.util.StringUtil;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by VincentLaf on 2017/12/29.
 */

public class StoryListAdapter extends BaseQuickAdapter<StoryListInfo, BaseViewHolder> {
    public StoryListAdapter(int resId, List data) {
        super(resId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, StoryListInfo item) {
//        helper.setText(R.id.text, item.getTitle());
//        helper.setImageResource(R.id.icon, item.getImageResource());
//        // 加载网络图片
//        Glide.with(mContext).load(item.getUserAvatar()).crossFade().into((ImageView) helper.getView(R.id.iv));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        helper.setText(R.id.z_story_item_title, item.getTitle());
        helper.setText(R.id.z_story_item_content, item.getContent());
        helper.setText(R.id.z_story_item_username, item.getUserName());
        helper.setText(R.id.z_story_item_date, simpleDateFormat.format(item.getDate()));
        String dis = String.valueOf(item.getDistance());
        helper.setText(R.id.z_story_item_distance, dis.substring(0, dis.indexOf('.') + 3) + "km");
        Glide.with(mContext)
                .load(RequestUtil.getHeadImage(item.getUserPic()))
                .into((ImageView) helper.getView(R.id.z_story_item_img));
    }
}


