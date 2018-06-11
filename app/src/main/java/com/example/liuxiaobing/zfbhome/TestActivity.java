package com.example.liuxiaobing.zfbhome;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.liuxiaobing.zfbhome.mvc.control.TestAdapter;
import com.example.liuxiaobing.zfbhome.mvc.model.EmptyEntity;

/**
 * Copyright@ XinXiang ShangHai
 * Created by ${user}
 * Date on 2018/6/8 7:49 PM
 * Desc:
 */
@RequiresApi(api = Build.VERSION_CODES.M)
public class TestActivity extends AppCompatActivity {

    private RelativeLayout mTest;
    private LXBRelativeLayout mFrameLayout;
    private LinearLayout layoutTest;
    private int topMargin = 0;

    protected RecyclerView mRecycView;
    protected TestAdapter mAdapter;
    private LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_test);
        initView();
    }


    private void initView(){


        mRecycView = findViewById(R.id.test_recleview);
        mRecycView.setVisibility(View.VISIBLE);
        linearLayoutManager = new LinearLayoutManager(this);
        mRecycView.setLayoutManager(linearLayoutManager);
        mAdapter = new TestAdapter(this);
        mRecycView.setAdapter(mAdapter);

        for(int i = 0; i < 50; i ++){

            EmptyEntity entity = new EmptyEntity();
            mAdapter.getDataList().add(entity);
        }


        layoutTest = findViewById(R.id.layout_test);
        mTest = findViewById(R.id.test);
        mFrameLayout = findViewById(R.id.frame);
        /*mFrameLayout.setScrollListener(new LXBRelativeLayout.onScrollListener() {
            @Override
            public void onScrollDown(float dy,float posY) {  //dy > 0

                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)layoutTest.getLayoutParams();
                topMargin = params.topMargin;
                if(topMargin <= mFrameLayout.getMargin()){
                    System.out.println("76-------------:"+topMargin);
                    params.setMargins(0,topMargin + (int)dy,0,0);
                    layoutTest.setLayoutParams(params);

                }

            }

            @Override
            public void onScrollUp(float dy,float posY) { //dy< 0

                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)layoutTest.getLayoutParams();
                topMargin = params.topMargin;
                if(topMargin  >= 0){
                    params.setMargins(0,topMargin + (int)dy,0,0);
                    layoutTest.setLayoutParams(params);
                    System.out.println("91-------------:"+topMargin);
                }
            }
        });*/


        /*mRecycView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            //返回当前recyclerview的可见的item数目，也就是datas.length
            //dx是水平滚动的距离，dy是垂直滚动距离，向上滚动的时候为正，向下滚动的时候为负
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int firstVisibleItemPosition= linearLayoutManager.findFirstVisibleItemPosition();//可见范围内的第一项的位置
                int lastVisibleItemPosition= linearLayoutManager.findLastVisibleItemPosition();//可见范围内的最后一项的位置
                int itemCount=linearLayoutManager.getItemCount();//recyclerview中的item的所有的数目
                System.out.println("109------------:"+dy);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)layoutTest.getLayoutParams();
                if(firstVisibleItemPosition == 0 && params.topMargin <= mFrameLayout.getMargin()){
                    mFrameLayout.setDispatchEvent(true);    //不分发事件，响应父布局触摸事件，达到整体下拉效果
                }
                else if(dy > 0 && params.topMargin >= mFrameLayout.getMargin()){
                    mFrameLayout.setDispatchEvent(true);   //不分发事件，响应父布局触摸事件，达到整体上拉效果
                }
                else{
                    mFrameLayout.setDispatchEvent(false);
                }
            }
        });


        findViewById(R.id.test).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

            }
        });
*/


    }
}
