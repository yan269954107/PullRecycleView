package com.yanxw.pullrefresh.pull;

/**
 * TopPullListener
 * Created by yanxinwei on 2017/5/5.
 */

public interface TopPullListener {
    void pullDistance(int distance);

    void pullUp();
}
