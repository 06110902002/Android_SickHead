package com.example.liuxiaobing.zfbhome.mvc.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.liuxiaobing.zfbhome.R;
import com.example.liuxiaobing.zfbhome.mvc.model.EmptyEntity;

/**
 * Copyright@ XinXiang ShangHai
 * Created by ${user}
 * Date on 2018/6/8 3:52 PM
 * Desc:
 */
public class TestView extends ViewHolder<EmptyEntity> {


    public TextView txtDay;
    public TextView txtMoth;
    public TextView txtWeek;


    public TestView(Context context, ViewGroup parent) {
        super(context, parent, R.layout.layout_timeline_date_item);
    }

    @Override
    public void setData(EmptyEntity data) {
        if (data != null) {
            /*txtDay.setText(data.getLogDay() + "");
            txtMoth.setText(data.getLogMonth() + "月");
            txtWeek.setText(data.getLogWeek());*/

        }
    }

    @Override
    public void findView(View itemView) {
        txtDay = (TextView) itemView.findViewById(R.id.txt_date_day);
        txtMoth = (TextView) itemView.findViewById(R.id.txt_date_moth);
        txtWeek = (TextView) itemView.findViewById(R.id.txt_date_week);

    }
}