package com.example.liuxiaobing.zfbhome;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Copyright@ XinXiang ShangHai
 * Created by ${user}
 * Date on 2018/6/11 3:37 PM
 * Desc:
 */
public class SwipRelativeLayout extends RelativeLayout {

    private RelativeLayout rlHeader;
    /**
     * 几个状态
     */
    public static int TOP = 0;//顶部
    public static int BOTTOM = 1;//底部
    public int currentState = BOTTOM;//当前状态
    /**
     * 变量
     *
     * @param context
     */
    private int mLastY = 0;  //最后的点
    private static int mNeedDistance;   // 需要滑动的距离
    private static int mMinHight; //最小高度
    private static int mOrignHight; //原始的高度

    private int mCurrentDistance = 0;  //当前的距离

    private LinearLayout mLayout;


    /**
     * /**
     * 初始化需要滚动的距离
     */


    public SwipRelativeLayout(Context context) {
        super(context, null);
    }

    public SwipRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public SwipRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        mOrignHight = rlHeader.getLayoutParams().height + mLayout.getLayoutParams().height;
        mMinHight = DensityUtil.dip2px(getContext(), 50);  //设置最小的高度为按钮的高度
        mNeedDistance = mOrignHight - mMinHight;
    }

    RecyclerView recyclerView;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        rlHeader = (RelativeLayout) findViewById(R.id.rl_header);

        rlHeader.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                init();
                rlHeader.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.test_recleview);
        mLayout = findViewById(R.id.layout_nav);
    }

    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        /*if (currentState == TOP && recyclerView.canScrollVertically(-1)) {
            return false;
        }*/
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = (int) ev.getY();
                tempY = (int) ev.getY();

                break;//传递事件 例如可以用来子view的点击事件等
            case MotionEvent.ACTION_MOVE:
                int y = (int) ev.getY();
                int dy = mLastY - y;
                if (mCurrentDistance >= mNeedDistance && dy >= 0) {

                    return false;  //传递事件
                }
                if (mCurrentDistance <= 0 && dy <= 0) {

                    return false; //把事件传递进去
                }

                return true;
            case MotionEvent.ACTION_UP:

                return false;
        }

        return super.onInterceptTouchEvent(ev);

    }

    private int tempY;
    private boolean isUp;

    private VelocityTracker mVelocityTracker;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(ev);

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = (int) ev.getY();
                break; //传递事件 例如可以用来子view的点击事件等
            case MotionEvent.ACTION_MOVE:
                int distance = ((int) getY() - tempY);

                int y = (int) ev.getY();
                int dy = mLastY - y;

                /*if (mCurrentDistance >= mNeedDistance && dy > 0) {
                    return false;  //传递事件
                }
                if (mCurrentDistance <= 0 && dy < 0) {
                    return false; //把事件传递进去
                }*/
                changeTheLayout(dy);
                mLastY = y;
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (mVelocityTracker != null) {
                    final VelocityTracker velocityTracker = mVelocityTracker;
                    velocityTracker.computeCurrentVelocity(1000);
                    int velocityY = (int) velocityTracker.getYVelocity();
                    if (velocityY > 400) {//下划
                        startToTop(0, mOrignHight);
                    } else if (velocityY < -400) {//上划
                        startToTop(1, mMinHight);
                    } else {
                        tempY = 0;
                        if (mCurrentDistance > mNeedDistance / 2) {
                            startToTop(1, mMinHight);
                        } else {
                            startToTop(0, mOrignHight);
                        }
                    }
                    mVelocityTracker.recycle();
                    mVelocityTracker = null;

                }

                break;
        }
        return false;

    }

    /**
     * 通过滑动的偏移量
     *
     * @param dy
     */
    private void changeTheLayout(int dy) {
        final ViewGroup.LayoutParams layoutParams = rlHeader.getLayoutParams();
        layoutParams.height = layoutParams.height - dy;
        rlHeader.setLayoutParams(layoutParams);

        checkTheHeight();
        rlHeader.requestLayout();

        //计算当前移动了多少距离
        mCurrentDistance = mOrignHight - rlHeader.getLayoutParams().height;

    }


    /**
     * 检查上边界和下边界
     */
    private void checkTheHeight() {
        final ViewGroup.LayoutParams layoutParams = rlHeader.getLayoutParams();
        if (layoutParams.height < mMinHight) {
            layoutParams.height = mMinHight;
            rlHeader.setLayoutParams(layoutParams);
            rlHeader.requestLayout();
        }
        if (layoutParams.height > mOrignHight) {
            layoutParams.height = mOrignHight;
            rlHeader.setLayoutParams(layoutParams);
            rlHeader.requestLayout();
        }

    }


    private void startToTop(float rate, int height) {

        if (rate == 1) {
            mCurrentDistance = mNeedDistance;
            currentState = TOP;

        } else {
            mCurrentDistance = 0;
            currentState = BOTTOM;
        }
        final RelativeLayout.LayoutParams rlParams = (RelativeLayout.LayoutParams) rlHeader.getLayoutParams();
        final ValueAnimator rlHAnim = ValueAnimator.ofFloat(rlParams.height, height).setDuration(200);
        rlHAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                rlParams.height = (int) animatedValue;
                rlHeader.setLayoutParams(rlParams);
            }
        });

        rlHAnim.start();

    }
}