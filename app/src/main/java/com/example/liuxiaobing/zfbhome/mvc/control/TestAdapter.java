package com.example.liuxiaobing.zfbhome.mvc.control;

import android.content.Context;
import android.view.ViewGroup;

import com.example.liuxiaobing.zfbhome.mvc.view.TestView;
import com.example.liuxiaobing.zfbhome.mvc.view.ViewHolder;


/**
 * Copyright@ XinXiang ShangHai
 * Created by ${user}
 * Date on 2018/6/8 3:50 PM
 * Desc:
 */
public class TestAdapter extends RecyclerViewBaseAdapter {

    public TestAdapter(Context context) {
        super(context);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType == 524){
            return new TestView(context,parent);
        }

        return super.onCreateViewHolder(parent,viewType);
    }
}
