package cn.edu.abc.graduatework.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

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
import cn.edu.abc.graduatework.ui.dialog.LoadDialog;
import cn.edu.abc.graduatework.util.SpUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyArticleActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.tv_empty)
    TextView mTvEmpty;
    private NewPublishAdapter mNewPublishAdapter;
    private LoadDialog mLoadDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_article);
        ButterKnife.bind(this);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mLoadDialog = new LoadDialog(this);
        mLoadDialog.show();
        mRvList.setLayoutManager(new LinearLayoutManager(this));
        mRvList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        RetrofitClient.getApiService().getMyArticle(SpUtil.getUserId(this)).enqueue(new Callback<Result<ArrayList<Article>>>() {
            @Override
            public void onResponse(Call<Result<ArrayList<Article>>> call, Response<Result<ArrayList<Article>>> response) {
                mLoadDialog.dismiss();
                if (response.isSuccessful()) {
                    Result<ArrayList<Article>> body = response.body();
                    if (body.getCode() == ResultEnum.SUCCESS.getCode()) {
                        updateUI(body.getValue());
                    }

                }
            }

            @Override
            public void onFailure(Call<Result<ArrayList<Article>>> call, Throwable t) {
                mLoadDialog.dismiss();

            }
        });
    }


    private void updateUI(ArrayList<Article> articles) {
        mTvEmpty.setVisibility(View.GONE);
        mRvList.setVisibility(View.VISIBLE);
        mNewPublishAdapter = new NewPublishAdapter(this, articles);
        mRvList.setAdapter(mNewPublishAdapter);
        mNewPublishAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(MyArticleActivity.this, ArticleInfoActivity.class));
            }
        });


    }
}
