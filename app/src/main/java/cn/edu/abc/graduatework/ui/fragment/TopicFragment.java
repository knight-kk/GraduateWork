package cn.edu.abc.graduatework.ui.fragment;

import android.app.ActivityOptions;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.edu.abc.graduatework.R;
import cn.edu.abc.graduatework.adapter.BaseAdapter;
import cn.edu.abc.graduatework.adapter.FollowTopicAdapter;
import cn.edu.abc.graduatework.adapter.MyViewPagerAdapter;
import cn.edu.abc.graduatework.constant.SpConstant;
import cn.edu.abc.graduatework.entity.Result;
import cn.edu.abc.graduatework.entity.ResultEnum;
import cn.edu.abc.graduatework.entity.Topic;
import cn.edu.abc.graduatework.ui.activity.topic.AllTopicActivity;
import cn.edu.abc.graduatework.ui.activity.topic.TopicInfoActivity;
import cn.edu.abc.graduatework.ui.activity.user.MyTopicActivity;
import cn.edu.abc.graduatework.ui.fragment.topic.ArticleListFragment;
import cn.edu.abc.graduatework.ui.fragment.topic.NewArticleFragment;
import cn.edu.abc.graduatework.viewmodel.TopicViewModel;

public class TopicFragment extends Fragment {
    Unbinder unbinder;
    @BindView(R.id.user_topic)
    IconTextView mUserTopic;
    @BindView(R.id.tv_all_top)
    IconTextView mTvAllTop;
    @BindView(R.id.ll_title)
    LinearLayout mLlTitle;
    @BindView(R.id.toolbar_driver)
    View mToolbarDriver;
    @BindView(R.id.rv_follow_topic)
    RecyclerView mRvFollowTopic;
    @BindView(R.id.tabs)
    TabLayout mTabs;
    @BindView(R.id.appbar)
    AppBarLayout mAppbar;
    @BindView(R.id.vp_topic)
    ViewPager mVpTopic;
    @BindView(R.id.header_title)
    AppCompatTextView mHeaderTitle;
    @BindView(R.id.tv_footer)
    TextView mTvFooter;
    @BindView(R.id.ll_no_follow_topic)
    LinearLayout mLlNoFollowTopic;
    private TopicViewModel mViewModel;
    private ArrayList<Topic> mTopics;
    private FollowTopicAdapter mTopicAdapter;

    public static TopicFragment newInstance() {
        return new TopicFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.topic_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(TopicViewModel.class);
        initView();
        initEvent();
        mViewModel.getCurrTabType().setValue(0);
        mViewModel.getMyTopicLiveData().observe(this, new Observer<Result<ArrayList<Topic>>>() {
            @Override
            public void onChanged(@Nullable Result<ArrayList<Topic>> result) {
                if (result != null) {
                    updateUI(result);

                }

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.fetchMyTopic(getContext().
                getSharedPreferences(SpConstant.SP_NAME, Context.MODE_PRIVATE).getString(SpConstant.USER_ID, ""));
    }

    private void updateUI(Result<ArrayList<Topic>> result) {
        if (result.getCode() == ResultEnum.SUCCESS.getCode()) {
            mTopics = result.getValue();
            if (mTopics == null||mTopics.size()==0) {
                mLlNoFollowTopic.setVisibility(View.VISIBLE);
                mRvFollowTopic.setVisibility(View.GONE);
                return;
            }
            mLlNoFollowTopic.setVisibility(View.GONE);
            mRvFollowTopic.setVisibility(View.VISIBLE);
            if (mTopicAdapter == null) {
                mTopicAdapter = new FollowTopicAdapter(getContext(), mTopics);
                mRvFollowTopic.setAdapter(mTopicAdapter);
                mTopicAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {

                    @Override
                    public void onItemClick(View view, int position) {
                        if (position > 0 && position != mTopics.size() + 1) {
                            Intent intent = new Intent(getContext(), TopicInfoActivity.class);
                            Bundle bundle = new Bundle();
                            if (mTopics != null) {
                                bundle.putSerializable(TopicInfoActivity.KEY_TOPIC, mTopics.get(position-1));
                            }
                            intent.putExtras(bundle);
                            if (Build.VERSION.SDK_INT >= 21) {
                                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity(), view.findViewById(R.id.imageView), "image").toBundle());
                            } else {
                                startActivity(intent);
                            }
                        } else if (position == mTopics.size() + 1) {
                            startActivity(new Intent(getContext(), MyTopicActivity.class));
                        }
                    }
                });
            }

        } else {
            Toast.makeText(getContext(), result.getMsg(), Toast.LENGTH_SHORT).show();
        }
    }

    private void initEvent() {
        mTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewModel.getCurrTabType().setValue(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initView() {
        initFollowTopic();
        initViewPager();
    }

    private void initViewPager() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(NewArticleFragment.newInstance("353a48ac1b6a42b8a17d41b60b161a1e"));
        fragments.add(ArticleListFragment.newInstance());
        fragments.add(ArticleListFragment.newInstance());
        mVpTopic.setAdapter(new MyViewPagerAdapter(getChildFragmentManager(),
                fragments, new String[]{"最新发表", "热帖", "最新回复"}));
        mTabs.setupWithViewPager(mVpTopic);
    }

    private void initFollowTopic() {
        mRvFollowTopic.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.HORIZONTAL, false));

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.tv_all_top)
    public void onViewClicked() {
        startActivity(new Intent(getContext(), AllTopicActivity.class));
    }
}
