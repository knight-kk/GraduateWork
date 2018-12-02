package cn.edu.abc.graduatework.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import cn.edu.abc.graduatework.R;

import cn.edu.abc.graduatework.constant.SpConstant;
import cn.edu.abc.graduatework.ui.activity.login.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        mSharedPreferences = getSharedPreferences(SpConstant.SP_NAME, MODE_PRIVATE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mSharedPreferences.getBoolean(SpConstant.IS_LOGIN, false)) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
                finish();
            }
        }, 2000);
    }
}
