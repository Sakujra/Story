package com.vincentlaf.story.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.vincentlaf.story.R;
import com.vincentlaf.story.activity.StoryDetailsActivity;
import com.vincentlaf.story.adapter.StoryListAdapter;
import com.vincentlaf.story.bean.ItemStoryList;
import com.vincentlaf.story.bean.Method;
import com.vincentlaf.story.bean.User;
import com.vincentlaf.story.bean.netbean.StoryListInfo;
import com.vincentlaf.story.bean.param.QueryListParam;
import com.vincentlaf.story.bean.result.QueryResult;
import com.vincentlaf.story.bean.result.Result;
import com.vincentlaf.story.exception.WrongRequestException;
import com.vincentlaf.story.others.App;
import com.vincentlaf.story.util.RequestUtil;
import com.vincentlaf.story.util.ToastUtil;

import java.util.ArrayList;

/**
 * Created by VincentLaf on 2017/12/17.
 */

public class FragmentTab2 extends Fragment {

    private static final String TAG = "FragmentTab2";

    private boolean hasNextPage = true;
    private int nextPage = 1;

    private ArrayList<StoryListInfo> mItemList = new ArrayList<>();

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

        mSwipeRefreshLayout.setRefreshing(true);
        refreshData();

        mStoryListAdapter = new StoryListAdapter(R.layout.z_item_storylist, mItemList);
        //列表项点击事件
        mStoryListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(getContext(), StoryDetailsActivity.class));
            }
        });
        //加载更多
        mStoryListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
//                loadPage();
            }
        }, mRecyclerView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerView.setAdapter(mStoryListAdapter);

        //刷新事件
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
    }

    private void loadMoreData() {
        if (hasNextPage) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    QueryListParam param = new QueryListParam();
                    param.setLon(App.getLon());
                    param.setLat(App.getLat());
                    param.setPage(nextPage);
                    try {
                        Result result = RequestUtil.doPost(RequestUtil.wifiUrl, Method.FIND_STORIES, param);
                        int code = result.getCode();
                        if (code == 0) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtil.toast("加载错误");
                                }
                            });
                        } else {
                            QueryResult<StoryListInfo> infos = result.getList(StoryListInfo.class);
                            hasNextPage = infos.isHasNext();
                            if (nextPage == 1) {
                                mItemList.clear();
                            }
                            mItemList.addAll(infos.getRows());

                            Log.d(TAG, "run: " + mItemList.get(0).getContent());

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mStoryListAdapter.loadMoreComplete();
                                    ToastUtil.toast("加载成功");
                                }
                            });
                        }
                    } catch (WrongRequestException e) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtil.toast("网络错误");
                                mStoryListAdapter.loadMoreFail();
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {

                    }
                }
            }).start();
        } else {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtil.toast("网络错误");
                    mStoryListAdapter.loadMoreEnd();
                }
            });
        }
    }

    private void refreshData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                QueryListParam param = new QueryListParam();
                param.setLon(App.getLon());
                param.setLat(App.getLat());
                param.setPage(1);
                try {
                    Result result = RequestUtil.doPost(RequestUtil.wifiUrl, Method.FIND_STORIES, param);
                    int code = result.getCode();
                    if (code == 0) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtil.toast("加载错误");
                            }
                        });
                    } else {
                        QueryResult<StoryListInfo> infos = result.getList(StoryListInfo.class);
                        hasNextPage = infos.isHasNext();
                        mItemList.clear();
                        mItemList.addAll(infos.getRows());
                        Log.d(TAG, "run: " + mItemList.get(0).getContent());
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtil.toast("刷新成功");
                            }
                        });
                    }
                } catch (WrongRequestException e) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.toast("网络错误");
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    });
                }
            }
        }).start();
    }
}
