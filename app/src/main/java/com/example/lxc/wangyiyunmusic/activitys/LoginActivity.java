package com.example.lxc.wangyiyunmusic.activitys;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

//import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.EncryptUtils;
import com.example.lxc.wangyiyunmusic.MainActivity;
import com.example.lxc.wangyiyunmusic.R;
import com.example.lxc.wangyiyunmusic.utils.UserUtils;
import com.example.lxc.wangyiyunmusic.views.InputView;

public class LoginActivity extends BaseActivity {
    private InputView inputPhone, inputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        inputPhone = findViewById(R.id.et_phone);
        inputPassword = findViewById(R.id.et_password);

        //设置NavigationBar
        initNavBar(false, "登录", false);
    }

    /**
     * 跳转注册页面点击事件
     *
     * @param view
     */
    public void onRegisterClick(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    /**
     * 登录按钮点击事件
     *  1.验证用户手机号是否被注册
     *  2.如果已经注册了，则验证用户手机号和密码是否匹配
     *  3.跳转主页面
     *
     * @param view
     */
    public void onLoginClick(View view) {
        String phone = inputPhone.getInputText();
        String password = inputPassword.getInputText();
        password = EncryptUtils.encryptMD5ToString(password);
        if (!UserUtils.valiDataLogin(this, phone, password)) {
            return;
        }
        if(UserUtils.login(this,phone,password)){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}