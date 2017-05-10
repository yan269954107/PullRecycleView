package com.yanxw.pullrefresh.pull;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * PullAdapter
 * Created by yanxinwei on 2017/5/5.
 */

public abstract class PullAdapter extends RecyclerView.Adapter {

    private static final int TYPE_HEADER = 1;

    public Context mContext;
    private View mHeaderView;

    @Override
    final public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            mHeaderView = LayoutInflater.from(mContext).inflate(provideHeaderLayout(), parent, false);
            return new PullHeaderHolder(mHeaderView);
        } else {
            return provideViewHolder(parent, viewType);
        }
    }

    public abstract int provideHeaderLayout();
    public abstract RecyclerView.ViewHolder provideViewHolder(ViewGroup parent, int viewType);

    @Override
    final public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position != 0) {
            bindHolder(holder, position - 1);
        }
    }

    public abstract HeaderData getHeaderData();
    public abstract void bindHolder(RecyclerView.ViewHolder holder, int position);

    @Override
    final public int getItemCount() {
        return getDataCount() + 1;
    }

    public abstract int getDataCount();

    @Override
    final public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else {
            return getDataViewType(position - 1);
        }
    }

    public int getDataViewType(int position) {
        return 0;
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    private class PullHeaderHolder extends RecyclerView.ViewHolder {

        public PullHeaderHolder(View itemView) {
            super(itemView);
        }

    }
}
