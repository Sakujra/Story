package com.vincentlaf.story.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.vincentlaf.story.bean.Method;
import com.vincentlaf.story.bean.netbean.StoryListInfo;
import com.vincentlaf.story.bean.param.QueryListParam;
import com.vincentlaf.story.bean.result.QueryResult;
import com.vincentlaf.story.bean.result.Result;
import com.vincentlaf.story.exception.WrongRequestException;
import com.vincentlaf.story.others.App;
import com.vincentlaf.story.others.CustomViewPager;
import com.vincentlaf.story.fragment.FragmentTab1;
import com.vincentlaf.story.fragment.FragmentTab2;
import com.vincentlaf.story.R;
import com.vincentlaf.story.util.RequestUtil;
import com.vincentlaf.story.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final int SIGN_IN = 0;

    public ArrayList<StoryListInfo> mItemList = new ArrayList<>();

    private boolean hasNextPage = true;
    private int nextPage = 1;
    private static final String TAG = "MainActivity";

    private CustomViewPager mViewPager;
    private TabLayout mTabLayout;
    private NavigationView mNaviView;

    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mViewPager = (CustomViewPager) findViewById(R.id.z_viewpager_main);
        mTabLayout = (TabLayout) findViewById(R.id.z_tablayout_main);
        mNaviView = (NavigationView) findViewById(R.id.nav_view);

        //取消左右手势
        mViewPager.setIsScrollable(false);
        //初始化ViewPager
        mViewPager.setAdapter(
                new MainPagerAdapter(getSupportFragmentManager())
                        .addFragment(new FragmentTab1(), "Map")
                        .addFragment(new FragmentTab2(), "Story")
        );
        //将TabLayout与ViewPager关联
        mTabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        mNavigationView.getHeaderView(0).findViewById(R.id.button_userPic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivityForResult(intent, SIGN_IN);
            }
        });

        //请求权限
        askForPermissions();
    }


    //处理拍照返回结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case SIGN_IN:
                if (resultCode == RESULT_OK) {
                    ImageView imageView = mNavigationView.getHeaderView(0).findViewById(R.id.button_userPic);
                    Glide.with(this)
                            .load(RequestUtil.getHeadImage(App.getUser().getUserPic()))
                            .into(imageView);
                    TextView textView = mNavigationView.getHeaderView(0).findViewById(R.id.nav_username);
                    textView.setText(App.getUser().getUserName());
                }
                break;
        }
    }

    //加载数据
    public void loadData(boolean isRefresh) {
        if (isRefresh) {
            nextPage = 1;
            loadDataOnPage(nextPage);
        } else {
            if (hasNextPage) {
                loadDataOnPage(nextPage);
            }
        }
    }

    //按页码加载数据
    private void loadDataOnPage(int page) {
        nextPage = page;
        new Thread(new Runnable() {
            @Override
            public void run() {
                QueryListParam param = new QueryListParam();
                param.setLon(App.getLon());
                param.setLat(App.getLat());
                param.setPage(nextPage);
                try {
                    Result result = RequestUtil.doPost(RequestUtil.wifiUrl, Method.FIND_STORIES, param);

                    Log.d(TAG, "run " + result.toString());

                    int code = result.getCode();
                    //加载错误
                    if (code == 0) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                MainPagerAdapter adapter = (MainPagerAdapter) mViewPager.getAdapter();
                                adapter.frag2LoadMoreFail();
                                ToastUtil.toast("加载失败");
                            }
                        });
                    } else {
                        //加载成功
                        QueryResult<StoryListInfo> infos = result.getList(StoryListInfo.class);
                        hasNextPage = infos.isHasNext();
                        if (nextPage == 1) {
                            mItemList.clear();
                        }
                        mItemList.addAll(infos.getRows());
                        //下次请求下一页
                        nextPage++;
                        Log.d(TAG, "run: " + mItemList.get(0).getContent());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                MainPagerAdapter adapter = (MainPagerAdapter) mViewPager.getAdapter();
                                //没有下一页
                                adapter.addMarkers(mItemList);
                                if (!hasNextPage) {
                                    adapter.frag2LoadMoreEnd();
                                    ToastUtil.toast("已全部加载");
                                } else {
                                    //还有下一页
                                    adapter.frag2LoadMoreComplete();
                                    ToastUtil.toast("加载成功");
                                }
                            }
                        });
                    }
                } catch (WrongRequestException e) {
                    //网络错误
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            MainPagerAdapter adapter = (MainPagerAdapter) mViewPager.getAdapter();
                            adapter.frag2LoadMoreFail();
                            ToastUtil.toast("网络错误");
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            MainPagerAdapter adapter = (MainPagerAdapter) mViewPager.getAdapter();
                            adapter.frag2SetIsRefreshing(false);
                        }
                    });
                }
            }
        }).start();
    }

    //运行时权限结果处理
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "必须同意所有权限才能使用本程序", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                } else {
                    Toast.makeText(this, "未知错误", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    private void askForPermissions() {
        ArrayList<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this
                , Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(this
                , Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(this
                , Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(this, permissions, 1);
        } else {
            requestLocation();
        }
    }

    private void requestLocation() {

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.action_settings:
//                startActivity(new Intent(this, LoginActivity.class));
//                break;
            case R.id.action_post:
                startActivity(new Intent(this, PostActivity.class));
                break;
            default:
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_collection) {
            startActivity(new Intent(this, CollectionActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

class MainPagerAdapter extends FragmentPagerAdapter {

    private FragmentTab2 mFragmentTab2 = null;
    private FragmentTab1 mFragmentTab1 = null;

    private FragmentManager mFragmentManager;

    private ArrayList<Fragment> mFragmentList = new ArrayList<>();

    private ArrayList<String> mTitleList = new ArrayList<>();

    MainPagerAdapter(FragmentManager fm) {
        super(fm);
        mFragmentManager = fm;
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    //TabLayout上显示的title
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList.get(position);
    }

    public MainPagerAdapter addFragment(Fragment fragment, String title) {
        if (fragment instanceof FragmentTab2) {
            mFragmentTab2 = (FragmentTab2) fragment;
        }
        if (fragment instanceof FragmentTab1) {
            mFragmentTab1 = (FragmentTab1) fragment;
        }
        mFragmentList.add(fragment);
        mTitleList.add(title);
        return this;
    }

    public void addMarkers(List<StoryListInfo> storyListInfos) {
        mFragmentTab1.addMarkers(storyListInfos);
    }

    public void frag2LoadMoreEnd() {
        mFragmentTab2.loadMoreEnd();
    }

    public void frag2LoadMoreComplete() {
        mFragmentTab2.loadMoreComplete();
    }

    public void frag2LoadMoreFail() {
        mFragmentTab2.loadMoreFail();
    }

    public void frag2SetIsRefreshing(boolean b) {
        mFragmentTab2.setIsRefreshing(b);
    }
}
