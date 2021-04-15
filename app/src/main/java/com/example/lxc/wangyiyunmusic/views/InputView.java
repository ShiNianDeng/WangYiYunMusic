package com.example.lxc.wangyiyunmusic.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.lxc.wangyiyunmusic.R;

public class InputView extends FrameLayout {
    private int inputIconId;    //输入框前面的小图标
    private String inputHint;   //输入框内的提示内容
    private Boolean isPassword; //输入框是否以密文的方式显示

    private View mView;
    private ImageView ivIcon;

    public String getInputText() {
        return etHint.getText().toString().trim();
    }

    private EditText etHint;

    public InputView(@NonNull Context context) {
        super(context);
        initView(context, null);
    }

    public InputView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public InputView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public InputView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {

        //获得布局属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.inputView);
        inputIconId = typedArray.getResourceId(R.styleable.inputView_input_icon, R.mipmap.logo);
        inputHint = typedArray.getString(R.styleable.inputView_input_hint);
        isPassword = typedArray.getBoolean(R.styleable.inputView_input_is_password, false);
        typedArray.recycle();

        //绑定布局
        mView = LayoutInflater.from(context).inflate(R.layout.input_view, this, false);
        ivIcon = mView.findViewById(R.id.input_icon);
        etHint = mView.findViewById(R.id.et_input);

        //设置布局属性
        ivIcon.setImageResource(inputIconId);
        etHint.setHint(inputHint);
        etHint.setInputType(isPassword ? InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD : InputType.TYPE_CLASS_PHONE);

        //添加布局
        addView(mView);
    }
}
