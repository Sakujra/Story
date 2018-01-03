package com.vincentlaf.story.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.View;
import android.widget.Adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.vincentlaf.story.R;
import com.vincentlaf.story.adapter.CollectionListAdapter;
import com.vincentlaf.story.adapter.CommentListAdapter;
import com.vincentlaf.story.bean.ItemCollection;
import com.vincentlaf.story.bean.ItemCommentList;

import java.util.ArrayList;
import java.util.List;

public class CollectionActivity extends AppCompatActivity {
    private List<ItemCollection> itemCollectionList=new ArrayList<>();
    private RecyclerView recyclerView;
    private CollectionListAdapter adapter;
    private Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        context=this;
        //评论列表初始化
        for (int i = 0; i < 10; i++) {
            itemCollectionList.add(new ItemCollection());
        }
        recyclerView=(RecyclerView) findViewById(R.id.z_recyclerview_collections);
        adapter = new CollectionListAdapter(R.layout.z_item_collection, itemCollectionList);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(context,StoryDetailsActivity.class));
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
    }
}
