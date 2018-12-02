package cn.edu.abc.graduatework.ui.fragment.topic;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.edu.abc.graduatework.R;
import cn.edu.abc.graduatework.adapter.BaseAdapter;
import cn.edu.abc.graduatework.adapter.NewPublishAdapter;
import cn.edu.abc.graduatework.entity.Article;
import cn.edu.abc.graduatework.entity.Result;
import cn.edu.abc.graduatework.entity.ResultEnum;
import cn.edu.abc.graduatework.ui.activity.ArticleInfoActivity;
import cn.edu.abc.graduatework.viewmodel.TopicViewModel;

public class NewArticleFragment extends Fragment {

    @BindView(R.id.rv_article)
    RecyclerView mRvArticle;
    @BindView(R.id.swipeRl)
    SwipeRefreshLayout mSwipeRl;
    Unbinder unbinder;
    private TopicViewModel mViewModel;
    private String topicId = "";
    public static final String TOPIC_ID = "topic_id";

    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mViewModel.fetchNewPublish(topicId);
        }
    };
    private NewPublishAdapter mNewPublishAdapter;
    private ArrayList<Article> mValue;

    public static NewArticleFragment newInstance(String topicId) {

        NewArticleFragment newArticleFragment = new NewArticleFragment();
        Bundle args = new Bundle();
        args.putString(TOPIC_ID, topicId);
        newArticleFragment.setArguments(args);
        return newArticleFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            topicId = arguments.getString(TOPIC_ID);
        }

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_aritcle_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();

        mViewModel = ViewModelProviders.of(this).get(TopicViewModel.class);
        mViewModel.getArticleLiveData().observe(this, new Observer<Result<ArrayList<Article>>>() {
            @Override
            public void onChanged(@Nullable Result<ArrayList<Article>> arrayListResult) {
                if (arrayListResult != null && arrayListResult.getCode() == ResultEnum.SUCCESS.getCode()) {
                    mSwipeRl.setRefreshing(false);
                    mValue=arrayListResult.getValue();
                    if (mValue != null) {
                    updateUI(mValue);
                    }
                }

            }
        });

        mViewModel.fetchNewPublish(topicId);

    }

    private void initView() {
        mSwipeRl.setColorSchemeResources(R.color.google_blue, R.color.google_green, R.color.google_red, R.color.google_yellow);
        mSwipeRl.setSlingshotDistance(100);
        mSwipeRl.setOnRefreshListener(mOnRefreshListener);
        mRvArticle.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvArticle.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

    private void updateUI(ArrayList<Article> articles) {
        mNewPublishAdapter = new NewPublishAdapter(getContext(), articles);
        mRvArticle.setAdapter(mNewPublishAdapter);
        mNewPublishAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), ArticleInfoActivity.class);
                intent.putExtra(ArticleInfoActivity.TEXT_HTML, mValue.get(position).getContent());
                intent.putExtra(ArticleInfoActivity.ARTICLE_ID, mValue.get(position).getId());
                intent.putExtra(ArticleInfoActivity.IS_COLLECTION, mValue.get(position).getIsCollection());
                startActivity(intent);
            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
