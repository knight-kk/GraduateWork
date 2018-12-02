package cn.edu.abc.graduatework.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.widget.FrameLayout;

import cn.edu.abc.graduatework.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.edu.abc.graduatework.ui.fragment.GraduateFragment;
import cn.edu.abc.graduatework.ui.fragment.HomeFragment;
import cn.edu.abc.graduatework.ui.fragment.TopicFragment;
import cn.edu.abc.graduatework.ui.fragment.UserFragment;


public class MainActivity extends BaseActivity {


    @BindView(R.id.frameLayout)
    FrameLayout mFrameLayout;
    @BindView(R.id.navigation)
    BottomNavigationView mNavigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, HomeFragment.newInstance()).commit();
        initEvent();
        mNavigation.setItemHorizontalTranslationEnabled(false);

    }

    private void initEvent() {
        mNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, HomeFragment.newInstance()).commit();
                        break;

                    case R.id.navigation_graduate:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, GraduateFragment.newInstance()).commit();
                        break;

                    case R.id.navigation_topic:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, TopicFragment.newInstance()).commit();
                        break;

                    case R.id.navigation_user:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, UserFragment.newInstance()).commit();
                        break;
                }

                return true;
            }
        });
    }


}
