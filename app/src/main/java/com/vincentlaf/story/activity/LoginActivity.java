package com.vincentlaf.story.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.vincentlaf.story.R;
import com.vincentlaf.story.bean.User;
import com.vincentlaf.story.bean.result.Result;
import com.vincentlaf.story.exception.WrongRequestException;
import com.vincentlaf.story.others.App;
import com.vincentlaf.story.util.ToastUtil;
import com.vincentlaf.story.util.RequestUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private EditText mInputUsername;

    private EditText mInputPsw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mInputUsername = (EditText) findViewById(R.id.z_input_login_username);
        mInputPsw = (EditText) findViewById(R.id.z_input_login_psw);

        findViewById(R.id.z_fab_register).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        //登录按钮
        findViewById(R.id.z_btn_login).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.toast("login");
            }
        });
    }


    private boolean isUserNameValid(String username) {
        return username.length() < 13;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

}

