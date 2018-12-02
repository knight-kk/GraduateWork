package cn.edu.abc.graduatework.ui.activity.login;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import cn.edu.abc.graduatework.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.edu.abc.graduatework.constant.SpConstant;
import cn.edu.abc.graduatework.entity.Result;
import cn.edu.abc.graduatework.entity.ResultEnum;
import cn.edu.abc.graduatework.ui.activity.BaseActivity;
import cn.edu.abc.graduatework.ui.dialog.LoadDialog;
import cn.edu.abc.graduatework.util.InputUtil;
import cn.edu.abc.graduatework.util.StringUtil;
import cn.edu.abc.graduatework.viewmodel.UserViewModel;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_userName)
    AppCompatEditText mEtUserName;
    @BindView(R.id.et_email)
    AppCompatEditText mEtEmail;
    @BindView(R.id.et_password)
    AppCompatEditText mEtPassword;
    @BindView(R.id.et_re_password)
    AppCompatEditText mEtRePassword;
    @BindView(R.id.btn_register)
    AppCompatButton mBtnRegister;
    private UserViewModel mViewModel;
    private LoadDialog mLoadDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initVew();
        initEvent();
        mViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        mViewModel.getRegisterLiveData().observe(this, new Observer<Result<String>>() {
            @Override
            public void onChanged(@Nullable Result<String> result) {
                if (mLoadDialog != null) {
                    mLoadDialog.dismiss();
                }
                updateUI(result);
            }
        });

    }

    private void updateUI(Result<String> result) {
        if (result != null) {
            if (result.getCode() == ResultEnum.SUCCESS.getCode()) {
                getSharedPreferences(SpConstant.SP_NAME, MODE_PRIVATE).edit().putString(SpConstant.USER_ID, result.getValue()).apply();
                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, PerfectInfoActivity.class));
                finish();
            } else {
                Toast.makeText(RegisterActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void initEvent() {
        InputUtil.ChangeButtonStatue(mBtnRegister, mEtUserName, mEtEmail, mEtPassword, mEtRePassword);

    }

    private void initVew() {
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @OnClick(R.id.btn_register)
    public void onViewClicked() {
        String userName = mEtUserName.getText().toString().trim();
        String email = mEtEmail.getText().toString().trim();
        String password = mEtPassword.getText().toString().trim();
        String rePassword = mEtRePassword.getText().toString().trim();
        if (!StringUtil.isEmail(email)) {
            Toast.makeText(this, "请输入正确的邮箱格式", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 6) {
            Toast.makeText(this, "密码长度至6位", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(rePassword)) {
            Toast.makeText(this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mLoadDialog == null) {
            mLoadDialog = new LoadDialog(this);
        }
        mLoadDialog.show();
        mViewModel.register(userName, email, password);
    }
}
