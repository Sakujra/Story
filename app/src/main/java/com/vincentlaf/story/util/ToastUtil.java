package com.vincentlaf.story.util;

import android.widget.Toast;

import com.vincentlaf.story.others.App;

/**
 * Created by VincentLaf on 2017/12/29.
 */

public class ToastUtil {

    public static void toast(String info) {
        Toast.makeText(App.getContext(), info, Toast.LENGTH_SHORT).show();
    }

    public static void longToast(String info) {
        Toast.makeText(App.getContext(), info, Toast.LENGTH_LONG).show();
    }
}
