package com.vincentlaf.story.util;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;

import com.vincentlaf.story.R;
import com.vincentlaf.story.others.App;

/**
 * Created by VincentLaf on 2018/1/4.
 */

public class MarkerUtil {

    public static final int MARKER_STATUS_NORMAL = 0;

    public static final int MARKER_STATUS_ACTIVE = 1;

    public static Bitmap getMarkerActiveBitmap() {
        return convertView2Bitmap(MARKER_STATUS_ACTIVE);
    }

    public static Drawable getMarkerActiveDrawable() {
        return new BitmapDrawable(null, getMarkerActiveBitmap());
    }

    public static Bitmap getMarkerNormalBitmap() {
        return convertView2Bitmap(MARKER_STATUS_NORMAL);
    }

    public static Drawable getMarkerNormalDrawable() {
        return new BitmapDrawable(null, getMarkerNormalBitmap());
    }

    //将xml表示的布局文件转化为Bitmap
    private static Bitmap convertView2Bitmap(int status) {
        View view = null;
        if (status == MARKER_STATUS_ACTIVE) {
            view = LayoutInflater.from(App.getContext()).inflate(R.layout.z_map_marker_active, null);
        } else {
            view = LayoutInflater.from(App.getContext()).inflate(R.layout.z_map_marker_normal, null);
        }
        //计算view所占大小
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        //view显示位置，大小
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        //构建缓存
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache(false));
        //销毁缓存
        view.destroyDrawingCache();
        return bitmap;
    }

//    private static Drawable convertBitmap2Drawable(Bitmap bmp) {
//        return new BitmapDrawable(null, bmp);
//    }

//    public static Drawable getImgDrawable() {
//        View view = LayoutInflater.from(App.getContext()).inflate(R.layout.z_map_marker_active, null);
//        //计算view所占大小
//        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
//                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//        //view显示位置，大小
//        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
//        //构建缓存
//        view.buildDrawingCache();
//        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache(false));
//        //销毁缓存
//        view.destroyDrawingCache();
//        //转为drawable对象
//        Drawable drawable = new BitmapDrawable(null, bitmap);
//        return drawable;
//    }
}
