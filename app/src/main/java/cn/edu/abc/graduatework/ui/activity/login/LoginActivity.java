package cn.edu.abc.graduatework.ui.activity.login;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Toast;

import cn.edu.abc.graduatework.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.edu.abc.graduatework.constant.SpConstant;
import cn.edu.abc.graduatework.entity.Result;
import cn.edu.abc.graduatework.entity.ResultEnum;
import cn.edu.abc.graduatework.entity.User;
import cn.edu.abc.graduatework.ui.activity.BaseActivity;
import cn.edu.abc.graduatework.ui.activity.MainActivity;
import cn.edu.abc.graduatework.ui.dialog.LoadDialog;
import cn.edu.abc.graduatework.util.InputUtil;
import cn.edu.abc.graduatework.util.StringUtil;
import cn.edu.abc.graduatework.viewmodel.UserViewModel;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_email)
    AppCompatEditText mEtEmail;
    @BindView(R.id.et_password)
    AppCompatEditText mEtPassword;
    @BindView(R.id.btn_forget)
    AppCompatButton mBtnForget;
    @BindView(R.id.btn_login)
    AppCompatButton mBtnLogin;
    @BindView(R.id.btn_register)
    AppCompatButton mBtnRegister;
    private UserViewModel mViewModel;
    private LoadDialog mLoadDialog;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
        initEvent();
        mViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        mViewModel.getLiveData().observe(this, new Observer<Result<User>>() {
            @Override
            public void onChanged(@Nullable Result<User> userResult) {
                if (mLoadDialog != null) {
                    mLoadDialog.dismiss();
                }
                updateUI(userResult);
            }
        });

    }

    private void initView() {
        mSharedPreferences = getSharedPreferences(SpConstant.SP_NAME, MODE_PRIVATE);
        mEtEmail.setText(mSharedPreferences.getString(SpConstant.USER_EMAIL, ""));
        mEtPassword.setText(mSharedPreferences.getString(SpConstant.USER_PASSWORD, ""));
    }

    private void updateUI(Result<User> userResult) {
        if (userResult != null) {
            if (userResult.getCode() == ResultEnum.SUCCESS.getCode()) {
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.putBoolean(SpConstant.IS_LOGIN, true);
                editor.putString(SpConstant.USER_ID,userResult.getValue().getId());
                editor.putString(SpConstant.USER_EMAIL, mEtEmail.getText().toString().trim());
                editor.putString(SpConstant.USER_PASSWORD, mEtPassword.getText().toString().trim());
                editor.apply();
                startActivity(new Intent(this, MainActivity.class));
                finish();
                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(LoginActivity.this, userResult.getMsg(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initEvent() {
        InputUtil.ChangeButtonStatue(mBtnLogin, mEtEmail, mEtPassword);
    }

    @OnClick({R.id.tv_close, R.id.btn_forget, R.id.btn_login, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_close:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;

            case R.id.btn_forget:
                break;
            case R.id.btn_login:

                String mEmail = mEtEmail.getText().toString().trim();
                if (!StringUtil.isEmail(mEmail)) {
                    Toast.makeText(this, "请输入正确的邮箱格式", Toast.LENGTH_SHORT).show();
                    return;
                }
                String password = mEtPassword.getText().toString().trim();
                if (mLoadDialog == null) {
                    mLoadDialog = new LoadDialog(this);

                    mLoadDialog.setCancelable(false);
                }
                mLoadDialog.show();
                //发起网络请求
                mViewModel.login(mEmail, password);

                break;
            case R.id.btn_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }
}
