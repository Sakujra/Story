package com.vincentlaf.story.others;

import android.app.Application;
import android.content.Context;

/**
 * Created by VincentLaf on 2017/12/29.
 */

public class App extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    //全局获取context
    public static Context getContext() {
        return mContext;
    }
}
