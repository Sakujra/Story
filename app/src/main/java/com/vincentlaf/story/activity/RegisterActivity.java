package com.vincentlaf.story.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.vincentlaf.story.R;
import com.vincentlaf.story.bean.User;
import com.vincentlaf.story.exception.WrongRequestException;
import com.vincentlaf.story.util.RequestUtil;

import java.io.IOException;

public class RegisterActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    private boolean isUserNameValid(String username) {
        return username.length() < 13;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }
}
