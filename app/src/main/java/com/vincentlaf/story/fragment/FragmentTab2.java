package com.vincentlaf.story.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.vincentlaf.story.R;
import com.vincentlaf.story.activity.StoryDetailsActivity;
import com.vincentlaf.story.adapter.StoryListAdapter;
import com.vincentlaf.story.bean.ItemStoryList;
import com.vincentlaf.story.others.ToastUtil;

import java.util.ArrayList;

/**
 * Created by VincentLaf on 2017/12/17.
 */

public class FragmentTab2 extends Fragment {

    private ArrayList<ItemStoryList> mItemList = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private StoryListAdapter mStoryListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab2, container, false);
        mRecyclerView = view.findViewById(R.id.z_recyclerview_frag2);
        mSwipeRefreshLayout = view.findViewById(R.id.z_swipeRefreshLayout_frag2);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        for (int i = 0; i < 10; i++) {
            mItemList.add(new ItemStoryList());
        }
        mStoryListAdapter = new StoryListAdapter(R.layout.z_item_storylist, mItemList);
        //列表项点击事件
        mStoryListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), StoryDetailsActivity.class));
            }
        });
        //加载更多
        mStoryListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //ui线程
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mStoryListAdapter.loadMoreComplete();
                    }
                });
            }
        }, mRecyclerView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerView.setAdapter(mStoryListAdapter);

        //刷新事件
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //ui线程
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mSwipeRefreshLayout.setRefreshing(false);
                                ToastUtil.toast("加载成功");
                            }
                        });
                    }
                }).start();
            }
        });
    }

}
