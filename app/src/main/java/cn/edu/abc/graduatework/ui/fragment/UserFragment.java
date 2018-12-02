package cn.edu.abc.graduatework.ui.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.joanzapata.iconify.widget.IconTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.edu.abc.graduatework.GlideApp;
import cn.edu.abc.graduatework.R;
import cn.edu.abc.graduatework.constant.SpConstant;
import cn.edu.abc.graduatework.entity.Result;
import cn.edu.abc.graduatework.entity.ResultEnum;
import cn.edu.abc.graduatework.entity.User;
import cn.edu.abc.graduatework.ui.activity.user.ByMeActivity;
import cn.edu.abc.graduatework.ui.activity.user.MyArticleActivity;
import cn.edu.abc.graduatework.ui.activity.user.UserCollectionActivity;
import cn.edu.abc.graduatework.ui.activity.login.LoginActivity;
import cn.edu.abc.graduatework.ui.activity.user.MyTopicActivity;
import cn.edu.abc.graduatework.ui.activity.user.UserInfoActivity;
import cn.edu.abc.graduatework.viewmodel.UserViewModel;
import de.hdodenhof.circleimageview.CircleImageView;


public class UserFragment extends BaseFragment {

    @BindView(R.id.img_avatar)
    CircleImageView mImgAvatar;
    @BindView(R.id.btn_to_login)
    AppCompatButton mBtnToLogin;
    @BindView(R.id.ll_layout_user_info)
    LinearLayoutCompat mLlLayoutUserInfo;
    @BindView(R.id.user_notice)
    IconTextView mUserNotice;
    @BindView(R.id.user_topic)
    IconTextView mUserTopic;
    @BindView(R.id.user_article)
    IconTextView mUserArticle;
    @BindView(R.id.user_collection)
    IconTextView mUserCollection;
    @BindView(R.id.settings)
    IconTextView mSettings;
    @BindView(R.id.by_me)
    IconTextView mByMe;
    Unbinder unbinder;
    @BindView(R.id.tv_user_name)
    AppCompatTextView mTvUserName;
    @BindView(R.id.tv_user_email)
    AppCompatTextView mTvUserEmail;
    @BindView(R.id.ll_user_info)
    LinearLayout mLlUserInfo;
    private UserViewModel mViewModel;

    public UserFragment() {
        // Required empty public constructor
    }

    public static UserFragment newInstance() {
        return new UserFragment();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        mViewModel.getLiveData().observe(this, new Observer<Result<User>>() {
            @Override
            public void onChanged(@Nullable Result<User> userResult) {
                updateUI(userResult);
            }
        });

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SpConstant.SP_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean(SpConstant.IS_LOGIN, false)) {
            mBtnToLogin.setVisibility(View.GONE);
            mLlUserInfo.setVisibility(View.VISIBLE);
            mViewModel.getUserInfo(sharedPreferences.getString(SpConstant.USER_ID, ""));
        }
    }

    /** 更新界面
     * @param userResult 服务器返回的用户数据
     */
    private void updateUI(Result<User> userResult) {
        if (userResult != null) {
            if (userResult.getCode() == ResultEnum.SUCCESS.getCode()) {
                User user = userResult.getValue();
                GlideApp.with(getContext()).load(user.getImg())
                        .placeholder(R.drawable.profile)
                        .error(R.drawable.error_img)
                        .fitCenter()
                        .into(mImgAvatar);
                mTvUserName.setText(user.getName());
                mTvUserEmail.setText(user.getEmail());
            } else {
                Toast.makeText(getContext(), "数据获取失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.img_avatar, R.id.btn_to_login, R.id.ll_layout_user_info, R.id.user_notice, R.id.user_topic, R.id.user_article, R.id.user_collection, R.id.settings, R.id.by_me})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_avatar:
                break;
            case R.id.btn_to_login:
                startActivity(new Intent(getContext(), LoginActivity.class));
                break;
            case R.id.ll_layout_user_info:
                openActivity(UserInfoActivity.class);
                break;
            case R.id.user_notice:
                Toast.makeText(getContext(), "开发中", Toast.LENGTH_SHORT).show();
                break;
            case R.id.user_topic:
                startActivity(new Intent(getContext(), MyTopicActivity.class));
                break;
            case R.id.user_article:
                startActivity(new Intent(getContext(), MyArticleActivity.class));
                break;
            case R.id.user_collection:
                startActivity(new Intent(getContext(), UserCollectionActivity.class));
                break;
            case R.id.settings:
                Toast.makeText(getContext(), "开发中", Toast.LENGTH_SHORT).show();
                break;
            case R.id.by_me:
                startActivity(new Intent(getContext(), ByMeActivity.class));
                break;
        }
    }
}
