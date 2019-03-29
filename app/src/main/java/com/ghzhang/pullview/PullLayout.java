package com.ghzhang.pullview;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;

public class PullLayout extends LinearLayout {

    private boolean isScrolling;
    private float touchY;
    private float touchX;
    private int largeHegint = -1;
    private static final int STYLE_MARGIN = 0;
    private static final int STYLE_PADDING = 1;
    private static int STYLE = 1;  //默认padding
    private Scroller mScroller;
    private ViewConfiguration viewConfiguration;
    private String TAG = "hui";

    public PullLayout(Context context) {
        this(context, null);
    }


    public PullLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);

    }


    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PullLayout, defStyleAttr, 0);
        int height = typedArray.getInteger(R.styleable.PullLayout_stretchHeight, largeHegint);
        if (height != -1) {
            largeHegint = (int)dp2px(height);
        }

        STYLE = typedArray.getInt(R.styleable.PullLayout_style, STYLE);

        typedArray.recycle();

        mScroller = new Scroller(getContext(), new DecelerateInterpolator());
        viewConfiguration = ViewConfiguration.get(context);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:

                touchY = event.getRawY();

                Log.d(TAG, "onTouchEvent ,down");

                break;
            case MotionEvent.ACTION_MOVE:

                Log.d(TAG, "onTouchEvent ,move");

                float distanceY = touchY - event.getRawY();
                int disY = (int) ((distanceY) / 20);

                Log.d("hui", "distanceY-->" + distanceY + "   disY-->" + disY);

                /**
                 * 限制上滑并且滑动区间不超过容器高度
                 */
                if (distanceY < 0)
                    beginScroll(0, disY);
                Log.d("hui", "largeHegint-->" + largeHegint+"----->contentHeight------"+getHeight());


                break;


            case MotionEvent.ACTION_UP:
                Log.d(TAG, "onTouchEvent ,up");
                reset(0, 0);
                break;

            default:
                Log.d(TAG, "onTouchEvent--" + event.getAction());
                break;
        }

        return true;
    }


    protected void reset(int x, int y) {
        int dx = x - mScroller.getFinalX();
        int dy = y - mScroller.getFinalY();
        Log.d(TAG, "----getFinalX3-----        " + mScroller.getFinalX() + "----getFinalY3-------       " + mScroller.getFinalY());

        beginScroll(dx, dy);
    }

    private void beginScroll(int dx, int dy) {
        Log.d(TAG, "----getFinalX1-----        " + mScroller.getFinalX() + "----getFinalY1-------       " + mScroller.getFinalY());
        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy);
        Log.d(TAG, "----getFinalX2-----         " + mScroller.getFinalX() + "----getFinalY2-------        " + mScroller.getFinalY());
        Log.d(TAG, "----beginScroll-----         " + dx + "----beginScroll-------        " + dy);

        postInvalidateOnAnimation();


    }


    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            Log.d(TAG, "-----getCurrX------       " + mScroller.getCurrX() + "-----getCurrY------      " + mScroller.getCurrY());

            int curry = mScroller.getCurrY();

            if (largeHegint != -1) {
                curry = (int) Math.max(mScroller.getCurrY(), -largeHegint); //设置最大滑动距离
            } else {
                curry = Math.max(curry, -getHeight());//并且最大移动距离不能大于height
            }
            switch (STYLE) {
                case STYLE_MARGIN:
                    //设置margin

                    setLayout(curry);
                    break;

                case STYLE_PADDING:
                    //布局内容移动
                    scrollTo(mScroller.getCurrX(), curry);

                    break;
            }


            postInvalidateOnAnimation();

        }
        super.computeScroll();
    }

    private void setLayout(int currY) {

        LayoutParams layoutParams = (LayoutParams) getLayoutParams();
        //限制拉伸高度
        if (largeHegint != -1) {
            currY = (int) Math.max(currY, -largeHegint);
        }
        layoutParams.topMargin = Math.abs(currY);
        setLayoutParams(layoutParams);

    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                touchY = ev.getRawY();
                touchX = ev.getRawX();
                Log.d(TAG, "down");

                isScrolling = false;

                break;
            case MotionEvent.ACTION_MOVE:
                float dy = touchY - ev.getRawY();
                float dx = touchX - ev.getRawX();
                if (Math.abs(dy) > viewConfiguration.getScaledPagingTouchSlop()
                        || Math.abs(dx) > viewConfiguration.getScaledPagingTouchSlop()) {
                    isScrolling = true;
                    Log.d(TAG, "onInterceptTouchEvent ,开始拦截获取事件---" + dy);
                } else {
                    isScrolling = false;
                }

                break;
            default:
                Log.d(TAG, "ev" + ev.getAction());
                isScrolling = false;
                break;

        }
        return isScrolling;

    }



    public  float dp2px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }


}
