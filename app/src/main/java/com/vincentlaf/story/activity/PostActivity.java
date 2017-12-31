package com.vincentlaf.story.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vincentlaf.story.R;
import com.vincentlaf.story.others.ToastUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class PostActivity extends AppCompatActivity {

    public static final int TAKE_PHOTO = 1;
    private Uri imageUri;

    private ImageView mImg;
    private ImageView mImageView;
    private Dialog mDialog;

    private String mImgUrl = "https://cn.bing.com/az/hprichbg/rb/PineZion_ZH-CN13789067332_1920x1080.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        mImg = (ImageView)findViewById(R.id.z_img_token);
        mImageView = (ImageView) findViewById(R.id.z_imageview_post);
        Glide.with(this).load(mImgUrl).into(mImageView);

        setupBottomDialog();


        //拍照
        findViewById(R.id.z_fab_picture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.show();//显示对话框
            }
        });
    }

    private void setupBottomDialog() {
        mDialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        View view = LayoutInflater.from(this).inflate(R.layout.z_layout_bottom_dialog, null);
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
        // 将属性设置给窗体
//        dialogWindow.setAttributes(lp);
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
            imageUri = FileProvider.getUriForFile(PostActivity.this,
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
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(imageUri));
                        mImg.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }
}
