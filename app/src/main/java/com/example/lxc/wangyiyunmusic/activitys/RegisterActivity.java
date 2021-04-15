package com.example.lxc.wangyiyunmusic.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.lxc.wangyiyunmusic.R;
import com.example.lxc.wangyiyunmusic.utils.UserUtils;
import com.example.lxc.wangyiyunmusic.views.InputView;

public class RegisterActivity extends BaseActivity {

    private InputView inputPhone, inputPassword, inputPasswordConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        initNavBar(true, "注册", false);

        inputPhone = fd(R.id.input_phone);
        inputPassword = fd(R.id.input_password);
        inputPasswordConfirm = fd(R.id.input_password_confirm);
    }

    /**
     * 注册按钮点击事件
     * 1、用户输入合法性验证
     *      1、用户输入的手机号是不是合法的
     *      2、用户是否输入了密码和确定密码，并且这两次输入是否相同
     *      3、输入的手机号是否已经被注册
     * 2、保存用户输入的手机号和密码（MD5加密密码）
     */
    public void onRegister2Click(View view) {
        String phone = inputPhone.getInputText();
        String password = inputPassword.getInputText();
        String passwordConfirm = inputPasswordConfirm.getInputText();
        if (!UserUtils.valiDataRegister(this, phone, password, passwordConfirm))  return;
        onBackPressed();
    }
}