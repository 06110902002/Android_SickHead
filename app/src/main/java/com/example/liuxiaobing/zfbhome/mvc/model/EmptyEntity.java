package com.example.liuxiaobing.zfbhome.mvc.model;


import com.example.liuxiaobing.zfbhome.mvc.MVCConstant;

/**
 * Created by xiaobingLiu
 * Copriht @YunHang ShangHai
 * DATE：   2018/4/12 0012
 * Description :
 */

public class EmptyEntity extends BaseData {

    private String tips;

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    @Override
    public int getItemType() {
        return MVCConstant.EMPTY_TIPS;
    }
}
