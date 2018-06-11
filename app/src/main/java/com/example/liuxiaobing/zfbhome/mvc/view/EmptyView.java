package com.example.liuxiaobing.zfbhome.mvc.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.liuxiaobing.zfbhome.R;
import com.example.liuxiaobing.zfbhome.mvc.model.EmptyEntity;


/**
 * Created by lxb on 2017/2/8.
 */

public class EmptyView extends ViewHolder<EmptyEntity> {

    public LinearLayout mFrame;
    public TextView mTips;
    private EmptyEntity emptyEntity;


    public EmptyView(Context context, ViewGroup parent) {
        super(context, parent, R.layout.layout_list_empty);
    }

    @Override
    public void setData(EmptyEntity data) {
        if (emptyEntity != data) {
            emptyEntity = data;

        }
    }

    @Override
    public void findView(View itemView) {
        mTips = (TextView) itemView.findViewById(R.id.txt_tips);

    }
}