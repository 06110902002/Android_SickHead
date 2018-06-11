package com.example.liuxiaobing.zfbhome.mvc.model;


/**
 * 数据基类,主要扩展接口用
 * Created by lxb on 2017/3/24.
 */

public class BaseData implements ViewDataType{

    /**
     * 子类需要重载
     * @return
     */
    @Override
    public int getItemType() {
        return 0;
    }
}
