package com.example.liuxiaobing.zfbhome;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.liuxiaobing.zfbhome.mvc.control.TestAdapter;
import com.example.liuxiaobing.zfbhome.mvc.model.EmptyEntity;

/**
 * Copyright@ XinXiang ShangHai
 * Created by ${user}
 * Date on 2018/6/11 3:34 PM
 * Desc:
 */
public class SwipActivity extends AppCompatActivity {

    private RelativeLayout mTest;
    private SwipRelativeLayout mFrameLayout;
    private LinearLayout layoutTest;
    private int topMargin = 0;

    protected RecyclerView mRecycView;
    protected TestAdapter mAdapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_swip);
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






    }
}
