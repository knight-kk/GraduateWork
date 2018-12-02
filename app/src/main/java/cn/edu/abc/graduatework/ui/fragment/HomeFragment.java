package cn.edu.abc.graduatework.ui.fragment;

import android.app.ActivityOptions;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.transformer.ZoomOutSlideTransformer;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.edu.abc.graduatework.R;
import cn.edu.abc.graduatework.adapter.BaseAdapter;
import cn.edu.abc.graduatework.adapter.GoodGraduateAdapter;
import cn.edu.abc.graduatework.adapter.HotTopicAdapter;
import cn.edu.abc.graduatework.adapter.NewsAdapter;
import cn.edu.abc.graduatework.entity.Graduate;
import cn.edu.abc.graduatework.entity.News;
import cn.edu.abc.graduatework.entity.Result;
import cn.edu.abc.graduatework.entity.ResultEnum;
import cn.edu.abc.graduatework.entity.Topic;
import cn.edu.abc.graduatework.ui.activity.GraduateActivity;
import cn.edu.abc.graduatework.ui.activity.NewInfoActivity;
import cn.edu.abc.graduatework.ui.activity.topic.TopicInfoActivity;
import cn.edu.abc.graduatework.util.GlideImageLoader;
import cn.edu.abc.graduatework.viewmodel.HomeViewModel;

public class HomeFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.banner)
    Banner mBanner;
    @BindView(R.id.rv_good_graduate)
    RecyclerView mRvGoodGraduate;
    @BindView(R.id.rv_news)
    RecyclerView mRvNews;
    @BindView(R.id.rv_hot_topic)
    RecyclerView mRvHotTopic;
    private HomeViewModel mViewModel;
    private NewsAdapter mNewsAdapter;
    private HotTopicAdapter mTopicAdapter;
    private GoodGraduateAdapter mGraduateAdapter;
    private ArrayList<Graduate> mGraduates;
    private ArrayList<Topic> mTopics;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        mViewModel.fetchNews();
        mViewModel.fetchHotTopic();
        mViewModel.fetchGraduate();
        mViewModel.getNewsLiveData().observe(this, new Observer<Result<ArrayList<News>>>() {
            @Override
            public void onChanged(@Nullable Result<ArrayList<News>> result) {
                if (result.getCode() == ResultEnum.SUCCESS.getCode()) {
                    ArrayList<News> news = result.getValue();
                    updateNews(news);
                }
            }
        });
        mViewModel.getHotTopicLiveData().observe(this, new Observer<Result<ArrayList<Topic>>>() {
            @Override
            public void onChanged(@Nullable Result<ArrayList<Topic>> result) {
                mTopics = result.getValue();
                updateHotTopic(mTopics);
            }
        });

        mViewModel.getGraduateLiveData().observe(this, new Observer<ArrayList<Graduate>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Graduate> graduates) {
                mGraduates = graduates;
                updateGraduate(graduates);
            }
        });
        initView();
    }

    /**
     * 更新毕业生数据
     *
     * @param graduates
     */
    private void updateGraduate(ArrayList<Graduate> graduates) {
        if (mGraduateAdapter == null) {
            mRvGoodGraduate.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.HORIZONTAL, false));
            mGraduateAdapter = new GoodGraduateAdapter(getContext(), graduates);
            mRvGoodGraduate.setAdapter(mGraduateAdapter);
            mGraduateAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {

                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(getContext(), GraduateActivity.class);
                    Bundle bundle = new Bundle();
                    if (mGraduates != null) {
                        bundle.putSerializable(GraduateActivity.KEY_GRADUATE, mGraduates.get(position));
                    }
                    intent.putExtras(bundle);
                    if (Build.VERSION.SDK_INT >= 21) {
                        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity(), view.findViewById(R.id.imageView), "image").toBundle());
                    } else {
                        openActivity(GraduateActivity.class, bundle);
                    }
                }
            });
        } else {
            mGraduateAdapter.notifyDataSetChanged();
        }

    }

    /**
     * 跟新圈子数据
     *
     * @param topics
     */
    private void updateHotTopic(ArrayList<Topic> topics) {
        if (mTopicAdapter == null) {
            mRvHotTopic.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.HORIZONTAL, false));
            mTopicAdapter = new HotTopicAdapter(getContext(), topics);
            mRvHotTopic.setAdapter(mTopicAdapter);
            mTopicAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {

                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(getContext(), TopicInfoActivity.class);
                    Bundle bundle = new Bundle();
                    if (mTopics != null) {
                        bundle.putSerializable(TopicInfoActivity.KEY_TOPIC, mTopics.get(position));
                    }
                    intent.putExtras(bundle);
                    if (Build.VERSION.SDK_INT >= 21) {
                        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity(), view.findViewById(R.id.imageView), "image").toBundle());
                    } else {
                        startActivity(intent);
                    }
                }
            });
        } else {
            mTopicAdapter.notifyDataSetChanged();
        }

    }

    private void updateNews(final ArrayList<News> news) {
        if (mNewsAdapter == null) {
            mRvNews.setLayoutManager(new LinearLayoutManager(getContext()));
            mNewsAdapter = new NewsAdapter(getContext(), news);
            mRvNews.setAdapter(mNewsAdapter);
            mRvNews.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
            mNewsAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(getContext(), NewInfoActivity.class);
                    intent.putExtra(NewInfoActivity.TEXT_HTML,news.get(position).getContent());
                    intent.putExtra(NewInfoActivity.ARTICLE_ID,news.get(position).getId());
                    startActivity(intent);
                }
            });
        } else {
            mNewsAdapter.notifyDataSetChanged();
        }

    }


    private void initView() {
        initBanner();
        initRvGraduate();
    }


    private void initRvGraduate() {

    }

    private void initBanner() {
        ArrayList<Integer> localImages = new ArrayList<>();
        localImages.add(R.drawable.banner1);
        localImages.add(R.drawable.banner2);
        localImages.add(R.drawable.banner3);
//        localImages.add(R.drawable.banner4);
        mBanner.setBannerAnimation(ZoomOutSlideTransformer.class);
        //设置图片加载器
        mBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        mBanner.setImages(localImages);
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
