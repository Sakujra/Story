package com.vincentlaf.story.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.vincentlaf.story.R;
import com.vincentlaf.story.adapter.StoryListAdapter;
import com.vincentlaf.story.bean.ItemStoryList;

import java.util.ArrayList;

/**
 * Created by VincentLaf on 2017/12/17.
 */

public class FragmentTab2 extends Fragment {

    private ArrayList<ItemStoryList> mItemList = new ArrayList<>();

    private RecyclerView mRecyclerView;

    private StoryListAdapter mStoryListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab2, container, false);
        mRecyclerView = view.findViewById(R.id.z_recyclerview_frag2);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        for (int i = 0; i < 10; i++) {
            mItemList.add(new ItemStoryList());
        }
        mStoryListAdapter = new StoryListAdapter(R.layout.z_item_storylist, mItemList);
        mStoryListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerView.setAdapter(mStoryListAdapter);
    }


}
