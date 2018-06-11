package com.example.liuxiaobing.zfbhome.mvc.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by lxb on 2017/2/8.
 */
public abstract class ViewHolder<T> extends RecyclerView.ViewHolder {
    protected Context mContext;

    public ViewHolder(Context context, ViewGroup parent, int layoutId) {
        super(LayoutInflater.from(context).inflate(layoutId, parent, false));
        mContext = context;
        findView(itemView);
    }

    public ViewHolder(View itemView) {
        super(itemView);
    }

    /**
     * 设置数据的接口，即将数据与View联系起来
     *
     * @param data
     */
    public abstract void setData(T data);

    /**
     * 与适配器对应的控件初始化操作，需要子类扩展
     *
     * @param itemView
     */
    public abstract void findView(View itemView);


}


