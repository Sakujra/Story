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

public class RegisterActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private RegisterActivity.UserRegisterTask mAuthTask = null;
    private AutoCompleteTextView userNameView;
    private EditText passwordView;
    private AutoCompleteTextView phoneView;
    private Button registerButton;
    private View mProgressView;
    private View mRegisterFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userNameView=(AutoCompleteTextView)findViewById(R.id.newUserName);
        passwordView=(EditText)findViewById(R.id.newPassword);
        phoneView=(AutoCompleteTextView)findViewById(R.id.phone);
        registerButton=(Button)findViewById(R.id.register_button);
        mProgressView=findViewById(R.id.register_progress);
        mRegisterFormView=findViewById(R.id.register_form);


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });
    }
    private boolean isUserNameValid(String username) {
        //TODO: Replace this with your own logic
        return username.length()<13;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    private void attemptRegister() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        userNameView.setError(null);
        passwordView.setError(null);
        phoneView.setError(null);

        // Store values at the time of the login attempt.
        String username = userNameView.getText().toString();
        String password = passwordView.getText().toString();
        String phone=phoneView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            passwordView.setError(getString(R.string.error_invalid_password));
            focusView = passwordView;
            cancel = true;
        }
        if (TextUtils.isEmpty(phone)) {
            phoneView.setError(getString(R.string.error_invalid_password));
            focusView = phoneView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(username)) {
            userNameView.setError(getString(R.string.error_field_required));
            focusView = userNameView;
            cancel = true;
        } else if (!isUserNameValid(username)) {
            userNameView.setError(getString(R.string.error_invalid_username));
            focusView = userNameView;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new RegisterActivity.UserRegisterTask(username,phone, password,this);
            mAuthTask.execute((Void) null);
        }
    }
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mRegisterFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
    public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {

        private final String mUser;
        private final String mPassword;
        private  final String mPhone;
        private final Context context;

        UserRegisterTask(String user,String phone, String password,Context context) {
            mUser = user;
            mPassword = password;
            mPhone=phone;
            this.context=context;
        }



        @Override
        protected Boolean doInBackground(Void... params) {
            System.out.println("Login...");
            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }
            //验证登录
            User user=null;
            JSONObject result=null;
            try {
                JSONObject param=new JSONObject();
                param.put("userPhone",mUser);
                param.put("userPass",mPassword);
                param.put("userPhone",mPhone);
                result = RequestUtil.doPost(RequestUtil.monitorUrl,"register", param);
            }catch (WrongRequestException wre){
                Toast.makeText(context,wre.getMessage(),Toast.LENGTH_LONG);
                return false;
            } catch (IOException e) {
                Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG);
                return false;
            }
            user=JSONObject.toJavaObject(result,User.class);
            if(user!=null){
                Toast.makeText(context,"注册成功",Toast.LENGTH_SHORT);
                return true;
            }
            return false;//mUser.equals("johnson")&&mPassword.equals("123456");
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);
            if (success) {
                //跳转
                Intent intent = new Intent(context,MainActivity.class);
                startActivity(intent);
            } else {
                passwordView.setError(getString(R.string.error_incorrect_password));
                passwordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
