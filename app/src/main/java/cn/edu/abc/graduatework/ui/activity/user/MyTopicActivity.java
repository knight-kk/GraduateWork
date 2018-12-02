package cn.edu.abc.graduatework.ui.activity.user;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.edu.abc.graduatework.R;
import cn.edu.abc.graduatework.adapter.MyViewPagerAdapter;
import cn.edu.abc.graduatework.ui.activity.BaseActivity;
import cn.edu.abc.graduatework.ui.fragment.topic.MyCreateTopicFragment;
import cn.edu.abc.graduatework.ui.fragment.topic.MyTopicFragment;

public class MyTopicActivity extends BaseActivity {

    @BindView(R.id.tabs)
    TabLayout mTabs;
    @BindView(R.id.appbar)
    AppBarLayout mAppbar;
    @BindView(R.id.vp_my_topic)
    ViewPager mVpMyTopic;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_topic);
        ButterKnife.bind(this);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new MyTopicFragment());
        fragments.add(new MyCreateTopicFragment());
        mVpMyTopic.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager(), fragments, new String[]{"我的关注", "我的创建"}));
        mTabs.setupWithViewPager(mVpMyTopic);
    }
}
