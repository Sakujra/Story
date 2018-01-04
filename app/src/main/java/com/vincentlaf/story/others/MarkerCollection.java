package com.vincentlaf.story.others;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.vincentlaf.story.R;

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
    public void addMarker(double x, double y, Object information) {
        LatLng latLng = new LatLng(x, y);
        MarkerOptions markerOption = new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .position(latLng)
                .title(latLng.toString())
                .snippet("aa")
                .draggable(true);
        Marker marker = aMap.addMarker(markerOption);
        marker.setObject(information);
        markerList.add(marker);
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


}
