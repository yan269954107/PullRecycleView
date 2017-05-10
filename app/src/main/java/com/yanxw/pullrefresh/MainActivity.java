package com.yanxw.pullrefresh;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.yanxw.pullrefresh.pull.OnLoadListener;
import com.yanxw.pullrefresh.pull.PullRecycleView;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements OnLoadListener{

    @BindView(R.id.prv_sample)
    PullRecycleView mRecycleView;

    private SampleAdapter mSampleAdapter;

    private HeaderLoading mHeaderLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mHeaderLoading = new HeaderLoading();
        mHeaderLoading.setLoadListener(this);

        List<String> data = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O");
        mSampleAdapter = new SampleAdapter(data, this, R.layout.item_sample_header);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mRecycleView.setAdapter(mSampleAdapter);
        mRecycleView.setPullListener(mHeaderLoading);

        mRecycleView.post(new Runnable() {
            @Override
            public void run() {
                mHeaderLoading.init(mSampleAdapter.getHeaderView());
            }
        });
    }

    @Override
    public void onLoad() {
        Log.d("tag", "@@@@ onLoad");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mHeaderLoading.loadComplete();
            }
        }, 1000);
    }
}
