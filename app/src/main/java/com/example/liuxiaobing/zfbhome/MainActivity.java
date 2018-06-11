package com.example.liuxiaobing.zfbhome;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.liuxiaobing.zfbhome.mvc.control.TestAdapter;
import com.example.liuxiaobing.zfbhome.mvc.model.EmptyEntity;

public class MainActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener{

    protected TextView txtTitle;
    protected RelativeLayout mBack;
    protected RecyclerView mRecycView;
    protected TestAdapter mAdapter;


    private AppBarLayout abl_bar;
    private View v_title_big_mask;
    private View v_toolbar_small_mask;
    private View v_toolbar_search_mask;
    private View include_toolbar_search;
    private View include_toolbar_small;
    private int mMaskColor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    protected void initView(){

        mRecycView = findViewById(R.id.recleview);
        mRecycView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TestAdapter(this);
        mRecycView.setAdapter(mAdapter);

        for(int i = 0; i < 50; i ++){

            EmptyEntity entity = new EmptyEntity();
            mAdapter.getDataList().add(entity);
        }

        //AppBarLayout
        abl_bar = (AppBarLayout) findViewById(R.id.abl_bar);
        abl_bar.addOnOffsetChangedListener(this);
        //顶部搜索布局
        include_toolbar_search = findViewById(R.id.include_toolbar_search);
        //扫一扫 付一付 聊一聊 咻一咻 的小图标布局
        include_toolbar_small = findViewById(R.id.include_toolbar_small);


        //顶部搜索布局遮罩
        v_toolbar_search_mask = findViewById(R.id.v_toolbar_search_mask);
        //扫一扫 付一付 聊一聊 咻一咻 的大图标布局遮罩
        v_title_big_mask = findViewById(R.id.v_title_big_mask);
        //扫一扫 付一付 聊一聊 咻一咻 的大图标布局遮罩
        v_toolbar_small_mask = findViewById(R.id.v_toolbar_small_mask);
        //背景颜色
        mMaskColor = getResources().getColor(R.color.blue_dark);

        findViewById(R.id.test).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,SwipActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

        int absVerticalOffset = Math.abs(verticalOffset);//AppBarLayout竖直方向偏移距离px
        int totalScrollRange = appBarLayout.getTotalScrollRange();//AppBarLayout总的距离px

        int argb = Color.argb(absVerticalOffset, Color.red(mMaskColor), Color.green(mMaskColor), Color.blue(mMaskColor));
        int argbDouble = Color.argb(absVerticalOffset * 2, Color.red(mMaskColor), Color.green(mMaskColor), Color.blue(mMaskColor));

        int title_small_offset = (200 - absVerticalOffset) < 0 ? 0 : 200 - absVerticalOffset;
        int title_small_argb = Color.argb(title_small_offset * 2, Color.red(mMaskColor),
                Color.green(mMaskColor), Color.blue(mMaskColor));

        if (absVerticalOffset <= totalScrollRange / 2) {
            include_toolbar_search.setVisibility(View.VISIBLE);
            include_toolbar_small.setVisibility(View.GONE);

            //v_toolbar_search_mask.setBackgroundColor(argbDouble);
        } else {
            include_toolbar_search.setVisibility(View.GONE);
            include_toolbar_small.setVisibility(View.VISIBLE);

            //v_toolbar_small_mask.setBackgroundColor(title_small_argb);

        }
        //上滑时遮罩由全透明到半透明
        v_title_big_mask.setBackgroundColor(argb);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
