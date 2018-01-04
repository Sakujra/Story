package com.vincentlaf.story.activity;

import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.vincentlaf.story.R;
import com.vincentlaf.story.adapter.CommentListAdapter;
import com.vincentlaf.story.bean.ItemCommentList;
import com.vincentlaf.story.util.ToastUtil;

import java.util.ArrayList;

public class StoryDetailsActivity extends AppCompatActivity {

    private FloatingActionButton mFab;

    private TextView mTxtBtnLike;
    private boolean mIsLike = false;

    private TextView mTxtBtnComment;

    private TextView mTxtBtnFavorite;
    private boolean mIsFavorite = false;

    private Dialog mCommentDlg;

    private RecyclerView mRecyclerView;
    private NestedScrollView mScrollView;

    private ArrayList<ItemCommentList> mItemList = new ArrayList<>();

    private CommentListAdapter mAdapter;

    private String mImgUrl = "https://cn.bing.com/az/hprichbg/rb/PineZion_ZH-CN13789067332_1920x1080.jpg";

    private String mContent = "The United States Declaration of Independence is the statement adopted by the Second Continental Congress meeting at the Pennsylvania State House (Independence Hall) in Philadelphia on July 4, 1776, which announced that the thirteen American colonies,[2] then at war with the Kingdom of Great Britain, regarded themselves as thirteen independent sovereign states, no longer under British rule. These states would found a new nation – the United States of America. John Adams was a leader in pushing for independence, which was passed on July 2 with no opposing vote cast. A committee of five had already drafted the formal declaration, to be ready when Congress voted on independence.\n" +
            "\n" +
            "John Adams persuaded the committee to select Thomas Jefferson to compose the original draft of the document,[3] which Congress would edit to produce the final version. The Declaration was ultimately a formal explanation of why Congress had voted on July 2 to declare independence from Great Britain, more than a year after the outbreak of the American Revolutionary War. The next day, Adams wrote to his wife Abigail: \"The Second Day of July 1776, will be the most memorable Epocha, in the History of America.\"[4] But Independence Day is actually celebrated on July 4, the date that the Declaration of Independence was approved.\n" +
            "\n" +
            "After ratifying the text on July 4, Congress issued the Declaration of Independence in several forms. It was initially published as the printed Dunlap broadside that was widely distributed and read to the public. The source copy used for this printing has been lost, and may have been a copy in Thomas Jefferson's hand.[5] Jefferson's original draft, complete with changes made by John Adams and Benjamin Franklin, and Jefferson's notes of changes made by Congress, are preserved at the Library of Congress. The best-known version of the Declaration is a signed copy that is displayed at the National Archives in Washington, D.C., and which is popularly regarded as the official document. This engrossed copy was ordered by Congress on July 19 and signed primarily on August 2.[6][7]\n" +
            "\n" +
            "The sources and interpretation of the Declaration have been the subject of much scholarly inquiry. The Declaration justified the independence of the United States by listing colonial grievances against King George III, and by asserting certain natural and legal rights, including a right of revolution. Having served its original purpose in announcing independence, references to the text of the Declaration were few in the following years. Abraham Lincoln made it the centerpiece of his rhetoric (as in the Gettysburg Address of 1863) and his policies. Since then, it has become a well-known statement on human rights, particularly its second sentence:";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_details);

        mFab = (FloatingActionButton) findViewById(R.id.z_fab_story);

        mTxtBtnLike = (TextView) findViewById(R.id.z_txt_btn_like);
        mTxtBtnComment = (TextView) findViewById(R.id.z_txt_btn_comment);
        mTxtBtnFavorite = (TextView) findViewById(R.id.z_txt_btn_favorite);
        mRecyclerView = (RecyclerView) findViewById(R.id.z_recyclerview_comments);
        mScrollView = (NestedScrollView) findViewById(R.id.z_scrollview_story);

        Toolbar toolbar = (Toolbar) findViewById(R.id.z_toolbar_story);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.z_collapsingtoolbarlayout_story);
        ImageView imageView = (ImageView) findViewById(R.id.z_imageview_story);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbarLayout.setTitle("story");
        Glide.with(this).load(mImgUrl).into(imageView);

        //初始化评论对话框
        setupCommentDialog();

        //初始化点赞、评论、收藏三个按钮
        initBtnLikeCommentFavorite();

        initCommentItems();

        mAdapter = new CommentListAdapter(R.layout.z_item_comment, mItemList);
//        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                ToastUtil.toast("Clicked");
//            }
//        });
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view instanceof TextView) {
                    TextView textView = (TextView) view;

                    if (mItemList.get(position).getLike()) {
                        //取消点赞
                        Drawable icon = getResources().getDrawable(R.drawable.ic_like_not_24dp);
                        icon.setBounds(0, 0, icon.getMinimumWidth(), icon.getMinimumHeight());
                        textView.setCompoundDrawables(icon, null, null, null);
//                        mIsLike = false;
                        mItemList.get(position).setLike(false);
                    } else {
                        //点赞
                        Drawable icon = getResources().getDrawable(R.drawable.ic_like_24dp);
                        icon.setBounds(0, 0, icon.getMinimumWidth(), icon.getMinimumHeight());
                        textView.setCompoundDrawables(icon, null, null, null);
//                        mIsLike = true;
                        mItemList.get(position).setLike(true);
                    }
                }
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        mScrollView.smoothScrollTo(0, 0);
    }

    private void initBtnLikeCommentFavorite() {
        //喜欢
        mTxtBtnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mIsLike) {
                    Drawable icon = getResources().getDrawable(R.drawable.ic_like_not_24dp);
                    icon.setBounds(0, 0, icon.getMinimumWidth(), icon.getMinimumHeight());
                    mTxtBtnLike.setCompoundDrawables(icon, null, null, null);
                    mIsLike = false;
                } else {
                    Drawable icon = getResources().getDrawable(R.drawable.ic_like_24dp);
                    icon.setBounds(0, 0, icon.getMinimumWidth(), icon.getMinimumHeight());
                    mTxtBtnLike.setCompoundDrawables(icon, null, null, null);
                    mIsLike = true;
                }
            }
        });

        //评论
        mTxtBtnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               mCommentDlg.show();
            }
        });

        //收藏
        mTxtBtnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mIsFavorite) {
                    Drawable icon = getResources().getDrawable(R.drawable.ic_favorite_not_24dp);
                    icon.setBounds(0, 0, icon.getMinimumWidth(), icon.getMinimumHeight());
                    mTxtBtnFavorite.setCompoundDrawables(null, null, icon, null);
                    mIsFavorite = false;
                } else {
                    Drawable icon = getResources().getDrawable(R.drawable.ic_favorite_24dp);
                    icon.setBounds(0, 0, icon.getMinimumWidth(), icon.getMinimumHeight());
                    mTxtBtnFavorite.setCompoundDrawables(null, null, icon, null);
                    mIsFavorite = true;
                }
            }
        });
    }

    private void setupCommentDialog() {
        mCommentDlg = new Dialog(this, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        View view = LayoutInflater.from(this).inflate(R.layout.z_dialog_comment, null);
        //取消
        view.findViewById(R.id.z_btn_comment_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCommentDlg.dismiss();
            }
        });
        view.findViewById(R.id.z_btn_comment_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.toast("发送");
                mCommentDlg.dismiss();
            }
        });
        //将布局设置给Dialog
        mCommentDlg.setContentView(view);
        //获取当前Activity所在的窗体
        Window dialogWindow = mCommentDlg.getWindow();
        //设置Dialog在窗体底部
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        //全宽
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
    }

    private void initCommentItems() {
        //评论列表初始化
        for (int i = 0; i < 10; i++) {
            ItemCommentList item = new ItemCommentList();
            item.setLike(false);
            mItemList.add(item);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }
}
