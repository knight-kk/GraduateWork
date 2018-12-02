package cn.edu.abc.graduatework.ui.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.edu.abc.graduatework.R;
import cn.edu.abc.graduatework.adapter.MyViewPagerAdapter;
import cn.edu.abc.graduatework.entity.MessageEvent;
import cn.edu.abc.graduatework.entity.School;
import cn.edu.abc.graduatework.ui.activity.SelectSchoolActivity;
import cn.edu.abc.graduatework.ui.fragment.graduate.GraduateNewsFragment;
import cn.edu.abc.graduatework.ui.fragment.graduate.StudentFragment;
import cn.edu.abc.graduatework.viewmodel.GraduateViewModel;

public class GraduateFragment extends Fragment {


    Unbinder unbinder;
    @BindView(R.id.tv_title)
    AppCompatTextView mTvTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.tabs)
    TabLayout mTabs;
    @BindView(R.id.appbar)
    AppBarLayout mAppbar;
    @BindView(R.id.vp_graduate)
    ViewPager mVpGraduate;
    private GraduateViewModel mViewModel;
    private String[]  tabNames = {"毕业生", "就业资讯"};
    public  School mSchool;

    public static GraduateFragment newInstance() {
        return new GraduateFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.graduate_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initEvent();
        mViewModel = ViewModelProviders.of(this).get(GraduateViewModel.class);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent<School> event) {
        mSchool = event.getValue();
        mTvTitle.setText(mSchool.getName());
//        initViewPager();
    }
    private void initEvent() {
    }

    private void initView() {
        mSchool=new School();
        mSchool.setName("安徽商贸职业技术学院");
        mSchool.setSchoolId("1");
//
        initViewPager();
    }

    @Override
    public void onResume() {
        super.onResume();
        mToolbar.getBackground().setAlpha((255));

    }

    /**
     * 初始化ViewPager并和选项卡关联
     */
    private void initViewPager() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(StudentFragment.newInstance());
        fragments.add(GraduateNewsFragment.newInstance());
        mVpGraduate.setAdapter(new MyViewPagerAdapter(getChildFragmentManager(), fragments, tabNames));
        mTabs.setupWithViewPager(mVpGraduate);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }


    @OnClick(R.id.toolbar)
    public void onViewClicked() {
        startActivity(new Intent(getContext(), SelectSchoolActivity.class));
    }


}
