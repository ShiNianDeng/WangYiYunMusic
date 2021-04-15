package com.example.lxc.wangyiyunmusic.activitys;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lxc.wangyiyunmusic.R;

public class BaseActivity extends Activity {
    private ImageView ivBack, ivMe;
    private TextView tvTitle;


    /**
     * findViewById
     *
     * @param id
     * @param <T>
     * @return
     */
    protected <T extends View> T fd(@IdRes int id) {
        return findViewById(id);
    }

    /**
     * 设置NavigationBar
     *
     * @param isShowBack 是否显示返回键
     * @param title      标题
     * @param isShowMe   是否显示我的
     */
    protected void initNavBar(boolean isShowBack, String title, boolean isShowMe) {
        //获取布局视图
        ivBack = fd(R.id.iv_back);
        ivMe = fd(R.id.iv_me);
        tvTitle = fd(R.id.tv_title);

        //绑定视图
        ivBack.setVisibility(isShowBack ? View.VISIBLE : View.GONE);
        ivMe.setVisibility(isShowMe ? View.VISIBLE : View.GONE);
        tvTitle.setText(title);

        //设置返回点击事件
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //设置我的点击事件
        ivMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BaseActivity.this,MeActivity.class);
                startActivity(intent);
            }
        });
    }
}