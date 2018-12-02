package cn.edu.abc.graduatework.ui.activity.topic;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.ShareCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.edu.abc.graduatework.GlideApp;
import cn.edu.abc.graduatework.R;
import cn.edu.abc.graduatework.adapter.MyViewPagerAdapter;
import cn.edu.abc.graduatework.constant.SpConstant;
import cn.edu.abc.graduatework.entity.Result;
import cn.edu.abc.graduatework.entity.ResultEnum;
import cn.edu.abc.graduatework.entity.Topic;
import cn.edu.abc.graduatework.net.RetrofitClient;
import cn.edu.abc.graduatework.ui.activity.BaseActivity;
import cn.edu.abc.graduatework.ui.fragment.topic.NewArticleFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopicInfoActivity extends BaseActivity {

    @BindView(R.id.img_icon)
    ImageView mImgIcon;
    @BindView(R.id.tv_title)
    AppCompatTextView mTvTitle;
    @BindView(R.id.tv_author)
    AppCompatTextView mTvAuthor;
    @BindView(R.id.tv_info)
    AppCompatTextView mTvInfo;
    @BindView(R.id.btn_follow)
    AppCompatButton mBtnFollow;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.appbar)
    AppBarLayout mAppbar;
    @BindView(R.id.vp_topic)
    ViewPager mVpTopic;
    @BindView(R.id.tabs)
    TabLayout mTabs;

    public static final String KEY_TOPIC = "key_topic";
    @BindView(R.id.floatingActionButton)
    FloatingActionButton mFloatingActionButton;
    private Topic mTopic;
    private String mUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_info);
        ButterKnife.bind(this);
        initVew();
        initEvent();
        mUserId = getSharedPreferences(SpConstant.SP_NAME, MODE_PRIVATE).getString(SpConstant.USER_ID, "");
        RetrofitClient.getApiService().getTopicInfo(mTopic.getId(), mUserId).enqueue(new Callback<Result<Topic>>() {
            @Override
            public void onResponse(Call<Result<Topic>> call, Response<Result<Topic>> response) {
                if (response.isSuccessful()) {
                    Result<Topic> body = response.body();
                    if (body.getCode() == ResultEnum.SUCCESS.getCode()) {
                        Topic value = body.getValue();
                        mTvAuthor.setText(value.getAuthor() + " • 创建");
                        if (value.getIsFollow() == 0) {
                            mBtnFollow.setBackgroundResource(R.drawable.bg_btn_follow_green);
                            mBtnFollow.setText("关注");
                            mTopic.setIsFollow(1);
                        } else {
                            mBtnFollow.setBackgroundResource(R.drawable.bg_btn_gray);
                            mBtnFollow.setText("已关注");
                            mTopic.setIsFollow(0);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Result<Topic>> call, Throwable t) {

            }
        });


    }

    private void initEvent() {
        mAppbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                if (i == 0) {// 展开
                    mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
                    if (mToolbar.getMenu().size() > 0) {
                        mToolbar.getMenu().getItem(0).setIcon(R.drawable.ic_share_white_24dp);
                    }
                    mToolbar.setTitle(" ");
                } else if (Math.abs(i) >= appBarLayout.getTotalScrollRange()) {//收起
                    mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
                    if (mToolbar.getMenu().size() > 0) {
                        mToolbar.getMenu().getItem(0).setIcon(R.drawable.ic_share_black_24dp);
                    }
                    mToolbar.setTitle(mTopic.getTitle());
                }
            }
        });

    }

    private void initVew() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mTopic = (Topic) extras.getSerializable(KEY_TOPIC);
            GlideApp.with(this).load(mTopic.getImgUrl())
                    .placeholder(R.drawable.default_img)
                    .error(R.drawable.error_img)
                    .fitCenter()
                    .into(mImgIcon);

            if (mTopic.getTitle() != null) {
                mTvTitle.setText(mTopic.getTitle());
            }
            if (mTopic.getContent() != null) {
                mTvInfo.setText(mTopic.getContent());
            }
        }


        initToolbar();
        initViewPager();
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.topic_share:
                        Intent shareIntent = ShareCompat.IntentBuilder.from(TopicInfoActivity.this)
                                .setText("我在高校毕业生关注了" + mTopic.getTitle())
                                .setType("text/plain")
                                .createChooserIntent();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        } else {
                            shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                        }
                        startActivity(shareIntent);
                        break;
                }
                return false;
            }
        });
    }

    private void initViewPager() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(NewArticleFragment.newInstance(mTopic.getId()));
        fragments.add(NewArticleFragment.newInstance(mTopic.getId()));
        fragments.add(NewArticleFragment.newInstance(mTopic.getId()));
        fragments.add(NewArticleFragment.newInstance(mTopic.getId()));
        mVpTopic.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager(),
                fragments, new String[]{"最新发表", "热帖", "最新回复", "成员"}));
        mTabs.setupWithViewPager(mVpTopic);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mToolbar.inflateMenu(R.menu.topic_menu);
        return super.onCreateOptionsMenu(menu);
    }

    @OnClick({R.id.btn_follow, R.id.floatingActionButton})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.btn_follow:
                if (mTopic.getIsFollow() == 0) {
                    mBtnFollow.setBackgroundResource(R.drawable.bg_btn_gray);
                    mBtnFollow.setText("已关注");
                } else {
                    mBtnFollow.setBackgroundResource(R.drawable.bg_btn_follow_green);
                    mBtnFollow.setText("关注");
                }

                RetrofitClient.getApiService().followTopic(mTopic.getId(), mUserId).enqueue(new Callback<Result<String>>() {
                    @Override
                    public void onResponse(Call<Result<String>> call, Response<Result<String>> response) {
                        if (response.isSuccessful()) {
                            if (mTopic.getIsFollow() == 0) {
                                mBtnFollow.setBackgroundResource(R.drawable.bg_btn_follow_green);
                                mBtnFollow.setText("关注");
                                mTopic.setIsFollow(0);
                                Toast.makeText(TopicInfoActivity.this, "取消关注成功", Toast.LENGTH_SHORT).show();
                            } else {
                                mBtnFollow.setBackgroundResource(R.drawable.bg_btn_gray);
                                mBtnFollow.setText("已关注");
                                mTopic.setIsFollow(1);
                                Toast.makeText(TopicInfoActivity.this, "关注成功", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Result<String>> call, Throwable t) {
                    }
                });
                break;

            case R.id.floatingActionButton:
                Bundle bundle = new Bundle();
                bundle.putString(AddArticleActivity.TOPIC_ID, mTopic.getId());
                openActivity(AddArticleActivity.class, bundle);
                break;
        }


    }


}
