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
 * Date on 2018/6/8 7:51 PM
 * Desc:
 */
public class LXBRelativeLayout extends RelativeLayout {

    private static final String TAG = "wwq";
    private RelativeLayout rlHeader;
    private TextView tvName, tvSchool;
    private ImageView ivHeader;
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
    private float mRate = 0;  //距离与目标距离的变化率
    //mRate=mCurrentDistance/mNeedDistance
    private int mPhotoOriginHeight; //图片的原始高度
    private int mPhotoOriginWidth; //图片的原始宽度
    private int mPhotoLeft;  //图片距左边的距离
    private int mPhotoTop;  //图片距离上边的距离
    private int mPhotoNeedMoveDistanceX;  // 图片需要移动的X距离
    private int mPhotoNeedMoveDistanceY;  // 图片需要移动的Y距离
    //需要移动的文字
    private int mTextLeft;  //文字距左边的距离
    private int mTextTop;  //文字距离上边的距离
    private int mTextNeedMoveDistanceX;  // 文字需要移动的X距离
    private int mTextNeedMoveDistanceY;  //文字需要移动的Y距离
    private LinearLayout mLayout;


    /**
     * /**
     * 初始化需要滚动的距离
     */


    public LXBRelativeLayout(Context context) {
        super(context, null);
    }

    public LXBRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public LXBRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        mOrignHight = rlHeader.getLayoutParams().height;
        mMinHight = DensityUtil.dip2px(getContext(), 100);  //设置最小的高度为这么多
        mNeedDistance = mOrignHight - mMinHight;
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ivHeader.getLayoutParams();
        mPhotoOriginHeight = params.height;
        mPhotoOriginWidth = params.width;
        mPhotoLeft = params.leftMargin;
        mPhotoTop = params.topMargin;
        mPhotoNeedMoveDistanceX = DensityUtil.getScreenWidth((Activity) getContext()) / 2 - mPhotoLeft - mMinHight;
        mPhotoNeedMoveDistanceY = mPhotoTop - DensityUtil.dip2px(getContext(), 5);
        /*******************移动的文字初始化***************************/
        RelativeLayout.LayoutParams textParams = (RelativeLayout.LayoutParams) tvName.getLayoutParams();
        mTextLeft = textParams.leftMargin;
        mTextTop = textParams.topMargin;
        mTextNeedMoveDistanceX = DensityUtil.getScreenWidth((Activity) getContext()) / 2 - mTextLeft + 10;
        mTextNeedMoveDistanceY = mTextTop - textParams.height - mMinHight / 4;  //这里已改成剪去获取textview高度的

    }

    RecyclerView recyclerView;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        rlHeader = (RelativeLayout) findViewById(R.id.rl_header);
        ivHeader = (ImageView) findViewById(R.id.iv_header);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvSchool = (TextView) findViewById(R.id.tv_school);
        rlHeader.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                init();
                rlHeader.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.test_recleview);
        mLayout = findViewById(R.id.layout_test_top);
    }

    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (currentState == TOP && recyclerView.canScrollVertically(-1)) {
            return false;
        }
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
        mRate = (float) (mCurrentDistance * 1.0 / mNeedDistance);

        changeTheAlphaAndPostion(mRate);  //获取偏移率然后改变某些控件的透明度，和位置

    }
    //获取了偏移率（0.0-1.0），然后就可以根据这个改变透明度了和大小了。

    /**
     * 根据变化率来改变这些这些控件的变化率位置
     *
     * @param rate
     */
    private void changeTheAlphaAndPostion(float rate) {
        //先改变一些控件的透明度
        if (rate >= 1) {
        } else if (0.0 != rate) {
            tvSchool.setAlpha(1 - rate * 2);
        }

        //接下来是改变控件的大小和位置了  （这就是关键了）
        final RelativeLayout.LayoutParams photoParams = (RelativeLayout.LayoutParams) ivHeader.getLayoutParams();
        photoParams.width = (int) (mPhotoOriginWidth - (rate * (mPhotoOriginWidth - DensityUtil.dip2px(getContext(), 40))));
        photoParams.height = (int) (mPhotoOriginWidth - (rate * (mPhotoOriginWidth - DensityUtil.dip2px(getContext(), 40))));
        photoParams.leftMargin = (int) (mPhotoLeft + mPhotoNeedMoveDistanceX * rate);
        photoParams.topMargin = (int) (mPhotoTop - mPhotoNeedMoveDistanceY * rate);
        Log.d(TAG, "photoParams.leftMargin" + photoParams.leftMargin);
        Log.d(TAG, " photoParams.topMargin" + photoParams.topMargin);
        ivHeader.setLayoutParams(photoParams);
        /*********************文字设置****************************/
        final RelativeLayout.LayoutParams textParams = (RelativeLayout.LayoutParams) tvName.getLayoutParams();
        textParams.leftMargin = (int) (mTextLeft + mTextNeedMoveDistanceX * rate);
        textParams.topMargin = (int) (mTextTop - mTextNeedMoveDistanceY * rate);
        Log.d(TAG, "textParams.leftMargin" + textParams.leftMargin);
        Log.d(TAG, " textParams.topMargin" + textParams.topMargin);
        tvName.setLayoutParams(textParams);
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

        Log.d("wwq", "startTo--   " + rate);
        if (rate == 1) {
            mCurrentDistance = mNeedDistance;
            currentState = TOP;

        } else {
            mCurrentDistance = 0;
            currentState = BOTTOM;
        }

        final RelativeLayout.LayoutParams rlParams = (RelativeLayout.LayoutParams) rlHeader.getLayoutParams();
        final RelativeLayout.LayoutParams ivParams = (RelativeLayout.LayoutParams) ivHeader.getLayoutParams();
        final RelativeLayout.LayoutParams tvNameParams = (RelativeLayout.LayoutParams) tvName.getLayoutParams();
        RelativeLayout.LayoutParams tvSchoolParams = (RelativeLayout.LayoutParams) tvSchool.getLayoutParams();
        final ValueAnimator rlHAnim = ValueAnimator.ofFloat(rlParams.height, height).setDuration(200);
        rlHAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                rlParams.height = (int) animatedValue;
                rlHeader.setLayoutParams(rlParams);
            }
        });

        ValueAnimator ivLeftAnim = ValueAnimator.ofFloat(ivParams.leftMargin, mPhotoLeft + mPhotoNeedMoveDistanceX * rate).setDuration(200);
        ivLeftAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                ivParams.leftMargin = (int) value;
                ivHeader.setLayoutParams(ivParams);
            }
        });
        ValueAnimator ivTopAnim = ValueAnimator.ofFloat(ivParams.topMargin, mPhotoTop - mPhotoNeedMoveDistanceY * rate).setDuration(200);
        ivTopAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                ivParams.topMargin = (int) value;
                ivHeader.setLayoutParams(ivParams);

            }
        });

        ValueAnimator ivScaleAnim = ValueAnimator.ofFloat(ivParams.height, mPhotoOriginWidth - (rate * (mPhotoOriginWidth - DensityUtil.dip2px(getContext(), 40)))).setDuration(200);
        ivScaleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                ivParams.width = (int) value;
                ivParams.height = (int) value;
                ivHeader.setLayoutParams(ivParams);
            }
        });


        final ValueAnimator tvNameLeftAnim = ValueAnimator.ofFloat(tvNameParams.leftMargin, mTextLeft + mTextNeedMoveDistanceX * rate).setDuration(200);
        tvNameLeftAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                tvNameParams.leftMargin = (int) value;
                tvName.setLayoutParams(tvNameParams);
            }
        });

        ValueAnimator tvNameTopAnim = ValueAnimator.ofFloat(tvNameParams.topMargin, mTextTop - mTextNeedMoveDistanceY * rate).setDuration(200);
        tvNameTopAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                tvNameParams.topMargin = (int) value;
                tvName.setLayoutParams(tvNameParams);
            }
        });

        ValueAnimator tvSchoolAlphaAnim = ValueAnimator.ofFloat(tvSchool.getAlpha() * 2, rate == 1 ? 0 : 1).setDuration(200);
        tvSchoolAlphaAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                tvSchool.setAlpha(value);
            }
        });
        tvSchoolAlphaAnim.start();

        ivTopAnim.start();
        rlHAnim.start();
        ivLeftAnim.start();
        tvNameTopAnim.start();
        tvNameLeftAnim.start();
        ivScaleAnim.start();
    }
}
