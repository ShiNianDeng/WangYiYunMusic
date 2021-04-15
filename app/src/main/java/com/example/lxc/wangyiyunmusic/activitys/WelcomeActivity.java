package com.example.lxc.wangyiyunmusic.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.lxc.wangyiyunmusic.MainActivity;
import com.example.lxc.wangyiyunmusic.R;
import com.example.lxc.wangyiyunmusic.utils.UserUtils;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends BaseActivity {

    private Timer mTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        init();
    }

    private void init() {
        final boolean isLogin = UserUtils.valiDataUserLogin(this);
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(isLogin){
                    toMain();
                }else{
                    toLogin();
                }
            }
        }, 1 * 1000);
    }

    private void toLogin() {
        Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void toMain() {
        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}