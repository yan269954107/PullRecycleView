package com.yanxw.pullrefresh.pull;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * PullRecycleview
 * Created by yanxinwei on 2017/5/5.
 */

public class PullRecycleView extends RecyclerView {

    private boolean isScrolledUp = false;
    private float startTopY = 0;  //滑到顶部不能再滑动时的Y坐标

    private TopPullListener mPullListener;

    public PullRecycleView(Context context) {
        this(context, null);
    }

    public PullRecycleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        int action = MotionEventCompat.getActionMasked(e);
        if (action == MotionEvent.ACTION_DOWN) {
            isScrolledUp = false;
        } else if (action == MotionEvent.ACTION_MOVE) {
            //如果不能上滑了
            if (!canScrollVertically(-1)) {
                if (!isScrolledUp) {
                    isScrolledUp = true;
                    startTopY = e.getY();
                } else {
                    if (mPullListener != null) {
                        float distance = e.getY() - startTopY;
                        //!canScrollVertically(-1) 这个判断在顶部向上滑的时候也会成立，需要过滤掉负数的情况
                        if (distance > 0) {
                            mPullListener.pullDistance((int) (distance));
                        } else {
                            isScrolledUp = false;
                        }
                    }
                    return true;
                }
            }
        } else if (action == MotionEvent.ACTION_UP) {
            if (isScrolledUp && mPullListener != null) {
                mPullListener.pullUp();
            }
        }
        return super.onTouchEvent(e);
    }

    public void setPullListener(TopPullListener pullListener) {
        mPullListener = pullListener;
    }
}
