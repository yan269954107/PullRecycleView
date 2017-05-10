package com.yanxw.pullrefresh;

import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yanxw.pullrefresh.pull.OnLoadListener;
import com.yanxw.pullrefresh.pull.TopPullListener;

/**
 * HeaderLoading
 * Created by yanxinwei on 2017/5/9.
 */

public class HeaderLoading implements TopPullListener {

    private View mHeaderView;
    private LinearLayout mLlLoading;
    private int mHeaderHeight;
    private int mLlLoadingMargin;

    private boolean isLoading;
    private OnLoadListener mLoadListener;

    public void init(View headerView) {
        mHeaderView = headerView;
        mHeaderHeight = mHeaderView.getHeight();
        mLlLoading = (LinearLayout) mHeaderView.findViewById(R.id.ll_loading);
        mLlLoadingMargin = ((RelativeLayout.LayoutParams) mLlLoading.getLayoutParams()).topMargin;
    }

    @Override
    public void pullDistance(int distance) {
        int offset = distance / 2;
        int height = mHeaderHeight + offset;
        setHeaderHeight(height);
        setLoadingMargin(offset / 2);
    }

    @Override
    public void pullUp() {
        if (isLoading && mLoadListener != null) {
            mLoadListener.onLoad();
        }
        if (!isLoading) {
            hideMarginAnimation();
        }
        hideHeightAnimation();
    }

    private void setHeaderHeight(int height) {
        ViewGroup.LayoutParams params = mHeaderView.getLayoutParams();
        params.height = height;
        mHeaderView.setLayoutParams(params);
        mHeaderView.requestLayout();
    }

    private void setLoadingMargin(int offset) {
        int margin = mLlLoadingMargin + offset;

        isLoading = margin > 0;

        if (margin > 0) margin = 0;

        setMargin(margin);
    }

    private void setMargin(int margin) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mLlLoading.getLayoutParams();
        params.topMargin = margin;
        mLlLoading.setLayoutParams(params);
        mLlLoading.requestLayout();
    }

    private void hideHeightAnimation() {
        int height = mHeaderView.getHeight();
        ValueAnimator animator = ValueAnimator.ofInt(height, mHeaderHeight);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 3.为目标对象的属性设置计算好的属性值
                int animatorValue = (int) animation.getAnimatedValue();
                setHeaderHeight(animatorValue);
            }
        });
        animator.setDuration(300);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
    }

    private void hideMarginAnimation() {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mLlLoading.getLayoutParams();
        int margin = params.topMargin;
        ValueAnimator animator = ValueAnimator.ofInt(margin, mLlLoadingMargin);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 3.为目标对象的属性设置计算好的属性值
                int animatorValue = (int) animation.getAnimatedValue();
                setMargin(animatorValue);
            }
        });
        animator.setDuration(300);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
    }

    public void loadComplete() {
        hideMarginAnimation();
        isLoading = false;
    }

    public void setLoadListener(OnLoadListener loadListener) {
        mLoadListener = loadListener;
    }
}
