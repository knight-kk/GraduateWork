package cn.edu.abc.graduatework.ui.fragment.graduate;

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
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.edu.abc.graduatework.R;
import cn.edu.abc.graduatework.adapter.BaseAdapter;
import cn.edu.abc.graduatework.adapter.NewsAdapter;
import cn.edu.abc.graduatework.entity.MessageEvent;
import cn.edu.abc.graduatework.entity.News;
import cn.edu.abc.graduatework.entity.Result;
import cn.edu.abc.graduatework.entity.ResultEnum;
import cn.edu.abc.graduatework.entity.School;
import cn.edu.abc.graduatework.net.RetrofitClient;
import cn.edu.abc.graduatework.ui.activity.NewInfoActivity;
import cn.edu.abc.graduatework.viewmodel.GraduateNewsViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GraduateNewsFragment extends Fragment {

    @BindView(R.id.rv_news)
    RecyclerView mRvNews;
    @BindView(R.id.swipeRl)
    SwipeRefreshLayout mSwipeRl;
    Unbinder unbinder;
    @BindView(R.id.tv_empty)
    TextView mTvEmpty;
    private GraduateNewsViewModel mViewModel;

    private static final String TAG = "GraduateNewsFragment";
    private NewsAdapter mNewsAdapter;
    private String mSchoolId = "1";
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            RetrofitClient.getApiService().getNews(mSchoolId).enqueue(new Callback<Result<ArrayList<News>>>() {
                @Override
                public void onResponse(Call<Result<ArrayList<News>>> call, Response<Result<ArrayList<News>>> response) {
                    mSwipeRl.setRefreshing(false);
                    if (response.isSuccessful()) {
                        Result<ArrayList<News>> result = response.body();
                        if (result.getCode() == ResultEnum.SUCCESS.getCode()) {

                            ArrayList<News> value = result.getValue();
                            if (value != null && value.size() > 0) {
                                mRvNews.setVisibility(View.VISIBLE);
                                mTvEmpty.setVisibility(View.GONE);
                                updateUI(value);
                            } else {
                                mRvNews.setVisibility(View.GONE);
                                mTvEmpty.setVisibility(View.VISIBLE);
                            }
                        } else {
                            mRvNews.setVisibility(View.GONE);
                            mTvEmpty.setVisibility(View.VISIBLE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<Result<ArrayList<News>>> call, Throwable t) {

                }
            });
        }
    };


    public static GraduateNewsFragment newInstance() {
        return new GraduateNewsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent<School> event) {
        if (event != null) {
            mSchoolId = event.getValue().getSchoolId();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.graduate_news_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
    }

    @Override
    public void onResume() {
        super.onResume();
        mSwipeRl.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRl.setRefreshing(true);
                mOnRefreshListener.onRefresh();

            }
        });
    }

    private void initView() {
        mSwipeRl.setColorSchemeResources(R.color.google_blue, R.color.google_green, R.color.google_red, R.color.google_yellow);
        mSwipeRl.setSlingshotDistance(100);

        mSwipeRl.setOnRefreshListener(mOnRefreshListener);

        mRvNews.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvNews.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

    private void updateUI(final ArrayList<News> news) {
        if (mNewsAdapter == null) {
            mNewsAdapter = new NewsAdapter(getContext(), news);
            mNewsAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(getContext(), NewInfoActivity.class);
                    intent.putExtra(NewInfoActivity.TEXT_HTML,news.get(position).getContent());
                    intent.putExtra(NewInfoActivity.ARTICLE_ID,news.get(position).getId());
                    startActivity(intent);
                }
            });
            mRvNews.setAdapter(mNewsAdapter);
        } else {
            mNewsAdapter.notifyDataSetChanged();
        }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
