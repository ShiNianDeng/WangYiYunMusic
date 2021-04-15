package com.example.lxc.wangyiyunmusic.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.lxc.wangyiyunmusic.R;

public class TabLable extends LinearLayout {
    private String tabTitle;

    private View mView;
    private TextView tvTitle;

    public String getTvTitleText() {
        return tvTitle.getText().toString();
    }

    public TabLable(@NonNull Context context) {
        super(context);
        initView(context,null);
    }

    public TabLable(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context,attrs);
    }

    public TabLable(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context,attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TabLable(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context,attrs);
    }


    private void initView(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.tabLable);
        tabTitle = typedArray.getString(R.styleable.tabLable_tab_title);
        typedArray.recycle();

        mView = LayoutInflater.from(context).inflate(R.layout.tab_lable,this,false);
        tvTitle = mView.findViewById(R.id.tab_lable_title);
        tvTitle.setText(tabTitle);

        addView(mView);
    }




}
