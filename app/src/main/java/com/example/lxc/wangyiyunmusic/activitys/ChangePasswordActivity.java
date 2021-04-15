package com.example.lxc.wangyiyunmusic.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.lxc.wangyiyunmusic.R;
import com.example.lxc.wangyiyunmusic.utils.UserUtils;
import com.example.lxc.wangyiyunmusic.views.InputView;

public class ChangePasswordActivity extends BaseActivity {
    private InputView inputBefore,inputLater,inputLaterConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        initView();
    }

    private void initView() {
        initNavBar(true,"修改密码",false);
        inputBefore = fd(R.id.input_before);
        inputLater = fd(R.id.input_later);
        inputLaterConfirm = fd(R.id.input_later_confirm);
    }

    /**
     * 修改密码点击事件：
     *  调用UserUtils中的修改密码方法
     * @param view
     */
    public void onChangeClick(View view) {
        boolean result = UserUtils.changePassword(this,inputBefore.getInputText(),inputLater.getInputText(),inputLaterConfirm.getInputText());
        if(!result){
            return;
        }
        UserUtils.loginOut(this);
    }
}