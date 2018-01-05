package com.vincentlaf.story.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.vincentlaf.story.R;
import com.vincentlaf.story.bean.Method;
import com.vincentlaf.story.bean.User;
import com.vincentlaf.story.bean.result.Result;
import com.vincentlaf.story.exception.WrongRequestException;
import com.vincentlaf.story.others.App;
import com.vincentlaf.story.util.RequestUtil;
import com.vincentlaf.story.util.ToastUtil;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private AlertDialog mWaitingDlg = null;

    private EditText mInputUsername;

    private EditText mInputPsw;

    private static final String TAG = "LoginActivity";

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
                if (!checkInput()) return;

                //显示等待对话框
                if (mWaitingDlg == null) {
                    mWaitingDlg = new AlertDialog.Builder(LoginActivity.this)
                            .setView(new ProgressBar(LoginActivity.this))
                            .setCancelable(false)
                            .show();
                    //设置背景透明
                    mWaitingDlg.getWindow().setBackgroundDrawable(new ColorDrawable());
                } else {
                    mWaitingDlg.show();
                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String username = mInputUsername.getText().toString();
                        String psw = mInputPsw.getText().toString();

                        User user = new User();
                        user.setUserPhone(username);
                        user.setUserPass(psw);
                        try {
                            Result result = RequestUtil.doPost(RequestUtil.wifiUrl, Method.LOGIN, user);
                            int code = result.getCode();
                            if (code == 0) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ToastUtil.toast("登录信息错误");
                                    }
                                });
                            } else {
                                User returnUser = result.getEntityData(User.class);
                                StringBuilder str = new StringBuilder();
                                str.append(returnUser.getUserId() + " " + returnUser.getUserName());
                                App.setUser(returnUser);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ToastUtil.toast("登录成功");
                                    }
                                });
                                Intent intent = new Intent();
                                setResult(RESULT_OK, intent);
                                finish();
                            }
                        } catch (WrongRequestException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtil.toast("网络错误");
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            //关闭等待对话框
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mWaitingDlg.dismiss();
                                }
                            });
                        }
                    }
                }).start();
            }
        });
    }

    private boolean checkInput() {
        return true;
    }
}

