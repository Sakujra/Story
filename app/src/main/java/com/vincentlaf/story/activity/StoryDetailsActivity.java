package com.vincentlaf.story.activity;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.vincentlaf.story.R;

public class StoryDetailsActivity extends AppCompatActivity {

    private FloatingActionButton mFab;

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

        Toolbar toolbar = (Toolbar) findViewById(R.id.z_toolbar_story);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.z_collapsingtoolbarlayout_story);
        ImageView imageView = (ImageView) findViewById(R.id.z_imageview_story);
        TextView textView = (TextView) findViewById(R.id.z_textview_test);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbarLayout.setTitle("story");
        Glide.with(this).load(mImgUrl).into(imageView);
        textView.setText(mContent);

        //悬浮按钮点击事件
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(StoryDetailsActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
            }
        });
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
