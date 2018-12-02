package cn.edu.abc.graduatework.ui.activity.topic;


import android.app.ActivityOptions;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.edu.abc.graduatework.R;
import cn.edu.abc.graduatework.adapter.BaseAdapter;
import cn.edu.abc.graduatework.adapter.TopicAdapter;
import cn.edu.abc.graduatework.entity.Result;
import cn.edu.abc.graduatework.entity.ResultEnum;
import cn.edu.abc.graduatework.entity.Topic;
import cn.edu.abc.graduatework.ui.activity.BaseActivity;
import cn.edu.abc.graduatework.viewmodel.AllTopicViewModel;

public class AllTopicActivity extends BaseActivity {


    @BindView(R.id.ck_change_type)
    CheckBox mCkChangeType;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.appbar)
    AppBarLayout mAppbar;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.floatingActionButton)
    FloatingActionButton mFloatingActionButton;
    private AllTopicViewModel mViewModel;
    private TopicAdapter mTopicAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_topic);
        ButterKnife.bind(this);

        mViewModel = ViewModelProviders.of(this).get(AllTopicViewModel.class);

        mViewModel.getMutableLiveData().observe(this, new Observer<Result<ArrayList<Topic>>>() {
            @Override
            public void onChanged(@Nullable Result<ArrayList<Topic>> result) {
                if (result.getCode() == ResultEnum.SUCCESS.getCode()) {
                    updateUI(result.getValue());
                } else {
                    Toast.makeText(AllTopicActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
                }

            }
        });
        initView();
        initEvent();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewModel.loadData();
    }

    private void initView() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void updateUI(final ArrayList<Topic> topics) {
        if (mTopicAdapter == null) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mTopicAdapter = new TopicAdapter(this, topics);
            mRecyclerView.setAdapter(mTopicAdapter);
            mTopicAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(AllTopicActivity.this, TopicInfoActivity.class);
                    Bundle bundle = new Bundle();
                    if (topics != null) {
                        bundle.putSerializable(TopicInfoActivity.KEY_TOPIC, topics.get(position));
                    }
                    intent.putExtras(bundle);
                    if (Build.VERSION.SDK_INT >= 21) {
                        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(AllTopicActivity.this, view.findViewById(R.id.imageView), "image").toBundle());
                    } else {
                        startActivity(intent);
                    }
                }
            });
        } else {
            mTopicAdapter.notifyDataSetChanged();
        }

    }

    private void initEvent() {
        mCkChangeType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mTopicAdapter != null) {
                    if (isChecked) {
                        mTopicAdapter.setGrid(true);
                        mRecyclerView.setLayoutManager(new GridLayoutManager(AllTopicActivity.this, 2));
                        mRecyclerView.setAdapter(mTopicAdapter);
                    } else {
                        mTopicAdapter.setGrid(false);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(AllTopicActivity.this));
                        mRecyclerView.setAdapter(mTopicAdapter);
                    }
                }
            }
        });
    }


    @OnClick(R.id.floatingActionButton)
    public void onViewClicked() {
        startActivity(new Intent(AllTopicActivity.this, AddTopicActivity.class));
    }

}
