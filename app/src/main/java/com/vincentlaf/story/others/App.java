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



    @Override
    public void onCreate() {
        user=null;
        super.onCreate();
        mContext = getApplicationContext();
    }

    //全局获取context
    public static Context getContext() {
        return mContext;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        App.user = user;
    }
}
