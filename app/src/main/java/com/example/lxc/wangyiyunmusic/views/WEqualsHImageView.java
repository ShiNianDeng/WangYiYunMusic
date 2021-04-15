package com.example.lxc.wangyiyunmusic.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.lxc.wangyiyunmusic.R;

public class WEqualsHImageView extends FrameLayout {

    private int ivRes;

    public ImageView getIvImg() {
        return ivImg;
    }

    private String ivNumber;

    private ImageView ivImg;

    public TextView getTvNumber() {
        return tvNumber;
    }

    private View mView;
    private TextView tvNumber;

    public WEqualsHImageView(@NonNull Context context) {
        super(context);
        initView(context, null);

    }

    public WEqualsHImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }


    public WEqualsHImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public WEqualsHImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, attrs);

    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.wEqualsHImageView);
        ivRes = typedArray.getResourceId(R.styleable.wEqualsHImageView_iv_icon, R.mipmap.img1);
        ivNumber = typedArray.getString(R.styleable.wEqualsHImageView_iv_number);
        typedArray.recycle();

        mView = LayoutInflater.from(context).inflate(R.layout.imageview_wequalsh, null, false);
        ivImg = mView.findViewById(R.id.iv_img);
        tvNumber = mView.findViewById(R.id.tv_number);

        ivImg.setImageResource(ivRes);
        tvNumber.setText(ivNumber);

        addView(mView);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
//        获取View宽度
//        int width = MeasureSpec.getSize(widthMeasureSpec);
//        获取View模式
//        match_parent、warp_content、具体dp
//        int mode = MeasureSpec.getMode(widthMeasureSpec);
    }
}
