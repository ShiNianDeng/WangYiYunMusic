package com.example.lxc.wangyiyunmusic.helps;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.IOException;

public class MyMediaPlayerHelp extends MediaPlayer {

    private Context context;

    public String getCurrentPlayPath() {
        return currentPlayPath;
    }

    public void setCurrentPlayPath(String currentPlayPath) {
        this.currentPlayPath = currentPlayPath;
    }

    private String currentPlayPath;
    private static MyMediaPlayerHelp instance;
    private OnMyMediaPlayerHelperPreparedListener onMyMediaPlayerHelperPreparedListener;

    public void setOnMyMediaPlayerHelperPreparedListener(OnMyMediaPlayerHelperPreparedListener onMyMediaPlayerHelperPreparedListener) {
        this.onMyMediaPlayerHelperPreparedListener = onMyMediaPlayerHelperPreparedListener;
    }

    /**
     * 单例模式:
     * 1.私有构造方法
     * 2.声明这个类的实例
     * 3.构建一个返回这个类实例的静态方法
     */
    private MyMediaPlayerHelp(Context context) {
        this.context = context;
    }

    public static MyMediaPlayerHelp getInstance(Context context) {
        if (instance == null) {
            synchronized (MyMediaPlayerHelp.class) {
                if (instance == null) {
                    instance = new MyMediaPlayerHelp(context);
                }
            }
        }
        return instance;
    }


    /**
     * 设置播放音乐的路径
     * 1.如果音乐正在播放,或者需要播放的音乐和正在播放的音乐路径不一致，则重置播放的状态
     * 2.设置播放路径
     * 3.准备播放
     */
    public void setPath(String needPlayPath) {
        //1.如果音乐正在播放,或者需要播放的音乐和正在播放的音乐路径不一致，则重置播放的状态
        if (currentPlayPath == null || isPlaying() || !currentPlayPath.equals(needPlayPath)) {
            reset();
        }

        currentPlayPath = needPlayPath;
        try {
            //2.设置播放路径
            setDataSource(context, Uri.parse(needPlayPath));

        } catch (IOException e) {
            e.printStackTrace();
        }
        //3.准备播放
        prepareAsync();
        //注册一个监听者，当媒体源准备好的时候回调
        setOnPreparedListener(new OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                if (onMyMediaPlayerHelperPreparedListener != null) {
                    onMyMediaPlayerHelperPreparedListener.onPrepared(mp);
                }
            }
        });

        //注册一个监听者，当音乐播放完成后回调
        setOnCompletionListener(new OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if(onMyMediaPlayerHelperPreparedListener != null){
                    onMyMediaPlayerHelperPreparedListener.onCompletion(mp);
                }
            }
        });
    }

    /**
     * 播放音乐
     */
    public void playMusic() {
        if (isPlaying()) return;
        start();
    }

    /**
     * 暂停音乐
     */
    public void pauseMusic() {
        pause();
    }

    public interface OnMyMediaPlayerHelperPreparedListener {
        void onPrepared(MediaPlayer mp);
        void onCompletion(MediaPlayer mp);
    }
}
