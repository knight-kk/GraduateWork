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
import cn.edu.abc.graduatework.ui.activity.ArticleInfoActivity;
import cn.edu.abc.graduatework.viewmodel.TopicViewModel;

public class ArticleListFragment extends Fragment {

    @BindView(R.id.rv_article)
    RecyclerView mRvArticle;
    @BindView(R.id.swipeRl)
    SwipeRefreshLayout mSwipeRl;
    Unbinder unbinder;
    private TopicViewModel mViewModel;
    private int mTabType;
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            switch (mTabType) {
                case 0:
//                    mViewModel.fetchNewPublish("");
                    break;
                case 1:
//                    mViewModel.fetchHotArticle("");
                    break;
                case 2:
//                    mViewModel.fetchNewReply("");
                    break;
            }
        }
    };
    private NewPublishAdapter mNewPublishAdapter;

    public static ArticleListFragment newInstance() {
        return new ArticleListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_puplish_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        if (getParentFragment() != null) {
            mViewModel = ViewModelProviders.of(getParentFragment()).get(TopicViewModel.class);
//            mViewModel.getArticleLiveData().observe(this, new Observer<ArrayList<Article>>() {
//                @Override
//                public void onChanged(@Nullable ArrayList<Article> articles) {
//                    mSwipeRl.setRefreshing(false);
//                    updateUI(articles);
//                }
//            });
            mViewModel.getCurrTabType().observe(this, new Observer<Integer>() {
                @Override
                public void onChanged(@Nullable Integer integer) {

                    if (integer != null && mSwipeRl != null) {
                        mTabType = integer;
                        if (isVisible()) {
                            mSwipeRl.setRefreshing(true);
                            mOnRefreshListener.onRefresh();
                        }
                    }
                }
            });
        }


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
                startActivity(new Intent(getContext(), ArticleInfoActivity.class));
            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
