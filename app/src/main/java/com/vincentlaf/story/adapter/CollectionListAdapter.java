package com.vincentlaf.story.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.vincentlaf.story.bean.ItemCollection;

import java.util.List;

/**
 * Created by DengCun on 2018/1/3.
 */

public class CollectionListAdapter extends BaseQuickAdapter<ItemCollection,BaseViewHolder> {
    public CollectionListAdapter(int layoutResId, @Nullable List<ItemCollection> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ItemCollection item) {

    }
}
