package com.vincentlaf.story.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.vincentlaf.story.R;
import com.vincentlaf.story.bean.Method;
import com.vincentlaf.story.bean.User;
import com.vincentlaf.story.bean.result.Result;
import com.vincentlaf.story.exception.WrongRequestException;
import com.vincentlaf.story.others.App;
import com.vincentlaf.story.util.RequestUtil;
import com.vincentlaf.story.util.StringUtil;
import com.vincentlaf.story.util.ToastUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {

    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;
    private Uri imageUri;

    private CircleImageView mImageView;
    private EditText mInputUsername;
    private EditText mInputPsw;
    private EditText mInputNickname;

    private Dialog mDialog;
    private AlertDialog mWaitingDlg = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mImageView = (CircleImageView) findViewById(R.id.z_img_register_headimg);
        mInputUsername = (EditText) findViewById(R.id.z_input_register_username);
        mInputPsw = (EditText) findViewById(R.id.z_input_register_psw);
        mInputNickname = (EditText) findViewById(R.id.z_input_register_nickname);

        setupBottomDialog();

        //选择头像
        findViewById(R.id.z_img_register_headimg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.show();//显示对话框
            }
        });

        findViewById(R.id.z_btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkInput()) return;

                //显示等待对话框
                if (mWaitingDlg == null) {
                    mWaitingDlg = new AlertDialog.Builder(RegisterActivity.this)
                            .setView(new ProgressBar(RegisterActivity.this))
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
                        String nickname = mInputNickname.getText().toString();
                        Bitmap bmp = ((BitmapDrawable) mImageView.getDrawable()).getBitmap();
                        byte[] bytes = RegisterActivity.this.bitmap2Bytes(bmp);
                        String img = "";
                        try {
                            img = StringUtil.byteArray2String(bytes);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                        User user = new User();
                        user.setUserPhone(username);
                        user.setUserPass(psw);
                        user.setUserName(nickname);
                        user.setUserPicEntity(img);
                        try {
                            Result result = RequestUtil.doPost(RequestUtil.monitorUrl, Method.REGISTER, user);
                            int code = result.getCode();
                            if (code == 0) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ToastUtil.toast("注册错误");
                                    }
                                });
                            } else {
                                User returnUser = result.getEntityData(User.class);
                                StringBuilder str = new StringBuilder();
                                str.append(returnUser.getUserId() + " " + returnUser.getUserName());
                                App.setUser(returnUser);
                            }
                        } catch (WrongRequestException e) {
                            e.printStackTrace();
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

    //将bitmap转为流
    public byte[] bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    private boolean checkInput() {
        return true;
    }

    private void setupBottomDialog() {
        mDialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        View view = LayoutInflater.from(this).inflate(R.layout.z_dialog_get_picture, null);
        //拍照
        view.findViewById(R.id.z_btn_take_picture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCamera();
                mDialog.dismiss();
            }
        });
        //从相册中选择
        view.findViewById(R.id.z_btn_choose_picture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(RegisterActivity.this
                        , Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(RegisterActivity.this
                            , new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    //打开相册
                    openAlbum();
                }
            }
        });
        //取消
        view.findViewById(R.id.z_btn_picture_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });
        //将布局设置给Dialog
        mDialog.setContentView(view);
        //获取当前Activity所在的窗体
        Window dialogWindow = mDialog.getWindow();
        //设置Dialog在窗体底部
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        //全宽
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
    }

    //打开相册
    private void openAlbum() {
        mDialog.dismiss();
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    //打开相机
    private void startCamera() {
        //存到应用关联缓存目录
        File outputImage = new File(getExternalCacheDir(), "output_img.jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Android 7.0以上
        if (Build.VERSION.SDK_INT >= 24) {
            imageUri = FileProvider.getUriForFile(RegisterActivity.this,
                    "com.vincentlaf.story.fileprovider", outputImage);
        } else {
//            Uri标识图片的本地真实路径
            imageUri = Uri.fromFile(outputImage);
        }
        //启动相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO);
    }

    //处理拍照返回结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                //拍照
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(imageUri));
                        int degree = 90;
                        bitmap = rotateImage(bitmap, degree);
//                        mImg.setImageBitmap(bitmap);
                        mImageView.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                //选择图片
                if (resultCode == RESULT_OK) {
                    //4.4及以上使用这个方法处理图片
                    handleImageOnKitKat(data.getData());
                }
            default:
                break;
        }
    }

    //4.4及以上系统使用这个方法处理图片
    private void handleImageOnKitKat(Uri uri) {
        String imagePath = null;
        if (DocumentsContract.isDocumentUri(this, uri)) {
            //如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                //解析出数字格式的id
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        , selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content:" +
                        "//download/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //如果是content类型的Uri,则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();
        }
        int degree = getOrientation(this, uri);
        //根据图片路径显示图片
        displayImage(imagePath, degree);
    }

    public static int getOrientation(Context content, Uri photoUri) {
        int orientation = 0;
        Cursor cursor = content.getContentResolver().query(photoUri,
                new String[]{MediaStore.Images.ImageColumns.ORIENTATION}
                , null, null, null);
        if (cursor != null) {
            if (cursor.getCount() != 1) {
                return -1;
            }
            cursor.moveToFirst();
            orientation = cursor.getInt(0);
            cursor.close();
        }
        return orientation;
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        //通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath, int degree) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            bitmap = rotateImage(bitmap, degree);
//            mImg.setImageBitmap(bitmap);
            mImageView.setImageBitmap(bitmap);
        } else {
            ToastUtil.toast("加载图片失败");
        }
    }

    private Bitmap rotateImage(Bitmap bmp, int degrees) {
        if (degrees != 0) {
            Matrix matrix = new Matrix();
            matrix.postRotate(degrees);
            return Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
        }
        return bmp;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    ToastUtil.toast("权限申请失败，无法打开相册");
                }
            default:
        }
    }
}
