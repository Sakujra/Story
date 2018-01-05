package com.vincentlaf.story.others;

import android.app.Application;
import android.content.Context;

import com.vincentlaf.story.bean.User;

/**
 * Created by VincentLaf on 2017/12/29.
 */

public class App extends Application {
    private static User user;
    private static Context mContext;
    private static Double lon = 23.4646621;
    private static Double lat = 95.1646562;

    @Override
    public void onCreate() {
        user = null;
        super.onCreate();
        mContext = getApplicationContext();
    }

    //全局获取context
    public static Context getContext() {
        return mContext;
    }

    public static void setLon(Double lon) {
        App.lon = lon;
    }

    public static void setLat(Double lat) {
        App.lat = lat;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        App.user = user;
    }

    public static Double getLon() {
        return lon;
    }

    public static Double getLat() {
        return lat;
    }
}
