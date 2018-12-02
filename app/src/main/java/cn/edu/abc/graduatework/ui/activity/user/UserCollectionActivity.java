package cn.edu.abc.graduatework.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.edu.abc.graduatework.R;
import cn.edu.abc.graduatework.adapter.BaseAdapter;
import cn.edu.abc.graduatework.adapter.NewPublishAdapter;
import cn.edu.abc.graduatework.entity.Article;
import cn.edu.abc.graduatework.entity.Result;
import cn.edu.abc.graduatework.entity.ResultEnum;
import cn.edu.abc.graduatework.net.RetrofitClient;
import cn.edu.abc.graduatework.ui.activity.ArticleInfoActivity;
import cn.edu.abc.graduatework.ui.activity.BaseActivity;
import cn.edu.abc.graduatework.util.SpUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserCollectionActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    private NewPublishAdapter mNewPublishAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_collection);
        ButterKnife.bind(this);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mRvList.setLayoutManager(new LinearLayoutManager(this));
        mRvList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        RetrofitClient.getApiService().getCollection(SpUtil.getUserId(this)).enqueue(new Callback<Result<ArrayList<Article>>>() {
            @Override
            public void onResponse(Call<Result<ArrayList<Article>>> call, Response<Result<ArrayList<Article>>> response) {
                if (response.isSuccessful()) {
                    if (response.body().getCode() == ResultEnum.SUCCESS.getCode()) {
                        updateUI(response.body().getValue());
                    }
                }
            }

            @Override
            public void onFailure(Call<Result<ArrayList<Article>>> call, Throwable t) {
            }
        });


    }

    private void updateUI(ArrayList<Article> articles) {
        mNewPublishAdapter = new NewPublishAdapter(this, articles);
        mRvList.setAdapter(mNewPublishAdapter);
        mNewPublishAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(UserCollectionActivity.this, ArticleInfoActivity.class));
            }
        });
    }
}
