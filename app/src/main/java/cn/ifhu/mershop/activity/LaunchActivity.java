package cn.ifhu.mershop.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.ifhu.mershop.R;
import cn.ifhu.mershop.activity.login.LoginActivity;
import cn.ifhu.mershop.utils.UserLogic;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        new Handler().postDelayed(() -> {
            if (UserLogic.isLogin()) {
                startActivity(new Intent(LaunchActivity.this,MainActivity.class));
            } else {
                startActivity(new Intent(LaunchActivity.this, LoginActivity.class));
            }
        },1000);

    }
}
