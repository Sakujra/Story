package com.vincentlaf.story.adapter;


import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.vincentlaf.story.R;
import com.vincentlaf.story.bean.ItemCommentList;
import com.vincentlaf.story.bean.ItemStoryList;
import com.vincentlaf.story.others.App;

import java.util.List;

/**
 * Created by VincentLaf on 2017/12/29.
 */

public class CommentListAdapter extends BaseQuickAdapter<ItemCommentList, BaseViewHolder> {
    public CommentListAdapter(int resId, List data) {
        super(resId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ItemCommentList item) {
//        helper.setText(R.id.text, item.getTitle());
//        helper.setImageResource(R.id.icon, item.getImageResource());
//        // 加载网络图片
//        Glide.with(mContext).load(item.getUserAvatar()).crossFade().into((ImageView) helper.getView(R.id.iv));

        helper.addOnClickListener(R.id.z_txt_comment_item_like);

        //点赞部分
        View view = helper.getView(R.id.z_txt_comment_item_like);
        if (view instanceof TextView) {
            if (item.getLike()) {
                //点赞
                Drawable icon = App.getContext().getResources().getDrawable(R.drawable.ic_like_24dp);
                icon.setBounds(0, 0, icon.getMinimumWidth(), icon.getMinimumHeight());
                ((TextView) view).setCompoundDrawables(icon, null, null, null);
            } else {
                //不点赞
                Drawable icon = App.getContext().getResources().getDrawable(R.drawable.ic_like_not_24dp);
                icon.setBounds(0, 0, icon.getMinimumWidth(), icon.getMinimumHeight());
                ((TextView) view).setCompoundDrawables(icon, null, null, null);
            }
        }

    }
}


