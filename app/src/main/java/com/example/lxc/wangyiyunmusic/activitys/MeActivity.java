package com.example.lxc.wangyiyunmusic.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lxc.wangyiyunmusic.R;
import com.example.lxc.wangyiyunmusic.helps.UserHelp;
import com.example.lxc.wangyiyunmusic.utils.UserUtils;

public class MeActivity extends BaseActivity {

    private TextView tvUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);

        initView();
    }

    private void initView() {
        initNavBar(true, "个人中心", false);
        tvUser = fd(R.id.tv_user);
        tvUser.setText("用户名："+ UserHelp.geteInstance().getPhone());
    }

    /**
     * 退出登录点击事件
     * @param view
     */
    public void onloginOutClick(View view) {
        UserUtils.loginOut(this);
        Toast.makeText(this, "退出登录", Toast.LENGTH_SHORT).show();
    }

    /**
     * 修改密码点击事件
     * @param view
     */
    public void onChangeClick(View view) {
        Intent intent = new Intent(this,ChangePasswordActivity.class);
        startActivity(intent);
    }


}