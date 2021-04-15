package com.example.lxc.wangyiyunmusic.views;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.example.lxc.wangyiyunmusic.R;
import com.example.lxc.wangyiyunmusic.helps.MyMediaPlayerHelp;
import com.example.lxc.wangyiyunmusic.model.MusicModel;
import com.example.lxc.wangyiyunmusic.services.MusicService;

import jp.wasabeef.glide.transformations.CropCircleWithBorderTransformation;
import jp.wasabeef.glide.transformations.internal.Utils;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class PlayMusicView extends FrameLayout {
    private Context context;
    private View mView;
    private ImageView ivIcon, ivPlay, ivNeedle;
    private RelativeLayout rlDisc;
    private Intent mServiceIntent;
    private MusicService.MyBinder mBinder;
    private boolean isBinding;

    public void setMusicModel(MusicModel musicModel) {
        this.musicModel = musicModel;

        setImage();
    }

    private MusicModel musicModel;

    private Animation discAnim, playMusicAnim, stopMusicAnim;
    private boolean isPlaying;  //播放状态true:正在播放   false:停止、暂停播放

    public PlayMusicView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public PlayMusicView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public PlayMusicView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PlayMusicView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context context) {
        this.context = context;
        mView = LayoutInflater.from(context).inflate(R.layout.play_music_view, this, false);
        ivIcon = mView.findViewById(R.id.iv_icon);
        ivPlay = mView.findViewById(R.id.is_play);
        rlDisc = mView.findViewById(R.id.rl_disc);
        ivNeedle = mView.findViewById(R.id.iv_needle);
        loadAnim();

        //给光盘设置点击事件
        rlDisc.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePlayState();
            }
        });
        addView(mView);
    }

    public void setImage() {
        Glide.with(context)
                .load(musicModel.getPoster())
                .apply(bitmapTransform(
                        new CropCircleWithBorderTransformation(Utils.toDp(4), Color.rgb(0, 145, 86))))
                .into(ivIcon);
    }

    /**
     * 加载动画：
     * 1.discAnim:光盘转动的动画
     * 2.playMusicAnim:播放音乐时的指针动画
     * 3.stopMusicAnim:暂停、停止音乐时的指针动画
     */
    private void loadAnim() {
        discAnim = AnimationUtils.loadAnimation(context, R.anim.disc_turn);
        playMusicAnim = AnimationUtils.loadAnimation(context, R.anim.play_music_needle);
        stopMusicAnim = AnimationUtils.loadAnimation(context, R.anim.stop_music_needle);
    }

    /**
     * 播放音乐时候应该切换的动画：1.隐藏ivPlay图片2.光盘开始转动3.指针指向光盘4.播放状态变为true
     */
    public void playMusic() {
        isPlaying = true;
        ivPlay.setVisibility(View.GONE);
        rlDisc.startAnimation(discAnim);
        ivNeedle.startAnimation(playMusicAnim);

        startMusicServices();
    }

    /**
     * 暂停、停止音乐时候应该切换的动画：1.显示ivPlay图片2.光盘停止转动3.指针移开光盘4.播放状态变为false
     */
    public void stopMusic() {
        isPlaying = false;
        ivPlay.setVisibility(View.VISIBLE);
        rlDisc.clearAnimation();
        ivNeedle.startAnimation(stopMusicAnim);

        mBinder.pauseMusic();
    }

    /**
     * 切换音乐播放状态
     */
    public void togglePlayState() {
        if (isPlaying) {
            stopMusic();
        } else {
            playMusic();
        }
    }

    public void startMusicServices() {
        if (mServiceIntent == null) {
            mServiceIntent = new Intent(context, MusicService.class);
            context.startService(mServiceIntent);
        } else {
            mBinder.startMusic();
        }


        //如果没有绑定服务，则绑定
        if (!isBinding) {
            isBinding = true;
            context.bindService(mServiceIntent, conn, Context.BIND_AUTO_CREATE);
        }
    }

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = (MusicService.MyBinder) service;
            mBinder.setMusic(musicModel);
            mBinder.startMusic();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    public void destory() {
        if (isBinding) {
            isBinding = false;
            context.unbindService(conn);
        }
    }

}
