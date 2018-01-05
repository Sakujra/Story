package com.vincentlaf.story.util;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.vincentlaf.story.R;
import com.vincentlaf.story.others.App;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by VincentLaf on 2018/1/4.
 */

public class MarkerUtil {
    public static final int MARKER_STATUS_NORMAL = 0;

    public static final int MARKER_STATUS_ACTIVE = 1;

    public static Bitmap getMarkerActiveBitmap(String url) {
        return convertView2Bitmap(MARKER_STATUS_ACTIVE, url);
    }

    public static Drawable getMarkerActiveDrawable(String url) {
        return new BitmapDrawable(null, getMarkerActiveBitmap(url));
    }

    public static Bitmap getMarkerNormalBitmap(String url) {
        return convertView2Bitmap(MARKER_STATUS_NORMAL, url);
    }

    public static Drawable getMarkerNormalDrawable(String url) {
        return new BitmapDrawable(null, getMarkerNormalBitmap(url));
    }

    //将xml表示的布局文件转化为Bitmap
    private static Bitmap convertView2Bitmap(int status, String url) {
        View view;
        //active or not
        if (status == MARKER_STATUS_ACTIVE) {
            view = LayoutInflater.from(App.getContext()).inflate(R.layout.z_map_marker_active, null);
        } else {
            view = LayoutInflater.from(App.getContext()).inflate(R.layout.z_map_marker_normal, null);
        }
        //设置头像
        if (url != null) {
            CircleImageView circleImageView = (CircleImageView) view.findViewById(R.id.z_map_marker_headimg);
            Glide.with(App.getContext())
                    .load(RequestUtil.getHeadImage(url))
                    .into(circleImageView);
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

}
