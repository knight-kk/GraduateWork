package cn.edu.abc.graduatework.ui.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.edu.abc.graduatework.GlideApp;
import cn.edu.abc.graduatework.R;
import cn.edu.abc.graduatework.adapter.MyViewPagerAdapter;
import cn.edu.abc.graduatework.entity.Graduate;
import cn.edu.abc.graduatework.ui.fragment.graduate.GraduateInfoFragment;
import cn.edu.abc.graduatework.ui.fragment.topic.MyTopicFragment;
import cn.edu.abc.graduatework.util.ScreenUtil;
import de.hdodenhof.circleimageview.CircleImageView;

public class GraduateActivity extends AppCompatActivity {


    @BindView(R.id.bg_user_info)
    View mBgUserInfo;
    @BindView(R.id.user_icon)
    CircleImageView mUserIcon;
    @BindView(R.id.user_name)
    TextView mUserName;
    @BindView(R.id.user_school)
    TextView mUserSchool;
    @BindView(R.id.img_back)
    ImageView mImgBack;
    @BindView(R.id.user_small_icon)
    CircleImageView mUserSmallIcon;
    @BindView(R.id.bar_user_name)
    TextView mBarUserName;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tabs)
    TabLayout mTabs;
    @BindView(R.id.appbar)
    AppBarLayout mAppbar;
    private static final String TAG = "GraduateActivity";
    @BindView(R.id.ck_good)
    CheckBox mCkGood;

    public static final String KEY_GRADUATE = "key_graduate";
    @BindView(R.id.vp_graduate)
    ViewPager mVpGraduate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graduate);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        mToolbar.setPadding(0, ScreenUtil.getStatusBarHeight(this), 0, 0);
        initView();
        initEvent();
    }

    private void initView() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Graduate graduate = (Graduate) extras.getSerializable(KEY_GRADUATE);
            if (graduate != null) {
                GlideApp.with(this).load(graduate.getImgUrl())
                        .placeholder(R.drawable.default_img)
                        .error(R.drawable.error_img)
                        .fitCenter()
                        .into(mUserIcon);
                GlideApp.with(this).load(graduate.getImgUrl())
                        .placeholder(R.drawable.default_img)
                        .error(R.drawable.error_img)
                        .fitCenter()
                        .into(mUserSmallIcon);
                if (graduate.getUserName() != null) {
                    mUserName.setText(graduate.getUserName());
                    mBarUserName.setText(graduate.getUserName());
                }
                ArrayList<Fragment> fragments = new ArrayList<>();
                fragments.add(GraduateInfoFragment.newInstance(graduate.getGraduateInfo()));
                fragments.add(new MyTopicFragment());
                mVpGraduate.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager(), fragments, new String[]{"个人简介", "关注的圈子"}));
                mTabs.setupWithViewPager(mVpGraduate);
            }

        }


    }

    boolean isExpand = true;

    private void initEvent() {
        mAppbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {// 展开
                    mImgBack.setImageResource(R.drawable.ic_arrow_back_white_24dp);
                    getWindow().getDecorView().setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {//收起
                    mUserSmallIcon.setVisibility(View.VISIBLE);
                    mBarUserName.setVisibility(View.VISIBLE);
                    mImgBack.setImageResource(R.drawable.ic_arrow_back_black_24dp);
                    mCkGood.setVisibility(View.INVISIBLE);
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    isExpand = false;
                } else {
                    mUserSmallIcon.setVisibility(View.INVISIBLE);
                    mBarUserName.setVisibility(View.INVISIBLE);
                    mCkGood.setVisibility(View.VISIBLE);

                    float alphaValue = (float) Math.abs(verticalOffset) * 255f / (float) appBarLayout.getTotalScrollRange();
                    mToolbar.getBackground().setAlpha((int) alphaValue);
                }
                float alphaValue = (float) Math.abs(verticalOffset) * 255f / (float) appBarLayout.getTotalScrollRange();
                mToolbar.getBackground().setAlpha((int) alphaValue);
            }
        });


        mCkGood.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    String text = mCkGood.getText().toString().trim();
                    mCkGood.setText(" "+(Integer.parseInt(text) + 1) + "");
                } else {
                    String text = mCkGood.getText().toString().trim();
                    mCkGood.setText(" "+(Integer.parseInt(text) - 1) + "");
                }
            }
        });

    }

    @OnClick(R.id.img_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mToolbar.getBackground().setAlpha((255));
    }

}
