package com.yanxw.pullrefresh;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yanxw.pullrefresh.pull.HeaderData;
import com.yanxw.pullrefresh.pull.PullAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * SampleAdapter
 * Created by yanxinwei on 2017/5/5.
 */

public class SampleAdapter extends PullAdapter {

    private List<String> data;

    private int mHeaderLayout;

    public SampleAdapter(List<String> data, Context context, int headerLayout) {
        this.data = data;
        mContext = context;
        mHeaderLayout = headerLayout;
    }

    @Override
    public int provideHeaderLayout() {
        return mHeaderLayout;
    }

    @Override
    public RecyclerView.ViewHolder provideViewHolder(ViewGroup parent, int viewType) {
        return new SampleHolder(LayoutInflater.from(mContext).inflate(R.layout.item_sample, parent, false));
    }

    @Override
    public HeaderData getHeaderData() {
        return null;
    }

    @Override
    public void bindHolder(RecyclerView.ViewHolder holder, int position) {
        SampleHolder sampleHolder = (SampleHolder) holder;
        String str = data.get(position);
        sampleHolder.txtSample.setText(str);
    }

    @Override
    public int getDataCount() {
        return data == null ? 0 : data.size();
    }

    class SampleHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_sample)
        TextView txtSample;

        SampleHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
