package com.vincentlaf.story.others;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.animation.Animation;
import com.amap.api.maps.model.animation.ScaleAnimation;
import com.vincentlaf.story.R;
import com.vincentlaf.story.bean.netbean.StoryListInfo;
import com.vincentlaf.story.util.MarkerUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DengCun on 2017/12/29.
 */

public class MarkerCollection {
    private AMap aMap;

    public MarkerCollection(AMap aMap) {
        this.aMap = aMap;
    }

    private List<Marker> markerList = new ArrayList();

    public List getMarkerList() {
        return this.markerList;
    }

    public void setMarkerList(List<Marker> markerList) {
        this.markerList = markerList;
    }

    /**
     * 移除所有标记
     */
    public void clearAll() {
        if (aMap != null) {
            for (int i = 0; i < markerList.size(); i++) {
                removeMarker(markerList.get(i));
            }
        }
        markerList.clear();
    }

    /**
     * 添加一个标记
     *
     * @param x           经度
     * @param y           纬度
     * @param information 信息
     */
    public void addMarker(double x, double y, StoryListInfo information) {
        LatLng latLng = new LatLng(x, y);
        MarkerOptions markerOption = new MarkerOptions()
//                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .icon(BitmapDescriptorFactory.fromBitmap(MarkerUtil.getMarkerNormalBitmap(information.getUserPic())))
                .position(latLng)
                .title(latLng.toString())
                .snippet("aa")
                .draggable(true);
        Marker marker = aMap.addMarker(markerOption);
        marker.setObject(information);
        markerList.add(marker);
    }

    /**
     * 添加一个有生成动画的标记
     *
     * @param x           经度
     * @param y           纬度
     * @param information 信息
     */
    public void addMarkerWithGrowAnimation(double x, double y, StoryListInfo information) {
        LatLng latLng = new LatLng(x, y);
        MarkerOptions markerOption = new MarkerOptions()
//                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .icon(BitmapDescriptorFactory.fromBitmap(MarkerUtil.getMarkerNormalBitmap(information.getUserPic())))
                .position(latLng)
                .title(latLng.toString())
                .snippet("aa")
                .draggable(true);
        Marker marker = aMap.addMarker(markerOption);
        setGrowAnimation(marker);
        marker.setObject(information);
        markerList.add(marker);
    }


    public  void setMarkerIcon(Marker marker,Bitmap bitmap){
        marker.setIcon(BitmapDescriptorFactory.fromBitmap(bitmap));
    }

    /**
     * 移除一个标记
     *
     * @param marker
     */
    public void removeMarker(Marker marker) {
        if (aMap != null) {
            if (markerList.contains(marker)) {
                marker.destroy();
                markerList.remove(marker);
            }
        }
    }

    /**
     * 为一个marker设置生长动画
     * @param marker
     */
    public void setGrowAnimation(Marker marker) {
        if(marker != null) {
            Animation animation = new ScaleAnimation(0,1,0,1);
            animation.setInterpolator(new LinearInterpolator());
            //整个移动所需要的时间
            animation.setDuration(1000);
            //设置动画
            marker.setAnimation(animation);
            marker.startAnimation();
        }
    }

    /**
     * 判断2个marker是否相等,位置相等就认为相等
     * @param marker1
     * @param marker2
     * @return
     */
    public static boolean equal(Marker marker1,Marker marker2){
        boolean equal=false;
        if(marker1!=null&&marker2!=null){
            Double la1=new Double(marker1.getPosition().latitude);
            Double lo1=new Double(marker1.getPosition().longitude);
            Double la2=new Double(marker2.getPosition().latitude);
            Double lo2=new Double(marker2.getPosition().longitude);
            if(la1.equals(la2)&&lo1.equals(lo2)){
                equal=true;
            }
        }

        return equal;
    }



}
