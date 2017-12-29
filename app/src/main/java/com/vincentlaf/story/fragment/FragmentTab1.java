package com.vincentlaf.story.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.MyLocationStyle;
import com.vincentlaf.story.R;

/**
 * Created by VincentLaf on 2017/12/17.
 */

public class FragmentTab1 extends Fragment {

    private MapView mMapView;

    private AMap mAMap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab1, container, false);
        initview(savedInstanceState, view);
        return view;
    }

    private void initview(Bundle savedInstanceState, View view) {
        mMapView = view.findViewById(R.id.z_map);
        mMapView.onCreate(savedInstanceState);
        if (mAMap == null) {
            mAMap = mMapView.getMap();
        }
        MyLocationStyle myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类
        mAMap.setMyLocationStyle(myLocationStyle);
        mAMap.getUiSettings().setMyLocationButtonEnabled(true);
//        mAMap.setLocationSource(this);
        mAMap.setMyLocationEnabled(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }
}
